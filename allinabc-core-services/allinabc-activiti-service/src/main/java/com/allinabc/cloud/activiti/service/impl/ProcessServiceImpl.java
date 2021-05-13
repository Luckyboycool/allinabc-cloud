package com.allinabc.cloud.activiti.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.mapper.NodeDecisionMapper;
import com.allinabc.cloud.activiti.mapper.ProcessIdMapper;
import com.allinabc.cloud.activiti.pojo.bo.AuditorModel;
import com.allinabc.cloud.activiti.pojo.consts.ActivitiConstant;
import com.allinabc.cloud.activiti.pojo.params.AuditorParam;
import com.allinabc.cloud.activiti.pojo.params.DecisionParam;
import com.allinabc.cloud.activiti.pojo.po.NodeAuditor;
import com.allinabc.cloud.activiti.pojo.po.ProcessId;
import com.allinabc.cloud.activiti.pojo.vo.DecisionVO;
import com.allinabc.cloud.activiti.pojo.vo.NodeModel;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.service.IProcessService;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 13:56
 **/
@Service
@Slf4j
public class ProcessServiceImpl implements IProcessService {

    @Resource
    private ProcessIdMapper processIdMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Resource
    private NodeAuditorMapper nodeAuditorMapper;

    @Resource
    private NodeDecisionMapper nodeDecisionMapper;

    @Autowired
    private TaskService taskService;


    @Override
    public ProcessIdModel findProcessIdByFormType(String formType) {
        ConcurrentMap<String, Object> map = Maps.newConcurrentMap();
        map.put("FORM_TYPE", formType);
        List<ProcessId> re = processIdMapper.selectByMap(map);
        if (re != null && re.size() > 0) {
            ProcessIdModel processIdVO = BeanUtil.copyProperties(re.get(0), ProcessIdModel.class);
            return processIdVO;
        }
        return null;
    }

    @Override
    public List<NodeModel> findUserTaskNodes(String processDefinitionkey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionkey).latestVersion().singleResult();
        BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());
        List<NodeModel> nodeList = Lists.newArrayList();
        Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
        for (FlowElement element : flowElements) {
            NodeModel node = new NodeModel();
            node.setNodeId(element.getId());
            node.setNodeName(element.getName());
            if (element instanceof StartEvent) {
                // 开始节点
                node.setNodeType(ActivitiConstant.NODE_TYPE_START);
            } else if (element instanceof UserTask) {
                // 用户任务
                node.setNodeType(ActivitiConstant.NODE_TYPE_TASK);
            } else if (element instanceof EndEvent) {
                // 结束
                node.setNodeType(ActivitiConstant.NODE_TYPE_END);
            } else {
                // 排除其他连线或节点
                continue;
            }
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNodeAuditor(AuditorParam auditorParam) {
        List<AuditorModel> auditorModels = auditorParam.getAuditorModels();
        List<NodeAuditor> list = Lists.newArrayList();
        auditorModels.forEach(i -> {
            NodeAuditor nodeAuditor = new NodeAuditor(auditorParam.getNodeId(), auditorParam.getProcessId(), i.getAuditorType(), i.getAuditorKey());
            nodeAuditor.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            list.add(nodeAuditor);
        });

        int result = nodeAuditorMapper.saveNodeAuditor(list);
        if (result != list.size()) {
            throw new BusinessException("设置审批人失败");
        }
    }

    @Override
    public List<DecisionVO> findNodeDecision(DecisionParam decisionParam) {
        Task currentTask = taskService.createTaskQuery().taskId(decisionParam.getTaskId()).processInstanceId(decisionParam.getProcInstId()).singleResult();
        if(currentTask==null){
            return null;
        }else {
            DelegationState delegationState = currentTask.getDelegationState();
            if(null != delegationState && delegationState.toString().equals("PENDING")){
                //委托的任务，直接返回
                log.info("加签任务，统一设置为公共字段");
                decisionParam.setNodeId("common");
                decisionParam.setProcessId("common");
            }
            List<DecisionVO> ls = nodeDecisionMapper.selectNodeDecison(decisionParam);

            //判断是否为MT流程的JDV节点，展示驳回、加签、转办
            if (StringUtils.isNotEmpty(decisionParam.getNodeId())) {
                if ("ifab_mt_jdv".equals(decisionParam.getNodeId())) {
                    ls = ls.stream().filter(decisionVO -> !"核可".equals(decisionVO.getDecisionNameCn())).collect(Collectors.toList());
                    return ls;
                }
            }
            return ls;

        }

    }


}

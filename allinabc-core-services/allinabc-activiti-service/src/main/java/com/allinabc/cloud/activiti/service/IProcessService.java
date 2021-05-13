package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.params.AssignParam;
import com.allinabc.cloud.activiti.pojo.params.AuditorParam;
import com.allinabc.cloud.activiti.pojo.params.DecisionParam;
import com.allinabc.cloud.activiti.pojo.vo.DecisionVO;
import com.allinabc.cloud.activiti.pojo.vo.NodeModel;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 13:55
 **/
public interface IProcessService {

    /**
     * 根据表单类型查询流程定义ID
     * @param formType
     * @return
     */
    ProcessIdModel findProcessIdByFormType(String formType);

    /**
     * 获取所有用户节点信息
     * @return
     */
    List<NodeModel> findUserTaskNodes(String processDefinitionkey);

    /**
     * 配置节点审批人
     * @param auditorParam
     */
    void saveNodeAuditor(AuditorParam auditorParam);

    /**
     * 获取流程节点可决策类型
     * @param decisionParam
     * @return
     */
    List<DecisionVO> findNodeDecision(DecisionParam decisionParam);


}

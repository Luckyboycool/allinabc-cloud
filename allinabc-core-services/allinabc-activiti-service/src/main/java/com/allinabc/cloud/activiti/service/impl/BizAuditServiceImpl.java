package com.allinabc.cloud.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.mapper.BizAuditMapper;
import com.allinabc.cloud.activiti.pojo.bo.CommentBO;
import com.allinabc.cloud.activiti.pojo.consts.ActionTypeConstant;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.po.BizAudit;
import com.allinabc.cloud.activiti.pojo.vo.BizAuditVO;
import com.allinabc.cloud.activiti.service.BizAuditService;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/19 10:19
 **/
@Service
@Slf4j
public class BizAuditServiceImpl implements BizAuditService {

    @Resource
    private BizAuditMapper bizAuditMapper;

    @Autowired
    private TaskService taskService;

    /**
     * 添加业务评论
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, String actionKey, Task currentTask, String transferredUser, String principalUser, Map<String, Object> variables) {
        CommentBO commentBO = null;
        Authentication.setAuthenticatedUserId(processExecuteParam.getUserId());
        //basicInfoId
        String basicInfoId = (String) variables.get("basicInfoId");
        if(StringUtils.isEmpty(basicInfoId)){
            throw new BusinessException("添加评论时，获取的basicInfoId为空");
        }

        if(StringUtils.isEmpty(processExecuteParam.getComment())){
            //如果评论为空
             commentBO = getCommentBO(actionKey, null,transferredUser,principalUser,basicInfoId);
        }else{
            //如果评论不为空
             commentBO = getCommentBO(actionKey, processExecuteParam.getComment(),transferredUser,principalUser,basicInfoId);
        }
        //添加activiti的评论
        taskService.addComment(processExecuteParam.getTaskId(), processExecuteParam.getInstanceId(), JSON.toJSONString(commentBO));
        //审核记录表记录数据
        BizAudit bizAudit = new BizAudit();
        //设置任务ID
        bizAudit.setTaskId(processExecuteParam.getTaskId());
        bizAudit.setTaskDefKey(currentTask.getTaskDefinitionKey());
        //实例ID
        bizAudit.setProcInstId(processExecuteParam.getInstanceId());
        bizAudit.setProcDefKey(processInstance.getProcessDefinitionKey());
        //审核动作key
        bizAudit.setAction(actionKey);
        //评论
        if(!StringUtils.isEmpty(processExecuteParam.getComment())){
            bizAudit.setDecision(processExecuteParam.getComment());
        }
        //申请人
        bizAudit.setApplyer(processInstance.getStartUserId());
        //审核人
        bizAudit.setAuditor(commentBO.getUserId());
        //被转办人
        if(!StringUtils.isEmpty(transferredUser)){
            bizAudit.setTransferredUser(transferredUser);
        }
        //被委托人
        if(!StringUtils.isEmpty(principalUser)){
            bizAudit.setPrincipalUser(principalUser);
        }
        //用戶类型
        bizAudit.setUserType(commentBO.getAccountType());
        //创建时间
        bizAudit.setCreateTm(new Date());

        bizAudit.setBasicInfoId(basicInfoId);
        log.info("审核记录入库参数="+JSON.toJSONString(bizAudit));
        if(bizAuditMapper.insert(bizAudit)!=1){
            throw new RuntimeException("插入审核记录数据失败");
        }
        log.info("添加评论结束");
    }

    @Override
    public List<BizAuditVO> selectTaskCommentByProcessId(String processInstId) {
        List<BizAuditVO> ls = bizAuditMapper.selectTaskCommentByProcessId(processInstId);
        return ls;
    }

    @Override
    public BizAudit selectByCommentId(String id) {
        return bizAuditMapper.selectById(id);
    }


    /**
     * 获取commentBO
     * @return
     */
    public CommentBO getCommentBO(String actionType,String decision,String transferredUser,String principalUser,String basicInfoId){
        User currentUser = CurrentUserUtil.getCurrentUser();
        CommentBO commentBO = new CommentBO();
        commentBO.setAccountType(currentUser.getAccountType());
        commentBO.setDecision(decision);
        commentBO.setAction(actionType);
        if(!StringUtils.isEmpty(transferredUser)){
            commentBO.setTransferredUser(transferredUser);
        }
        if(!StringUtils.isEmpty(principalUser)){
            commentBO.setPrincipalUser(principalUser);
        }
        commentBO.setUserId(currentUser.getId());
        commentBO.setBasicInfoId(basicInfoId);
        return commentBO;
    }



}

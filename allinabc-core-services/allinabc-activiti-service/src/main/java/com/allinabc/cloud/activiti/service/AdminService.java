package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ActivateProcessParam;
import com.allinabc.cloud.activiti.pojo.params.AssignParam;
import com.allinabc.cloud.activiti.pojo.params.NodeJumpParam;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/9 10:44
 **/
public interface AdminService {

    /**
     * 据节点指派给某个人任务(为没有节点审批人的节点指派人【可选择多个人】
     *
     * @param assignParam
     */
    void assignNodeAuditor(AssignParam assignParam);

    /**
     * 指定跳转到某节点(节点：为指定节点ID)
     *
     * @param nodeJumpParam
     */
    void nodeJump(NodeJumpParam nodeJumpParam);

    /**
     * 重新启动一个流程(流程死掉此种情况，重新根据审批历史将新流程设置到之前流转的节点)
     *
     * @param activateProcessParam
     */
    void restartNewProcessInst(ActivateProcessParam activateProcessParam);

    /**
     * 取消流程
     *
     * @param cancleParam
     */
    void cancleProcess(TaskParam cancleParam);

    /**
     * 结束流程
     *
     * @param taskParam
     */
    void processEnd(TaskParam taskParam);

    /**
     * 保存日志
     * @param action
     * @param remark
     * @param basicInfoId
     * @param procDefKey
     * @param procInstId
     * @param taskDefKey
     * @param taskId
     * @param targetTaskDefKey
     * @param auditors
     */
    void saveBizProcessOperLog(String action, String remark, String basicInfoId, String procDefKey, String procInstId, String taskDefKey, String taskId, String targetTaskDefKey, String auditors,String newProcInstId);

}

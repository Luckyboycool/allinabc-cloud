package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.po.BizAudit;
import com.allinabc.cloud.activiti.pojo.vo.BizAuditVO;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/19 10:18
 **/
public interface BizAuditService {

    /**
     * 添加业务评论
     */
    void addComment(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, String action, Task currentTask, String transferredUser, String principalUser, Map<String, Object> variables);

    /**
     * 根据流程ID查询流转历史
     * @param processInstId
     * @return
     */
    List<BizAuditVO> selectTaskCommentByProcessId(String processInstId);

    /**
     * 根据流程ID查询流转历史
     * @param id
     * @return
     */
    BizAudit selectByCommentId(String id);

}

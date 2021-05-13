package com.allinabc.cloud.activiti.pojo.vo;

import com.allinabc.cloud.activiti.pojo.bo.AuditorBO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 15:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel implements Serializable {

    private static final long serialVersionUID = 307510731480458279L;

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程实例ID
     */
    private String instanceId;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 流程节点key
     */
    private String taskDefinitionKey;

    /**
     * 任务Id
     */
    private String taskId;

    /**
     * 流程节点名称
     */
    private String taskName;

    /**
     * 流程发起人
     */
    private String requester;

    /**
     * 申请人姓名
     */
    private String requesterName;

    /**
     * 申请人姓名+工号
     */
    private String nameAndJobNumber;


    /**
     * 处理人
     */
    private String assignee;

    /**
     * 申请单号
     */
    private String requestNo;

    /**
     * 版本
     */
    private String version;

    /**
     * 主题
     */
    private String subject;

    /**
     * 审核人
     */
    private List<AuditorBO> auditors;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 申请时间
     * --------------
     * @modify simon
     * @date 2021-04-23
     * @description 申请时间 数据库保存的是正确时间，不用加时区
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date requestTime;

    private String formName;


    /**
     * 批注
     */
    private List<CommentModel> comment;

    public TaskModel(String taskId,String taskName,String assignee,String taskDefinitionKey){
        this.taskId= taskId;
        this.taskName =taskName;
        this.assignee= assignee;
        this.taskDefinitionKey =taskDefinitionKey;
    }



}

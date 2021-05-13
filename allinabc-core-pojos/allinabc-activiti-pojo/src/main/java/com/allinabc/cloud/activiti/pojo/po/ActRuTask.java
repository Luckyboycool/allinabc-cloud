package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运行时任务数据表 ACT_RU_TASK
 * （执行中实时任务）代办任务查询表
 */
@Data
@TableName(value = "ACT_RU_TASK")
public class ActRuTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID_
     */
    @TableId(value = "ID_", type = IdType.AUTO)
    private String            id;

    /**
     * 版本号, 乐观锁
     */
    @TableField(value = "REV_")
    private Integer           rev;

    /**
     * 实例id（外键EXECUTION_ID_）执行实例ID
     */
    @TableField(value = "EXECUTION_ID_")
    private String            executionId;

    /**
     * 流程实例ID（外键PROC_INST_ID_）
     */
    @TableField(value = "PROC_INST_ID_")
    private String            procInstId;

    /**
     * 流程定义ID
     */
    @TableField(value = "PROC_DEF_ID_")
    private String            procDefId;

    /**
     * 任务名称 节点定义名称
     */
    @TableField(value = "NAME_")
    private String            name;

    /**
     * 父节任务ID
     */
    @TableField(value = "PARENT_TASK_ID_")
    private String            parentTaskId;

    /**
     * 任务描述 节点定义描述
     */
    @TableField(value = "DESCRIPTION_")
    private String            description;

    /**
     * 任务定义key 任务定义的ID
     */
    @TableField(value = "TASK_DEF_KEY_")
    private String            taskDefKey;

    /**
     * 所属人(老板) 拥有者（一般情况下为空，只有在委托时才有值）
     */
    @TableField(value = "OWNER_")
    private String            owner;

    /**
     * 代理人员 签收人或委托人(受让人)
     */
    @TableField(value = "ASSIGNEE_")
    private String            assignee;

    /**
     * 代理团 委托类型，DelegationState分为两种：PENDING，RESOLVED。如无委托则为空
     */
    @TableField(value = "DELEGATION_")
    private String            delegation;

    /**
     * 优先权 优先级别，默认为：50
     */
    @TableField(value = "PRIORITY_")
    private Integer           priority;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              createTime;

    /**
     * 执行时间
     */
    @TableField(value = "DUE_DATE_")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date              dueDate;

    /**
     * 暂停状态 1代表激活 2代表挂起
     */
    @TableField(value = "SUSPENSION_STATE_")
    private Integer           suspensionState;

    private String            category;
    private String            tenantId;
    private String            formKey;

    public void setId(String id)
    {
        this.id = id == null ? null : id.trim();
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId == null ? null : executionId.trim();
    }

    public void setProcInstId(String procInstId)
    {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public void setProcDefId(String procDefId)
    {
        this.procDefId = procDefId == null ? null : procDefId.trim();
    }

    public void setName(String name)
    {
        this.name = name == null ? null : name.trim();
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId == null ? null : parentTaskId.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setTaskDefKey(String taskDefKey)
    {
        this.taskDefKey = taskDefKey == null ? null : taskDefKey.trim();
    }

    public void setOwner(String owner)
    {
        this.owner = owner == null ? null : owner.trim();
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee == null ? null : assignee.trim();
    }

    public void setDelegation(String delegation)
    {
        this.delegation = delegation == null ? null : delegation.trim();
    }

    public void setCategory(String category)
    {
        this.category = category == null ? null : category.trim();
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public void setFormKey(String formKey)
    {
        this.formKey = formKey == null ? null : formKey.trim();
    }

}
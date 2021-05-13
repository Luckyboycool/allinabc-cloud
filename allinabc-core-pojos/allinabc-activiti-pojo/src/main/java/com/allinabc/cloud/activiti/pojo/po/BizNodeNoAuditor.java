
package com.allinabc.cloud.activiti.pojo.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "BIZ_NODE_NO_AUDIT",schema = "ACTDA1")
@NoArgsConstructor
public class BizNodeNoAuditor implements Serializable{

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String  id;

    @ApiModelProperty(value = "任务编号")
    @TableField("TASK_ID")
    private String taskId;

    @ApiModelProperty(value = "任务定义key")
    @TableField("TASK_DEF_KEY")
    private String taskDefKey;

    @ApiModelProperty(value = "流程定义key")
    @TableField("PROC_DEF_KEY")
    private String procDefKey;

    @ApiModelProperty(value = "流程实例ID")
    @TableField("PROC_INST_ID")
    private String procInstId;

    @ApiModelProperty(value = "申请人")
    @TableField("REQUESTER")
    private String requester;

    @ApiModelProperty(value = "是否可用(Y:可用，N：不可用  默认可用)")
    @TableField("IS_AVAILABLE")
    private String isAvailable;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATED_BY")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TM")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTm;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATED_BY")
    private String updatedBy;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TM")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;

    public BizNodeNoAuditor(String taskId, String taskDefKey, String procDefKey, String procInstId, String requester,  String createdBy, Date createTm) {
        this.taskId = taskId;
        this.taskDefKey = taskDefKey;
        this.procDefKey = procDefKey;
        this.procInstId = procInstId;
        this.requester = requester;
        this.createdBy = createdBy;
        this.createTm = createTm;
    }
}

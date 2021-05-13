package com.allinabc.cloud.starter.form.domain;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmBasicForm对象", description="")
@TableName(value = "ADM_BASIC_FORM", schema = "ADMINDA1")
public class BasicForm extends BasicInfo {

    @ApiModelProperty(value = "父表单ID")
    @TableField("PID")
    private String pid;

    @ApiModelProperty(value = "版本")
    @TableField("VERSION")
    private String version;

    @ApiModelProperty(value = "主题")
    @TableField("SUBJECT")
    private String subject;

    @ApiModelProperty(value = "申请单号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "程序代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "表单类型")
    @TableField("FORM_TYPE")
    private String formType;

    @ApiModelProperty(value = "流程ID")
    @TableField("PROCESS_ID")
    private String processId;

    @ApiModelProperty(value = "实例ID")
    @TableField("INSTANCE_ID")
    private String instanceId;

    @ApiModelProperty(value = "撰稿人")
    @TableField("DRAFTER")
    private String drafter;

    @ApiModelProperty(value = "申请人")
    @TableField("REQUESTER")
    private String requester;

    @ApiModelProperty(value = "申请时间")
    @TableField("REQUEST_TIME")
    private Date requestTime;

    @ApiModelProperty(value = "是否为草稿")
    @TableField("IS_DRAFT")
    private String isDraft;

    @ApiModelProperty(value = "表单状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "主表单id")
    @TableField("MAIN_ID")
    private String mainId;

    @ApiModelProperty(value = "申请人类型")
    @TableField("USER_TYPE")
    private String userType;

}

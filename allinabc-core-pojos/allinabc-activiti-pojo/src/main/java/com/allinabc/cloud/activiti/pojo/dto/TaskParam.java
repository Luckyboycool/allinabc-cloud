package com.allinabc.cloud.activiti.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 19:31
 **/
@Data
public class TaskParam extends BaseQueryParam {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "被转办人ID")
    private String transUserId;

    @ApiModelProperty(value = "被委派人ID")
    private String delegateUserId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "流程实例ID")
    private String processInstId;

    @ApiModelProperty(value = "流程定义key")
    private String processDefKey;

    @ApiModelProperty(value = "待办或已办")
    private String taskType;

    @ApiModelProperty(value = "表单ID")
    private String basicInfoId;

    @ApiModelProperty("删除原因")
    private String reason;

    @ApiModelProperty("申请人姓名")
    private String  requesterName;

    @ApiModelProperty("申请时间")
   // @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private String requestTm;

    @ApiModelProperty("申请开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date requestStartTm;

    @ApiModelProperty("申请结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date requestEndTm;

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("申请单编号")
    private String requestNo;

    @ApiModelProperty("是否模糊查询")
    private Boolean isFuzzyQuery;

    @ApiModelProperty(value = "表单类型")
    private String formType;
}

package com.allinabc.cloud.starter.form.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description BasicFormVO对象
 * @Author wangtaifeng
 * @Date 2021/3/5 9:46
 **/
@Data
public class BasicFormVO implements Serializable {

    private static final long serialVersionUID = 4435780670623342995L;

    @ApiModelProperty(value = "主键")
    private String  id;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTm;

    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;

    @ApiModelProperty(value = "父表单ID")
    private String pid;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "主题")
    private String subject;

    @ApiModelProperty(value = "申请单号")
    private String requestNo;

    @ApiModelProperty(value = "程序代码")
    private String appCode;

    @ApiModelProperty(value = "表单类型")
    private String formType;

    @ApiModelProperty(value = "流程ID")
    private String processId;

    @ApiModelProperty(value = "实例ID")
    private String instanceId;

    @ApiModelProperty(value = "撰稿人")
    private String drafter;

    @ApiModelProperty(value = "申请人")
    private String requester;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date requestTime;

    @ApiModelProperty(value = "是否为草稿")
    private String isDraft;

    @ApiModelProperty(value = "表单状态")
    private String status;

    @ApiModelProperty(value = "主表单id")
    private String mainId;

    @ApiModelProperty(value = "申请人类型")
    private String userType;

    @ApiModelProperty(value = "申请人姓名")
    private String requesterName;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "姓名和工号")
    private String nameAndJobNumber;

    @ApiModelProperty(value = "表单状态中文")
    private String statusCN;

    @ApiModelProperty(value = "表单状态英文")
    private String statusEN;

    @ApiModelProperty(value = "请求时间没有时间秒")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date requestDate;

    /**
     * 表单名称(页面显示)
     */
    private String formName;

}

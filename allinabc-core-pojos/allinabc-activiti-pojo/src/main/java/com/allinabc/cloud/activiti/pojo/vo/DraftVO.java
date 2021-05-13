package com.allinabc.cloud.activiti.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 17:11
 **/
@Data
public class DraftVO implements Serializable {

    private static final long serialVersionUID = -1548575739468092731L;

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

    /**
     * 主键
     */
    private String  id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTm;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;

    private String userType;

    private String mainId;

    private String name;

    /**
     * 姓名和工号
     */
    private String nameAndJobNumber;

    private String jobNumber;

    private String formName;

}

package com.allinabc.cloud.starter.form.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 10:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicFormParam extends CommonBasicParam {

    @ApiModelProperty(value = "父表单ID")
    private String pid;

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

    @ApiModelProperty(value = "是否为草稿")
    private String isDraft;

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

    @ApiModelProperty(value = "主表单id")
    private String mainId;

}

package com.allinabc.cloud.attach.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/19 10:19
 **/
@Data
public class AttachmentQueryParam implements Serializable {

    @ApiModelProperty(value = "附件主键ID")
    private String attachmentId;

    @ApiModelProperty(value = "业务类型字段")
    private String bizType;

    @ApiModelProperty(value = "业务主键Id")
    private String bizId;

    @ApiModelProperty(value = "逻辑状态 (常量值：Y-逻辑保留，N-逻辑删除，默认值：Y)")
    private String isAvailable;

}

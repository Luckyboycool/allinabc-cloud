package com.allinabc.cloud.attach.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/18 14:48
 **/
@Data
public class AttachmentRequest implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String attachmentId;

    @ApiModelProperty(value = "web_system.sys_application.app_code")
    private String sysCode;

    @ApiModelProperty(value = "web_system.sys_resource.RES_KEY")
    private String resCode;

    @ApiModelProperty(value = "业务类型字段")
    private String bizType;

}

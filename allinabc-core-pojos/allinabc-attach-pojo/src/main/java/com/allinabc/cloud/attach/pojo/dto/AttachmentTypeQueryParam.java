package com.allinabc.cloud.attach.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/18 15:34
 **/
@Data
public class AttachmentTypeQueryParam {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "web_system.sys_application.app_code")
    private String sysCode;

    @ApiModelProperty(value = "web_system.sys_resource.RES_KEY")
    private String resCode;

    @ApiModelProperty(value = "业务类型字段")
    private String bizType;
}

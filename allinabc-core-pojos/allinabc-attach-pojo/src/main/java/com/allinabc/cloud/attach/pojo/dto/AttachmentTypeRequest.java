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
public class AttachmentTypeRequest implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "web_system.sys_application.app_code")
    private String sysCode;

    @ApiModelProperty(value = "web_system.sys_resource.RES_KEY")
    private String resCode;

    @ApiModelProperty(value = "业务类型字段")
    private String bizType;

    @ApiModelProperty(value = "存储类型(关联web_system.sys_dict_data.dict_value")
    private String storageCode;

    @ApiModelProperty(value = "附件类型(0-通用模板, 1-通用附件，2-上传待处理)")
    private String uploadType;

    @ApiModelProperty(value = "上传前调用")
    private String callbackBefore;

    @ApiModelProperty(value = "上传后调用")
    private String callbackAfter;

    @ApiModelProperty(value = "备注")
    private String remark;
}

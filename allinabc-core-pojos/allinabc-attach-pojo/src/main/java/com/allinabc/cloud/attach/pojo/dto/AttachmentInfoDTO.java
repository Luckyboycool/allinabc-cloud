package com.allinabc.cloud.attach.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/17 19:25
 **/
@Data
public class AttachmentInfoDTO implements Serializable {

    @ApiModelProperty(value = "web_system.sys_application.app_code")
    private String sysCode;

    @ApiModelProperty(value = "web_system.sys_resource.RES_KEY")
    private String resCode;

    @ApiModelProperty(value = "存储类型(关联web_system.sys_dict_data.dict_value")
    private String storageCode;

    @ApiModelProperty(value = "附件类型(0-通用模板, 1-通用附件，2-上传待处理)")
    private String uploadType;

    @ApiModelProperty(value = "上传前调用")
    private String callbackBefore;

    @ApiModelProperty(value = "上传后调用")
    private String callbackAfter;

    @ApiModelProperty(value = "主键ID")
    private String attachmentId;

    @ApiModelProperty(value = "关联业务主键ID")
    private String bizId;

    @ApiModelProperty(value = "关联att_attachment_type.biz_type")
    private String bizType;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件原始名称")
    private String originalFileName;

    @ApiModelProperty(value = "文件大小")
    private BigDecimal fileSize;

    @ApiModelProperty(value = "文件url，相对位置")
    private String fileUrl;

    @ApiModelProperty(value = "逻辑状态 (常量值：Y-逻辑保留，N-逻辑删除，默认值：Y)")
    private String isAvailable;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTm;

    @ApiModelProperty(value = "修改人")
    private String updatedBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTm;

    @ApiModelProperty(value = "文件根路径")
    private String fileRootName;
}

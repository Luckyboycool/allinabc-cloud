package com.allinabc.cloud.attach.pojo.dto;

import com.allinabc.cloud.attach.pojo.po.Attachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/25 16:16
 **/
@Data
public class ESSignatureInfo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "signature_name")
    private String signatureName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑状态 (常量值：Y-逻辑保留，N-逻辑删除，默认值：Y)")
    private String isAvailable;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTm;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTm;

    @ApiModelProperty(value = "修改人")
    private String updatedBy;

    private List<Attachment> attachments;
}

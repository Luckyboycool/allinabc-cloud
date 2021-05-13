package com.allinabc.cloud.attach.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 9:35
 **/
@Data
public class AttachmentParam implements Serializable {

    private static final long serialVersionUID = -6490112122127005972L;

    @ApiModelProperty(value = "附件主键信息")
    private String id;

    @ApiModelProperty(value = "业务主键")
    private String bizId;
}

package com.allinabc.cloud.notice.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/20 13:37
 **/
@Data
public class ModifyNoticeTemplateDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板内容是否是html")
    @NotNull(message = "isHtml can not be null")
    private Boolean isHtml;

    @ApiModelProperty(value = "模板内容")
    @NotBlank(message = "content can not be null")
    private String content;

    @ApiModelProperty(value = "模板类型(0:邮件1：短信，2：站内信)")
    @NotBlank(message = "type can not be null")
    private String type;

    @ApiModelProperty(value = "模板主题")
    private String subject;

    @ApiModelProperty(value = "模板用途")
    private String noticeTypeCode;

}

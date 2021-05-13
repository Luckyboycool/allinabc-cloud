package com.allinabc.cloud.notice.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/20 14:33
 **/
@Data
public class NoticeTemplateVO implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板内容是否是html")
    private Boolean isHtml;

    @ApiModelProperty(value = "模板类型(0:邮件1：短信，2：站内信)")
    private String type;
}

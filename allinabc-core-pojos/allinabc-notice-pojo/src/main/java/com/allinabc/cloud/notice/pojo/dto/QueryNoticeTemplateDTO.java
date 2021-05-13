package com.allinabc.cloud.notice.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/20 13:37
 **/
@Data
public class QueryNoticeTemplateDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板内容是否是html")
    private Boolean isHtml;

    @ApiModelProperty(value = "模板类型(0:邮件1：短信，2：站内信)")
    private String type;

    @ApiModelProperty(value = "逻辑状态 (常量值：Y-逻辑保留，N-逻辑删除，默认值：Y)")
    private String isAvailable;
}

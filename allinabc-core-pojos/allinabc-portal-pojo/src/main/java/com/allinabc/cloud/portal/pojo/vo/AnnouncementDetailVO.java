package com.allinabc.cloud.portal.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Simon.Xue
 * @date 2/25/21 4:01 PM
 **/
@Data
public class AnnouncementDetailVO implements Serializable {
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 类型
     * 重要通知(1)/一般通知(0)
     */
    @ApiModelProperty(value = "类型 重要通知 1, 一般通知 0")
    private String type;

    /**
     * 发布类型
     * 即时(1)/定时(0)
     */
    @ApiModelProperty(value = "发布类型  即时 1, 定时 0")
    private String timeType;
    /**
     * 发布日期
     * 当“发布类型”为定时时展示，展示时控制必填
     */
    @ApiModelProperty(value = "发布日期", notes = "当“发布类型”为定时时展示，展示时控制必填")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date sendDate;

    /**
     * 有效期至
     * 为空默认永久有效
     */
    @ApiModelProperty(value = "有效期至", notes = "为空默认永久有效")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endDate;

    /**
     * 发布对象
     * All(0)/内部人员(1)/外部人员(2)
     */
    @ApiModelProperty(value = "发布对象 All(0)/内部人员(1)/外部人员(2)")
    private String receivePerson;

    /**
     * 发布内容
     */
    @ApiModelProperty(value = "发布内容")
    private String content;
}

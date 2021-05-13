package com.allinabc.cloud.portal.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Simon.Xue
 * @date 2/25/21 3:57 PM
 **/
@Data
public class AnnouncementUpdateDTO implements Serializable {

    @NotBlank(message = "ID不能为空")
    private String id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 类型
     * 重要通知(1)/一般通知(0)
     */
    @ApiModelProperty(value = "类型 重要通知 1, 一般通知 0")
    @NotBlank(message = "类型不能为空")
    private String type;

    /**
     * 发布类型
     * 即时(1)/定时(0)
     */
    @ApiModelProperty(value = "发布类型  即时 1, 定时 0")
    @NotBlank(message = "发布类型不能为空")
    private String timeType;
    /**
     * 发布日期
     * 当“发布类型”为定时时展示，展示时控制必填
     */
    @ApiModelProperty(value = "发布日期", notes = "当“发布类型”为定时时展示，展示时控制必填")
    private Date sendDate;

    /**
     * 有效期至
     * 为空默认永久有效
     */
    @ApiModelProperty(value = "有效期至", notes = "为空默认永久有效")
    private Date endDate;

    /**
     * 发布对象
     * All(0)/内部人员(1)/外部人员(2)
     */
    @ApiModelProperty(value = "发布对象 All(0)/内部人员(1)/外部人员(2)")
    @NotBlank(message = "发布对象不能为空")
    private String receivePerson;

    /**
     * 发布内容
     */
    @ApiModelProperty(value = "发布内容")
    @NotBlank(message = "发布内容不能为空")
    private String content;
}

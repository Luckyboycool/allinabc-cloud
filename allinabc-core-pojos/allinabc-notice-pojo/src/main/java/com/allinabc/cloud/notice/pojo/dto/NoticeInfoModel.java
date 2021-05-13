package com.allinabc.cloud.notice.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/9 11:11
 **/
@Data
public class NoticeInfoModel {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "关联nte_notice_code表的notice_type_code")
    private String noticeTypeCode;

    @ApiModelProperty(value = "发送类型0-邮件 1-短信")
    private String sendType;

    @ApiModelProperty(value = "通知主题")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否是html")
    private Boolean isHtml;

    @ApiModelProperty(value = "发送方")
    private String noticeFrom;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "发送状态(0-未发送；1-发送成功；2-发送失败)")
    private String status;

    @ApiModelProperty(value = "通知方式：0-及时；1-定时")
    private String noticeWay;

    @ApiModelProperty(value = "是否需要重试")
    private String needRetry;

    @ApiModelProperty(value = "重试次数")
    private Integer retryCount;

    private List<NoticeParticipantModel> noticeParticipantModels;
}

package com.allinabc.cloud.notice.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通知信息内容表
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-07
 */
@Data
@ApiModel(value="NteNoticeInfo对象", description="通知信息内容表")
@TableName(value = "NTE_NOTICE_INFO")
public class NoticeInfo implements Serializable {


    private static final long serialVersionUID = 709049023222482117L;

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
    private Date sendTime;

    @ApiModelProperty(value = "发送状态(0-未发送；1-发送成功；2-发送失败)")
    private String status;

    @ApiModelProperty(value = "通知方式：0-及时；1-定时")
    private String noticeWay;

    @ApiModelProperty(value = "是否需要重试")
    private Boolean needRetry;

    @ApiModelProperty(value = "重试次数")
    private Integer retryCount;


}

package com.allinabc.cloud.notice.pojo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 9:41
 **/
@Data
public class MailRequest implements Serializable {

    /**
     * 邮件信息发送主键
     */
    private String id;

    /**
     * 通知类型编码
     */
    //@NotBlank(message = "notice Type code can not null")
    private String noticeTypeCode;

    /**
     * 邮件主题
     */
   // @NotBlank(message = "subject can not null")
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否是html
     */
    private Boolean isHtml;

    /**
     * 附件信息
     */
    private List<Attachment> attachments;

    /**
     * 发送方
     */
    private String noticeFrom;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime sendTime;

    /**
     * 发送状态(0-未发送；1-发送成功；2-发送失败)
     */
    private String status;

    /**
     * 通知方式：0-及时；1-定时
     */
    //@NotBlank(message = "noticeWay can not null")
    private String noticeWay;

    /**
     * 是否需要重试
     */
    private Boolean needRetry;

    /**
     * 重试次数
     */
    private Integer retryCount;

   /**
   * 抄送和收件人
   */
    private List<NoticeParticipantDTO> noticeParticipant;

    /**
     * 模板文件路径
     */
    private String templateUrl;

}

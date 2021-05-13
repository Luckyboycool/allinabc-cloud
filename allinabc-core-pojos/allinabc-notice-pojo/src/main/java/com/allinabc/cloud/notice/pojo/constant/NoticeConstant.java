package com.allinabc.cloud.notice.pojo.constant;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 15:35
 **/
public class NoticeConstant {
    /**
     * 通知方式（及时）
     */
    public static final String NOTICE_WAY_ZERO = "0";

    /**
     * 通知方式（定时）
     */
    public static final String NOTICE_WAY_ONE = "1";

    /**
     * 参与对象类型(USER)
     */
    public static final String OBJECT_TYPE_USER = "USER";

    /**
     * 参与对象类型(GROUP)
     */
    public static final String OBJECT_TYPE_GROUP = "GROUP";

    /**
     * 参与对象类型(EMAIL)
     * OBJECT_ID为电子邮件地址
     */
    public static final String OBJECT_TYPE_EMAIL = "EMAIL";

    /**
     * participant_type(抄送方)
     */
    public static final String PARTICIPANT_TYPE_NOTICE_CC = "NOTICE_CC";

    /**
     * participant_type(接收方)
     */
    public static final String PARTICIPANT_TYPE_NOTICE_TO = "NOTICE_TO";

    /**
     * 通知发送成功
     */
    public static final String NOTICE_SEND_STATUS_SUCCESS = "1";

    /**
     * 通知发送失败
     */
    public static final String NOTICE_SEND_STATUS_FAIL = "2";

    /**
     * 不需要重试
     */
    public static final String NOTICE_NOT_NEED_RETRY = "0";

    /**
     * 需要重试
     */
    public static final String NOTICE_NEED_RETRY = "1";

    /**
     * 邮件
     */
    public static final String SEND_TYPE_MAIL = "0";


    /**
     * SMS
     */
    public static final String SEND_TYPE_SMS = "1";



}

package com.allinabc.cloud.notice.pojo.dto;

import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/9 11:17
 **/
@Data
public class SendTypeModel {
    /**
     * 发送方式
     */
    private String sendType;

    /**
     * 账号（邮箱、手机号）
     */
    private String account;
}

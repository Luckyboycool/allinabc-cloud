package com.allinabc.cloud.notice.api.service.api;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:29
 **/
public interface ApiNoticeService {

    /**
     * 发送邮件
     * @param mailRequest
     * @return
     */
    String sendMail(MailRequest mailRequest);
}

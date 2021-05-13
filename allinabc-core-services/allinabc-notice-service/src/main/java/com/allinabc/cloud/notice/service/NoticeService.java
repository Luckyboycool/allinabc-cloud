package com.allinabc.cloud.notice.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 10:27
 **/
public interface NoticeService {

    /**
     * 发送邮件通知
     */
    Result<String> sendMail(MailRequest mailRequest);

    /**
     * 下载模板文件并设置邮件的内容
     */
    //void setMailContentByTemplateUrl(MailRequest mailRequest);
}

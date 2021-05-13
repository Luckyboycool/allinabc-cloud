package com.allinabc.cloud.notice.api.service.impl.feign.client;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 9:37
 **/
@FeignClient(value ="allinabc-notice" )
public interface NoticeServiceApi {

    /**
     * 发送邮件通知
     */
    @PostMapping("/noticeapi/sendmail")
    Result<String> sendMail(@RequestBody MailRequest mailRequest);
}

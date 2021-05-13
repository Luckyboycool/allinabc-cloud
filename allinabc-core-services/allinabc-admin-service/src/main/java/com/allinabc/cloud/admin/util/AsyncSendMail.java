package com.allinabc.cloud.admin.util;

import com.allinabc.cloud.notice.api.service.api.ApiNoticeService;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Simon.Xue
 * @date 2021/4/12 11:01 上午
 **/
@Component
public class AsyncSendMail {

    @Autowired
    private ApiNoticeService apiNoticeService;


    @Async
    public void handleSendMail(MailRequest mailRequest) {
        apiNoticeService.sendMail(mailRequest);
    }

}

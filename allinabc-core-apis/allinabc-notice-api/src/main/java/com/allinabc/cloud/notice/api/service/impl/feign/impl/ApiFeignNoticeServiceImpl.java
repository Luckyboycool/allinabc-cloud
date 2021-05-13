package com.allinabc.cloud.notice.api.service.impl.feign.impl;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.api.service.api.ApiNoticeService;
import com.allinabc.cloud.notice.api.service.impl.feign.client.NoticeServiceApi;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:33
 **/
@Service
public class ApiFeignNoticeServiceImpl implements ApiNoticeService {

    @Autowired
    private NoticeServiceApi noticeServiceApi;

    @Override
    public String sendMail(MailRequest mailRequest) {
        Result<String> result = noticeServiceApi.sendMail(mailRequest);
        if(result.getCode()==5000){
            throw new RuntimeException(result.getMessage());
        }
        return result!=null && result.getData()!=null ? result.getData() :null;
    }
}

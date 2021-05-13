package com.allinabc.cloud.notice.controller;

import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import com.allinabc.cloud.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 通知服务controller
 * @Author wangtaifeng
 * @Date 2020/12/8 10:04
 **/
@RestController
@RequestMapping("/noticeapi")
@Api(tags = "通知服务")
public class NoticeServiceController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 发送邮件通知
     */
    @PostMapping("/sendmail")
    @ApiOperation(value = "发送邮件通知", notes = "发送邮件通知")
    public Result<String> sendMail(@RequestBody @Validated MailRequest mailRequest) {
        if (StringUtils.isEmpty(mailRequest.getContent())) {
            return Result.failed("content can not null");
        }
        return noticeService.sendMail(mailRequest);
    }
}

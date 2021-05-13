package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.po.Notice;
import com.allinabc.cloud.admin.service.NoticeService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/notice")
@BizClassification(serviceName = "monitor",modelName = "notice",bizName = "消息通知")
public class NoticeController extends MybatisBaseCrudController<Notice> {

    @Autowired
    private NoticeService noticeService;

    @Override
    protected CommonService<Notice> getService() {
        return noticeService;
    }

}

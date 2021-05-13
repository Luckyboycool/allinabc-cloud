package com.allinabc.cloud.admin;

import com.allinabc.cloud.admin.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author QQF
 * @date 2020/12/16 10:10
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class NoticeServiceTest {

    @Resource
    private NoticeService sysNoticeService;
}
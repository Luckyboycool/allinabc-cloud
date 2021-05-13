package com.allinabc.cloud.admin;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.admin.pojo.vo.AppPathVO;
import com.allinabc.cloud.admin.pojo.vo.ApplicationVO;
import com.allinabc.cloud.admin.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/22 14:08
 **/
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class ApplicationServiceTest {
    @Autowired
    private ApplicationService sysApplicationService;

    @Test
    public void findAppCode(){
        List<ApplicationVO> appCode = sysApplicationService.findAppCode();
        log.info(JSON.toJSONString(appCode));
    }

    @Test
    public void findAppPaht(){
        List<AppPathVO> appPaht = sysApplicationService.findAppPaht();
        log.info(JSON.toJSONString(appPaht));
    }
}

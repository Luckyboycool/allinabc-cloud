package com.allinabc.cloud.admin;

import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.admin.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author QQF
 * @date 2020/12/17 9:41
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class ConfigServiceTest {

    @Resource
    private ConfigService sysConfigService;


    @Test
    public void selectByKey(){
        Config sysConfig = sysConfigService.selectByKey("IMAGE_MAX_SIZE");
    }


    @Test
    public void selectByKeyAndApp(){
        Config sysconfig = sysConfigService.selectByKeyAndApp("AIYEI_ADC","IMAGE_MAX_SIZE");
    }

    @Test
    public void selectValueByKey(){
        String value = sysConfigService.selectValueByKey("IMAGE_MAX_SIZE");
    }

}
package com.allinabc.cloud.admin;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.admin.pojo.po.DictType;
import com.allinabc.cloud.admin.service.DictTypeService;
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
 * @Date 2020/12/22 14:32
 **/
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DictTypeServiceTest {

    @Autowired
    private DictTypeService sysDictTypeService;

    @Test
    public void selectList(){
        DictType sysDictType = new DictType();
        sysDictType.setAppCode("APP Code");
        List<DictType> sysDictTypes = sysDictTypeService.selectList(sysDictType);
        log.info("返回数据="+ JSON.toJSONString(sysDictTypes));
    }
}

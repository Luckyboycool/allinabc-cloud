package com.allinabc.cloud.admin;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.admin.service.DictDataService;
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
 * @Date 2020/12/22 14:13
 **/
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DictDataServiceTest {

    @Autowired
    private DictDataService sysDictDataService;

    @Test
    public void selectByValue(){
        DictData sysDictData = sysDictDataService.selectByValue("CLASSITY_GROUP","MAN_BIN");
        log.info("返回数据="+JSON.toJSON(sysDictData));
    }

    @Test
    public void selectDictLabel(){
        String s = sysDictDataService.selectDictLabel("CLASSITY_GROUP", "MAN_BIN");
        log.info("返回数据="+JSON.toJSON(s));
    }

    @Test
    public void selectByDictType(){
        List<DictData> classity_group = sysDictDataService.selectByDictType("CLASSITY_GROUP");
        log.info("返回数据="+JSON.toJSON(classity_group));
    }

    @Test
    public void selectList(){
        DictData sysDictData = new DictData();
        sysDictData.setDictType("CLASSITY_GROUP");
        List<DictData> sysDictDataList = sysDictDataService.selectList(sysDictData);
        log.info("返回数据="+JSON.toJSONString(sysDictDataList));
    }

    @Test
    public void getDictValueByTypeAndCode(){
        DictData sysDictData = new DictData();
        sysDictData.setDictType("CLASSITY_GROUP");
        List<DictData> sysDictDataList = sysDictDataService.getDictValueByTypeAndCode("CLASSITY_GROUP","APP Code");
        log.info("返回数据="+JSON.toJSONString(sysDictDataList));
    }
}

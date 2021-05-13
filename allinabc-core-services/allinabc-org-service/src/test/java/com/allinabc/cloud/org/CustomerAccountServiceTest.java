package com.allinabc.cloud.org;

import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.api.service.api.ApiCidbService;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/23 10:56 上午
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAccountServiceTest {
    @Autowired
    private ApiCidbService apiCidbService;
    /**
     * 获取部分客户列表
     */
    //@Test
    public void testGetCidbList() {
        List<String> custCodes = new ArrayList<>();
        custCodes.add("ZYY");
        custCodes.add("XLY");
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = apiCidbService.findListByCustCodes(custCodes);
        Assert.assertNotNull(cidbBasicSimpleVOList);
        Assert.assertEquals(custCodes.size(), cidbBasicSimpleVOList.size());
        log.info("result = {}", JSONObject.toJSONString(cidbBasicSimpleVOList));
    }
}

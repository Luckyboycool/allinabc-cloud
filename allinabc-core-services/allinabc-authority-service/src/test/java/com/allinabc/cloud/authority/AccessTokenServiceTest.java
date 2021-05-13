package com.allinabc.cloud.authority;

import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.api.service.api.ApiUserService;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.authority.service.AccessTokenService;
import com.allinabc.cloud.common.core.utils.RandomUtil;
import com.allinabc.cloud.common.core.utils.security.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2021/3/5 11:01 上午
 **/
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AccessTokenServiceTest {
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private ApiUserService userService;

    /**
     * 获得Token值
     */
    @Test
    public void testSetToken() {
        SysUser simon = userService.getUserByName("simon");
        simon.setUserType("0");
        Map<String, Object> token = accessTokenService.createToken(simon);
        log.info("{}", JSONObject.toJSON(token));
    }
    @Test
    public void testGenUser() {
        String salt = RandomUtil.randomStr(6);
        String username = "00001111";
        String password ="123456";
        String hash = Md5Utils.hash(username + password + salt);
        log.info("salt = {}, username = {}, password = {}", salt, username, hash);
    }

    public static void main(String[] args) {
        String salt = RandomUtil.randomStr(6);
        String username = "GTA5";
        String password ="123456";
        String hash = Md5Utils.hash(username + password + salt);
        log.info("salt = {}, username = {}, password = {}", salt, username, hash);
    }
}

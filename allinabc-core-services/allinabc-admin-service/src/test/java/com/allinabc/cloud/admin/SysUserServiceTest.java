package com.allinabc.cloud.admin;

import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.admin.service.UserService;
import com.allinabc.cloud.common.core.utils.security.Md5Utils;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author QQF
 * @date 2020/12/16 10:09
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class SysUserServiceTest {

    @Resource
    private UserService sysUserService;

    @Test
    public void selectById(){
        sysUserService.selectById("1");
    }


    @Test
    public void singletonList(){
        String ids = "1,2,3,4,5,6,7,,";
        ids = Joiner.on(",").skipNulls().join(ids.split(","));
        log.info("{}", ids);
        Arrays.asList(ids.split(","));
    }

    /**
     * 生成密码
     */
    @Test
    public void testGenPassword() {
        //Md5Utils.hash(accountResp.getUserName() + oldPassword + accountResp.getSalt());
        String salt = "gguGGe";
        String password = "123456";
        String username = "simon";
        String hash = Md5Utils.hash(username + password + salt);
        log.info("username = {}, password = {}, hashPassword = {}", username, password, hash);
    }

    @Test
    public void testSelectByUserName() {
        SysUser simon = sysUserService.selectByUserName("simon");
        log.info("{}", JSONObject.toJSON(simon));
    }
}
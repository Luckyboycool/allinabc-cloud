package com.allinabc.cloud.authority.controller;

import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.authority.pojo.LoginForm;
import com.allinabc.cloud.authority.service.AccessTokenService;
import com.allinabc.cloud.authority.service.SysLoginService;
import com.allinabc.cloud.common.core.exception.user.UserException;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "登录")
@RestController
public class TokenController {
    @Resource
    private AccessTokenService tokenService;

    @Resource
    private SysLoginService sysLoginService;

    @Autowired
    private Environment env;

    @ApiOperation("登录")
    @PostMapping("login")
    public Result<Map<String, Object>> login(@RequestBody @Validated LoginForm form) {
        Map<String, Object> retMap = Maps.newHashMap();
        String userType = form.getUserType();

        try {
            SysUser sysUser = sysLoginService.login(form.getUsername(), form.getPassword(), userType);
            sysUser.setAccountType(userType);
            retMap = tokenService.createToken(sysUser);
            retMap.put("username", sysUser.getUserName());
            retMap.put("jobNumber", sysUser.getJobNumber());

        } catch (UserException u) {

            return Result.failed(env.getProperty(u.getCode()));
        }
        return Result.success(retMap);
    }

    @ApiOperation("退出")
    @PostMapping("logout")
    public Result<?> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        SysUser sysUser = tokenService.queryByToken(token);
        if (null != sysUser) {
            sysLoginService.logout(sysUser);
            tokenService.expireToken(sysUser.getId());
        }
        return Result.success();
    }
}

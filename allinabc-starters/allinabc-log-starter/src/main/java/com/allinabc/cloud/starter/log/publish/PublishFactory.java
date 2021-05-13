package com.allinabc.cloud.starter.log.publish;


import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.core.utils.AddressUtils;
import com.allinabc.cloud.common.core.utils.IpUtils;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.core.utils.spring.SpringContextHolder;
import com.allinabc.cloud.starter.log.event.SysLoginInfoEvent;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class PublishFactory {

    /**
     * 记录登陆信息
     * @param sysUser 用户信息
     * @param status 状态
     * @param message 消息
     * @param args 列表
     */
    public static void recordLoginInfo(final SysUser sysUser, final String status, final String message,
                                       final Object ... args) {
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        LoginInfoDTO loginInfo = new LoginInfoDTO();
        loginInfo.setUserName(sysUser.getUserName());
        loginInfo.setIpAddr(ip);
        loginInfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginInfo.setBrowser(browser);
        loginInfo.setOsType(os);
        loginInfo.setMessage(message);
        loginInfo.setLoginTime(new Date());
        loginInfo.setUserId(sysUser.getId());
        loginInfo.setUserType(sysUser.getAccountType());
        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status)) {
            loginInfo.setStatus(Constants.SUCCESS);
        }
        if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.FAIL);
        }
        if (Constants.LOGOUT.equals(status)) {
            loginInfo.setStatus(Constants.LOGOUT_SUCCESS);
        }
        // 发布事件
        SpringContextHolder.publishEvent(new SysLoginInfoEvent(loginInfo));
    }

}

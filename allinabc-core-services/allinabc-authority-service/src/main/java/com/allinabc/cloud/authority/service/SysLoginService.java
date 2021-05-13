package com.allinabc.cloud.authority.service;

import com.allinabc.cloud.admin.api.service.api.ApiUserService;
import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.authority.util.PasswordUtil;
import com.allinabc.cloud.common.core.enums.UserLoginType;
import com.allinabc.cloud.common.core.enums.UserStatus;
import com.allinabc.cloud.common.core.exception.user.UserPasswordNotMatchException;
import com.allinabc.cloud.common.core.exception.user.UserTypeException;
import com.allinabc.cloud.common.core.utils.IpUtils;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.org.api.service.api.ApiCustomerAccountService;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.starter.log.publish.PublishFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class SysLoginService {

    @Autowired
    private ApiUserService userService;
    @Resource
    private ApiCustomerAccountService apiCustomerAccountService;

    @Resource
    private ApiEmployeeService apiEmployeeService;

    /**
     * 登录
     */
    public SysUser login(String username, String password, String userType) {
        // 查询用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setAccountType(userType);
        //用户登陆类型  错误
        if (!UserLoginType.isExist(userType)) {
            throw new UserTypeException();
        }

        if (UserLoginType.EMPLOYEE.getCode().equals(userType)) {
            Employee employee = apiEmployeeService.getByUsername(username);
            Assert.notNull(employee, "员工账号不存在");
            BeanUtils.copyBeanProp(sysUser, employee);
            sysUser.setStatus(String.valueOf("1".equals(employee.getStatus())?UserStatus.OK.getCode(): UserStatus.DISABLE.getCode()));
            sysUser.setJobNumber(employee.getJobNumber());
            sysUser.setLoginId(employee.getLoginId());
            sysUser.setUserName(employee.getName());
        } else if (UserLoginType.ACCOUNT.getCode().equals(userType)) {
            CustomerAccount customerAccount = apiCustomerAccountService.getByUsername(username);
            Assert.notNull(customerAccount, "客户账号不存在");
            BeanUtils.copyBeanProp(sysUser, customerAccount);
            sysUser.setUserName(customerAccount.getUsername());
        } else {
            sysUser =  userService.getUserByName(username);
        }

        Assert.notNull(sysUser, "call api get user by name failure...");

        if (UserLoginType.EMPLOYEE.getCode().equals(userType)) {
            if (!"123456".equals(password)) {
                throw new UserPasswordNotMatchException("用户或密码不正确");
            }
        } else if (!PasswordUtil.matches(sysUser, password)) {
            PublishFactory.recordLoginInfo(sysUser, Constants.LOGIN_FAIL, "用户或密码不正确");
            throw new UserPasswordNotMatchException("用户或密码不正确");
        }
        sysUser.setAccountType(userType);
        PublishFactory.recordLoginInfo(sysUser, Constants.LOGIN_SUCCESS, "登录成功");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyBeanProp(userDTO, sysUser);

        //TODO 更新系统用户信息 后期有可能删除
        if (UserLoginType.SYSTEM.getCode().equals(userType)) {
            recordLoginInfo(userDTO);
        }
        return sysUser;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(UserDTO user) {
        user.setLastLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLastLoginTime(new Date());
        userService.updateLoginRecord(user);
    }

    public void logout(SysUser sysUser) {
        PublishFactory.recordLoginInfo(sysUser, Constants.LOGOUT, "登出成功");
    }

}
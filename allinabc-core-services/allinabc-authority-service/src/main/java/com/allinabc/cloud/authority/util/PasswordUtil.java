package com.allinabc.cloud.authority.util;


import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.core.utils.security.Md5Utils;

public class PasswordUtil {

    public static boolean matches(SysUser sysUser, String newPassword) {
        return sysUser.getPassword().equals(encryptPassword(sysUser.getUserName(), newPassword, sysUser.getSalt()));
    }

    public static String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
}
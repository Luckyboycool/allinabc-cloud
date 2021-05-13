package com.allinabc.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Simon.Xue
 * @date 2021/3/4 10:24 上午
 **/
@AllArgsConstructor
@Getter
public enum UserLoginType {
    SYSTEM("0", "系统用户"),
    EMPLOYEE("1", "员工"),
    ACCOUNT("2", "客户");

    String code;
    String msg;

    /**
     * 是否存在用户类型
     * @param code
     * @return
     */
    public static boolean isExist(String code) {
        for (UserLoginType value : UserLoginType.values()) {
            if (value.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}

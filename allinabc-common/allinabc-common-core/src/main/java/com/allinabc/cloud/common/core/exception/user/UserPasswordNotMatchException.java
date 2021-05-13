package com.allinabc.cloud.common.core.exception.user;

import com.allinabc.cloud.common.core.exception.base.BaseException;

/**
 * 用户密码不正确或不符合规范异常类
 */
public class UserPasswordNotMatchException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException(String message) {
        super(message);
    }
}

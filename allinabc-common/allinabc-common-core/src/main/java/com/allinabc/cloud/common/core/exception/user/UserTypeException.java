package com.allinabc.cloud.common.core.exception.user;

/**
 * 用户类型异常类
 */
public class UserTypeException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserTypeException()
    {
        super("userType.not.exists", null);
    }

}

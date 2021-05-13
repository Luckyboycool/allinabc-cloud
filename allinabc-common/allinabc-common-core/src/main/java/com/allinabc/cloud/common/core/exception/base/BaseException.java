package com.allinabc.cloud.common.core.exception.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 基础异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    /** 所属模块 */
    private String  module;
    /** 错误码 */
    private String  code;
    /** 错误消息 */
    private String  defaultMessage;
    /** 错误码对应的参数 */
    private Object[] args;

    public BaseException(String module, String code, Object[] args)
    {
        this(module, code, null, args);
    }

    public BaseException(String module, String defaultMessage)
    {
        this(module, null, defaultMessage, null);
    }

    public BaseException(String code, Object[] args)
    {
        this(null, code, null, args);
    }

    public BaseException(String defaultMessage)
    {
        this(null, null, defaultMessage, null);
    }

/*    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }*/


    public BaseException(Throwable throwable, String code) {
        super(throwable);
        this.defaultMessage = throwable.getMessage();
        this.code = code;
    }


    public BaseException(String error, Throwable throwable, String code) {
        super(error, throwable);
        this.defaultMessage = error;
        this.code = code;
    }

    public BaseException(String module, String code, String defaultMessage)
    {
        this(module, code, defaultMessage, null);
    }

}

package com.allinabc.cloud.common.web.pojo.resp;

import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int     code;
    private String  message;
    private T       data;

    /**
     * 成功返回结果
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<T>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ApiResultCode.SUCCESS.getCode(), ApiResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<T>(ApiResultCode.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功返回结果
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<T>(ApiResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 成功返回结果
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(int code, String message) {
        return new Result<T>(code, message, null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(ResultCode code) {
        return new Result<T>(code.getCode(), code.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(ResultCode code, String message) {
        return new Result<T>(code.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(String message) {
        return new Result<T>(ApiResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(int code,String message) {
        return new Result<T>(code, message, null);
    }

    /**
     * 参数校验失败
     * @param <T>
     * @return
     */
    public static <T> Result<T> validateFailed() {
        return failed(ApiResultCode.VALIDATE_PARAMS_FAILED);
    }

    /**
     * 参数校验失败
     * @param <T>
     * @return
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<T>(ApiResultCode.VALIDATE_PARAMS_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<T>(ApiResultCode.VALIDATE_TOKEN_FAILED.getCode(), ApiResultCode.VALIDATE_TOKEN_FAILED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> forbidden(T data) {
        return new Result<T>(ApiResultCode.VALIDATE_AUTH_FAILED.getCode(), ApiResultCode.VALIDATE_AUTH_FAILED.getMessage(), data);
    }

    /**
     * 判断是否返回成功
     * @return
     */
    @JsonIgnore
    public boolean isSuccess(){
        return ApiResultCode.SUCCESS.getCode()==this.code;
    }

}

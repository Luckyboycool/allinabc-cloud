package com.allinabc.cloud.common.core.exception.handler;


import com.allinabc.cloud.common.core.exception.base.BaseException;
import com.allinabc.cloud.common.core.exception.user.ForbiddenException;
import com.allinabc.cloud.common.core.exception.user.UnauthorizedException;
import com.allinabc.cloud.common.core.utils.RequestContext;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
@Order(Integer.MIN_VALUE)
@Slf4j
public class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class, MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class, Exception.class, BaseException.class, UnauthorizedException.class})
    public final Result<?> handleException(Exception ex, WebRequest request) {
        HttpStatus status;
        HttpServletRequest req = RequestContext.getServletRequest();
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
        } else if (ex instanceof MissingPathVariableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof MissingServletRequestParameterException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ServletRequestBindingException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ConversionNotSupportedException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof TypeMismatchException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotWritableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestPartException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BindException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof AsyncRequestTimeoutException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        } else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof BaseException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (ex instanceof ForbiddenException) {
            return Result.failed(ApiResultCode.FORBIDDEN, ex.getMessage());
        }

        if (ex instanceof MethodArgumentNotValidException){
            FieldError fieldError = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError();
            String message = fieldError.getDefaultMessage();
            Integer code = ApiResultCode.VALIDATE_PARAMS_FAILED.getCode();
            pointLog(ex, req.getRequestURI(), status.value(), code,message);
            return Result.failed(ApiResultCode.VALIDATE_PARAMS_FAILED, message);
        }else if(ex instanceof UnauthorizedException){
            Integer code = ApiResultCode.VALIDATE_AUTH_FAILED.getCode();
            String message = ApiResultCode.VALIDATE_AUTH_FAILED.getMessage();
            pointLog(ex, req.getRequestURI(), status.value(), code,message);
            return Result.failed(ApiResultCode.VALIDATE_AUTH_FAILED, message);
        }else {
            String code=(ex instanceof BaseException)?((BaseException) ex).getCode():"50000";
            Integer errorCode = StringUtils.isEmpty(code)?50000:Integer.valueOf(code);
            String message=(ex instanceof BaseException)?((BaseException) ex).getDefaultMessage():ex.getMessage();
            pointLog(ex, req.getRequestURI(), status.value(), errorCode,message);
            return Result.failed(ApiResultCode.FAILED, message);
        }


    }

    /**
     * 打印异常日志
     * @param ex
     * @param requestURI
     * @param value
     * @param code
     */
    private void pointLog(Exception ex, String requestURI, int value, Integer code,String message) {
        log.error("requestUri:{},message:{},status:{},errorCode:{}", requestURI, message, value, code);
        log.error(ex.getMessage(),ex);
    }
}

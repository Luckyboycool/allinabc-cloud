package com.allinabc.cloud.starter.log.aspect;

import cn.hutool.core.annotation.AnnotationUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.pojo.dto.OperLogDTO;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.core.utils.AddressUtils;
import com.allinabc.cloud.common.core.utils.IpUtils;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.core.utils.spring.SpringContextHolder;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.starter.log.annotation.OperLog;
import com.allinabc.cloud.starter.log.enums.BusinessStatus;
import com.allinabc.cloud.starter.log.event.SysOperLogEvent;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录处理
 */
@Aspect
@Slf4j
public class OperLogAspect {

    @Value("spring.profile.active")
    private String profile;

    @Pointcut("@annotation(com.allinabc.cloud.starter.log.annotation.OperLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     * @param joinPoint 切点
     */
    @AfterReturning(returning = "rvl",pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint,Object rvl) {
        handleLog(joinPoint, null,rvl);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e,null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e,Object rvl) {
        try {
            // 获得注解
            OperLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // *========数据库日志=========*//
            OperLogDTO operLog = new OperLogDTO();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
            operLog.setOperTime(new Date());
            String username = request.getHeader(Constants.CURRENT_USERNAME);
            operLog.setOperName(username);
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(joinPoint, controllerLog, operLog, args,rvl);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
        }
        catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, OperLog log, OperLogDTO operLog, Object[] args,Object rvl) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        //Object title = BeanUtil.getFieldValue(joinPoint.getTarget(), "BIZ_NAME");
        Object title = AnnotationUtil.getAnnotationValue(joinPoint.getTarget().getClass(), BizClassification.class, "bizName");
        String nTitle = null != title ? title.toString() : null;
        nTitle = Strings.isNullOrEmpty(log.title()) ? nTitle : log.title();
        if(Strings.isNullOrEmpty(nTitle)) {
            throw new BusinessException("common oper log property [BIZ_NAME] is empty!");
        }
        operLog.setTitle(nTitle);
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog, args,rvl);
        }
    }

    /**
     *  获取请求的参数，放到log中
     * 
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(OperLogDTO operLog, Object[] args,Object rvl) throws Exception {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse))
                .collect(Collectors.toList());
//        log.debug("args:{}", param);
        log.info("\n请求头参数:{}", JSONObject.toJSONString(param));
        log.info("\n响应报文:{}",JSONObject.toJSON(rvl));
        String params = JSON.toJSONString(param, true);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }

}
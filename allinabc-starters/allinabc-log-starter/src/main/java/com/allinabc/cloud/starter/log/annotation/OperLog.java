package com.allinabc.cloud.starter.log.annotation;



import com.allinabc.cloud.starter.log.enums.BusinessType;
import com.allinabc.cloud.starter.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /** 模块 */
    String title() default "";
    /** 功能 */
    BusinessType businessType() default BusinessType.OTHER;
    /** 操作人类别 */
    OperatorType operatorType() default OperatorType.MANAGE;
    /** 是否保存请求的参数 */
    boolean isSaveRequestData() default true;

}

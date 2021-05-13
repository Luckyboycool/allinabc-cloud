package com.allinabc.cloud.starter.web.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveOperation {

    String title() default "";


}

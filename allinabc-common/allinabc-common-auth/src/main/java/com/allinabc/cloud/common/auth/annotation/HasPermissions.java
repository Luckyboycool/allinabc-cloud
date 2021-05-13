package com.allinabc.cloud.common.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermissions {

    String value() default "";

    String service() default "";

    String model() default "";

    String method() default "";

}
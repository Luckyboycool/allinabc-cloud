package com.allinabc.cloud.starter.redis.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisEvict {

    String key();

    String fieldKey() default "";

    /**
     * fieldKey是否加密
     * 如果需要加密，该值需置为 true
     * @return
     */
    boolean fieldKeyMD5() default false;

}
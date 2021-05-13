package com.allinabc.cloud.starter.redis.annotation;

import com.allinabc.cloud.starter.redis.enums.RedisCacheKeyType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * 业务键
     * 构成Redis Key的业务模块
     */
    String keyModel() default "";

    /**
     * 值键表达式
     * 构成Redis Key的值标记
     */
    String expression() default "";

    /**
     * 值键类型
     */
    RedisCacheKeyType keyType() default RedisCacheKeyType.HASH;

    /**
     * 超期时间，单位秒(s)
     */
    long expired() default 3600;

}

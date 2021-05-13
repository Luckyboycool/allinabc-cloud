package com.allinabc.cloud.starter.redis.enums;

import sun.security.provider.MD5;

/**
 * Redis值键的表达类型
 */
public enum RedisCacheKeyType {

    /**
     * 将参数以Hash的方式表示
     * 表达式不为空的时候，取参数的值替换表达式
     * 表达式为空的时候，将参数表示成完整Hash
     */
    HASH,

    /**
     * 将参数加密成MD5码，作为键值
     */
    MD5;

}

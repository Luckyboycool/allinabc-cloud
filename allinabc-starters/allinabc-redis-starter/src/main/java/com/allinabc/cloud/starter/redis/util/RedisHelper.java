package com.allinabc.cloud.starter.redis.util;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisHelper {

    @Autowired
    private RedisTemplate<String, Object>   redisTemplate;
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> stringRedisTemplate;

    /** 默认过期时长，单位：秒 */
    private final static long   DEFAULT_EXPIRE_TIME = 15;
    /** 不设置过期时长 */
    private final static long   NOT_EXPIRE = -1;

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @param expire 超期时间(s)
     */
    public void set(String key, Object value, long expire) {
        stringRedisTemplate.set(key, JSONUtil.toJsonStr(value));
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 插入缓存（默认超期时间）
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 更新超期时间
     * @param key 键
     * @param expire 超期时间(s)
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串值
     * @param key 键
     * @return 字符串值
     */
    public String get(String key) {
        return stringRedisTemplate.get(key);
    }

    /**
     * 返回指定类型的数据
     * @param key 键
     * @param clazz 类型
     * @return 指定类型的值数据
     */
    public <T> T getBean(String key, Class<T> clazz) {
        String valueStr = this.get(key);
        return null != valueStr ? JSONUtil.toBean(valueStr, clazz) : null;
    }

    /**
     * 移除缓存
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 模糊移除缓存
     * @param key 模糊键
     */
    public void deleteKeys(String key) {
        Set<String> keys = redisTemplate.keys(key);
        if(null == keys)
            return;
        redisTemplate.delete(keys);
    }

    /**
     * 判断键是否存在
     * @param key 键
     * @return 是否存在
     */
    public boolean containsKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        return null != hasKey ? hasKey : false;
    }

}

package com.allinabc.cloud.authority.service;

import cn.hutool.core.util.IdUtil;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.starter.redis.annotation.RedisEvict;
import com.allinabc.cloud.starter.redis.util.RedisHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("accessTokenService")
public class AccessTokenService {

    @Autowired
    private RedisHelper redis;

    /** 超期时间 */
    private final static long   EXPIRE        = 12 * 60 * 60;

    private final static String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USER_ID = Constants.ACCESS_USERID;

    public SysUser queryByToken(String token)
    {
        return redis.getBean(ACCESS_TOKEN + token, SysUser.class);
    }

    @RedisEvict(key = "user_perms", fieldKey = "#user.id")
    public Map<String, Object> createToken(SysUser sysUser) {
        // 生成token
        String token = IdUtil.fastSimpleUUID();
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", sysUser.getId());
        map.put("token", token);
        map.put("expire", EXPIRE);
        // expireToken(userId);
        redis.set(ACCESS_TOKEN + token, sysUser, EXPIRE);
        redis.set(ACCESS_USER_ID + sysUser.getId(), token, EXPIRE);
        return map;
    }


    public void expireToken(String userId) {
        String token = redis.get(ACCESS_USER_ID + userId);
        if (StringUtils.isNotBlank(token))
        {
            redis.delete(ACCESS_USER_ID + userId);
            redis.delete(ACCESS_TOKEN + token);
        }
    }


}
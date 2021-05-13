package com.allinabc.cloud.starter.web.util;

import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.spring.SpringUtils;
import com.allinabc.cloud.starter.redis.util.RedisHelper;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

public class CurrentUserUtil {

    public static User getCurrentUser(){
        User retUser = null;
        String token = null;
        if (null != ServletUtils.getRequest()) {
            HttpServletRequest request = ServletUtils.getRequest();
            token = request.getHeader("token");
        }

        RedisHelper redisUtils = SpringUtils.getBean(RedisHelper.class);

        if (!Strings.isNullOrEmpty(token)) {
            retUser = (User)redisUtils.getBean("access_token_" + token, User.class);
        }
        return retUser;
    }

    public static String getCurrentUserId(){
        return null != getCurrentUser() ? getCurrentUser().getId() : null;
    }

}

package com.allinabc.cloud.starter.web.aspect;

import com.allinabc.cloud.common.auth.annotation.HasFormAuth;
import com.allinabc.cloud.common.core.exception.user.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class PreFormAuthorizeAspect {

    @Around("@annotation(com.allinabc.cloud.common.auth.annotation.HasFormAuth)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HasFormAuth annotation = method.getAnnotation(HasFormAuth.class);
        Object[] args = point.getArgs();

        if (annotation == null) {
            return point.proceed();
        }
        String[] authTypes = annotation.authTypes();
        if (has(authTypes)) {
            return point.proceed();
        }
        else {
            throw new ForbiddenException();
        }
    }

    private boolean has(String[] authTypes) {
//        // 用超管帐号方便测试，拥有所有权限
//        HttpServletRequest request = ServletUtils.getRequest();
//        RedisUtils redis = SpringUtils.getBean(RedisUtils.class);
//        String token = request.getHeader(Constants.TOKEN);
//        Map<String, Object> userMap = redis.getMap(Constants.ACCESS_TOKEN + token);
//
//        String userId = MapUtil.get(userMap, "id", String.class);
//        if(Strings.isNullOrEmpty(userId)) {
//            return false;
//        }
//
//        log.debug("userId:{}", userId);
//        if (StringUtils.equals(Constants.USER_ADMIN, userId)) {
//            return true;
//        }
//
//        Result result = resourcesService.listPermissions(userId);
//        if (null == result || !StringUtils.equals(APIStatus.OK, result.getCode())) {
//            return false;
//        }
//
//        List<String> permissions = (List<String>) result.getData();
//            return permissions.stream().anyMatch(authority::equals);
        return false;
    }
}

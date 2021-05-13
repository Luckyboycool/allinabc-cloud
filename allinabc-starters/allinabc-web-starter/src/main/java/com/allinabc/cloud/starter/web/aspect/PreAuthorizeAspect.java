package com.allinabc.cloud.starter.web.aspect;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.map.MapUtil;
import com.allinabc.cloud.common.auth.annotation.HasPermissions;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.core.exception.user.ForbiddenException;
import com.allinabc.cloud.common.core.exception.user.UnauthorizedException;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.core.utils.spring.SpringUtils;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.starter.redis.util.RedisHelper;
import com.allinabc.cloud.starter.web.feign.RemoteResourcesService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class PreAuthorizeAspect {

    @Resource
    private RemoteResourcesService resourcesService;

    @Value("${jems.web.code}")
    private String appCode;

    private final static long   EXPIRE        = 12 * 60 * 60;

    @Around("@annotation(com.allinabc.cloud.common.auth.annotation.HasPermissions)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HasPermissions annotation = method.getAnnotation(HasPermissions.class);
        if (annotation == null) {
            return point.proceed();
        }

        String authority = annotation.value();

        Object service = AnnotationUtil.getAnnotationValue(method.getDeclaringClass(), BizClassification.class, "serviceName");
        Object model = AnnotationUtil.getAnnotationValue(method.getDeclaringClass(), BizClassification.class, "modelName");
        String nService = null != service ? service.toString() : null;
        String nModel = null != model ? model.toString() : null;

        String cService = annotation.service();

        String cModel = annotation.model();
        String cMethod = annotation.method();

        cService = Strings.isNullOrEmpty(cService) ? nService : cService;
        cModel = Strings.isNullOrEmpty(cModel) ? nModel : cModel;

        if(Strings.isNullOrEmpty(authority)) {
            if(Strings.isNullOrEmpty(cService) || Strings.isNullOrEmpty(cModel) || Strings.isNullOrEmpty(cMethod)) {
                throw new BusinessException("common auth property [SERVICE_NAME] or [MODEL_NAME] is empty!");
            }
            authority = String.format("%s:%s:%s", cService, cModel, cMethod);
        }

        if(has(authority)) {
            return point.proceed();
        }else {
            throw new ForbiddenException();
        }
    }

    private boolean has(String authority) {
        HttpServletRequest request = ServletUtils.getRequest();
        // 校验是否为开发模式，开发模式可以不校验权限
        String devKey = request.getHeader(Constants.DEV_MODEL_KEY);
        if(StringUtils.equals(Constants.DEV_MODEL_VAL, devKey)) {
            return true;
        }

        // 根据Token获取访问者信息
        RedisHelper redis = SpringUtils.getBean(RedisHelper.class);
        String token = request.getHeader(Constants.TOKEN);
        Map<String, Object> userMap = redis.getBean(Constants.ACCESS_TOKEN + token,Map.class);

        if(null == userMap) {
            throw new UnauthorizedException();
        }

        String userId = MapUtil.get(userMap, "id", String.class);
        if (!Strings.isNullOrEmpty(userId)) {
            redis.expire(Constants.ACCESS_TOKEN + token, EXPIRE);
            redis.expire(Constants.ACCESS_USERID + userId, EXPIRE);

            // 超管拥有所有权限
            log.debug("userId:{}", userId);
            if (StringUtils.equals(Constants.USER_ADMIN, userId)) {
                return true;
            }
            Result result = resourcesService.listPermissions(userId, appCode);
            if (null == result || !(ApiResultCode.SUCCESS.getCode()==result.getCode())) {
                return false;
            }
            List<String> permissions = (List<String>) result.getData();
            return permissions.stream().anyMatch(authority::equals);
        }
        return false;
    }
}
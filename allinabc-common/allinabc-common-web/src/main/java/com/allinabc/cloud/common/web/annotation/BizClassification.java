package com.allinabc.cloud.common.web.annotation;

import java.lang.annotation.*;

/**
 * 业务分类注解
 * @author wangtaifeng
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizClassification {

    /**
     * 服务名称
     */
    String serviceName();

    /**
     * 模块名称
     */
    String modelName();

    /**
     * 业务名称
     */
    String bizName();
}

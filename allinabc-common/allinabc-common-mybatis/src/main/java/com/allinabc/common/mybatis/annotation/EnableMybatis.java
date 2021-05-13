package com.allinabc.common.mybatis.annotation;


import com.allinabc.common.mybatis.config.DruidProperties;
import com.allinabc.common.mybatis.config.MyBatisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Description 自定义Mybatis注解
 * @Author wangtaifeng
 * @Date 2020/9/23 14:47
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MyBatisConfig.class, DruidProperties.class})
public @interface EnableMybatis {
}

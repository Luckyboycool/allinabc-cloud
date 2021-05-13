package com.allinabc.common.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description 数据源配置
 * @Author wangtaifeng
 * @Date 2020/9/23 14:45
 **/
@Configuration
@EnableTransactionManagement
@Import(DruidProperties.class)
@ConditionalOnProperty(prefix = "jems.web", name = "enabled", havingValue = "true")
public class MyBatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Value("${jems.db.typeandversion}")
    private String dbTypeAndVersion;

    public MyBatisConfig(){

    }

    @Resource
    private DruidProperties druidProperties;

    //Druid数据源配置,目前只配置了一个,后续可扩展
    @Bean(name="dataSource")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSource(){
        DruidDataSource build = DataSourceBuilder.create().type(DruidDataSource.class).build();
        return druidProperties.dataSource(build);
    }

    @Bean(name={"transactionManager"})
    @Primary
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(this.dataSource());
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if(dbTypeAndVersion.equals(DbType.MYSQL.getDb())) {
            logger.info("mysql PaginationInnerInterceptor......");
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        }else if(dbTypeAndVersion.equals(DbType.ORACLE.getDb())){
            logger.info("oracle PaginationInnerInterceptor......");
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
        }else if(dbTypeAndVersion.equals(DbType.ORACLE_12C.getDb())){
            logger.info("oracle_12C PaginationInnerInterceptor......");
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE_12C));
        }else{
            logger.info("默认设置:mysql PaginationInnerInterceptor......");
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        }
        return interceptor;
    }

    /**
     * 后续版本会删除，此处需要设置
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

}

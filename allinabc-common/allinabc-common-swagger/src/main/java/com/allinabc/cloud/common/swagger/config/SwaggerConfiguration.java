package com.allinabc.cloud.common.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "restApi")
    @Order(value = 1)
    public Docket restApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.buildApiInfo()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(buildSecurityContext()))
                .securitySchemes(Lists.newArrayList(buildSecuritySchema()));
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                        .title("API Document")
                .description("Allinabc Cloud API Document")
                .contact(new Contact("Allinabc Micro Service R & D", "http://www.allinabc.com/", "mail@joinfun.com.cn"))
                .version("1.0.0")
                .build();
    }

    private SecurityContext buildSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(buildDefaultAuth())
                .forPaths(PathSelectors.regex("/.*")).build();
    }

    private List<SecurityReference> buildDefaultAuth() {
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = new AuthorizationScope("global", "accessEverything");
        return Lists.newArrayList(new SecurityReference("BearerToken", scopes));
    }

    private ApiKey buildSecuritySchema() {
        return new ApiKey("TOKEN", "token", "header");
    }

}

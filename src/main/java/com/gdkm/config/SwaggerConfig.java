package com.gdkm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 默认扫描包
     */
    private static final String SWAGGER_SCAN_BASE_PACKAGE="com.gdkm";
    private static final String SWAGGER_VERSION="1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描包路径
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("linux开发文档")
                .description("linux开发文档")
                .license("")
                .licenseUrl("http://localhost:8888")
                .termsOfServiceUrl("http://localhost:8888")
                .version(SWAGGER_VERSION)
                .contact(new Contact("李卓恒","",""))
                .build();
    }
}

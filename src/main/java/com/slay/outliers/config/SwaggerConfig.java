package com.slay.outliers.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAutoConfiguration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket oauthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("oauth-member")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.slay.outliers.member.controller"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket questionApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("question")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.slay.outliers.question.controller"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("slayoutliers API 문서")
                .version("1.0.0")
                .description("API 문서")
                .build();
    }
}

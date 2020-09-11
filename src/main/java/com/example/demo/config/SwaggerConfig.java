/** Copyright  Citic Trust, 2020 中信信托有限公司，版权所有 2020 
 tae5Le2Choh5see2rieshaiQuooGhe7eeth0yahg3ud6eeb1ahsha6eloh2aiYai
*/



package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger config
 */
@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
public class SwaggerConfig {

    /**
     * @Value("${swagger.show}
     */
    private boolean swaggerShow = true;

    @Bean
    public Docket init() {
        System.out.println("初始化成功");
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo API")
                .description("demo API Document")
                .version("1.0")
                .build();
    }
}

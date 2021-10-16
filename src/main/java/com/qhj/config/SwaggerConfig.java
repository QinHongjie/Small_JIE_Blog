package com.qhj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by QHJ on 2021/3/31
 */
//@EnableWebMvc
//扫描的API Controller包
//@ComponentScan(basePackages = {"com.qhj.api"})
@Configuration
@EnableSwagger2
@Profile({"dev"})
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Blog-接口")
                .description("API接口")
                //创建人
                .contact(new Contact("qinhongjie","","2317630938@qq.com"))
                .version("1.1.0")
                .build();
    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qhj.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}



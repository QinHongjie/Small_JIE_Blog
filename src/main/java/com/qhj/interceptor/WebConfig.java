package com.qhj.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by QHJ on 2021/3/25
 * 拦截器配置
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login");
//    }

}

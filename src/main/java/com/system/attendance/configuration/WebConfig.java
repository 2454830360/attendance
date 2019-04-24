package com.system.attendance.configuration;


import com.system.attendance.Handler.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器，放行不需要校验token的路由
        registry.addInterceptor(new JwtInterceptor())
                .excludePathPatterns("/qrCode/**","/login/**","/test/**")
                .addPathPatterns("/**");
    }
}

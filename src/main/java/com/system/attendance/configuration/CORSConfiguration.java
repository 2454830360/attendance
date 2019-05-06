package com.system.attendance.configuration;

import com.system.attendance.Handler.JwtInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义CORS配置，解决跨域访问问题
 * 放行全部
 */

@Configuration
public class CORSConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CORSConfiguration.class);

    //放行全部
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    @Bean
    public WebMvcConfigurer getConfigure(){
        return new WebMvcConfigurer() {
//            @Override
//            //重写父类提供的跨域请求处理的接口
//            public void addCorsMappings(CorsRegistry registry) {
//                //添加映射路径
//                registry.addMapping("/**")
//                        .allowedOrigins("*")//放行哪些原始域
//                        .allowCredentials(true)//是否发送cookie信息
//                        .allowedMethods("*")//放行哪些原始域（请求方式），"GET","POST","PUT","DELETE"
//                        .allowedHeaders("*")//放行哪些原始域（头部信息）
//                        .exposedHeaders();
//                //.exposedHeaders("Cache-Control", "Pragma", "Expires");  //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //注意拦截器的顺序
                //添加拦截器，放行不需要校验token的路由,现在放行了二维码，登录，测试，导入导出的
                registry.addInterceptor(new JwtInterceptor())
                        .excludePathPatterns("/qrCode/**","/login/**","/test/**","/error/**","/excel_out/**","/excel_in/**","/attend/excel")
                        .addPathPatterns("/**");
                //在拦截器打印访问URL
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                        if(response.getStatus()/100>=4){
                            LOG.info("访问错误:URL----"+request.getAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE)+",状态："+response.getStatus());
                        }else {
                            LOG.info("访问成功:URL----"+request.getRequestURI());
                        }
                    }
                });
            }
        };
    }
}

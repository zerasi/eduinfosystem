package com.web.sys.config;


import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author hll
 * @date 2018年1月6日 上午11:23:37
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    /**
     * 配置静态资源
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(new CheckLoginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/") //登录页
                .excludePathPatterns("/favicon**") //登录页
//                .excludePathPatterns("/index") //登录页
                .excludePathPatterns("/register") //登录页
                .excludePathPatterns("/login/**") //登录页
                .excludePathPatterns("/reg/**") //登录页dataInfo
                .excludePathPatterns("/dataInfo/**") //登录页dataInfo

                .excludePathPatterns("/static/**") //

                .excludePathPatterns("/api/log/**")
                .excludePathPatterns("/front/**")
                .excludePathPatterns("/api/front/**")
                .excludePathPatterns("/api/odr/**")
        ;
        super.addInterceptors(registry);
    }
}
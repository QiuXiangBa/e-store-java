package com.followba.store.web.config;

import com.followba.store.web.interceptor.*;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author 祥霸
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class})
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private WebProperties webProperties;

    private static final List<String> EXCLUDE_PATH_ = Arrays.asList("/js/**", "/css/**", "/images/**");

    @Bean
    public PreLoginInterceptor getPreLoginInterceptor() {
        return new PreLoginInterceptor();
    }

//    @Bean
//    public CROSInterceptor getCROSInterceptor() {
//        return new CROSInterceptor();
//    }


    @Bean
    public WapLoginInterceptor getWapLoginInterceptor() {
        return new WapLoginInterceptor();
    }

    @Bean
    public WapAjaxAuthInterceptor getWapAjaxAuthInterceptor() {
        return new WapAjaxAuthInterceptor();
    }

    @Bean
    public ThreadLocalInterceptor getThreadLocalInterceptor() {
        return new ThreadLocalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPreLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(webProperties.getExcludePath());
//        registry.addInterceptor(getCROSInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getWapLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(webProperties.getExcludePath());
        registry.addInterceptor(getWapAjaxAuthInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(webProperties.getExcludePathAuth());
        registry.addInterceptor(getThreadLocalInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH_);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许所有来源访问，也可以指定特定的来源：如 "http://localhost:8080"
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许所有请求方法
                .allowedHeaders("*") // 允许所有请求头
//                .allowCredentials(true) // 允许发送凭据（如 cookies）
                .maxAge(3600); // 预检请求的有效期，单位为秒
    }
}

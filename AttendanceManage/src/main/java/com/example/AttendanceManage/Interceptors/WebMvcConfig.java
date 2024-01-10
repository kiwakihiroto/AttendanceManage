package com.example.AttendanceManage.Interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public SessionCheckInterceptor SessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(SessionCheckInterceptor())
                .addPathPatterns("/*")//処理を実行するページ
                .excludePathPatterns("/login");//対象外
    }

}


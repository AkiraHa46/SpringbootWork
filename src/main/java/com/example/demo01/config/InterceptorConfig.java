package com.example.demo01.config;

import com.example.demo01.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") //拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/login", "/login/register", "/**/export"
                        ,"/**/import","/file/**","/swagger-ui.html", "/swagger-resources/**"
                        , "/webjars/**");
        //excludePathPatterns这步为白名单，白名单内的vue不需要进行拦截
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}

package com.cafe24.shkim30.config;

import com.cafe24.shkim30.interceptor.BlogBasicInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public BlogBasicInterceptor blogBasicInterceptor() {
        return new BlogBasicInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blogBasicInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**"); // 오류페이지 경로

    }

    // ForwardedHeaderFilter는 X-Forwarded-For 같은 헤더를 인식해서 실제 클라이언트 IP로 request.getRemoteAddr()
    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

}

package com.cafe24.shkim30.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BlogBasicInterceptor implements HandlerInterceptor {
    @Value("${constant.backendUrl}")
    public String BACKEND_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("backendUrl", BACKEND_URL);

        return true;
    }

}

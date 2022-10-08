package com.cafe24.shkim30.config;

import com.cafe24.shkim30.library.LogTrace;
import com.cafe24.shkim30.library.libTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new libTrace();
    }
}

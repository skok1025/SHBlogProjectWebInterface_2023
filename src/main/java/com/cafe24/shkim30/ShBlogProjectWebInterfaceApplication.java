package com.cafe24.shkim30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ShBlogProjectWebInterfaceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ShBlogProjectWebInterfaceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ShBlogProjectWebInterfaceApplication.class,args);
	}
}

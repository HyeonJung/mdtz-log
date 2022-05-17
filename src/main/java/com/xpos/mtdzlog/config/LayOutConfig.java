package com.xpos.mtdzlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class LayOutConfig {
	
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}

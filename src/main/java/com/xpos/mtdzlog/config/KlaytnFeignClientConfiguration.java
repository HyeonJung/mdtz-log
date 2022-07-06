package com.xpos.mtdzlog.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KlaytnFeignClientConfiguration {

    @Value("${klaytn.api.accessKeyId}")
    private String accessKeyId;

    @Value("${klaytn.api.secretAccessKey}")
    private String secretAccessKey;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(accessKeyId, secretAccessKey);
    }
}

package com.itrain.common.client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import feign.Logger;
import feign.RequestInterceptor;

@Configuration
@EnableFeignClients(basePackages = { "com.itrain.common.client" })
public class FeignClientConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

}
package com.itrain.web;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ITrainWebApp implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(ITrainWebApp.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.from(ZoneOffset.UTC)));
    }

}

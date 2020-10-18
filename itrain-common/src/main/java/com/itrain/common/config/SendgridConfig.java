package com.itrain.common.config;

import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sendgrid")
public class SendgridConfig {

    private String apiKey;
    private String fromName;
    private String fromEmail;

    @Bean
    public Email sendGridSender() {
        return new Email(fromEmail, fromName);
    }

}

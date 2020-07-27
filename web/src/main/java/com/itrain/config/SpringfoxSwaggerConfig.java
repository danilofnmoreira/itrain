package com.itrain.config;

import com.fasterxml.classmate.types.ResolvedObjectType;
import com.itrain.payload.api.v1.response.ErrorResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;
import lombok.Setter;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringfoxSwaggerConfig {

    //@formatter:off
    @Bean
    public Docket springfoxDocketApi(SpringfoxSwaggerProperties swaggerProps) {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName(swaggerProps.getContactName())
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerProps.getBasePackage()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo(swaggerProps))
            .useDefaultResponseMessages(false)
            .additionalModels(ResolvedObjectType.create(ErrorResponse.class, null, null, null));
    }
    //@formatter:on

    //@formatter:off
    private ApiInfo apiInfo(SpringfoxSwaggerProperties swaggerProps) {
        return new ApiInfoBuilder()
            .title(swaggerProps.getAppName())
            .version(swaggerProps.getAppVersion())
            .description(swaggerProps.getApiDescription())
            .contact(new Contact(swaggerProps.getContactName(), swaggerProps.getContactSite(), swaggerProps.getContactEmail()))
            .build();
    }
    //@formatter:on

    @Controller
    class SpringFoxSwaggerController {
        @GetMapping(path = { "/" })
        public final String redirectToApiDocumentation() {
            return "redirect:/swagger-ui.html";
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "itrain.swagger")
    SpringfoxSwaggerProperties swaggerProps() {
        return new SpringfoxSwaggerProperties();
    }

    @Setter
    @Getter
    class SpringfoxSwaggerProperties {
        private String appName;
        private String appVersion;
        private String basePackage;
        private String contactName;
        private String contactSite;
        private String contactEmail;
        private String apiDescription;
    }

}
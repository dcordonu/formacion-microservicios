package com.hiberus.show.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ShowApiConfig {

    private static final String DESCRIPTION = "API to navigate shows info in our database";

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hiberus.show.api.controller"))
                .build()
                .forCodeGeneration(true)
                .apiInfo(new ApiInfoBuilder().title("Shows API").description(DESCRIPTION).version("v1").build());
    }
}

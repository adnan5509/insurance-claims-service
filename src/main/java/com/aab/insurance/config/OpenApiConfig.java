package com.aab.insurance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI insuranceClaimsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Insurance Claims Service API")
                        .description("API documentation for the Insurance Claims Service")
                        .version("v0.0.1"));
    }
}

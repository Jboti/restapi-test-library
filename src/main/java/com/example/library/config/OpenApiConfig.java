package com.example.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(
                    new Info()
                            .title("Library Management API")
                            .description("REST API Demo for managing books in a library system.")
                            .version("1.0")
                            .contact(
                                    // Only using "Example" for Demo
                                    new Contact()
                                            .name("example")
                                            .email("example@example.com")
                            )

                );
    }
}

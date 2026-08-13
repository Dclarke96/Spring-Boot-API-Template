package com.dylanclarke.springbootapitemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springBootApiTemplateOpenAPI() {

        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()

                // Apply JWT authentication requirement to API documentation
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )

                // Define JWT Bearer authentication scheme
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )

                // API Metadata
                .info(new Info()
                        .title("Spring Boot API Template")
                        .description(
                                "REST API template for building secure, maintainable Spring Boot services."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Dylan Clarke")
                                .url("https://github.com/Dclarke96"))
                        .license(new License()
                                .name("MIT License")
                        )
                );
    }
}
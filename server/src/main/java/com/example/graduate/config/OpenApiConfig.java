 package com.example.graduate.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

@Configuration
public class OpenApiConfig {

    // Đọc giá trị từ application.properties hoặc application.yml
    @Value("${server.url.local}")
    private String localServerUrl;

    @Value("${server.url.production}")
    private String productionServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Graduate API")
                        .version("1.0.0")
                        .description("This is a RESTful API for an e-commerce application built with Java Spring Boot. It provides endpoints for managing products, categories, users, orders, and more. The API follows best practices for RESTful design and includes features such as authentication, authorization, and data validation.")
                )
                .servers(List.of(
                        new Server().url(localServerUrl).description("Local Development Server"),
                        new Server().url(productionServerUrl).description("Production Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key")) 
                .components(
                    new Components()
                        .addSecuritySchemes("bearer-key", 
                            new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("bearer-key") // Name used to reference this scheme
                        )
                );
    }
}

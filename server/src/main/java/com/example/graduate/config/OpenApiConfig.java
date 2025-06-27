 package com.example.graduate.config;
// import io.swagger.v3.oas.annotations.OpenAPIDefinition;
// import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
// import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
// import io.swagger.v3.oas.annotations.info.Info;
// import io.swagger.v3.oas.annotations.security.SecurityScheme;
// import io.swagger.v3.oas.annotations.servers.Server;
// import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;

// @OpenAPIDefinition(
//     info = @Info(
//         title = "Graduate API",
//         version = "1.0.0",
//         description = "This is a RESTful API for an e-commerce application built with Java Spring Boot. It provides endpoints for managing products, categories, users, orders, and more. The API follows best practices for RESTful design and includes features such as authentication, authorization, and data validation."
//     ),
//     servers = {
//         @Server(url = "http://localhost:8088", description = "Local Development Server"),
//         @Server(url = "http://45.117.179.16:8088", description = "Production Server"),
//     }
// )

// @SecurityScheme(
//     name = "bearer-key", // Can be any name, used to reference this scheme in the
//     // @SecurityRequirement annotation
//     type = SecuritySchemeType.HTTP,
//     scheme = "bearer",
//     bearerFormat = "JWT",
//     in = SecuritySchemeIn.HEADER
// )
// @Configuration
// public class OpenApiConfig {

// }
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

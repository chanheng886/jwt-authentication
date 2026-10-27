package com.example.backend.config;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class OpenApiConfig {
    private static final String SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("P-Shop Solar Backend API")
                        .description("REST API documentation with JWT authentication")
                        .version("v1.0"))
                // Apply bearerAuth globally across all endpoints
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

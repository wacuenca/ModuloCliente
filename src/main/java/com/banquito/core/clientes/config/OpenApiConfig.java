package com.banquito.core.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Api de gestión de Clientes")
                .version("1.0.0")
                .description("Microservicio para gestión de clientes, incluyendo personas y empresas.")
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("desarrollo@espe.edu.ec"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")));
    }
}
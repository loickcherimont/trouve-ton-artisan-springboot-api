package com.github.loickcherimont.trouve_ton_artisan_springboot_api.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Trouve ton artisan API")
                        .description("API Spring Boot to connect homeowners with craftsmen from the " +
                                "Auvergne-Rhône-Alpes region.")
                        .version("1.0")
                        .contact(new Contact().name("Loick CHERIMONT").email("loickcherimont@gmail.com")
                                .url("https://github.com/loickcherimont"))
                        .license(new License().name("MIT License")
                                .url("https://github.com/loickcherimont/trouve-ton-artisan-springboot-api/blob/main/LICENSE")));
    }

}

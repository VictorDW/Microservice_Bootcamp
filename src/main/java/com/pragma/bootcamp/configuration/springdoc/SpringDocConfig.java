package com.pragma.bootcamp.configuration.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
        .info(new Info()
            .title("Bootcamp Microservice")
            .version("1.0.0")
            .description("bootcamp microservice of the OnClass application, " +
                "contains the functionalities related to the creation of technologies, capabilities, bootcamps and version of these, " +
                "as well as being able to list each of these.")
            .contact(new Contact()
                .name("Developer Victor Agudelo")
                .email("victor.agudelo.dw@gmail.com")));
  }
}

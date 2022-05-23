package com.kmong.project.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI springShopOpenAPI() {
		
		SecurityScheme securityScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER).name("Authorization");
		
		return new OpenAPI()
				.info(new Info().title("kmong project").description("kmong project application").version("v0.0.1"))
				.components(new Components()
				          .addSecuritySchemes("Authorization", securityScheme))
				.addSecurityItem(new SecurityRequirement().addList("Authorization"));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("kmong").pathsToMatch("/**").build();
	}
}

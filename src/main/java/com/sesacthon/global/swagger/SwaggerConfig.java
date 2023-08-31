package com.sesacthon.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("위즈 API")
            .description("foreco팀의 위즈 API 입니다.")
            .version("v0.0.1"));
  }

   @Bean
   public GroupedOpenApi mockApi() {
       return GroupedOpenApi.builder()
               .group("mock-apis")
               .pathsToMatch("/mock/**")
               .build();
   }

   @Bean
  public GroupedOpenApi realApi() {
    return GroupedOpenApi.builder()
        .group("real-apis")
        .pathsToMatch("/**")
        .pathsToExclude("/mock/**")
        .build();
   }

}

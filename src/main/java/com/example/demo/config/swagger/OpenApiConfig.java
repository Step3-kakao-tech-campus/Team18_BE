package com.example.demo.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        Info info = new Info()
                .title("Garden API 명세서")
                .version(version)
                .description("가드너 팀의 Garden API 명세서");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

//    private static final String SERVICE_NAME = "Garden";
//    private static final String API_VERSION = "0.0.1";
//    private static final String API_DESCRIPTION = "가드너 팀의 Garden API 명세서";
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(SERVICE_NAME)
//                .version(API_VERSION)
//                .description(API_DESCRIPTION)
//                .build();
//    }
}

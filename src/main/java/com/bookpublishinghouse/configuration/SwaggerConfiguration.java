package com.bookpublishinghouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket buildSwaggerConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.bookpublishinghouse"))
                .build()
                .apiInfo(getApiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private ApiInfo getApiInfo() {

        return new ApiInfoBuilder()
                .title("Book Publishing House API")
                .description("This documentation contains all information related to the Book Publishing House API")
                .version("1.0.0")
                .contact(new Contact("Dominik Morožin",
                                     "https://www.linkedin.com/in/dominik-morozin/",
                                     "domorozin@gmail.com"))
                .build();

    }

    private ApiKey apiKey() {

        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {

        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {

        AuthorizationScope[] authorizationScopes = {
                new AuthorizationScope("author", "access author routes"),
                new AuthorizationScope("admin", "access admin routes")
        };
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}

package com.great.deploy.dolpin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//  my-app.client-id=myApp
//  my-app.client-secret=pass
  @Value("${my-app.client-id}")
  private String clientId;
  @Value("${my-app.client-secret}")
  private String clientSecret;

  @Bean
  public Docket api() {

    List<ResponseMessage> list = new java.util.ArrayList<>();
    list.add(new ResponseMessageBuilder().code(500).message("500 message")
            .responseModel(new ModelRef("Result")).build());
    list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
            .responseModel(new ModelRef("Result")).build());
    list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
            .responseModel(new ModelRef("Result")).build());

    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.great.deploy.dolpin"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Collections.singletonList(securitySchema()))
            .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
            .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
            .globalResponseMessage(RequestMethod.POST, list);
    }


  private OAuth securitySchema() {

    List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
    authorizationScopeList.add(new AuthorizationScope("read", "read all"));
    authorizationScopeList.add(new AuthorizationScope("write", "access all"));

    List<GrantType> grantTypes = new ArrayList<>();
    GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080/oauth/token");

    grantTypes.add(creGrant);

    return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.ant("/api/**"))
            .build();
  }

  private List<SecurityReference> defaultAuth() {

    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
    authorizationScopes[0] = new AuthorizationScope("read", "read all");
    authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
    authorizationScopes[2] = new AuthorizationScope("write", "write all");

    return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
  }

  @Bean
  public SecurityConfiguration securityInfo() {
    return new SecurityConfiguration(clientId, clientSecret, "", "", "",
            ApiKeyVehicle.HEADER, "", " ");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Dolpin")
            .description("")
            .termsOfServiceUrl("https://www.dolpin.club/api")
            .contact(new Contact("Hasson", "http://www.example.com", "hasson@example.com"))
            .license("Open Source").licenseUrl("https://www.example.com").version("1.0.0").build();
  }
}

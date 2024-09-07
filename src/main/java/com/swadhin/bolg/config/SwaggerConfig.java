package com.swadhin.bolg.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Blogging Application APIs",
        description = "This API is for the Blogging Application",
        termsOfService = "Terms and conditions apply",
        contact = @Contact(
            name = "Swadhin Swain",
            email = "swainswadhin353@gmail.com",
            url = "https://swadhin.com"
        ),
        license = @License(
            name = "Swadhin License",
            url = "https://swadhin.com/license"
        ),
        version = "v1"
    ),
    servers = {
        @Server(description = "Local API", url = "http://localhost:8080/")
    },
    security = @SecurityRequirement(name = "blogAppSecurity")
)
@SecurityScheme(
    name = "blogAppSecurity",
    in = SecuritySchemeIn.HEADER,
    type = SecuritySchemeType.HTTP,
    scheme = "Bearer",
    bearerFormat = "JWT",
    description = "JWT-based authentication for the Blogging Application"
    
)
public class SwaggerConfig {
    // This class is used to configure Swagger with OpenAPI definitions
}

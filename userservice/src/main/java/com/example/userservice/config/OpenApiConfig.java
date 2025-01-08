package com.example.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "User service API",
                description = "Document api for user service",
                version = "1.0.0",
                contact = @Contact(
                        name = "Nguyen Huu Loc",
                        email = "lockbkbang@gmail.com",
                        url = "https://www.facebook.com/nguyen.huu.loc.888963"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                )
        )
)
public class OpenApiConfig {
}

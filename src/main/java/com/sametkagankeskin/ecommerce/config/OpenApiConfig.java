package com.sametkagankeskin.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Btk Akademi - Innova"), description = "Samet Kagan Keskin", title = "Btk Akademi - Innova Final Proje"), servers = {
                @Server(description = "Local ENV", url = "http://localhost:8080"),

})

public class OpenApiConfig {

}

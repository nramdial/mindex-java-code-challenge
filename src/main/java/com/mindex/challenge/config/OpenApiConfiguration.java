package com.mindex.challenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Mindex Java Code Challenge",
            description = "Mindex Java Code Challenge for sample Employee CRM",
            version = "v1",
            contact =
                @Contact(name = "Nathan Ramdial", url = "", email = "nathan.ramdial@gmail.com")),
    servers = @Server(url = "http://localhost:8080"))
class OpenApiConfiguration {}

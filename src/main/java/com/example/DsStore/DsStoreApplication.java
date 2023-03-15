package com.example.DsStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DSstore API", description = "API for managing DSstore resources", version = "1.0"), servers = @Server(url = "http://localhost:9090", description = "Local server"))
public class DsStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsStoreApplication.class, args);
	}
}

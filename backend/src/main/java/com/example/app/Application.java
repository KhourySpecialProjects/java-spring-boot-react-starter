package com.example.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Server "/" keeps the generated frontend client on relative URLs, so requests
// go through the Vite proxy in dev and nginx in deploy.
@OpenAPIDefinition(info = @Info(title = "API", version = "1.0.0"), servers = @Server(url = "/"))
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

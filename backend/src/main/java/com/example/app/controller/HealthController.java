package com.example.app.controller;

import com.example.app.dto.HealthDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

  private final JdbcClient jdbcClient;

  public HealthController(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  @Operation(operationId = "getHealth")
  @ApiResponse(responseCode = "200", description = "The API and database are reachable")
  @GetMapping
  public HealthDTO getHealth() {
    jdbcClient.sql("SELECT 1").query(Integer.class).single();
    return new HealthDTO("ok");
  }
}

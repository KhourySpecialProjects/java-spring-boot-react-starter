package com.example.app.controller;

import com.example.app.dto.HealthDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

  @Operation(operationId = "getHealth")
  @ApiResponse(responseCode = "200", description = "The API is reachable")
  @GetMapping
  public HealthDTO getHealth() {
    return new HealthDTO("ok");
  }
}

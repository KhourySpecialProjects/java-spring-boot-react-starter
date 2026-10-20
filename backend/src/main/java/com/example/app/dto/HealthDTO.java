package com.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "Health",
    requiredProperties = {"status"})
public record HealthDTO(@Schema(allowableValues = "ok") String status) {}

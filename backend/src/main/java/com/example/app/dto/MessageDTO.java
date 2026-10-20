package com.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// name sets the type name in the generated frontend client.
@Schema(
    name = "Message",
    requiredProperties = {"id", "text", "createdAt"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
  private int id;
  private String text;
  private Instant createdAt;
}

package com.example.app.controller;

import com.example.app.dto.MessageDTO;
import com.example.app.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  // operationId becomes the function name in the generated frontend client.
  @Operation(operationId = "listMessages")
  @ApiResponse(responseCode = "200", description = "The 50 most recent messages")
  @GetMapping
  public List<MessageDTO> listMessages() {
    return messageService.listMessages();
  }
}

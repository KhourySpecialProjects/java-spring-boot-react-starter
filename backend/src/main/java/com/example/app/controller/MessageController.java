package com.example.app.controller;

import com.example.app.dto.MessageDTO;
import com.example.app.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // operationId becomes the function name in the generated frontend client.
  @Operation(operationId = "listMessages")
  @GetMapping
  public List<MessageDTO> listMessages() {
    return messageService.listMessages();
  }
}

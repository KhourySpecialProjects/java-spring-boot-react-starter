package com.example.app.dao;

import com.example.app.dto.MessageDTO;
import com.example.app.entity.Message;
import com.example.app.repository.MessageRepository;
import java.util.List;
import org.springframework.stereotype.Component;

/** The only class that talks to {@link MessageRepository}. Hands DTOs, never entities, upward. */
@Component
public class MessageDAO {

  private final MessageRepository repository;

  public MessageDAO(MessageRepository repository) {
    this.repository = repository;
  }

  public List<MessageDTO> findAll() {
    return repository.findAllByOrderByCreatedAtDescIdDesc().stream()
        .map(MessageDAO::toDTO)
        .toList();
  }

  private static MessageDTO toDTO(Message message) {
    return MessageDTO.builder()
        .id(message.getId())
        .text(message.getText())
        .createdAt(message.getCreatedAt())
        .build();
  }
}

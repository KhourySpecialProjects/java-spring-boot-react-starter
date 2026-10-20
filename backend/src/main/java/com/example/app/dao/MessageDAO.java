package com.example.app.dao;

import com.example.app.dto.MessageDTO;
import com.example.app.entity.Message;
import com.example.app.repository.MessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDAO {

  private final MessageRepository repository;

  public List<MessageDTO> findLatest() {
    return repository.findLatest().stream().map(MessageDAO::toDTO).toList();
  }

  private static MessageDTO toDTO(Message message) {
    return MessageDTO.builder()
        .id(message.getId())
        .text(message.getText())
        .createdAt(message.getCreatedAt())
        .build();
  }
}

package com.example.app.service;

import com.example.app.dao.MessageDAO;
import com.example.app.dto.MessageDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageDAO messageDAO;

  public List<MessageDTO> listMessages() {
    return messageDAO.findLatest();
  }
}

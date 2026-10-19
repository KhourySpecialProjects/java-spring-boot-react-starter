package com.example.app.service;

import com.example.app.dao.MessageDAO;
import com.example.app.dto.MessageDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageDAO messageDAO;

  public MessageService(MessageDAO messageDAO) {
    this.messageDAO = messageDAO;
  }

  public List<MessageDTO> listMessages() {
    return messageDAO.findAll();
  }
}

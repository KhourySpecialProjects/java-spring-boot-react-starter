package com.example.app.repository;

import com.example.app.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findAllByOrderByCreatedAtDescIdDesc();
}

package com.example.app.repository;

import com.example.app.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

  @Query(
      value = "SELECT * FROM messages ORDER BY created_at DESC, id DESC LIMIT 50",
      nativeQuery = true)
  List<Message> findLatest();
}

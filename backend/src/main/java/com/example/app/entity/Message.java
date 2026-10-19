package com.example.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

// @Getter/@Setter because Lombok's equals/hashCode break JPA entities.
@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "text")
  private String text;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;
}

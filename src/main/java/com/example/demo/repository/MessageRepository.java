package com.example.demo.repository;

import com.example.demo.model.Message;
import com.example.demo.model.enums.SelectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Mensagens por seção (chat, books, games, etc)
    List<Message> findBySelectionTypeOrderByCreatedAtAsc(SelectionType selectionType);
}

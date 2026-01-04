package com.example.demo.controller;

import com.example.demo.model.enums.SelectionType;
import com.example.demo.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SectionController {

    private final MessageRepository messageRepository;

    public SectionController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 📌 Controller genérico para todas as seções
    @GetMapping("/{section}")
    public String section(
            @PathVariable String section,
            Model model
    ) {

        SelectionType type;

        try {
            // converte "books" -> BOOKS
            type = SelectionType.valueOf(section.toUpperCase());
        } catch (IllegalArgumentException e) {
            // se a rota não existir
            return "redirect:/chat";
        }

        model.addAttribute(
                "messages",
                messageRepository.findBySelectionTypeOrderByCreatedAtAsc(type)
        );

        model.addAttribute("section", section);

        return "sections/" + section;
    }
}

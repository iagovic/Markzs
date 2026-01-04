package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.model.enums.SelectionType;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatController(MessageRepository messageRepository,
                          UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // 📌 Exibe o chat
    @GetMapping
    public String chat(Model model) {
        model.addAttribute(
                "messages",
                messageRepository.findBySelectionTypeOrderByCreatedAtAsc(SelectionType.CHAT)
        );
        return "chat/index";
    }

    // 📌 Envia mensagem no chat
    @PostMapping
    public String sendMessage(@RequestParam("content") String content,
                              @AuthenticationPrincipal UserDetails userDetails) {

        if (content == null || content.trim().isEmpty()) {
            return "redirect:/chat";
        }

        User author = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow();

        Message message = new Message();
        message.setContent(content);
        message.setSelectionType(SelectionType.CHAT);
        message.setAuthor(author);

        messageRepository.save(message);

        return "redirect:/chat";
    }
}

package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/get-by-receiver-and-sender-id/{receiverId}")
    public ResponseEntity<?> getAllMessages(@PathVariable Integer receiverId, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        return messageService.getMessages(receiverId, user.getId());
    }
}
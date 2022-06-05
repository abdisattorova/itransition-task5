package com.example.chatapp.controller;

import com.example.chatapp.dto.MessageDto;
import com.example.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;

    @MessageMapping("/send-message")
    public void sendMessage(MessageDto messageDto) throws Exception {
        messageService.send(messageDto);
    }

}
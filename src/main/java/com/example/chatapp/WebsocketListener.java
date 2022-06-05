package com.example.chatapp;

import com.example.chatapp.controller.UserController;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

/**
 * Abdulqodir Ganiev 5/14/2022 12:43 PM
 */

@RequiredArgsConstructor
@Component
public class WebsocketListener {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate template;
    private final UserController userController;

    @EventListener
    public void evenConnected(SessionConnectedEvent event) {

        for (User user : userRepository.findAll()) {

            template.convertAndSendToUser(
                    user.getId().toString(),
                    "/contact",
                    userController.getAllUsers().getBody()
            );

        }
    }
}

package com.example.chatapp.service;

import com.example.chatapp.dto.MessageDto;
import com.example.chatapp.entity.ChatRoom;
import com.example.chatapp.entity.Message;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.ChatRoomRepository;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;

    public ResponseEntity<?> getMessages(Integer receiverId, Integer senderId) {

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceAccessException("User not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        Integer chatId = chatRoomService.getChatId(receiver, sender, false);

        List<Message> messageList = messageRepository.findAllByChatId(chatId);
        List<MessageDto> messageDtos = messageList.stream().map(message -> new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getText(),
                message.getReceiver().getId(),
                message.getReceiver().getName(),
                message.getSender().getId(),
                message.getSender().getName()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(messageDtos);

    }

    public void send(MessageDto messageDto)  {

        User receiver = userRepository.findById(messageDto.getReceiverId())
                .orElseThrow(() -> new ResourceAccessException("User not found"));
        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new ResourceAccessException("User not found"));

        Message msg = messageRepository.save(new Message(
                        messageDto.getTitle(),
                        messageDto.getText(),
                        receiver,
                        sender,
                        chatRoomService.getChatId(
                                receiver,
                                sender,
                                true)
                )
        );


        messageDto.setId(msg.getId());

        messagingTemplate.convertAndSendToUser(
                messageDto.getReceiverId().toString(),
                "/queue/messages",
                messageDto
        );

    }
}

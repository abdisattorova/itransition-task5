package com.example.chatapp.service;

import com.example.chatapp.entity.ChatRoom;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Integer getChatId(User receiver, User sender, boolean shouldCreateChatRoom) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByReceiverAndSender(receiver, sender);

        if (optionalChatRoom.isPresent())
            return optionalChatRoom.get().getChatId();
        else if (!shouldCreateChatRoom)
            return null;

        Integer generatedChatId = (int) (Math.random() * 100_000);


        if (sender.getId().equals(receiver.getId())){
            ChatRoom chatRoom = new ChatRoom(
                    generatedChatId,
                    receiver,
                    sender
            );

            ChatRoom save = chatRoomRepository.save(chatRoom);
            return save.getChatId();
        }

        ArrayList<ChatRoom> chatRooms = new ArrayList<>();

        ChatRoom chatRoom = new ChatRoom(
                generatedChatId,
                receiver,
                sender
        );
        ChatRoom chatRoom2 = new ChatRoom(
                generatedChatId,
                sender,
                receiver);

        chatRooms.add(chatRoom);
        if (!sender.equals(receiver))
            chatRooms.add(chatRoom2);

        List<ChatRoom> savedChatRooms = chatRoomRepository.saveAll(chatRooms);

        if (savedChatRooms.get(1).getChatId().equals(savedChatRooms.get(0).getChatId()))
            return savedChatRooms.get(0).getChatId();
        else
            return null;
    }
}

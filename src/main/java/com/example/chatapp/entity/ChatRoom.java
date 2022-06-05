package com.example.chatapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer chatId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    public ChatRoom(Integer chatId, User receiver, User sender) {
        this.chatId = chatId;
        this.receiver = receiver;
        this.sender = sender;
    }
}

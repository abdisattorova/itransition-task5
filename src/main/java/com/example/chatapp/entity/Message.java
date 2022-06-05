package com.example.chatapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Entity(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;
    String text;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    @ManyToOne
    User sender;

    Integer chatId;

    @Enumerated(EnumType.STRING)
    MessageStatus status;

    LocalDateTime time = LocalDateTime.now();

    public Message(String text, User receiver) {
        this.text = text;
        this.receiver = receiver;
    }

    public Message(String title,String text, User receiver, User sender, Integer chatId) {
        this.title=title;
        this.text = text;
        this.receiver = receiver;
        this.sender = sender;
        this.chatId = chatId;
    }


}

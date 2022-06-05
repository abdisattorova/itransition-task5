package com.example.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class MessageDto {

    private Integer id;
    private String title;
    private String text;
    private Integer receiverId;
    private String receiverName;
    private Integer senderId;
    private String senderName;


}

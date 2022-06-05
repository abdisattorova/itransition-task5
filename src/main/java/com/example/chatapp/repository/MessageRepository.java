package com.example.chatapp.repository;

import com.example.chatapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

   List<Message> findAllByReceiverId(Integer receiverId);
   List<Message> findAllByChatId(Integer chatId);


}

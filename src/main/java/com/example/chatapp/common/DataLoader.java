package com.example.chatapp.common;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {


            userRepository.save(
                    new User(
                            1,
                            "asadbek",
                            false,
                            LocalDateTime.now()
                    )
            );

            userRepository.save(
                    new User(
                            2,
                            "umid",
                            false,
                            LocalDateTime.now()
                    )
            );
        }
    }
}

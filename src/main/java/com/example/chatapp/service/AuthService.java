package com.example.chatapp.service;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User findByName(User userDto) {

        if (userRepository.findByName(userDto.getName()).isPresent()) {
            return userRepository.findByName(userDto.getName()).get();
        }

        User user = new User();
        user.setName(userDto.getName());
        return userRepository.save(user);
    }
}

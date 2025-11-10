package com.example.my_project.service;

import com.example.my_project.dto.UserById;
import com.example.my_project.dto.UserDto;
import com.example.my_project.model.User;
import com.example.my_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    public UserService(UserRepository userRepository, UserEventProducer userEventProducer) {
        this.userRepository = userRepository;
        this.userEventProducer = userEventProducer;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        String userJson = String.format("{\"id\": %d, \"email\": \"%s\", \"fullName\": \"%s\"}",
                savedUser.getId(), savedUser.getEmail(), savedUser.getFullName());
        userEventProducer.sendUserCreatedEvent(userJson);
        return savedUser;
    }

    public UserById getUserById(Long id) {
        return this.userRepository.findById(id).map( user -> new UserById(user.getId(), user.getFullName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).map(user -> new UserDto(user.getId(), user.getEmail(), user.getFullName()));
    }
}

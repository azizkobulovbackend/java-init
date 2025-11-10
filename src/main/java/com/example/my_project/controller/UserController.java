package com.example.my_project.controller;

import com.example.my_project.dto.UserById;
import com.example.my_project.dto.UserDto;
import com.example.my_project.model.User;
import com.example.my_project.service.MessageProducer;
import com.example.my_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final MessageProducer producer;

    public UserController(UserService userService, MessageProducer producer) {
        this.userService = userService;
        this.producer = producer;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        Optional<UserDto> user = this.userService.getUserByEmail(email);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserById> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/kafka")
    public String sendMessage(@RequestParam String message) {
        producer.sendMessage("my-topic", message);
        return "Message sent successfully!";
    }
}

package com.example.my_project.controller;

import com.example.my_project.dto.UserInfoResponse;
import com.example.my_project.model.User;
import com.example.my_project.repository.TaskRepository;
import com.example.my_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User route")
public class UserController {
    private final UserService UserService;
    private final TaskRepository taskRepository;

    @Operation(summary = "User data", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/me")
    public UserInfoResponse getUserInfo(@AuthenticationPrincipal User user) {
        return new UserInfoResponse(taskRepository.findByUser(user), user);
    }
}

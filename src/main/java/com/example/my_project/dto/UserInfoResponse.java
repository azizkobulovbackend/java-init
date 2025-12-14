package com.example.my_project.dto;

import com.example.my_project.model.Task;
import com.example.my_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private List<Task> tasks;
    private User user;
}

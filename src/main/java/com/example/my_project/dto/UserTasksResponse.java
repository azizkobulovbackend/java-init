package com.example.my_project.dto;

import com.example.my_project.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserTasksResponse {
    private Long userId;
    private List<Task> tasks;
}

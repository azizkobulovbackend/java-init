package com.example.my_project.service;

import com.example.my_project.dto.CreateTaskRequest;
import com.example.my_project.dto.UpdateTaskRequest;
import com.example.my_project.dto.UserTasksResponse;
import com.example.my_project.model.Task;
import com.example.my_project.model.User;
import com.example.my_project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(CreateTaskRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        return taskRepository.save(task);
    }

    public UserTasksResponse getUserTasks() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Task> tasks = taskRepository.findByUser(user);

        return new UserTasksResponse(user.getId(), tasks);

    }

    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public Task updateTaskById(UUID id, UpdateTaskRequest body) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if(body.getDescription() != null) task.setDescription(body.getDescription());
        if(body.getTitle() != null) task.setTitle(body.getTitle());

        return taskRepository.save(task);
    }

    public void deleteTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskRepository.delete(task);
    }
}

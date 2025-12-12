package com.example.my_project.controller;

import com.example.my_project.dto.CreateTaskRequest;
import com.example.my_project.dto.UpdateTaskRequest;
import com.example.my_project.dto.UserTasksResponse;
import com.example.my_project.model.Task;
import com.example.my_project.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create task", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Get User Tasks", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping
    public ResponseEntity<UserTasksResponse> getUserTasks() {
        return ResponseEntity.ok(taskService.getUserTasks());
    }

    @Operation(summary = "Get task by id", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Update task by id", security = { @SecurityRequirement(name = "bearerAuth") })
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable UUID id, @RequestBody UpdateTaskRequest body) {
        Task task = taskService.updateTaskById(id, body);

        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Delete task by id", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateTaskById(@PathVariable UUID id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }


}

package com.example.my_project.dto;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
}
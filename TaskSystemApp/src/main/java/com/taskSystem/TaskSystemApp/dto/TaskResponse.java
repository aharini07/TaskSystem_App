package com.taskSystem.TaskSystemApp.dto;

import com.taskSystem.TaskSystemApp.enums.Priority;
import com.taskSystem.TaskSystemApp.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        Priority priority,
        Status status,
        String category,
        String assignedTo,
        LocalDateTime createdt,
        LocalDateTime updatedAt

) {
}

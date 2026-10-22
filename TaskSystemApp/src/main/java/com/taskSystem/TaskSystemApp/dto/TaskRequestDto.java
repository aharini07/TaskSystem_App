package com.taskSystem.TaskSystemApp.dto;

import com.taskSystem.TaskSystemApp.enums.Priority;
import com.taskSystem.TaskSystemApp.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;


public record TaskRequestDto(
        @NotBlank(message = "Title is needed")
        String title,
        String description,

        @NotNull(message = "due date required")
        LocalDate dueDate,

        @NotNull(message = "priority needed")
        Priority priority,

        @NotNull(message= "status required")
        Status status,

        @NotBlank(message = "category required")
        String category,

        @NotBlank(message = "Assigned person info needed")
        String assignedTo

) {

}

package com.taskSystem.TaskSystemApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "usernme required")
        @Size(min=4,max=15,message = "Username must be between 4 and 15 character")
        String username,

        @NotBlank(message = "password required")
        @Size(min=6,message = "Password must be atleast 6 characters")
        String password
) {
}

package com.taskSystem.TaskSystemApp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.taskSystem.TaskSystemApp.dto.TaskRequestDto;
import com.taskSystem.TaskSystemApp.dto.TaskResponse;
import com.taskSystem.TaskSystemApp.model.Users;
import com.taskSystem.TaskSystemApp.repository.UserRepository;
import com.taskSystem.TaskSystemApp.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    private Users getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // CREATE A TASK
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequestDto dto) {
        TaskResponse response = taskService.createTask(getCurrentUser(), dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // GET TASK BY ID
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        TaskResponse response = taskService.getTaskById(taskId, getCurrentUser());

        return ResponseEntity.ok(response);
    }

    // GET ALL TASKS (Paginated)
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(@PageableDefault(size = 10, sort = "dueDate") Pageable pageable) {

        Page<TaskResponse> tasks = taskService.getAllTasks(getCurrentUser(), pageable);

        return ResponseEntity.ok(tasks);
    }

    // UPDATE A TASK
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequestDto dto) {
        TaskResponse response = taskService.updateTask(taskId, dto, getCurrentUser());
        return ResponseEntity.ok(response);
    }
    // DELETE A TASK
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}
package com.taskSystem.TaskSystemApp.model;

import com.taskSystem.TaskSystemApp.enums.Priority;
import com.taskSystem.TaskSystemApp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is needed")
    @Column(nullable=false)
    private String Title;

    @Column(length = 1500)
    private String description;

    @NotNull(message="Due date is required")
    @Column(name="due_date",nullable = false)
    private LocalDate due_date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Priority should not be empty")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status required")
    @Column(nullable = false)
    private Status status;

    @NotNull(message = "category required")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "Should assign to a person info required")
    @Column(nullable = false)
    private String assignedTo;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false,updatable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isDeleted=false;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();


    }

    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}

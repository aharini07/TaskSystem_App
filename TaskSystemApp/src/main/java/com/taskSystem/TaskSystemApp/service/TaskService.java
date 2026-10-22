package com.taskSystem.TaskSystemApp.service;

import com.taskSystem.TaskSystemApp.dto.TaskRequestDto;
import com.taskSystem.TaskSystemApp.dto.TaskResponse;
import com.taskSystem.TaskSystemApp.exception.OwnershipException;
import com.taskSystem.TaskSystemApp.exception.ResourceNotFoundException;
import com.taskSystem.TaskSystemApp.mapper.TaskMapper;
import com.taskSystem.TaskSystemApp.model.Task;
import com.taskSystem.TaskSystemApp.model.Users;
import com.taskSystem.TaskSystemApp.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponse createTask(Users user, @Valid TaskRequestDto req){
        Task task=taskMapper.convertDtoToEntity(req);
        task.setUser(user);
        task.setDeleted(false);

        Task savedTask=taskRepository.save(task);
        return taskMapper.convertEntityToDto(savedTask);
    }

    @Transactional
    public Page<TaskResponse>getAllTasks(Users user,Pageable pageable){
        return taskRepository.findActiveTasksByUser(user,pageable)
                .map(taskMapper::convertEntityToDto);
    }

    @Transactional
    public TaskResponse getTaskById(Long id,Users user) {
        Task task = taskRepository.findActiveTaskByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id + " of user " + user));

        return taskMapper.convertEntityToDto(task);
    }
    @Transactional
    public TaskResponse updateTask(Long id,@Valid TaskRequestDto req,Users user){

        Task task=taskRepository.findActiveTaskByIdAndUser(id,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found with id : "+id));

        //checking the ownership of the task
        if (!task.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("You dont own this task..");
        }

        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setDue_date(req.dueDate());
        task.setPriority(req.priority());
        task.setStatus(req.status());
        task.setCategory(req.category());
        task.setAssignedTo(req.assignedTo());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.convertEntityToDto(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id,Users user){
        Task task=taskRepository.findActiveTaskByIdAndUser(id,user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id : "+id));

        task.setDeleted(true);
        taskRepository.save(task);
    }



}

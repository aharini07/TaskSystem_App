package com.taskSystem.TaskSystemApp.mapper;

import com.taskSystem.TaskSystemApp.dto.TaskRequestDto;
import com.taskSystem.TaskSystemApp.dto.TaskResponse;
import com.taskSystem.TaskSystemApp.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task convertDtoToEntity(TaskRequestDto dto){
        if(dto == null) return null;
        Task task=new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDue_date(dto.dueDate());
        task.setStatus(dto.status());
        task.setCategory(dto.category());
        task.setAssignedTo(dto.assignedTo());

        return task;
    }

    public TaskResponse convertEntityToDto(Task entity) {
        if(entity==null) return null;
        return new TaskResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDue_date(),
                entity.getPriority(),
                entity.getStatus(),
                entity.getCategory(),
                entity.getAssignedTo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );

    }
}

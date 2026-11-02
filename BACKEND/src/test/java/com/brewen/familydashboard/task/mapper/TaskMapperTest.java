package com.brewen.familydashboard.task.mapper;

import com.brewen.familydashboard.task.dto.TaskResponse;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    @Test
    void shouldMapTaskToTaskResponse() {
        LocalDate dueDate = LocalDate.of(2026, 10, 15);

        LocalDateTime createdAt =
                LocalDateTime.of(2026, 9, 25, 13, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 9, 25, 13, 30);

        Task task = new Task();

        task.setId(1L);
        task.setTitle("Faire les courses");
        task.setDescription("Acheter du lait et du pain");
        task.setDueDate(dueDate);
        task.setAssignedTo("Brewen");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);

        TaskResponse response = TaskMapper.toResponse(task);

        assertEquals(task.getId(), response.id());
        assertEquals(task.getTitle(), response.title());
        assertEquals(task.getDescription(), response.description());
        assertEquals(task.getDueDate(), response.dueDate());
        assertEquals(task.getAssignedTo(), response.assignedTo());
        assertEquals(task.getStatus(), response.status());
        assertEquals(task.getPriority(), response.priority());
        assertEquals(task.getCreatedAt(), response.createdAt());
        assertEquals(task.getUpdatedAt(), response.updatedAt());
    }
}
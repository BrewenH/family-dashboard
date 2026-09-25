package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        String assignedTo,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

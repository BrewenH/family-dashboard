package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskResponseTest {

    @Test
    void shouldExposeTaskResponseData() {
        LocalDate dueDate = LocalDate.of(2026, 10, 15);

        LocalDateTime createdAt =
                LocalDateTime.of(2026, 9, 25, 13, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 9, 25, 13, 30);

        TaskResponse response = new TaskResponse(
                1L,
                "Faire les courses",
                "Acheter du lait et du pain",
                dueDate,
                "Brewen",
                TaskStatus.TODO,
                TaskPriority.MEDIUM,
                createdAt,
                updatedAt
        );

        assertEquals(1L, response.id());
        assertEquals("Faire les courses", response.title());

        assertEquals(
                "Acheter du lait et du pain",
                response.description()
        );

        assertEquals(dueDate, response.dueDate());
        assertEquals("Brewen", response.assignedTo());
        assertEquals(TaskStatus.TODO, response.status());
        assertEquals(TaskPriority.MEDIUM, response.priority());
        assertEquals(createdAt, response.createdAt());
        assertEquals(updatedAt, response.updatedAt());
    }
}
package com.brewen.familydashboard.task.controller;

import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import com.brewen.familydashboard.task.dto.TaskRequest;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Faire les courses");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);

        when(taskService.getTasks(null, false))
                .thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Faire les courses"))
                .andExpect(jsonPath("$[0].status").value("TODO"))
                .andExpect(jsonPath("$[0].priority").value("MEDIUM"));

        verify(taskService).getTasks(null, false);
    }

    @Test
    void shouldCreateTask() throws Exception {
        String requestBody = """
            {
              "title": "Faire les courses",
              "description": "Acheter du lait et du pain",
              "dueDate": "2026-09-30",
              "assignedTo": "Brewen",
              "status": "TODO",
              "priority": "MEDIUM"
            }
            """;

        Task createdTask = new Task();
        createdTask.setId(1L);
        createdTask.setTitle("Faire les courses");
        createdTask.setDescription("Acheter du lait et du pain");
        createdTask.setDueDate(LocalDate.of(2026, 9, 30));
        createdTask.setAssignedTo("Brewen");
        createdTask.setStatus(TaskStatus.TODO);
        createdTask.setPriority(TaskPriority.MEDIUM);

        when(taskService.createTask(any(TaskRequest.class)))
                .thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Faire les courses"))
                .andExpect(jsonPath("$.description").value("Acheter du lait et du pain"))
                .andExpect(jsonPath("$.dueDate").value("2026-09-30"))
                .andExpect(jsonPath("$.assignedTo").value("Brewen"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));

        ArgumentCaptor<TaskRequest> requestCaptor =
                ArgumentCaptor.forClass(TaskRequest.class);

        verify(taskService).createTask(requestCaptor.capture());

        TaskRequest capturedRequest = requestCaptor.getValue();

        assertEquals("Faire les courses", capturedRequest.getTitle());
        assertEquals(
                "Acheter du lait et du pain",
                capturedRequest.getDescription()
        );
        assertEquals(
                LocalDate.of(2026, 9, 30),
                capturedRequest.getDueDate()
        );
        assertEquals("Brewen", capturedRequest.getAssignedTo());
        assertEquals(TaskStatus.TODO, capturedRequest.getStatus());
        assertEquals(TaskPriority.MEDIUM, capturedRequest.getPriority());
    }
    @Test
    void shouldReturnTaskWhenIdExists() throws Exception {
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Sortir les poubelles");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);

        when(taskService.getTaskById(1L))
                .thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sortir les poubelles"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("HIGH"));

        verify(taskService).getTaskById(1L);
    }

    @Test
    void shouldDeleteTaskWhenIdExists() throws Exception {
        when(taskService.deleteTaskById(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/tasks/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTaskById(1L);
    }

    @Test
    void shouldUpdateTaskStatusWhenTaskExists() throws Exception {
        String requestBody = """
            {
              "status": "IN_PROGRESS"
            }
            """;

        Task updatedTask = new Task();

        updatedTask.setId(1L);
        updatedTask.setTitle("Faire les courses");
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);
        updatedTask.setPriority(TaskPriority.MEDIUM);

        when(taskService.updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        )).thenReturn(Optional.of(updatedTask));

        mockMvc.perform(patch("/api/tasks/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Faire les courses"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));

        verify(taskService).updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        );
    }
}
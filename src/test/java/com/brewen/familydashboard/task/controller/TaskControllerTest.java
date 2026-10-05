package com.brewen.familydashboard.task.controller;

import com.brewen.familydashboard.task.dto.TaskResponse;
import com.brewen.familydashboard.task.dto.TaskStatusRequest;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        LocalDate dueDate = LocalDate.of(2026, 10, 15);

        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 2, 12, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 10, 2, 12, 30);

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

        when(taskService.getTasks(null, false))
                .thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].*").value(hasSize(9)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(
                        jsonPath("$[0].title")
                                .value("Faire les courses")
                )
                .andExpect(
                        jsonPath("$[0].description")
                                .value("Acheter du lait et du pain")
                )
                .andExpect(
                        jsonPath("$[0].dueDate")
                                .value("2026-10-15")
                )
                .andExpect(
                        jsonPath("$[0].assignedTo")
                                .value("Brewen")
                )
                .andExpect(
                        jsonPath("$[0].status")
                                .value("TODO")
                )
                .andExpect(
                        jsonPath("$[0].priority")
                                .value("MEDIUM")
                )
                .andExpect(
                        jsonPath("$[0].createdAt")
                                .value("2026-10-02T12:00:00")
                )
                .andExpect(
                        jsonPath("$[0].updatedAt")
                                .value("2026-10-02T12:30:00")
                );

        verify(taskService).getTasks(null, false);
    }

    @Test
    void shouldCreateTask() throws Exception {
        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 2, 12, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 10, 2, 12, 0);

        String requestBody = """
            {
              "title": "Faire les courses",
              "description": "Acheter du lait et du pain",
              "dueDate": "2026-10-15",
              "assignedTo": "Brewen",
              "status": "TODO",
              "priority": "MEDIUM"
            }
            """;

        Task createdTask = new Task();

        createdTask.setId(1L);
        createdTask.setTitle("Faire les courses");
        createdTask.setDescription("Acheter du lait et du pain");
        createdTask.setDueDate(LocalDate.of(2026, 10, 15));
        createdTask.setAssignedTo("Brewen");
        createdTask.setStatus(TaskStatus.TODO);
        createdTask.setPriority(TaskPriority.MEDIUM);
        createdTask.setCreatedAt(createdAt);
        createdTask.setUpdatedAt(updatedAt);

        when(taskService.createTask(any(TaskRequest.class)))
                .thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(9)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(
                        jsonPath("$.title")
                                .value("Faire les courses")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Acheter du lait et du pain")
                )
                .andExpect(
                        jsonPath("$.dueDate")
                                .value("2026-10-15")
                )
                .andExpect(
                        jsonPath("$.assignedTo")
                                .value("Brewen")
                )
                .andExpect(
                        jsonPath("$.status")
                                .value("TODO")
                )
                .andExpect(
                        jsonPath("$.priority")
                                .value("MEDIUM")
                )
                .andExpect(
                        jsonPath("$.createdAt")
                                .value("2026-10-02T12:00:00")
                )
                .andExpect(
                        jsonPath("$.updatedAt")
                                .value("2026-10-02T12:00:00")
                );

        ArgumentCaptor<TaskRequest> requestCaptor =
                ArgumentCaptor.forClass(TaskRequest.class);

        verify(taskService).createTask(requestCaptor.capture());

        TaskRequest capturedRequest = requestCaptor.getValue();

        assertEquals(
                "Faire les courses",
                capturedRequest.getTitle()
        );
        assertEquals(
                "Acheter du lait et du pain",
                capturedRequest.getDescription()
        );
        assertEquals(
                LocalDate.of(2026, 10, 15),
                capturedRequest.getDueDate()
        );
        assertEquals(
                "Brewen",
                capturedRequest.getAssignedTo()
        );
        assertEquals(
                TaskStatus.TODO,
                capturedRequest.getStatus()
        );
        assertEquals(
                TaskPriority.MEDIUM,
                capturedRequest.getPriority()
        );
    }
    @Test
    void shouldReturnTaskWhenIdExists() throws Exception {
        LocalDate dueDate = LocalDate.of(2026, 10, 15);

        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 2, 12, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 10, 2, 12, 30);

        Task task = new Task();

        task.setId(1L);
        task.setTitle("Sortir les poubelles");
        task.setDescription("Sortir les poubelles jaunes");
        task.setDueDate(dueDate);
        task.setAssignedTo("Brewen");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);

        when(taskService.getTaskById(1L))
                .thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(9)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(
                        jsonPath("$.title")
                                .value("Sortir les poubelles")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Sortir les poubelles jaunes")
                )
                .andExpect(
                        jsonPath("$.dueDate")
                                .value("2026-10-15")
                )
                .andExpect(
                        jsonPath("$.assignedTo")
                                .value("Brewen")
                )
                .andExpect(
                        jsonPath("$.status")
                                .value("TODO")
                )
                .andExpect(
                        jsonPath("$.priority")
                                .value("HIGH")
                )
                .andExpect(
                        jsonPath("$.createdAt")
                                .value("2026-10-02T12:00:00")
                )
                .andExpect(
                        jsonPath("$.updatedAt")
                                .value("2026-10-02T12:30:00")
                );

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
        LocalDateTime createdAt =
                LocalDateTime.of(2026, 10, 2, 12, 0);

        LocalDateTime updatedAt =
                LocalDateTime.of(2026, 10, 2, 12, 30);

        String requestBody = """
            {
              "status": "IN_PROGRESS"
            }
            """;

        Task updatedTask = new Task();

        updatedTask.setId(1L);
        updatedTask.setTitle("Faire les courses");
        updatedTask.setDescription("Acheter du lait et du pain");
        updatedTask.setDueDate(LocalDate.of(2026, 10, 15));
        updatedTask.setAssignedTo("Brewen");
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);
        updatedTask.setPriority(TaskPriority.MEDIUM);
        updatedTask.setCreatedAt(createdAt);
        updatedTask.setUpdatedAt(updatedAt);

        when(taskService.updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        )).thenReturn(Optional.of(updatedTask));

        mockMvc.perform(patch("/api/tasks/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(9)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(
                        jsonPath("$.title")
                                .value("Faire les courses")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Acheter du lait et du pain")
                )
                .andExpect(
                        jsonPath("$.dueDate")
                                .value("2026-10-15")
                )
                .andExpect(
                        jsonPath("$.assignedTo")
                                .value("Brewen")
                )
                .andExpect(
                        jsonPath("$.status")
                                .value("IN_PROGRESS")
                )
                .andExpect(
                        jsonPath("$.priority")
                                .value("MEDIUM")
                )
                .andExpect(
                        jsonPath("$.createdAt")
                                .value("2026-10-02T12:00:00")
                )
                .andExpect(
                        jsonPath("$.updatedAt")
                                .value("2026-10-02T12:30:00")
                );

        verify(taskService).updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        );
    }

    @Test
    void shouldReturnTaskResponsesWhenGettingAllTasks() {
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Faire les courses");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);

        when(taskService.getTasks(null, false))
                .thenReturn(List.of(task));

        ResponseEntity<List<TaskResponse>> result =
                taskController.getTasks(null, false);

        List<TaskResponse> responseBody = result.getBody();

        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertInstanceOf(TaskResponse.class, responseBody.getFirst());

        verify(taskService).getTasks(null, false);
    }

    @Test
    void shouldReturnTaskResponseWhenGettingTaskById() {
        Task task = new Task();

        task.setId(1L);
        task.setTitle("Sortir les poubelles");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);

        when(taskService.getTaskById(1L))
                .thenReturn(Optional.of(task));

        ResponseEntity<TaskResponse> result =
                taskController.getTaskById(1L);

        TaskResponse responseBody = result.getBody();

        assertNotNull(responseBody);
        assertEquals(1L, responseBody.id());
        assertEquals("Sortir les poubelles", responseBody.title());
        assertEquals(TaskStatus.TODO, responseBody.status());
        assertEquals(TaskPriority.HIGH, responseBody.priority());

        verify(taskService).getTaskById(1L);
    }

    @Test
    void shouldReturnTaskResponseWhenCreatingTask() {
        TaskRequest request = new TaskRequest();

        request.setTitle("Faire les courses");
        request.setDescription("Acheter du lait et du pain");
        request.setDueDate(LocalDate.of(2026, 10, 15));
        request.setAssignedTo("Brewen");
        request.setStatus(TaskStatus.TODO);
        request.setPriority(TaskPriority.MEDIUM);

        Task createdTask = new Task();

        createdTask.setId(1L);
        createdTask.setTitle("Faire les courses");
        createdTask.setDescription("Acheter du lait et du pain");
        createdTask.setDueDate(LocalDate.of(2026, 10, 15));
        createdTask.setAssignedTo("Brewen");
        createdTask.setStatus(TaskStatus.TODO);
        createdTask.setPriority(TaskPriority.MEDIUM);

        when(taskService.createTask(any(TaskRequest.class)))
                .thenReturn(createdTask);

        ResponseEntity<TaskResponse> result =
                taskController.createTask(request);

        TaskResponse responseBody = result.getBody();

        assertNotNull(responseBody);
        assertEquals(1L, responseBody.id());
        assertEquals("Faire les courses", responseBody.title());
        assertEquals(
                "Acheter du lait et du pain",
                responseBody.description()
        );
        assertEquals(
                LocalDate.of(2026, 10, 15),
                responseBody.dueDate()
        );
        assertEquals("Brewen", responseBody.assignedTo());
        assertEquals(TaskStatus.TODO, responseBody.status());
        assertEquals(TaskPriority.MEDIUM, responseBody.priority());

        verify(taskService).createTask(request);
    }

    @Test
    void shouldReturnTaskResponseWhenUpdatingTaskStatus() {
        TaskStatusRequest request = new TaskStatusRequest();

        request.setStatus(TaskStatus.IN_PROGRESS);

        Task updatedTask = new Task();

        updatedTask.setId(1L);
        updatedTask.setTitle("Faire les courses");
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);
        updatedTask.setPriority(TaskPriority.MEDIUM);

        when(taskService.updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        )).thenReturn(Optional.of(updatedTask));

        ResponseEntity<TaskResponse> result =
                taskController.updateTaskStatus(1L, request);

        TaskResponse responseBody = result.getBody();

        assertNotNull(responseBody);
        assertEquals(1L, responseBody.id());
        assertEquals("Faire les courses", responseBody.title());
        assertEquals(
                TaskStatus.IN_PROGRESS,
                responseBody.status()
        );
        assertEquals(
                TaskPriority.MEDIUM,
                responseBody.priority()
        );

        verify(taskService).updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        );
    }

    @Test
    void shouldReturnNotFoundWhenGettingMissingTask() throws Exception {
        when(taskService.getTaskById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(taskService).getTaskById(999L);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingTask() throws Exception {
        when(taskService.deleteTaskById(999L))
                .thenReturn(false);

        mockMvc.perform(delete("/api/tasks/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(taskService).deleteTaskById(999L);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingStatusOfMissingTask()
            throws Exception {
        String requestBody = """
            {
              "status": "IN_PROGRESS"
            }
            """;

        when(taskService.updateTaskStatus(
                999L,
                TaskStatus.IN_PROGRESS
        )).thenReturn(Optional.empty());

        mockMvc.perform(patch("/api/tasks/{id}/status", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        verify(taskService).updateTaskStatus(
                999L,
                TaskStatus.IN_PROGRESS
        );
    }
}
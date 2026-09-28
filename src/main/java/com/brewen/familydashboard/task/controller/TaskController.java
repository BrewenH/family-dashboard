package com.brewen.familydashboard.task.controller;

import com.brewen.familydashboard.task.dto.TaskRequest;
import com.brewen.familydashboard.task.dto.TaskResponse;
import com.brewen.familydashboard.task.dto.TaskStatusRequest;
import com.brewen.familydashboard.task.mapper.TaskMapper;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "false") boolean sortByPriority
    ) {
        List<TaskResponse> responses = taskService
                .getTasks(status, sortByPriority)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id
    ) {
        return taskService.getTaskById(id)
                .map(TaskMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusRequest request
    ) {
        return taskService.updateTaskStatus(id, request.getStatus())
                .map(TaskMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request
    ) {
        Task createdTask = taskService.createTask(request);

        TaskResponse response = TaskMapper.toResponse(createdTask);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        boolean deleted = taskService.deleteTaskById(id);

        if (!deleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }


}

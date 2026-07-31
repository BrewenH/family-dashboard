package com.brewen.familydashboard.task.controller;

import com.brewen.familydashboard.task.dto.TaskRequest;
import com.brewen.familydashboard.task.dto.TaskStatusRequest;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);

        return task
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable("id") Long id,
            @RequestBody TaskStatusRequest taskStatusRequest
    ) {
        if (taskStatusRequest.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Task> updatedTask = taskService.updateTaskStatus(id, taskStatusRequest.getStatus());

        return updatedTask
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
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

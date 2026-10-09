package com.brewen.familydashboard.task.service;

import com.brewen.familydashboard.task.dto.TaskRequest;
import com.brewen.familydashboard.task.exception.TaskNotFoundException;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(TaskStatus status, boolean sortByPriority) {
        List<Task> tasks;

        if (status == null) {
            tasks = taskRepository.findAll();
        }else {
            tasks = taskRepository.findByStatus(status);
        }

        if (!sortByPriority) {
            return tasks;
        }

        return tasks.stream()
                .sorted((firstTask, secondTask) ->
                        Integer.compare(
                                getPriorityWeight(secondTask),
                                getPriorityWeight(firstTask)
                        )
                )
                .toList();
    }

    private int getPriorityWeight(Task task) {
        if (task.getPriority() == null) {
            return 0;
        }

        return switch (task.getPriority()) {
            case LOW -> 1;
            case MEDIUM -> 2;
            case HIGH -> 3;
        };
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTaskStatus(
            Long id,
            TaskStatus status
    ) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(status);

        return taskRepository.save(task);
    }

    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setAssignedTo(taskRequest.getAssignedTo());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
    }
}

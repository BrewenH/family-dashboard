package com.brewen.familydashboard.task.service;

import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldReturnAllTasksWhenStatusIsNotProvided() {
        Task firstTask = createTask(
                1L,
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        Task secondTask = createTask(
                2L,
                "Ranger la cuisine",
                TaskStatus.DONE,
                TaskPriority.HIGH
        );

        List<Task> repositoryTasks = List.of(firstTask, secondTask);

        when(taskRepository.findAll()).thenReturn(repositoryTasks);

        List<Task> result = taskService.getTasks(null, false);

        assertEquals(2, result.size());
        assertEquals(repositoryTasks, result);

        verify(taskRepository).findAll();
    }

    @Test
    void shouldReturnTasksFilteredByStatus() {
        Task todoTask = createTask(
                1L,
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        List<Task> todoTasks = List.of(todoTask);

        when(taskRepository.findByStatus(TaskStatus.TODO))
                .thenReturn(todoTasks);

        List<Task> result = taskService.getTasks(TaskStatus.TODO, false);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.TODO, result.getFirst().getStatus());

        verify(taskRepository).findByStatus(TaskStatus.TODO);
    }

    @Test
    void shouldReturnTaskWhenIdExists() {
        Task task = createTask(
                1L,
                "Sortir les poubelles",
                TaskStatus.IN_PROGRESS,
                TaskPriority.HIGH
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Sortir les poubelles", result.get().getTitle());

        verify(taskRepository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenTaskIdDoesNotExist() {
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(999L);

        assertTrue(result.isEmpty());

        verify(taskRepository).findById(999L);
    }

    private Task createTask(
            Long id,
            String title,
            TaskStatus status,
            TaskPriority priority
    ) {
        Task task = new Task();

        task.setId(id);
        task.setTitle(title);
        task.setStatus(status);
        task.setPriority(priority);

        return task;
    }
}

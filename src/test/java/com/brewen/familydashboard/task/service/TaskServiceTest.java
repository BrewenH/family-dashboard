package com.brewen.familydashboard.task.service;

import com.brewen.familydashboard.task.dto.TaskRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void shouldCreateTask() {
        TaskRequest request = new TaskRequest();

        request.setTitle("Faire les courses");
        request.setDescription("Acheter du lait");
        request.setAssignedTo("Brewen");
        request.setStatus(TaskStatus.TODO);
        request.setPriority(TaskPriority.MEDIUM);

        Task savedTask = createTask(
                1L,
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(request);

        assertEquals(1L, result.getId());
        assertEquals("Faire les courses", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        assertEquals(TaskPriority.MEDIUM, result.getPriority());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldDeleteTaskWhenIdExists() {
        when(taskRepository.existsById(1L))
                .thenReturn(true);

        boolean result = taskService.deleteTaskById(1L);

        assertTrue(result);

        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteTaskWhenIdDoesNotExist() {
        when(taskRepository.existsById(999L))
                .thenReturn(false);

        boolean result = taskService.deleteTaskById(999L);

        assertFalse(result);

        verify(taskRepository).existsById(999L);
        verify(taskRepository, never()).deleteById(999L);
    }

    @Test
    void shouldUpdateTaskStatusWhenTaskExists() {
        Task task = createTask(
                1L,
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task))
                .thenReturn(task);

        Optional<Task> result = taskService.updateTaskStatus(
                1L,
                TaskStatus.IN_PROGRESS
        );

        assertTrue(result.isPresent());
        assertEquals(TaskStatus.IN_PROGRESS, result.get().getStatus());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldReturnEmptyWhenUpdatingStatusOfMissingTask() {
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<Task> result = taskService.updateTaskStatus(
                999L,
                TaskStatus.DONE
        );

        assertTrue(result.isEmpty());

        verify(taskRepository).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void shouldSortTasksFromHighestToLowestPriority() {
        Task lowTask = createTask(
                1L,
                "Tâche faible",
                TaskStatus.TODO,
                TaskPriority.LOW
        );

        Task highTask = createTask(
                2L,
                "Tâche urgente",
                TaskStatus.TODO,
                TaskPriority.HIGH
        );

        Task mediumTask = createTask(
                3L,
                "Tâche moyenne",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        when(taskRepository.findAll())
                .thenReturn(List.of(lowTask, highTask, mediumTask));

        List<Task> result = taskService.getTasks(null, true);

        assertEquals(TaskPriority.HIGH, result.get(0).getPriority());
        assertEquals(TaskPriority.MEDIUM, result.get(1).getPriority());
        assertEquals(TaskPriority.LOW, result.get(2).getPriority());

        verify(taskRepository).findAll();
    }

    @Test
    void shouldKeepRepositoryOrderWhenPrioritySortingIsDisabled() {
        Task lowTask = createTask(
                1L,
                "Tâche faible",
                TaskStatus.TODO,
                TaskPriority.LOW
        );

        Task highTask = createTask(
                2L,
                "Tâche urgente",
                TaskStatus.TODO,
                TaskPriority.HIGH
        );

        List<Task> repositoryTasks = List.of(lowTask, highTask);

        when(taskRepository.findAll())
                .thenReturn(repositoryTasks);

        List<Task> result = taskService.getTasks(null, false);

        assertEquals(repositoryTasks, result);
        assertEquals(TaskPriority.LOW, result.get(0).getPriority());
        assertEquals(TaskPriority.HIGH, result.get(1).getPriority());

        verify(taskRepository).findAll();
    }

    @Test
    void shouldFilterByStatusAndSortByPriority() {
        Task lowTodoTask = createTask(
                1L,
                "TODO faible",
                TaskStatus.TODO,
                TaskPriority.LOW
        );

        Task highTodoTask = createTask(
                2L,
                "TODO urgente",
                TaskStatus.TODO,
                TaskPriority.HIGH
        );

        Task mediumTodoTask = createTask(
                3L,
                "TODO moyenne",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        when(taskRepository.findByStatus(TaskStatus.TODO))
                .thenReturn(List.of(
                        lowTodoTask,
                        highTodoTask,
                        mediumTodoTask
                ));

        List<Task> result = taskService.getTasks(TaskStatus.TODO, true);

        assertEquals(3, result.size());
        assertEquals(TaskPriority.HIGH, result.get(0).getPriority());
        assertEquals(TaskPriority.MEDIUM, result.get(1).getPriority());
        assertEquals(TaskPriority.LOW, result.get(2).getPriority());

        verify(taskRepository).findByStatus(TaskStatus.TODO);
    }

    @Test
    void shouldPlaceTaskWithoutPriorityLast() {
        Task taskWithoutPriority = createTask(
                1L,
                "Tâche sans priorité",
                TaskStatus.TODO,
                null
        );

        Task mediumTask = createTask(
                2L,
                "Tâche moyenne",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        Task highTask = createTask(
                3L,
                "Tâche urgente",
                TaskStatus.TODO,
                TaskPriority.HIGH
        );

        when(taskRepository.findAll())
                .thenReturn(List.of(
                        taskWithoutPriority,
                        mediumTask,
                        highTask
                ));

        List<Task> result = taskService.getTasks(null, true);

        assertEquals(TaskPriority.HIGH, result.get(0).getPriority());
        assertEquals(TaskPriority.MEDIUM, result.get(1).getPriority());
        assertNull(result.get(2).getPriority());

        verify(taskRepository).findAll();
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

package com.brewen.familydashboard.task.repository;

import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldSaveAndFindTaskById() {
        Task task = createTask(
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        task.setDescription("Acheter du lait et du pain");
        task.setAssignedTo("Brewen");

        Task savedTask = taskRepository.save(task);

        Optional<Task> result = taskRepository.findById(savedTask.getId());

        assertNotNull(savedTask.getId());
        assertTrue(result.isPresent());
        assertEquals("Faire les courses", result.get().getTitle());
        assertEquals(TaskStatus.TODO, result.get().getStatus());
        assertEquals(TaskPriority.MEDIUM, result.get().getPriority());
    }

    @Test
    void shouldFindTasksByStatus() {
        Task todoTask = createTask(
                "Faire les courses",
                TaskStatus.TODO,
                TaskPriority.MEDIUM
        );

        Task doneTask = createTask(
                "Sortir les poubelles",
                TaskStatus.DONE,
                TaskPriority.HIGH
        );

        taskRepository.saveAll(List.of(todoTask, doneTask));

        List<Task> result = taskRepository.findByStatus(TaskStatus.TODO);

        assertEquals(1, result.size());
        assertEquals("Faire les courses", result.getFirst().getTitle());
        assertEquals(TaskStatus.TODO, result.getFirst().getStatus());
        assertEquals(TaskPriority.MEDIUM, result.getFirst().getPriority());
    }

    private Task createTask(
            String title,
            TaskStatus status,
            TaskPriority priority
    ) {
        Task task = new Task();

        task.setTitle(title);
        task.setStatus(status);
        task.setPriority(priority);

        return task;
    }
}
package com.brewen.familydashboard.task.model;

import com.brewen.familydashboard.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskLifecycleTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldInitializeTimestampsWhenTaskIsCreated() {
        Task task = new Task();

        task.setTitle("Tester les timestamps");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);

        assertNull(task.getCreatedAt());
        assertNull(task.getUpdatedAt());

        Task savedTask = taskRepository.saveAndFlush(task);

        assertNotNull(savedTask.getCreatedAt());
        assertNotNull(savedTask.getUpdatedAt());
        assertEquals(
                savedTask.getCreatedAt(),
                savedTask.getUpdatedAt()
        );
    }

    @Test
    void shouldUpdateOnlyUpdatedAtWhenTaskIsModified() {
        Task task = new Task();

        task.setTitle("Titre initial");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);

        Task savedTask = taskRepository.saveAndFlush(task);

        LocalDateTime initialCreatedAt = savedTask.getCreatedAt();
        LocalDateTime previousUpdatedAt =
                savedTask.getUpdatedAt().minusSeconds(1);

        savedTask.setTitle("Titre modifié");
        savedTask.setUpdatedAt(previousUpdatedAt);

        Task updatedTask = taskRepository.saveAndFlush(savedTask);

        assertEquals(initialCreatedAt, updatedTask.getCreatedAt());
        assertTrue(updatedTask.getUpdatedAt().isAfter(previousUpdatedAt));
    }
}
package com.brewen.familydashboard.task.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class TaskNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithTaskIdInMessage() {
        TaskNotFoundException exception =
                new TaskNotFoundException(999L);

        assertInstanceOf(RuntimeException.class, exception);

        assertEquals(
                "Task not found with id: 999",
                exception.getMessage()
        );
    }
}
package com.brewen.familydashboard.task.repository;

import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);
}

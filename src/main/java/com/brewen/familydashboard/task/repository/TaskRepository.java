package com.brewen.familydashboard.task.repository;

import com.brewen.familydashboard.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskStatus;

public class TaskStatusRequest {

    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}


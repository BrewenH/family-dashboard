package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskStatus;

public class TaskStatusRequest {

    private TaskStatus status;

    public TaskStatusRequest() {
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskStatusRequest(TaskStatus status) {
        this.status = status;
    }
}

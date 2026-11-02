package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskStatusRequest {

    private TaskStatus status;
}


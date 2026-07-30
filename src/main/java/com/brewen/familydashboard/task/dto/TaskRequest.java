package com.brewen.familydashboard.task.dto;

import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    private String description;
    private LocalDate dueDate;
    private String assignedTo;

    @NotNull(message = "Le statut est obligatoire")
    private TaskStatus status;
    @NotNull(message = "La priorité est obligatoire")
    private TaskPriority priority;
}

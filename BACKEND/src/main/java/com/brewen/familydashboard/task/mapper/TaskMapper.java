package com.brewen.familydashboard.task.mapper;

import com.brewen.familydashboard.familymember.dto.FamilyMemberResponse;
import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.task.dto.TaskResponse;
import com.brewen.familydashboard.task.model.Task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        // On crée la réponse du membre si le membre existe, sinon null
        FamilyMemberResponse memberResponse = null;
        if (task.getAssignedMember() != null) {
            FamilyMember m = task.getAssignedMember();
            memberResponse = new FamilyMemberResponse(
                    m.getId(),
                    m.getDisplayName(),
                    m.getColor(),
                    m.isActive(),
                    m.getCreatedAt(),
                    m.getUpdatedAt()
            );
        }

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getAssignedTo(),
                memberResponse,
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

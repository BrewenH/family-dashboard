package com.brewen.familydashboard.task.service;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TaskMigrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Test
    void shouldMigrateAssignedToStringToMemberEntity() {
        // GIVEN
        // 1. On crée un membre
        FamilyMember member = new FamilyMember();
        member.setDisplayName("Jean");
        member.setColor("Blue");
        member.setActive(true);
        familyMemberRepository.save(member);

        // 2. On crée une tâche avec l'ancien format (String uniquement)
        Task task = new Task();
        task.setTitle("Tâche à migrer");
        task.setAssignedTo("Jean"); // Le nom correspond au membre
        task.setAssignedMember(null); // <--- C'est ce qu'on veut corriger
        taskRepository.save(task);

        // WHEN
        // ❌ Erreur de compilation : la méthode migrateAssignedMembers() n'existe pas encore
        taskService.migrateAssignedMembers();

        // THEN
        Task migratedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(migratedTask.getAssignedMember()).isNotNull();
        assertThat(migratedTask.getAssignedMember().getDisplayName()).isEqualTo("Jean");
    }
}

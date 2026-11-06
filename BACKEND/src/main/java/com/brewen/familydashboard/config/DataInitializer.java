package com.brewen.familydashboard.config;

import com.brewen.familydashboard.familymember.model.FamilyMember;
import com.brewen.familydashboard.familymember.repository.FamilyMemberRepository;
import com.brewen.familydashboard.task.model.Task;
import com.brewen.familydashboard.task.model.TaskPriority;
import com.brewen.familydashboard.task.model.TaskStatus;
import com.brewen.familydashboard.task.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(TaskRepository taskRepository, FamilyMemberRepository memberRepository) {
        return args -> {
            // 1. On vérifie si la base est vide pour ne pas créer de doublons à chaque redémarrage
            if (memberRepository.count() == 0) {
                System.out.println("Initializing seed data for Family Dashboard...");

                // 2. Création des membres de la famille
                FamilyMember dad = new FamilyMember();
                dad.setDisplayName("Papa");
                dad.setColor("#3498db"); // Bleu
                dad.setActive(true);
                memberRepository.save(dad);

                FamilyMember mom = new FamilyMember();
                mom.setDisplayName("Maman");
                mom.setColor("#e74c3c"); // Rouge
                mom.setActive(true);
                memberRepository.save(mom);

                FamilyMember child = new FamilyMember();
                child.setDisplayName("Enfant");
                child.setColor("#f1c40f"); // Jaune
                child.setActive(true);
                memberRepository.save(child);

                // 3. Création de tâches de test liées aux membres
                Task t1 = new Task();
                t1.setTitle("Sortir les poubelles");
                t1.setDescription("Ne pas oublier le bac jaune aujourd'hui");
                t1.setPriority(TaskPriority.HIGH);
                t1.setStatus(TaskStatus.TODO);
                t1.setAssignedMember(dad);
                t1.setDueDate(LocalDate.now().plusDays(1));

                Task t2 = new Task();
                t2.setTitle("Courses hebdomadaires");
                t2.setDescription("Lait, œufs, pain et fruits");
                t2.setPriority(TaskPriority.MEDIUM);
                t2.setStatus(TaskStatus.TODO);
                t2.setAssignedMember(mom);
                t2.setDueDate(LocalDate.now().plusDays(2));

                Task t3 = new Task();
                t3.setTitle("Ranger la chambre");
                t3.setDescription("Mettre les jouets dans le coffre");
                t3.setPriority(TaskPriority.LOW);
                t3.setStatus(TaskStatus.IN_PROGRESS);
                t3.setAssignedMember(child);
                t3.setDueDate(LocalDate.now().plusDays(3));

                Task t4 = new Task();
                t4.setTitle("Arroser les plantes");
                t4.setDescription("Surtout les orchidées du salon");
                t4.setPriority(TaskPriority.LOW);
                t4.setStatus(TaskStatus.DONE);
                t4.setAssignedMember(dad);
                t4.setDueDate(LocalDate.now());

                // 4. Sauvegarde massive des tâches
                taskRepository.saveAll(List.of(t1, t2, t3, t4));

                System.out.println("🚀 Seed data successfully initialized!");
                System.out.println("Tâches créées : 4");
                System.out.println("Membres créés : 3");
            } else {
                System.out.println("Database already contains data. Skipping initialization.");
            }
        };
    }
}

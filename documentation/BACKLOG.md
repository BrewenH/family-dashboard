# Backlog du projet Family Dashboard

L'approche actuelle est le **"Thin Slice"** : priorité absolue au déploiement de la V1.

---

## Légende
`[x]` Terminé | `[ ]` À faire | `[~]` En cours

---

## 🟢 PHASE 1 : Le Seuil de Déployabilité (V1)

### Module d'initialisation
- [x] FD-001 - Créer le projet Spring Boot
- [x] FD-002 - Ajouter un endpoint de contrôle avec GET /api/health

### Module Task : Fondations
- [x] FD-003 - Concevoir et préparer le module Task
- [x] FD-004 - Créer l’entité Task
- [x] FD-005 - Créer TaskRepository
- [x] FD-006 - Créer TaskService
- [x] FD-007 - Ajouter GET /api/tasks
- [x] FD-008 - Créer une tâche avec POST /api/tasks
- [x] FD-009 - Récupérer une tâche avec GET /api/tasks/{id}
- [x] FD-010 - Supprimer une tâche avec DELETE /api/tasks/{id}
- [x] FD-011 - Modifier le statut avec PATCH /api/tasks/{id}/status
- [x] FD-012 - Ajouter les validations sur TaskRequest
- [x] FD-013 - Ajouter une gestion globale des erreurs de validation
- [x] FD-014 - Filtrer les tâches par statut
- [x] FD-015 - Trier les tâches par priorité
- [x] FD-016 - Initialiser automatiquement createdAt avec @PrePersist
- [x] FD-017 - Initialiser automatiquement updatedAt avec @PreUpdate
- [x] FD-018 - Ajouter les tests unitaires de lecture du TaskService
- [x] FD-019 - Ajouter les tests unitaires d’écriture du TaskService
- [x] FD-020 - Ajouter les tests unitaires du tri par priorité
- [x] FD-021 - Ajouter le premier test du TaskController avec MockMvc
- [x] FD-022 - Tester POST /api/tasks avec MockMvc
- [x] FD-023 - Tester GET /api/tasks/{id} avec MockMvc
- [x] FD-024 - Tester DELETE /api/tasks/{id} avec MockMvc
- [x] FD-025 - Tester PATCH /api/tasks/{id}/status avec MockMvc
- [x] FD-026 - Ajouter les tests d’intégration de TaskRepository
- [x] FD-027 - Tester le cycle de vie JPA de Task
- [x] FD-028 - Créer TaskResponse
- [x] FD-029 - Créer un mapper entre Task et TaskResponse
- [x] FD-030 - Retourner TaskResponse depuis TaskController
- [x] FD-031 - Adapter les tests à TaskResponse
- [x] FD-032 - Tester les réponses 404 du TaskController avec MockMvc
- [x] FD-033 - Créer TaskNotFoundException
- [x] FD-034 - Centraliser les erreurs de tâche inexistante
- [x] FD-035 - Gérer les statuts invalides dans les paramètres
- [x] FD-036 - Gérer les corps JSON mal formés
- [x] FD-037 - Ajouter un format commun pour les réponses d’erreur

### Module FamilyMember (Socle technique)
- [x] FM-001 - Concevoir le module FamilyMember
- [x] FM-002 - Définir le modèle FamilyMember
- [x] FM-003 - Définir les relations avec les futurs modules
- [x] FM-004 - Créer l’entité FamilyMember
- [x] FM-005 - Créer FamilyMemberRepository
- [x] FM-006 - Créer FamilyMemberService
- [x] FM-007 - Créer FamilyMemberController
- [x] FM-008 - Ajouter un membre de la famille
- [x] FM-009 - Consulter les membres de la famille
- [x] FM-010 - Consulter un membre par identifiant
- [x] FM-011 - Ajouter les validations
- [x] FM-012 - Ajouter la gestion des erreurs
- [x] FM-013 - Ajouter les tests unitaires
- [ ] FM-014 - Ajouter les tests du controller
- [ ] FM-015 - Ajouter les tests d’intégration

### Liaison Task <-> FamilyMember (Priorité Haute)
- [ ] FM-LNK-001 - Ajouter la relation @ManyToOne dans l'entité Task
- [ ] FM-LNK-002 - Adapter TaskRequest et TaskResponse pour inclure le membre
- [ ] FM-LNK-003 - Migrer les données existantes de `assignedTo` (String) vers `assignedMember` (Entity)
- [ ] FM-LNK-004 - Adapter TaskService et TaskController pour gérer le membre

### Frontend Angular (V1)
- [ ] FE-001 - Initialisation du projet Angular et configuration environnement
- [ ] FE-002 - Création du layout Dashboard (Mode Kiosque)
- [ ] FE-003 - Implémentation du module Tâches (Liste, Création, Statut)
- [ ] FE-004 - Implémentation du module Membres (Liste, Création)
- [ ] FE-005 - Intégration API Backend (Tasks & Members)

### Déploiement Raspberry Pi (V1)
- [ ] HW-001 - Définir les besoins matériels
- [ ] HW-002 - Choisir le Raspberry Pi et la mémoire
- [ ] HW-003 - Choisir l’écran et le mode d’interaction
- [ ] HW-004 - Déploiement du Backend et Frontend sur le Pi
- [ ] HW-005 - Configuration du mode kiosque Chromium

---

## 🔵 PHASE 2 : Évolutions et Modules Complémentaires

### Module Wishlist
- [ ] WL-001 - Concevoir le module Wishlist
- [ ] WL-002 - Concevoir l’entité Wishlist
- [ ] WL-003 - Concevoir l’entité WishlistItem
- [ ] WL-004 - Définir la relation avec FamilyMember
- [ ] WL-005 - Créer l’entité Wishlist
- [ ] WL-006 - Créer l’entité WishlistItem
- [ ] WL-007 - Créer WishlistRepository
- [ ] WL-008 - Créer WishlistItemRepository
- [ ] WL-009 - Créer WishlistService
- [ ] WL-010 - Créer WishlistController
- [ ] WL-011 - Créer automatiquement une wishlist pour un membre
- [ ] WL-012 - Ajouter un souhait en texte libre
- [ ] WL-013 - Consulter la wishlist d’un membre
- [ ] WL-014 - Supprimer un souhait
- [ ] WL-015 - Modifier le texte d’un souhait

### Module FamilyEvent
- [ ] FEVT-001 - Concevoir le module FamilyEvent
- [ ] FEVT-002 - Créer l'entité et le repository
- [ ] FEVT-003 - Créer le service et le controller
- [ ] FEVT-004 - Implémenter la gestion des participations

### Autres modules et améliorations
- [ ] SHOP-001 - Créer le module Liste de courses
- [ ] WTH-001 - Intégrer un widget Météo (API externe)
- [ ] CON-001 - Intégrer un widget Concerts/Festivals
- [ ] NOT-001 - Implémenter le système de notifications

---

## 🛠 Documentation et Qualité
- [x] DOC-001 - Créer PROJECT_CONTEXT.md
- [x] DOC-002 - Créer PROJECT_JOURNAL.md
- [x] DOC-003 - Créer BACKLOG.md
- [ ] DOC-004 - Ajouter un README principal
- [ ] DOC-005 - Documenter le lancement local
- [ ] DOC-006 - Documenter Swagger
- [ ] DOC-007 - Documenter le workflow Git et TDD
- [x] DOC-008 - Documenter le transfert vers un autre Copilot
- [x] DOC-009 - Définir le workflow de synchronisation entre plusieurs ordinateurs
- [x] DOC-010 - Rendre obligatoire la mise à jour du journal et du backlog à chaque ticket

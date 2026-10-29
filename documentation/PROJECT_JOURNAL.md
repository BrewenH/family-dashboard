# Journal du projet Family Dashboard

Ce fichier conserve l'historique chronologique et technique du projet.

---

## Jalon 1 : Initialisation du Backend
**Tickets** : FD-001, FD-002
- **Réalisé** : Création du projet Spring Boot 3.3.2, Java 21. Configuration Maven. Mise en place du dépôt Git et workflow PR. Création de l'endpoint de santé `GET /api/health`.
- **Décisions** : Développement Backend avant Frontend. Code en anglais, Interface en français.
- **Difficultés** : Configuration initiale du remote GitHub.

---

## Jalon 2 : Conception et Fondations du Module Task
**Tickets** : FD-003 à FD-011
- **Réalisé** :
    - Conception du modèle `Task` (id, title, description, dueDate, assignedTo, status, priority, createdAt).
    - Implémentation du CRUD complet : GET (all, byId), POST, DELETE, PATCH (status).
    - Création des enums `TaskStatus` et `TaskPriority`.
- **Décisions** : `assignedTo` géré temporairement comme un String simple. Structure par packages : Controller, Service, Repository, Model, DTO.
- **Difficultés** : Erreurs de mapping sur l'endpoint DELETE.

---

## Jalon 3 : Validation et Robustesse (Module Task)
**Tickets** : FD-012 à FD-017
- **Réalisé** :
    - Ajout des validations Bean Validation (`@NotBlank`, `@NotNull`) sur `TaskRequest`.
    - Centralisation des erreurs via `GlobalExceptionHandler`.
    - Implémentation du filtrage par statut et du tri métier par priorité (HIGH > MEDIUM > LOW).
    - Automatisation des timestamps (`createdAt`, `updatedAt`) via `@PrePersist` et `@PreUpdate`.
- **Décisions** : Utilisation de `@RestControllerAdvice` pour uniformiser les réponses d'erreur.

---

## Jalon 4 : Couverture de Tests et Qualité (Module Task)
**Tickets** : FD-018 à FD-027
- **Réalisé** :
    - Tests unitaires du `TaskService` (lecture, écriture, tri).
    - Tests de la couche Web via `MockMvc` (POST, GET, DELETE, PATCH).
    - Tests d'intégration `TaskRepository` avec base H2.
    - Validation du cycle de vie JPA (timestamps).
- **Décisions** : Adoption stricte du cycle TDD (RED $\rightarrow$ GREEN $\rightarrow$ REFACTOR).

---

## Jalon 5 : Contrat d'API et DTOs (Module Task)
**Tickets** : FD-028 à FD-031
- **Réalisé** :
    - Création du record `TaskResponse` pour découpler l'entité JPA de l'API.
    - Implémentation de `TaskMapper` pour la conversion.
    - Migration du `TaskController` pour retourner `TaskResponse`.
    - Sécurisation du contrat JSON (exactement 9 propriétés exposées).
- **Décisions** : Utilisation de records Java pour l'immutabilité des DTOs.

---

## Jalon 6 : Gestion Avancée des Erreurs et Format Commun
**Tickets** : FD-032 à FD-037
- **Réalisé** :
    - Création de `TaskNotFoundException` pour centraliser la gestion des 404.
    - Implémentation de `ApiErrorResponse` : format unique pour toutes les erreurs (timestamp, status, error, message, path).
    - Gestion des cas limites : paramètres de statut invalides (400), JSON mal formés (400).
- **Décisions** : Le service lève des exceptions métier, le `GlobalExceptionHandler` les traduit en HTTP.

---

## Jalon 7 : Socle Technique FamilyMember
**Tickets** : FM-001 à FM-007
- **Réalisé** :
    - **Conception** : Définition du rôle du membre, distinction Membre vs Utilisateur, et définition des relations futures.
    - **Modèle** : Création de l'entité `FamilyMember` (displayName, color, active, timestamps).
    - **Implémentation** : Création du `FamilyMemberRepository`, `FamilyMemberService` et `FamilyMemberController`.
    - **API** : Mise en place de l'endpoint `POST /api/family-members` (Retourne 201 Created).
- **Décisions** : Utilisation de `ZoneOffset.UTC` pour les timestamps afin d'assurer l'indépendance du fuseau horaire.
- **Difficultés (Infra)** : Correction de la version Spring Boot (3.3.2), alignement des packages de test et résolution des conflits de classpath IDE lors de la migration PC.

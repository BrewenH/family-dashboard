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

---

## Jalon 8 : Socle Technique FamilyMember
**Tickets** : FM-001 à FM-007
- **Réalisé** :
  - Conception du rôle et du modèle de `FamilyMember` (Identité stable, distinction Membre vs Utilisateur).
  - Définition des relations futures avec les modules Task, Wishlist et FamilyEvent.
  - Implémentation de l'entité `FamilyMember` avec gestion UTC des timestamps.
  - Création du Repository, du Service et du Controller.
  - Mise en place de l'endpoint `POST /api/family-members` retournant un statut `201 Created`.
- **Décisions** : Utilisation de `ZoneOffset.UTC` pour l'indépendance du fuseau horaire.
- **Difficultés (Infra)** : Correction de la version Spring Boot (3.3.2) et résolution des conflits de classpath IDE lors de la migration entre ordinateurs.

### FM-009 - Consulter les membres de la famille
**Branche** : `feature/family-member-list`
**Objectif** : Implémenter la récupération de la liste complète des membres via `GET /api/family-members`.
**Réalisé** :
- Ajout de la méthode `getAllMembers` dans `FamilyMemberService` avec mapping via stream.
- Création de l'endpoint `GET` dans `FamilyMemberController`.
- Utilisation de `@Transactional(readOnly = true)` pour optimiser la lecture.
  **Tests** : Tests MockMvc validant le statut `200 OK` et la structure du tableau JSON retourné.
  **Décisions** : Retour d'une liste de `FamilyMemberResponse` pour maintenir le découplage API/Modèle.
  **Commit** : `Implement family member listing endpoint`

### FM-010 - Consulter un membre par identifiant
**Branche** : `feature/family-member-get-by-id`
**Objectif** : Implémenter la récupération d'un membre spécifique via son ID avec gestion du cas "Not Found".
**Réalisé** :
- Création de l'exception métier `FamilyMemberNotFoundException`.
- Ajout du handler correspondant dans `GlobalExceptionHandler` pour retourner un `404 Not Found`.
- Implémentation de la logique `findById().orElseThrow()` dans `FamilyMemberService`.
- Création de l'endpoint `GET /api/family-members/{id}` dans le controller.
  **Tests** : Tests MockMvc validant le cas nominal (200 OK) et le cas d'absence (404 Not Found).
  **Décisions** : Centralisation de la gestion d'erreur pour maintenir un format de réponse `ApiErrorResponse` uniforme.
  **Commit** : `Implement get member by ID with 404 handling`

### FM-011 - Ajouter les validations
**Branche** : `feature/family-member-validations`
**Objectif** : Sécuriser la création des membres en interdisant les noms vides ou trop longs.
**Réalisé** :
- Application des contraintes `@NotBlank` et `@Size(max=50)` sur le DTO `FamilyMemberRequest`.
- Activation de la validation dans le controller via l'annotation `@Valid`.
  **Tests** : Tests MockMvc confirmant le retour d'un `400 Bad Request` pour les données invalides.
  **Décisions** : Utilisation du `GlobalExceptionHandler` pour transformer les erreurs de validation en `ApiErrorResponse`.
  **Commit** : `Implement validation for family member creation`

### FM-012 - Ajouter la gestion des erreurs
**Branche** : `feature/family-member-error-handling`
**Objectif** : Sécuriser l'API contre les erreurs imprévues pour éviter les réponses HTML par défaut.
**Réalisé** :
- Ajout d'un handler générique pour `Exception.class` dans `GlobalExceptionHandler`.
- Garantie que toute erreur non gérée retourne un statut `500 Internal Server Error` via `ApiErrorResponse`.
  **Tests** : Test MockMvc simulant un crash du service pour vérifier la réponse 500 JSON.
  **Décisions** : Masquage des stacktraces techniques pour le client final tout en conservant le message d'erreur.
  **Commit** : `Implement global exception fallback for 500 errors`

### FM-013 - Ajouter les tests unitaires
**Branche** : `feature/family-member-unit-tests`
**Objectif** : Valider la logique métier du `FamilyMemberService` en isolation totale.
**Réalisé** :
- Mise à jour de `FamilyMemberServiceTest` pour couvrir 100% des méthodes du service.
- Tests de succès pour la création, la récupération de la liste et la récupération par ID.
- Test d'échec pour la récupération d'un membre inexistant (vérification de `FamilyMemberNotFoundException`).
  **Tests** : Utilisation de Mockito pour simuler le repository. Build vert sur l'ensemble de la suite.
  **Décisions** : Tests unitaires purs (sans chargement du contexte Spring) pour garantir une exécution ultra-rapide.
  **Commit** : `Implement comprehensive unit tests for FamilyMemberService`

### FM-014 - Ajouter les tests du controller
**Branche** : `feature/family-member-controller-tests`
**Objectif** : Valider le contrat d'API REST du module FamilyMember via MockMvc.
**Réalisé** :
- Implémentation d'une suite de tests exhaustive pour le `FamilyMemberController`.
- Validation des statuts HTTP : 201 (Création), 200 (Lecture), 400 (Validation), 404 (Absence).
- Vérification du formatage JSON des réponses et des erreurs.
  **Tests** : Tests d'intégration de la couche Web avec simulation du service via `@MockBean`.
  **Décisions** : Utilisation de `jsonPath` pour sécuriser précisément le contenu des réponses API.
  **Commit** : `Implement comprehensive MockMvc tests for FamilyMemberController`

### FM-015 - Ajouter les tests d’intégration
**Branche** : `feature/family-member-integration-tests`
**Objectif** : Valider le mapping JPA et le cycle de vie des entités avec une base de données réelle (H2).
**Réalisé** :
- Implémentation de `FamilyMemberRepositoryTest` avec `@DataJpaTest`.
- Validation de la persistance et de la récupération des membres.
- Vérification du fonctionnement réel des callbacks `@PrePersist` et `@PreUpdate`.
  **Tests** : Utilisation de `saveAndFlush()` pour forcer la synchronisation avec la base et valider les timestamps.
  **Décisions** : Utilisation de H2 pour garantir l'indépendance des tests d'intégration.
  **Commit** : `Implement FamilyMember integration tests with JPA lifecycle validation`

### FM-LNK-001 - Ajouter la relation @ManyToOne dans l'entité Task
**Branche** : `feature/task-familymember-relation`
**Objectif** : Établir le lien technique entre une tâche et un membre de la famille.
**Réalisé** :
- Ajout de la relation `@ManyToOne` (Lazy) dans l'entité `Task` vers `FamilyMember`.
- Création de la colonne `family_member_id` en base de données.
- Conservation temporaire du champ `assignedTo` (String) pour assurer la rétrocompatibilité.
  **Tests** : Test d'intégration validant la persistance d'une tâche avec son membre associé.
  **Décisions** : Choix du `FetchType.LAZY` pour optimiser les performances de lecture des listes de tâches.
  **Commit** : `feat(task): add ManyToOne relation to FamilyMember`

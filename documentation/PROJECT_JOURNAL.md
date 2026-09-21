# Journal du projet Family Dashboard

Ce fichier conserve l’historique chronologique du projet.

Pour chaque ticket, il permet de retrouver :

- les fonctionnalités réalisées ;
- les décisions techniques ;
- les difficultés rencontrées ;
- les corrections appliquées ;
- les tests effectués ;
- le commit associé ;
- la prochaine étape prévue.

---

## Jalon 1 : Initialisation du backend

### Tickets concernés

- FD-001 : création du projet Spring Boot
- FD-002 : premier endpoint de contrôle

### Réalisé

- Création du backend Spring Boot.
- Configuration du projet Maven.
- Utilisation de Java 21.
- Ajout des dépendances Spring principales.
- Premier démarrage de l’application.
- Création de l’endpoint `GET /api/health`.
- Initialisation du dépôt Git.
- Connexion du dépôt local au dépôt GitHub.
- Mise en place du workflow par branches et Pull Requests.
- Première Pull Request mergée dans `main`.

### Décisions

- Le backend est développé avant le frontend.
- Le code backend est écrit en anglais.
- L’interface utilisateur future sera en français.
- La branche `main` doit rester stable.
- Les évolutions importantes utilisent des branches dédiées.

### Difficultés rencontrées

- Le projet n’était pas encore initialisé comme dépôt Git.
- Le remote GitHub n’était pas configuré.
- Certaines opérations Git ont d’abord été réalisées depuis le terminal.

### Corrections appliquées

- Initialisation du dépôt avec Git.
- Création de la branche `main`.
- Ajout du remote `origin`.
- Premier push vers GitHub.
- Mise en place progressive des Pull Requests.

---

## Jalon 2 : Conception du module Task

### Ticket concerné

- FD-003 : conception et préparation du module Task

### Réalisé

- Analyse du besoin fonctionnel.
- Définition des champs de `Task`.
- Définition des statuts.
- Définition des priorités.
- Choix d’une architecture par fonctionnalité.
- Découpage du module en petits tickets.

### Modèle initial

```text
Task

id
title
description
dueDate
assignedTo
status
priority
createdAt
```

### Statuts définis

```text
TODO
IN_PROGRESS
DONE
```

### Priorités définies

```text
LOW
MEDIUM
HIGH
```

### Décisions

- `assignedTo` reste temporairement un texte simple.
- Aucun module `FamilyMember` n’est créé à ce stade.
- Les statuts et priorités utilisent des enums Java.
- Le module est organisé en packages `controller`, `dto`, `model`, `repository` et `service`.

---

## Jalon 3 : Première version fonctionnelle du module Task

### Tickets concernés

- FD-004 : création de l’entité Task
- FD-005 : création du TaskRepository
- FD-006 : création du TaskService
- FD-007 : ajout de `GET /api/tasks`
- FD-008 : ajout de `POST /api/tasks`
- FD-009 : ajout de `GET /api/tasks/{id}`
- FD-010 : ajout de `DELETE /api/tasks/{id}`
- FD-011 : ajout de `PATCH /api/tasks/{id}/status`

### Réalisé

- Création de l’entité `Task`.
- Création des enums `TaskStatus` et `TaskPriority`.
- Création de `TaskRepository`.
- Création de `TaskService`.
- Création de `TaskController`.
- Création de `TaskRequest`.
- Création de `TaskStatusRequest`.
- Création d’une tâche.
- Récupération de toutes les tâches.
- Récupération par identifiant.
- Suppression d’une tâche.
- Modification du statut.
- Tests manuels avec Swagger.

### Endpoints ajoutés

```http
GET /api/tasks
POST /api/tasks
GET /api/tasks/{id}
DELETE /api/tasks/{id}
PATCH /api/tasks/{id}/status
```

### Décisions

- Les DTO sont utilisés pour les données entrantes.
- L’API retourne encore directement l’entité `Task`.
- `createdAt` est généré par le backend.
- Les réponses utilisent `ResponseEntity` lorsque le statut HTTP doit être contrôlé.
- `Optional` est utilisé pour représenter une tâche éventuellement absente.

### Difficultés rencontrées

- Le mapping `/{id}` avait été oublié sur l’endpoint DELETE.
- Spring retournait une erreur `MissingPathVariableException`.
- Certains commits ont été tentés avant la sélection des fichiers.
- Plusieurs warnings concernaient du code utilisé indirectement par Spring ou Jackson.
- Des constructeurs et setters étaient signalés comme inutilisés.

### Corrections appliquées

- Ajout de :

```java
@DeleteMapping("/{id}")
```

- Adoption de la section Commit d’IntelliJ.
- Utilisation progressive de Lombok sur les DTO.
- Suppression du code répétitif devenu inutile.
- Maintien des méthodes utilisées par réflexion malgré certains avertissements.

---

## Jalon 4 : Validation et gestion globale des erreurs

### Tickets concernés

- FD-012 : validation de TaskRequest
- FD-013 : gestion globale des erreurs de validation

### Réalisé

- Ajout de `@NotBlank` sur le titre.
- Ajout de `@NotNull` sur le statut.
- Ajout de `@NotNull` sur la priorité.
- Ajout de `@Valid` dans le controller.
- Création de `ValidationErrorResponse`.
- Création de `GlobalExceptionHandler`.
- Gestion de `MethodArgumentNotValidException`.
- Retour d’une liste de messages de validation.

### Exemple de réponse

```json
{
  "errors": [
    "Le titre est obligatoire",
    "Le statut est obligatoire",
    "La priorité est obligatoire"
  ]
}
```

### Décisions

- Les messages destinés à l’utilisateur restent en français.
- Les erreurs de validation sont centralisées avec `@RestControllerAdvice`.
- Le format de réponse reste volontairement simple.
- Les erreurs métier ne sont pas encore centralisées.

### Difficultés rencontrées

- Sonar signalait certains constructeurs vides.
- Des getters et setters étaient signalés comme inutilisés.
- La méthode du handler était signalée comme inutilisée.

### Corrections appliquées

- Utilisation de Lombok sur les DTO simples.
- Conservation du handler car il est appelé par Spring via `@ExceptionHandler`.
- Validation du comportement avec Swagger.

---

## Jalon 5 : Filtrage et tri des tâches

### Tickets concernés

- FD-014 : filtrer les tâches par statut
- FD-015 : trier les tâches par priorité

### Réalisé

- Ajout d’une recherche par statut dans le repository.
- Ajout du paramètre optionnel `status`.
- Ajout du paramètre optionnel `sortByPriority`.
- Combinaison du filtre et du tri.
- Définition d’un ordre métier des priorités.

### Endpoints concernés

```http
GET /api/tasks
GET /api/tasks?status=TODO
GET /api/tasks?status=IN_PROGRESS
GET /api/tasks?status=DONE
GET /api/tasks?sortByPriority=true
GET /api/tasks?status=TODO&sortByPriority=true
```

### Ordre métier retenu

```text
HIGH
MEDIUM
LOW
```

### Décisions

- Conserver un seul endpoint `GET /api/tasks`.
- Utiliser des paramètres optionnels.
- Effectuer le tri métier dans `TaskService`.
- Ne pas utiliser un simple tri alphabétique en base.
- Placer une priorité absente après les priorités renseignées.

### Difficultés rencontrées

- Un commit du filtre avait reçu un ancien message de commit par erreur.
- Plusieurs opérations Git ont été temporairement bloquées par des erreurs DNS vers GitHub.

### Corrections appliquées

- Vérification plus attentive du message dans la section Commit.
- Nouvelle tentative du push sans recréer le commit.
- Utilisation de la commande suivante lors des problèmes DNS :

```powershell
ipconfig /flushdns
```

---

## Jalon 6 : Gestion automatique des timestamps

### Tickets concernés

- FD-016 : initialisation automatique de createdAt
- FD-017 : initialisation automatique de updatedAt

### Réalisé

- Déplacement de l’initialisation de `createdAt` dans l’entité.
- Ajout de `@PrePersist`.
- Ajout du champ `updatedAt`.
- Ajout de `@PreUpdate`.
- Initialisation identique de `createdAt` et `updatedAt` à la création.
- Actualisation de `updatedAt` lors d’une modification.
- Conservation de `createdAt` lors des mises à jour.
- Refactoring de `Task` avec Lombok.

### Code principal

```java
@PrePersist
public void initializeTimestamps() {
    LocalDateTime now = LocalDateTime.now();

    this.createdAt = now;
    this.updatedAt = now;
}

@PreUpdate
public void updateTimestamp() {
    this.updatedAt = LocalDateTime.now();
}
```

### Décisions

- Les timestamps sont une responsabilité de l’entité.
- `TaskService` ne définit plus directement `createdAt`.
- Une seule valeur `now` est utilisée lors de la création.
- Lombok fournit les getters, setters et le constructeur sans argument.
- `@Data` n’est pas utilisé sur l’entité JPA.

### Difficultés rencontrées

- Deux méthodes étaient annotées avec `@PrePersist`.
- Hibernate refusait de créer `entityManagerFactory`.
- Certains callbacks JPA étaient signalés comme inutilisés.
- Des problèmes DNS temporaires empêchaient les push.

### Corrections appliquées

- Suppression de l’ancien callback `initializeCreatedAt()`.
- Conservation d’un seul callback `@PrePersist`.
- Ajout d’un seul callback `@PreUpdate`.
- Conservation des callbacks malgré les avertissements de l’IDE.
- Nettoyage du cache DNS lors des problèmes réseau.

---

## Jalon 7 : Premiers tests automatisés du TaskService

### Tickets concernés

- FD-018 : tests unitaires de lecture
- FD-019 : tests unitaires d’écriture
- FD-020 : tests unitaires du tri

### Réalisé

- Création de `TaskServiceTest`.
- Utilisation de JUnit 5.
- Utilisation de Mockito.
- Simulation de `TaskRepository`.
- Injection du repository simulé dans `TaskService`.
- Tests des méthodes de lecture.
- Tests des méthodes d’écriture.
- Tests du tri et du filtre.
- Vérification des interactions avec le repository.
- Vérification de l’absence d’appel avec `never()`.

### Comportements couverts

- Récupérer toutes les tâches.
- Filtrer les tâches par statut.
- Récupérer une tâche par identifiant.
- Retourner un résultat vide pour un identifiant inexistant.
- Créer une tâche.
- Supprimer une tâche existante.
- Refuser la suppression d’une tâche inexistante.
- Modifier le statut.
- Retourner un résultat vide en cas de modification impossible.
- Trier les tâches dans l’ordre `HIGH`, `MEDIUM`, `LOW`.
- Conserver l’ordre du repository si le tri est désactivé.
- Combiner le filtre et le tri.
- Placer les tâches sans priorité en dernière position.

### Décisions

- Les tests du service ne chargent pas le contexte Spring Boot.
- Aucune base de données réelle n’est utilisée.
- Les callbacks JPA seront testés plus tard avec des tests d’intégration.
- Les tests de FD-020 caractérisent un comportement déjà existant.
- Les futures fonctionnalités doivent suivre une démarche TDD stricte.

### Difficultés rencontrées

- Mockito affiche un warning concernant l’auto-attachement de son agent.
- Une première configuration Maven de l’agent a produit une propriété non résolue.
- Une tentative avec `mock-maker-subclass` n’a pas supprimé tous les warnings.
- Le Maven Wrapper a temporairement été bloqué par un problème DNS.
- JUnit 5 signalait des modificateurs `public` inutiles.

### Corrections et décisions

- Retrait des modificateurs `public` inutiles.
- Validation systématique avec :

```powershell
.\mvnw clean test
```

- Suppression des configurations Mockito expérimentales.
- Warning Mockito accepté temporairement car non bloquant.
- Conservation d’un build Maven vert.

---

## Décision méthodologique : adoption du TDD

Les nouvelles fonctionnalités doivent désormais suivre le cycle :

```text
RED
Écrire un test en échec.
```

```text
GREEN
Ajouter le minimum de code pour faire passer le test.
```

```text
REFACTOR
Améliorer le code sous la protection des tests.
```

Pour une fonctionnalité déjà existante, les tests ajoutés sont considérés comme des tests de caractérisation.

---

## Décision produit : Wishlist familiale

### Objectif

Chaque membre pourra disposer d’une wishlist permanente visible par les autres membres.

Cette wishlist pourra servir lors :

- des anniversaires ;
- de Noël ;
- des fêtes ;
- des occasions particulières.

### MVP prévu

- Une wishlist par membre.
- Ajout d’un souhait en texte libre.
- Consultation de la wishlist.
- Suppression d’un souhait.

### Évolutions envisagées

- Lien vers un produit.
- Image.
- Prix indicatif.
- Niveau d’envie.
- Réservation d’un cadeau.
- Masquage de la réservation au propriétaire.
- État acheté.
- Prévention des achats en double.

### Dépendance

Un modèle `FamilyMember` minimal doit être créé avant le module Wishlist.

---

## Workflow Git actuel

Les opérations quotidiennes sont principalement réalisées dans IntelliJ.

Après une Pull Request mergée :

```text
1. main > Checkout
2. main > Update
3. Vérifier le merge dans Git > Log
4. Supprimer la branche feature locale
5. Fetch
```

Le terminal reste utilisé pour :

- les diagnostics ;
- les problèmes DNS ;
- certaines commandes Maven ;
- les vérifications avancées.

---

## État actuel du projet

### Dernier ticket développé

```text
FD-020 - Tests unitaires du tri par priorité
```

### Point à vérifier

La branche et la Pull Request de FD-020 doivent être vérifiées avant de commencer le prochain ticket si elles ne sont pas encore mergées dans `main`.

### Prochaine étape recommandée

```text
Ajouter les tests du TaskController avec MockMvc
```

Cette étape devra suivre la démarche TDD pour les comportements qui ne sont pas encore couverts.

---

## Décision d’organisation : continuité entre plusieurs ordinateurs

### Contexte

Le projet Family Dashboard est développé alternativement depuis un ordinateur professionnel et un ordinateur personnel.

Chaque environnement peut utiliser une conversation Copilot différente.

### Décision prise

Le dépôt GitHub et les fichiers Markdown du dossier `documentation` deviennent la mémoire officielle du projet.

Les conversations Copilot sont utilisées comme assistants, mais elles ne doivent pas être la seule source de contexte.

### Documents concernés

```text
documentation/
├── PROJECT_CONTEXT.md
├── PROJECT_JOURNAL.md
└── BACKLOG.md
```

### Workflow retenu

À chaque ticket :

- passer le ticket à `[~]` lors de son démarrage ;
- développer selon le cycle TDD ;
- mettre à jour le journal ;
- mettre à jour le backlog ;
- mettre à jour le contexte si une décision durable apparaît ;
- inclure les modifications documentaires dans le commit ;
- pousser la branche sur GitHub ;
- merger la Pull Request avant de reprendre sur un autre ordinateur.

### Reprise en cours de ticket

Si un changement d’ordinateur intervient avant la fin d’un ticket :

- conserver la même branche distante ;
- documenter la phase TDD actuelle ;
- indiquer le test en cours ;
- indiquer les fichiers modifiés ;
- indiquer la prochaine action exacte ;
- pousser un commit intermédiaire sur la branche feature ;
- ne pas merger un état rouge dans `main`.

### Règle durable

Une seule branche distante doit représenter un ticket actif.

Le second environnement récupère cette branche et poursuit le travail au lieu de créer une implémentation parallèle.

---

## FD-021 - Premiers tests du TaskController avec MockMvc

### Branche

`feature/task-controller-get-tests`

### Objectif

Tester la couche web du module Task avec MockMvc, en commençant par `GET /api/tasks`.

### Phase TDD actuelle

- [x] RED
- [x] GREEN
- [x] REFACTOR

### État actuel

Le premier test MockMvc de `TaskController` fonctionne correctement.

Le test vérifie que `GET /api/tasks` :

- retourne un statut HTTP `200 OK` ;
- retourne une liste au format JSON ;
- contient les informations attendues pour une tâche ;
- appelle `TaskService` avec un statut absent et le tri désactivé.

Le `TaskService` est remplacé dans le contexte MVC par un mock Spring grâce à `@MockitoBean`.

### Résultat TDD

- RED : le test échouait parce que `TaskService` était absent du contexte chargé par `@WebMvcTest`.
- GREEN : ajout d’un `TaskService` simulé avec `@MockitoBean`, configuration de sa réponse et vérification du JSON retourné.
- REFACTOR : aucune modification du code de production n’était nécessaire. Le test reste ciblé sur la responsabilité du controller.

### Test ajouté

`shouldReturnAllTasks`

### Comportement testé

```http
GET /api/tasks
```

Résultat attendu :

```text
200 OK
```

Le tableau JSON retourné contient une tâche avec :

```text
id
title
status
priority
```

### Tests

- [x] Test `shouldReturnAllTasks` réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Fichiers concernés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Résultat final

Le premier test MockMvc du `TaskController` est terminé.

Le test vérifie que `GET /api/tasks` :

- retourne `200 OK` ;
- retourne un tableau JSON ;
- contient les données attendues ;
- appelle `TaskService` avec les paramètres attendus.

Aucune modification du code de production n’a été nécessaire.

### Commit prévu

`Add TaskController GET unit test`

### Prochaine étape

Créer la Pull Request de FD-021, merger dans `main`, puis poursuivre avec les tests MockMvc de création d’une tâche.

---

## FD-022 - Tester POST /api/tasks avec MockMvc

### Branche

`feature/task-controller-post-tests`

### Objectif

Tester avec MockMvc la création d’une tâche via `POST /api/tasks`.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Réalisé

- Ajout du test MockMvc de `POST /api/tasks`.
- Envoi d’un `TaskRequest` valide au format JSON.
- Simulation de `TaskService.createTask()` avec `@MockitoBean`.
- Vérification du statut HTTP retourné.
- Vérification du contenu JSON de la tâche créée.
- Capture du `TaskRequest` transmis au service avec `ArgumentCaptor`.
- Vérification de tous les champs reçus par le service.

### Résultat TDD

- RED : le service simulé ne retournait aucune tâche et les assertions JSON échouaient pour la raison attendue.
- GREEN : configuration de `TaskService.createTask()` pour retourner une tâche créée.
- REFACTOR : ajout d’un `ArgumentCaptor` pour vérifier précisément le `TaskRequest` transmis au service, sans modifier le code de production.

### Comportement testé

```http
POST /api/tasks
```

Le test vérifie que le controller :

- accepte un JSON valide ;
- retourne `200 OK` ;
- retourne la tâche créée au format JSON ;
- transmet correctement le titre ;
- transmet correctement la description ;
- transmet correctement la date d’échéance ;
- transmet correctement la personne assignée ;
- transmet correctement le statut ;
- transmet correctement la priorité.

### Test ajouté

`shouldCreateTask`

### Tests

- [x] Test MockMvc de création réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Conservation du comportement HTTP actuel avec `200 OK`.
- Utilisation d’un corps JSON sous forme de text block Java.
- Utilisation de `@MockitoBean` pour remplacer `TaskService`.
- Utilisation de `ArgumentCaptor<TaskRequest>` pour contrôler la désérialisation et l’appel au service.
- Aucune modification du code de production nécessaire.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskController POST unit test`

### Prochaine étape

Créer et merger la Pull Request de FD-022.

Le ticket suivant sera :

```text
FD-023 - Tester GET /api/tasks/{id} avec MockMvc
---

## FD-023 - Tester GET /api/tasks/{id} avec MockMvc

### Branche

`feature/task-controller-get-by-id-tests`

### Objectif

Tester avec MockMvc la récupération d’une tâche existante par son identifiant.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Réalisé

- Ajout du test MockMvc de `GET /api/tasks/{id}`.
- Simulation de `TaskService.getTaskById()`.
- Retour d’une tâche existante dans un `Optional`.
- Vérification du statut HTTP retourné.
- Vérification du contenu JSON de la tâche.
- Vérification de l’identifiant transmis au service.

### Résultat TDD

- RED : aucune réponse n’était configurée pour le service simulé. Le controller retournait `404 Not Found`, alors que le test attendait `200 OK`.
- GREEN : configuration de `TaskService.getTaskById(1L)` pour retourner une tâche existante dans un `Optional`.
- REFACTOR : ajout de la vérification de l’appel au service avec l’identifiant attendu. Aucune modification du code de production n’était nécessaire.

### Comportement testé

```http
GET /api/tasks/1
```

Lorsque la tâche existe, le test vérifie que le controller :

- retourne `200 OK` ;
- retourne la tâche au format JSON ;
- retourne l’identifiant attendu ;
- retourne le titre attendu ;
- retourne le statut attendu ;
- retourne la priorité attendue ;
- appelle `TaskService.getTaskById(1L)`.

### Test ajouté

`shouldReturnTaskWhenIdExists`

### Tests

- [x] Test MockMvc individuel réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Utilisation de `Optional.of(task)` pour simuler une tâche existante.
- Vérification du contrat HTTP actuel du controller.
- Vérification de l’appel au service avec `verify`.
- Aucune modification de `TaskController` nécessaire.
- Le cas `404 Not Found` sera sécurisé dans un test séparé afin de conserver des tests courts et ciblés.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskController GET by ID unit test`

### Prochaine étape

Créer et merger la Pull Request de FD-023.

Le ticket suivant sera :

```text
FD-024 - Tester DELETE /api/tasks/{id} avec MockMvc
```

Le cas `GET /api/tasks/{id}` avec un identifiant inexistant reste également à inscrire ou traiter dans la couverture des erreurs du controller.

---

## FD-024 - Tester DELETE /api/tasks/{id} avec MockMvc

### Branche

`feature/task-controller-delete-tests`

### Objectif

Tester avec MockMvc la suppression réussie d’une tâche existante via `DELETE /api/tasks/{id}`.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Réalisé

- Ajout du test MockMvc de `DELETE /api/tasks/{id}`.
- Simulation de `TaskService.deleteTaskById()`.
- Vérification du statut HTTP de suppression.
- Vérification de l’identifiant transmis au service.
- Validation de l’absence de contenu dans la réponse.

### Résultat TDD

- RED : sans configuration du mock, `TaskService.deleteTaskById(1L)` retournait `false` et le controller répondait `404 Not Found`, alors que le test attendait `204 No Content`.
- GREEN : configuration du service simulé pour retourner `true`, permettant au controller de répondre `204 No Content`.
- REFACTOR : ajout de la vérification de l’appel au service avec l’identifiant attendu. Aucune modification du code de production nécessaire.

### Comportement testé

```http
DELETE /api/tasks/1
```

Lorsque la tâche existe, le test vérifie que le controller :

- appelle `TaskService.deleteTaskById(1L)` ;
- retourne `204 No Content` ;
- ne retourne aucun contenu dans la réponse.

### Test ajouté

`shouldDeleteTaskWhenIdExists`

### Tests

- [x] Test MockMvc individuel réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Conservation du contrat HTTP actuel avec `204 No Content` pour une suppression réussie.
- Simulation du résultat métier avec `TaskService.deleteTaskById()`.
- Vérification de l’appel au service avec `verify`.
- Aucune modification de `TaskController` nécessaire.
- Le cas d’une tâche inexistante sera couvert séparément pour conserver un test ciblé.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskController DELETE unit test`

### Prochaine étape

Créer et merger la Pull Request de FD-024.

Le ticket suivant sera :

```text
FD-025 - Tester PATCH /api/tasks/{id}/status avec MockMvc
```
---

## FD-025 - Tester PATCH /api/tasks/{id}/status avec MockMvc

### Branche

`feature/task-controller-status-tests`

### Objectif

Tester avec MockMvc la modification du statut d’une tâche existante via `PATCH /api/tasks/{id}/status`.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Réalisé

- Ajout du test MockMvc de `PATCH /api/tasks/{id}/status`.
- Envoi du nouveau statut au format JSON.
- Simulation de `TaskService.updateTaskStatus()`.
- Retour d’une tâche mise à jour dans un `Optional`.
- Vérification du statut HTTP retourné.
- Vérification du contenu JSON de la tâche mise à jour.
- Vérification de l’identifiant et du statut transmis au service.

### Résultat TDD

- RED : le service simulé ne retournait aucune tâche mise à jour et le controller répondait `404 Not Found`, alors que le test attendait `200 OK`.
- GREEN : configuration de `TaskService.updateTaskStatus()` pour retourner une tâche avec le statut `IN_PROGRESS`.
- REFACTOR : ajout de la vérification de l’appel au service avec l’identifiant et le statut attendus. Aucune modification du code de production n’était nécessaire.

### Comportement testé

```http
PATCH /api/tasks/1/status
```

Corps de la requête :

```json
{
  "status": "IN_PROGRESS"
}
```

Lorsque la tâche existe, le test vérifie que le controller :

- accepte le JSON envoyé ;
- convertit correctement la valeur vers `TaskStatus.IN_PROGRESS` ;
- appelle `TaskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS)` ;
- retourne `200 OK` ;
- retourne la tâche mise à jour au format JSON ;
- retourne l’identifiant attendu ;
- retourne le titre attendu ;
- retourne le statut `IN_PROGRESS` ;
- retourne la priorité attendue.

### Test ajouté

`shouldUpdateTaskStatusWhenTaskExists`

### Tests

- [x] Test MockMvc individuel réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Conservation du contrat HTTP actuel avec `200 OK`.
- Utilisation d’un corps JSON sous forme de text block Java.
- Simulation de la tâche mise à jour avec `Optional.of(updatedTask)`.
- Vérification de la conversion du statut JSON vers l’enum Java.
- Vérification de l’appel au service avec `verify`.
- Aucune modification de `TaskController` nécessaire.
- Le cas d’une tâche inexistante sera couvert séparément.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskController status update unit test`

### Prochaine étape

Créer et merger la Pull Request de FD-025.

La prochaine phase de tests concernera les cas d’erreur du controller, notamment :

- récupération d’une tâche inexistante ;
- suppression d’une tâche inexistante ;
- modification du statut d’une tâche inexistante ;
- requêtes de création invalides.
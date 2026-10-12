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

---

## FD-026 - Ajouter les tests d’intégration de TaskRepository

### Branche

`feature/task-repository-integration-tests`

### Objectif

Tester l’intégration entre `TaskRepository`, Hibernate, l’entité `Task` et la base H2 de test.

### Approche de test

Les méthodes du repository existaient déjà.

Les tests ajoutés sont donc des tests de caractérisation permettant de vérifier le comportement réel de la couche JPA sans utiliser Mockito.

### Réalisé

- Création de `TaskRepositoryTest`.
- Utilisation de `@DataJpaTest`.
- Utilisation du vrai `TaskRepository`.
- Utilisation d’Hibernate et d’une base H2 de test.
- Test de la sauvegarde d’une tâche.
- Vérification de la génération automatique de l’identifiant.
- Test de la récupération avec `findById()`.
- Test de la méthode dérivée `findByStatus()`.
- Ajout d’une méthode utilitaire pour créer les tâches de test.
- Refactorisation des données de test afin de réduire la duplication.

### Résultats des tests de caractérisation

#### Sauvegarde et récupération par identifiant

Le test `shouldSaveAndFindTaskById` passe.

Il confirme que :

- une tâche peut être enregistrée ;
- un identifiant est généré ;
- la tâche peut être retrouvée avec `findById()` ;
- le statut est correctement persisté ;
- la priorité est correctement persistée.

#### Recherche par statut

Le test `shouldFindTasksByStatus` passe.

Il confirme que :

- plusieurs tâches peuvent être enregistrées ;
- `findByStatus(TaskStatus.TODO)` filtre les données persistées ;
- seules les tâches possédant le statut demandé sont retournées ;
- aucune requête JPQL personnalisée n’est nécessaire.

### Tests

- [x] Test `shouldSaveAndFindTaskById` réussi.
- [x] Test `shouldFindTasksByStatus` réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Utilisation de `@DataJpaTest` pour limiter le contexte aux composants JPA.
- Utilisation de la base H2 de test.
- Aucun mock Mockito utilisé pour le repository.
- Conservation de la méthode dérivée Spring Data JPA `findByStatus()`.
- Aucune modification du code de production nécessaire.
- Les données créées par chaque test restent isolées par le contexte transactionnel de test.

### Refactorisation

Une méthode utilitaire privée `createTask()` a été ajoutée dans la classe de test afin de :

- réduire la duplication ;
- rendre les scénarios plus lisibles ;
- centraliser la préparation minimale des entités de test.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/repository/TaskRepositoryTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskRepository integration tests`

### Prochaine étape

Créer et merger la Pull Request de FD-026.

Le ticket suivant sera :

```text
FD-027 - Tester le cycle de vie JPA de Task
```

FD-027 devra notamment vérifier :

- l’initialisation automatique de `createdAt` ;
- l’initialisation automatique de `updatedAt` ;
- la modification automatique de `updatedAt` lors d’une mise à jour ;
- la conservation de `createdAt` lors d’une mise à jour.

---

## FD-027 - Tester le cycle de vie JPA de Task

### Branche

`feature/task-lifecycle-integration-tests`

### Objectif

Tester les callbacks JPA de l’entité `Task` avec Hibernate et une base H2 de test.

### Approche de test

Les callbacks `@PrePersist` et `@PreUpdate` existaient déjà.

Les tests ajoutés sont donc des tests de caractérisation exécutés avec la vraie couche JPA.

### Réalisé

- Création de `TaskLifecycleTest`.
- Utilisation de `@DataJpaTest`.
- Utilisation du vrai `TaskRepository`.
- Utilisation d’Hibernate et d’une base H2 de test.
- Test de l’initialisation automatique de `createdAt`.
- Test de l’initialisation automatique de `updatedAt`.
- Vérification de l’égalité initiale des deux timestamps.
- Modification d’une tâche persistée.
- Vérification de la conservation de `createdAt`.
- Vérification de l’actualisation de `updatedAt`.
- Utilisation de `saveAndFlush()` pour déclencher les callbacks JPA pendant les tests.

### Résultats des tests de caractérisation

#### Création d’une tâche

Le test `shouldInitializeTimestampsWhenTaskIsCreated` passe.

Il confirme que :

- `createdAt` est nul avant la persistance ;
- `updatedAt` est nul avant la persistance ;
- `@PrePersist` initialise automatiquement les deux timestamps ;
- `createdAt` et `updatedAt` ont exactement la même valeur lors de la création.

#### Modification d’une tâche

Le test `shouldUpdateOnlyUpdatedAtWhenTaskIsModified` passe.

Il confirme que :

- une modification réelle déclenche `@PreUpdate` ;
- `createdAt` conserve sa valeur initiale ;
- `updatedAt` prend une valeur plus récente ;
- le callback ne nécessite aucune intervention du service.

### Tests ajoutés

- `shouldInitializeTimestampsWhenTaskIsCreated`
- `shouldUpdateOnlyUpdatedAtWhenTaskIsModified`

### Tests

- [x] Test de création et d’initialisation des timestamps réussi.
- [x] Test de modification des timestamps réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Utilisation de `@DataJpaTest` pour charger uniquement la couche JPA.
- Utilisation de la base H2 de test.
- Utilisation de `saveAndFlush()` pour forcer la synchronisation avec la base.
- Aucun mock Mockito utilisé.
- Les responsabilités des timestamps restent dans l’entité `Task`.
- Aucune modification du code de production nécessaire.
- Une ancienne valeur contrôlée est affectée à `updatedAt` avant la modification.
- Le test vérifie que `@PreUpdate` remplace cette valeur lors du `flush`.
- Aucun délai artificiel avec `Thread.sleep()` n’est utilisé.

### Qualité du test temporel

Le test ne dépend d’aucune attente temporelle.

Une valeur antérieure contrôlée est utilisée pour vérifier que le callback `@PreUpdate` actualise correctement `updatedAt`.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/model/TaskLifecycleTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add Task lifecycle integration tests`

### Prochaine étape

Créer et merger la Pull Request de FD-027.

Les prochaines étapes du module Task devront être choisies depuis le backlog à jour, notamment :

- les tests des réponses `404` du controller ;
- la création de `TaskResponse` et du mapper ;
- la centralisation des erreurs métier.

---

## FD-028 - Créer TaskResponse

### Branche

`feature/task-response`

### Objectif

Créer un DTO de réponse indépendant de l’entité JPA `Task`.

### Pourquoi ce DTO est nécessaire

Le controller retourne actuellement directement l’entité `Task`.

Cela couple le format public de l’API avec le modèle de persistance.

`TaskResponse` définit explicitement les informations exposées par l’API et prépare la suppression de ce couplage.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Résultat TDD

- RED : `TaskResponseTest` ne compilait pas car le type `TaskResponse` n’existait pas.
- GREEN : création du record `TaskResponse` avec les neuf composants exigés par le test.
- REFACTOR : vérification de l’ordre des composants, des types, des imports et de la simplicité du DTO. Aucune logique supplémentaire n’était nécessaire.

### Réalisé

- Création de `TaskResponseTest`.
- Définition du contrat de `TaskResponse` avant son implémentation.
- Création du record `TaskResponse`.
- Ajout des neuf composants attendus.
- Vérification des accesseurs générés par le record.
- Vérification de la conservation des types métier.
- Validation de l’immutabilité du DTO.
- Exécution de toute la suite Maven.

### Structure de TaskResponse

```text
Long id
String title
String description
LocalDate dueDate
String assignedTo
TaskStatus status
TaskPriority priority
LocalDateTime createdAt
LocalDateTime updatedAt
```

### Test ajouté

`shouldExposeTaskResponseData`

### Tests

- [x] Test `shouldExposeTaskResponseData` réussi.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Utilisation d’un record Java pour représenter le DTO de réponse.
- Le DTO est immutable.
- Aucun setter n’est disponible.
- Aucune annotation Lombok n’est nécessaire.
- `TaskResponse` n’est pas une entité JPA.
- Les enums `TaskStatus` et `TaskPriority` restent exposés avec leurs types métier.
- Les timestamps restent présents dans le contrat actuel de réponse.
- Aucun mapper n’est ajouté dans FD-028.
- Aucune modification du controller ou du service n’est réalisée dans FD-028.

### Hors périmètre confirmé

- Le mapping entre `Task` et `TaskResponse`.
- Le retour de `TaskResponse` dans le controller.
- L’adaptation des tests du service et du controller.
- La modification du format JSON actuel.

### Fichiers modifiés

- `src/main/java/com/brewen/familydashboard/task/dto/TaskResponse.java`
- `src/test/java/com/brewen/familydashboard/task/dto/TaskResponseTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskResponse DTO`

### Prochaine étape

Créer et merger la Pull Request de FD-028.

Le ticket suivant sera :

```text
FD-029 - Créer un mapper entre Task et TaskResponse
```

---

## FD-029 - Créer un mapper entre Task et TaskResponse

### Branche

`feature/task-mapper`

### Objectif

Créer un mapper chargé de convertir une entité JPA `Task` en DTO `TaskResponse`.

### Problème résolu

`TaskResponse` existait depuis FD-028, mais aucune conversion centralisée ne permettait encore de le construire à partir d’une entité `Task`.

Sans mapper, cette conversion aurait dû être répétée dans le service ou le controller.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Résultat TDD

- RED : `TaskMapperTest` ne compilait pas car le type `TaskMapper` n’existait pas.
- GREEN : création d’un mapper manuel minimal avec la méthode statique `toResponse(Task)`.
- REFACTOR : vérification de la responsabilité unique du mapper, de l’ordre des propriétés et de l’absence de logique métier.

### Réalisé

- Création de `TaskMapperTest`.
- Définition du contrat de mapping avant l’implémentation.
- Création de `TaskMapper`.
- Ajout de la méthode statique `toResponse(Task)`.
- Conversion des neuf propriétés de `Task`.
- Vérification de toutes les propriétés du `TaskResponse` obtenu.
- Ajout d’un constructeur privé empêchant l’instanciation de la classe utilitaire.
- Exécution de toute la suite Maven.

### Mapping réalisé

```text
Task.id          → TaskResponse.id
Task.title       → TaskResponse.title
Task.description → TaskResponse.description
Task.dueDate     → TaskResponse.dueDate
Task.assignedTo  → TaskResponse.assignedTo
Task.status      → TaskResponse.status
Task.priority    → TaskResponse.priority
Task.createdAt   → TaskResponse.createdAt
Task.updatedAt   → TaskResponse.updatedAt
```

### Test ajouté

`shouldMapTaskToTaskResponse

---

## FD-030 - Retourner TaskResponse depuis TaskController

### Branche

`feature/task-controller-response`

### Objectif

Modifier `TaskController` afin qu’il retourne des DTO `TaskResponse` au lieu d’exposer directement les entités JPA `Task`.

### Situation initiale

Les éléments suivants existaient déjà :

- `TaskResponse` définissait le contrat de réponse de l’API ;
- `TaskMapper.toResponse(Task)` permettait de convertir une entité vers le DTO ;
- `TaskController` retournait encore directement les entités `Task`.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Progression réalisée

- [x] Migrer `GET /api/tasks`
- [x] Migrer `GET /api/tasks/{id}`
- [x] Migrer `POST /api/tasks`
- [x] Migrer `PATCH /api/tasks/{id}/status`
- [x] Vérifier que DELETE reste inchangé
- [x] Exécuter toute la suite Maven

### Résultat TDD

#### GET /api/tasks

- RED : le test exigeait `ResponseEntity<List<TaskResponse>>`, alors que le controller retournait encore `ResponseEntity<List<Task>>`.
- GREEN : conversion de chaque tâche avec `TaskMapper.toResponse()` en utilisant un stream.
- REFACTOR : vérification de la lisibilité du mapping et conservation du contrat JSON existant.

#### GET /api/tasks/{id}

- RED : le test exigeait `ResponseEntity<TaskResponse>`, alors que le controller retournait encore `ResponseEntity<Task>`.
- GREEN : conversion de l’entité contenue dans l’`Optional` avec `TaskMapper.toResponse()`.
- REFACTOR : conservation du traitement `404 Not Found` et simplification de la chaîne avec `Optional.map()`.

#### POST /api/tasks

- RED : le test exigeait `ResponseEntity<TaskResponse>`, alors que le controller retournait encore `ResponseEntity<Task>`.
- GREEN : conversion de la tâche créée avec `TaskMapper.toResponse()`.
- REFACTOR : séparation claire entre la création métier et la construction de la réponse HTTP.

#### PATCH /api/tasks/{id}/status

- RED : le test exigeait `ResponseEntity<TaskResponse>`, alors que le controller retournait encore `ResponseEntity<Task>`.
- GREEN : conversion de la tâche mise à jour avec `TaskMapper.toResponse()`.
- REFACTOR : conservation du traitement `404 Not Found` et de la chaîne utilisant `Optional`.

### Réalisé

- Modification du type de retour de `GET /api/tasks`.
- Conversion de la liste de tâches vers une liste de `TaskResponse`.
- Modification du type de retour de `GET /api/tasks/{id}`.
- Conversion de la tâche récupérée vers `TaskResponse`.
- Modification du type de retour de `POST /api/tasks`.
- Conversion de la tâche créée vers `TaskResponse`.
- Modification du type de retour de `PATCH /api/tasks/{id}/status`.
- Conversion de la tâche mise à jour vers `TaskResponse`.
- Conservation de `DELETE /api/tasks/{id}` avec `ResponseEntity<Void>`.
- Ajout de tests directs vérifiant les types Java retournés.
- Conservation des tests MockMvc vérifiant le contrat HTTP et JSON.
- Exécution de toute la suite Maven.

### Flux de la liste des tâches

```text
TaskService
→ List<Task>
→ Stream<Task>
→ TaskMapper.toResponse()
→ Stream<TaskResponse>
→ List<TaskResponse>
→ ResponseEntity<List<TaskResponse>>
```

### Flux de récupération par identifiant

```text
TaskService.getTaskById()
→ Optional<Task>
→ TaskMapper.toResponse()
→ Optional<TaskResponse>
→ ResponseEntity<TaskResponse>
```

### Flux de création

```text
TaskRequest
→ TaskService.createTask()
→ Task
→ TaskMapper.toResponse()
→ TaskResponse
→ ResponseEntity<TaskResponse>
```

### Flux de modification du statut

```text
TaskStatusRequest
→ TaskService.updateTaskStatus()
→ Optional<Task>
→ TaskMapper.toResponse()
→ Optional<TaskResponse>
→ ResponseEntity<TaskResponse>
```

### Tests ajoutés

- `shouldReturnTaskResponsesWhenGettingAllTasks`
- `shouldReturnTaskResponseWhenGettingTaskById`
- `shouldReturnTaskResponseWhenCreatingTask`
- `shouldReturnTaskResponseWhenUpdatingTaskStatus`

### Tests existants conservés

- Test MockMvc de récupération de toutes les tâches.
- Test MockMvc de récupération par identifiant.
- Test MockMvc de création.
- Test MockMvc de modification du statut.
- Test MockMvc de suppression.

### Tests

- [x] Tests directs du controller réussis.
- [x] Tests MockMvc du controller réussis.
- [x] Tests unitaires du service réussis.
- [x] Tests du mapper réussis.
- [x] Tests d’intégration JPA réussis.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- `TaskController` ne retourne plus directement l’entité JPA `Task`.
- Les conversions sont centralisées dans `TaskMapper`.
- `TaskService` continue à manipuler les entités métier.
- Le mapping vers les DTO est réalisé à la limite de la couche web.
- Le contrat JSON existant est conservé.
- Les réponses `404 Not Found` sont conservées.
- L’endpoint DELETE reste inchangé car il retourne `ResponseEntity<Void>`.
- Aucun mapping inverse n’est ajouté.
- Aucun mapping de `TaskRequest` vers `Task` n’est ajouté.
- Aucun ajout de MapStruct n’est nécessaire pour cette migration.

### Endpoints retournant désormais TaskResponse

```http
GET /api/tasks
GET /api/tasks/{id}
POST /api/tasks
PATCH /api/tasks/{id}/status
```

### Endpoint inchangé

```http
DELETE /api/tasks/{id}
```

L’endpoint DELETE retourne :

```text
204 No Content
```

et ne nécessite aucun DTO de réponse.

### Fichiers modifiés

- `src/main/java/com/brewen/familydashboard/task/controller/TaskController.java`
- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_CONTEXT.md`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Return TaskResponse from TaskController`

### Prochaine étape

Créer et merger la Pull Request de FD-030.

Le ticket suivant sera :

```text
FD-031 - Adapter les tests à TaskResponse
```

FD-031 devra compléter la sécurisation du contrat public en vérifiant notamment :

- tous les champs exposés par `TaskResponse` ;
- les timestamps dans les réponses ;
- l’absence de champs internes non prévus ;
- la cohérence des tests après la migration vers le DTO.

---

## FD-031 - Adapter les tests à TaskResponse

### Branche

`feature/task-response-contract-tests`

### Objectif

Renforcer les tests du `TaskController` afin de sécuriser précisément le contrat public défini par `TaskResponse`.

### Situation initiale

`TaskController` retournait déjà `TaskResponse` pour les endpoints concernés.

Les tests MockMvc vérifiaient une partie du JSON, mais ne contrôlaient pas systématiquement :

- les neuf propriétés du DTO ;
- les timestamps ;
- le nombre exact de propriétés ;
- l’absence de champs supplémentaires.

### Approche de test

Le comportement existait déjà.

Les tests ajoutés et renforcés sont donc des tests de caractérisation.

Aucune modification du code de production n’a été nécessaire.

### Progression réalisée

- [x] Sécuriser le contrat de `GET /api/tasks`
- [x] Sécuriser le contrat de `GET /api/tasks/{id}`
- [x] Vérifier la réponse de `POST /api/tasks`
- [x] Vérifier la réponse de `PATCH /api/tasks/{id}/status`
- [x] Vérifier le nombre exact de propriétés
- [x] Vérifier les timestamps
- [x] Exécuter toute la suite Maven

### Contrat JSON sécurisé

Chaque objet `TaskResponse` expose exactement les neuf propriétés suivantes :

```text
id
title
description
dueDate
assignedTo
status
priority
createdAt
updatedAt
```

### GET /api/tasks

Le test confirme que :

- la réponse contient une liste ;
- le premier élément contient exactement neuf propriétés ;
- tous les champs de `TaskResponse` sont exposés ;
- les enums sont correctement sérialisés ;
- `dueDate` est correctement sérialisé ;
- `createdAt` et `updatedAt` sont présents ;
- aucun champ supplémentaire n’est exposé.

### GET /api/tasks/{id}

Le test confirme que :

- la réponse contient directement un objet JSON ;
- l’objet contient exactement neuf propriétés ;
- les valeurs métier sont correctement sérialisées ;
- les timestamps sont présents ;
- aucun champ supplémentaire n’est exposé.

### POST /api/tasks

Le test confirme les deux côtés du controller :

```text
JSON entrant
→ désérialisation vers TaskRequest
→ appel de TaskService

Task retournée par TaskService
→ mapping vers TaskResponse
→ JSON sortant
```

Il vérifie également :

- les neuf propriétés de la réponse ;
- les timestamps ;
- la date d’échéance ;
- les enums ;
- les données transmises au service avec `ArgumentCaptor`.

### PATCH /api/tasks/{id}/status

Le test confirme que :

- la requête contient le nouveau statut ;
- le service reçoit l’identifiant et le statut attendus ;
- la réponse contient exactement neuf propriétés ;
- le statut actualisé est retourné ;
- `createdAt` est exposé ;
- `updatedAt` contient la valeur actualisée ;
- aucun champ supplémentaire n’est exposé.

### Assertions principales

Les tests de liste utilisent :

```text
$[0].*
```

pour vérifier les propriétés du premier élément.

Les tests retournant un objet unique utilisent :

```text
$.*
```

pour vérifier les propriétés de la réponse.

L’assertion `hasSize(9)` garantit qu’aucun champ non prévu n’est exposé.

### Tests renforcés

- `shouldReturnAllTasks`
- `shouldReturnTaskWhenIdExists`
- `shouldCreateTask`
- `shouldUpdateTaskStatusWhenTaskExists`

### Tests

- [x] Contrat JSON de la liste validé.
- [x] Contrat JSON de la récupération par identifiant validé.
- [x] Contrat JSON de la création validé.
- [x] Contrat JSON de la modification du statut validé.
- [x] Nombre exact de propriétés validé.
- [x] Timestamps validés.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Les tests MockMvc protègent le contrat JSON public.
- Chaque réponse `TaskResponse` doit contenir exactement neuf propriétés.
- L’ajout involontaire d’un champ fera échouer les tests.
- Les tests vérifient les valeurs et la structure.
- Les timestamps utilisent des valeurs fixes afin de garder les tests déterministes.
- Le service reste simulé avec `@MockitoBean`.
- Les tests directs ajoutés dans FD-030 restent conservés.
- Aucune modification du DTO, du mapper, du service ou du controller n’était nécessaire.

### Incident IntelliJ non fonctionnel

IntelliJ a temporairement signalé `TaskResponse` comme une classe implicite de compact source file.

Le problème provenait de l’analyse incomplète du projet et s’est résolu après l’ouverture d’un type dépendant.

Aucune modification du code n’a été nécessaire.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Strengthen TaskResponse contract tests`

### Prochaine étape

Créer et merger la Pull Request de FD-031.

Le ticket suivant sera :

```text
FD-032 - Tester les réponses 404 du TaskController avec MockMvc
```

---

## FD-032 - Tester les réponses 404 du TaskController avec MockMvc

### Branche

`feature/task-controller-not-found-tests`

### Objectif

Sécuriser les réponses `404 Not Found` du `TaskController` lorsqu’un identifiant ne correspond à aucune tâche.

### Endpoints couverts

```http
GET /api/tasks/{id}
DELETE /api/tasks/{id}
PATCH /api/tasks/{id}/status
```

### Approche de test

Les comportements existaient déjà dans le controller.

Les tests ajoutés sont donc des tests de caractérisation.

Ils ont permis de vérifier le comportement actuel sans introduire artificiellement une modification du code de production.

### Progression réalisée

- [x] Tester le 404 de `GET /api/tasks/{id}`
- [x] Tester le 404 de `DELETE /api/tasks/{id}`
- [x] Tester le 404 de `PATCH /api/tasks/{id}/status`
- [x] Vérifier les interactions avec TaskService
- [x] Exécuter toute la suite Maven

### GET /api/tasks/{id}

Le test `shouldReturnNotFoundWhenGettingMissingTask` confirme le scénario suivant :

```text
GET /api/tasks/999
→ TaskService.getTaskById(999L)
→ Optional.empty()
→ 404 Not Found
```

Le test vérifie que :

- l’identifiant de l’URL est transmis au service ;
- le service retourne `Optional.empty()` ;
- le controller retourne `404 Not Found` ;
- aucune tâche et aucun `TaskResponse` ne sont retournés.

### DELETE /api/tasks/{id}

Le test `shouldReturnNotFoundWhenDeletingMissingTask` confirme le scénario suivant :

```text
DELETE /api/tasks/999
→ TaskService.deleteTaskById(999L)
→ false
→ 404 Not Found
```

Le test vérifie que :

- l’identifiant de l’URL est transmis au service ;
- le service indique que la suppression n’a pas été effectuée ;
- le controller retourne `404 Not Found` ;
- aucun contenu n’est retourné dans la réponse.

### PATCH /api/tasks/{id}/status

Le test `shouldReturnNotFoundWhenUpdatingStatusOfMissingTask` confirme le scénario suivant :

```text
PATCH /api/tasks/999/status
→ désérialisation du JSON
→ TaskStatus.IN_PROGRESS
→ TaskService.updateTaskStatus(999L, TaskStatus.IN_PROGRESS)
→ Optional.empty()
→ 404 Not Found
```

Le test vérifie que :

- le JSON est correctement désérialisé ;
- l’identifiant est transmis au service ;
- le statut `IN_PROGRESS` est transmis au service ;
- le service retourne `Optional.empty()` ;
- le controller retourne `404 Not Found` ;
- aucun `TaskResponse` n’est retourné.

### Tests ajoutés

- `shouldReturnNotFoundWhenGettingMissingTask`
- `shouldReturnNotFoundWhenDeletingMissingTask`
- `shouldReturnNotFoundWhenUpdatingStatusOfMissingTask`

### Tests

- [x] Test du GET avec identifiant inexistant réussi.
- [x] Test du DELETE avec identifiant inexistant réussi.
- [x] Test du PATCH avec identifiant inexistant réussi.
- [x] Interactions avec TaskService vérifiées avec `verify`.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Les réponses `404 Not Found` actuelles sont conservées.
- Les scénarios d’absence sont simulés explicitement dans les tests.
- `Optional.empty()` représente l’absence pour GET et PATCH.
- La valeur `false` représente l’absence de suppression pour DELETE.
- Les tests vérifient les statuts HTTP et les appels au service.
- Aucune modification de `TaskController` n’était nécessaire.
- Aucune modification de `TaskService` n’était nécessaire.
- Aucune exception métier n’est créée dans ce ticket.
- Aucun format JSON d’erreur commun n’est ajouté dans ce ticket.

### Limite actuelle

Le controller construit actuellement lui-même les réponses `404 Not Found` à partir des résultats retournés par le service.

La prochaine évolution introduira une exception métier dédiée afin de représenter explicitement l’absence d’une tâche.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskController not found tests`

### Prochaine étape

Créer et merger la Pull Request de FD-032.

Le ticket suivant sera :

```text
FD-033 - Créer TaskNotFoundException
```

---

## FD-033 - Créer TaskNotFoundException

### Branche

`feature/task-not-found-exception`

### Objectif

Créer une exception métier dédiée représentant explicitement l’absence d’une tâche.

### Problème initial

L’absence d’une tâche était représentée par :

- `Optional.empty()` pour la lecture et la modification ;
- `false` pour la suppression ;
- des réponses `404 Not Found` construites localement dans le controller.

Une exception métier dédiée prépare la centralisation de cette gestion dans FD-034.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Résultat TDD

- RED : `TaskNotFoundExceptionTest` ne compilait pas car `TaskNotFoundException` n’existait pas.
- GREEN : création d’une exception minimale héritant de `RuntimeException` et construisant son message à partir de l’identifiant.
- REFACTOR : vérification du nom, du package, du constructeur et du message. Aucune logique supplémentaire n’était nécessaire.

### Réalisé

- Création de `TaskNotFoundExceptionTest`.
- Définition du contrat de l’exception avant son implémentation.
- Création de `TaskNotFoundException`.
- Héritage de `RuntimeException`.
- Ajout d’un constructeur prenant l’identifiant de la tâche.
- Construction d’un message métier contenant l’identifiant.
- Vérification du type de l’exception.
- Vérification exacte du message.
- Exécution de toute la suite Maven.

### Contrat de l’exception

Construction :

```java
new TaskNotFoundException(999L)
```

Message produit :

```text
Task not found with id: 999
```

### Test ajouté

`shouldCreateExceptionWithTaskIdInMessage`

### Tests

- [x] Test unitaire de l’exception réussi.
- [x] Héritage de `RuntimeException` vérifié.
- [x] Message métier vérifié.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- `TaskNotFoundException` est une exception non vérifiée.
- L’identifiant recherché est inclus dans le message.
- L’exception reste indépendante de Spring et de la couche HTTP.
- Aucune annotation `@ResponseStatus` n’est ajoutée.
- Aucune propriété supplémentaire n’est nécessaire pour le moment.
- Aucun changement de `TaskService` n’est réalisé dans FD-033.
- Aucun changement de `TaskController` n’est réalisé dans FD-033.
- Aucun changement de `GlobalExceptionHandler` n’est réalisé dans FD-033.
- Les réponses `404 Not Found` existantes restent inchangées dans ce ticket.

### Hors périmètre confirmé

- Lever l’exception depuis le service.
- Remplacer les retours `Optional.empty()`.
- Remplacer le retour booléen de suppression.
- Traduire l’exception en réponse HTTP `404 Not Found`.
- Ajouter un format JSON commun pour les erreurs.

### Fichiers modifiés

- `src/main/java/com/brewen/familydashboard/task/exception/TaskNotFoundException.java`
- `src/test/java/com/brewen/familydashboard/task/exception/TaskNotFoundExceptionTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add TaskNotFoundException`

### Prochaine étape

Créer et merger la Pull Request de FD-033.

Le ticket suivant sera :

```text
FD-034 - Centraliser les erreurs de tâche inexistante
```

FD-034 devra notamment :

- utiliser `TaskNotFoundException` dans la logique métier ;
- centraliser sa traduction en `404 Not Found` ;
- adapter les tests du service ;
- adapter les tests du controller ;
- réduire la gestion locale des erreurs dans le controller.

---

## FD-034 - Centraliser les erreurs de tâche inexistante

### Branche

`feature/centralize-task-not-found`

### Objectif

Utiliser `TaskNotFoundException` dans la logique métier et centraliser sa traduction en réponse HTTP `404 Not Found`.

### Situation initiale

L’absence d’une tâche était représentée de plusieurs manières :

```text
getTaskById()
→ Optional.empty()

deleteTaskById()
→ false

updateTaskStatus()
→ Optional.empty()
```

Le `TaskController` interprétait lui-même ces valeurs afin de construire les réponses `404 Not Found`.

### Situation finale

Le flux est désormais centralisé :

```text
TaskService
→ TaskNotFoundException
→ GlobalExceptionHandler
→ 404 Not Found
```

Le controller ne construit plus localement les réponses `404` liées à une tâche inexistante.

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Progression réalisée

- [x] Faire lever l’exception par `getTaskById`
- [x] Faire lever l’exception par `deleteTaskById`
- [x] Faire lever l’exception par `updateTaskStatus`
- [x] Ajouter le traitement dans `GlobalExceptionHandler`
- [x] Simplifier les signatures du service
- [x] Simplifier `TaskController`
- [x] Adapter les tests du service
- [x] Adapter les tests du controller
- [x] Exécuter toute la suite Maven

### Résultat TDD de getTaskById

#### RED

Le repository simulé retournait :

```text
Optional.empty()
```

Le test attendait une `TaskNotFoundException`, mais le service retournait encore un `Optional.empty()`.

Le test échouait donc parce qu’aucune exception n’était levée.

#### GREEN

`TaskService.getTaskById()` a été modifiée pour lever :

```java
new TaskNotFoundException(id)
```

lorsque le repository ne trouve aucune tâche.

#### REFACTOR

La signature a été simplifiée :

```text
Optional<Task> getTaskById(Long id)
→ Task getTaskById(Long id)
```

La méthode retourne directement la tâche lorsqu’elle existe.

### Résultat TDD de deleteTaskById

#### RED

Le repository simulé retournait :

```text
existsById(999L)
→ false
```

Le test attendait une `TaskNotFoundException`, mais le service retournait encore `false`.

#### GREEN

`TaskService.deleteTaskById()` a été modifiée pour lever `TaskNotFoundException` lorsque la tâche n’existe pas.

#### REFACTOR

La signature a été simplifiée :

```text
boolean deleteTaskById(Long id)
→ void deleteTaskById(Long id)
```

Le résultat est désormais représenté ainsi :

```text
Succès
→ la méthode se termine normalement

Tâche inexistante
→ TaskNotFoundException
```

### Résultat TDD de updateTaskStatus

#### RED

Le repository simulé retournait :

```text
Optional.empty()
```

Le test attendait une `TaskNotFoundException`, mais le service retournait encore un `Optional.empty()`.

#### GREEN

`TaskService.updateTaskStatus()` a été modifiée pour lever `TaskNotFoundException` lorsque la tâche n’existe pas.

#### REFACTOR

La signature a été simplifiée :

```text
Optional<Task> updateTaskStatus(Long id, TaskStatus status)
→ Task updateTaskStatus(Long id, TaskStatus status)
```

La méthode retourne directement la tâche mise à jour.

### Gestion globale de l’exception

`GlobalExceptionHandler` traite désormais `TaskNotFoundException`.

Le traitement produit :

```text
404 Not Found
```

Le format commun des corps d’erreur n’est pas encore ajouté.

Dans FD-034, la réponse `404` reste sans corps spécifique.

### Simplification du controller

Les endpoints suivants ne construisent plus eux-mêmes de réponse `404` :

```http
GET /api/tasks/{id}
DELETE /api/tasks/{id}
PATCH /api/tasks/{id}/status
```

Le controller :

- reçoit les requêtes HTTP ;
- appelle `TaskService` ;
- construit les réponses nominales ;
- laisse remonter `TaskNotFoundException` ;
- délègue sa traduction HTTP à `GlobalExceptionHandler`.

### Adaptation des tests du service

Les scénarios d’absence utilisent désormais `assertThrows`.

```text
getTaskById()
→ TaskNotFoundException

deleteTaskById()
→ TaskNotFoundException

updateTaskStatus()
→ TaskNotFoundException
```

Les tests vérifient également :

- le message de l’exception ;
- l’identifiant absent ;
- l’absence de suppression pour une tâche inexistante ;
- l’absence de sauvegarde lors d’une modification impossible.

La classe `TaskServiceTest` contient 14 tests réussis.

### Adaptation des tests du controller

Les tests MockMvc simulent désormais directement :

```text
TaskService
→ TaskNotFoundException
```

Ils vérifient ensuite que :

```text
GlobalExceptionHandler
→ 404 Not Found
```

Les trois scénarios concernés sont :

- récupération d’une tâche inexistante ;
- suppression d’une tâche inexistante ;
- modification du statut d’une tâche inexistante.

### Signatures finales du service

```text
List<Task> getTasks(...)

Task createTask(TaskRequest request)

Task getTaskById(Long id)

void deleteTaskById(Long id)

Task updateTaskStatus(Long id, TaskStatus status)
```

### Tests

- [x] Tests de `getTaskById()` réussis.
- [x] Tests de `deleteTaskById()` réussis.
- [x] Tests de `updateTaskStatus()` réussis.
- [x] Les 14 tests de `TaskServiceTest` réussissent.
- [x] Tests nominaux du controller réussis.
- [x] Tests `404 Not Found` du controller réussis.
- [x] Test du gestionnaire global réussi à travers MockMvc.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- L’absence d’une tâche est une erreur métier représentée par `TaskNotFoundException`.
- `TaskService` ne retourne plus de valeur technique pour indiquer cette absence.
- `Optional.empty()` n’est plus retourné par les méthodes métier concernées.
- Le booléen de suppression a été supprimé.
- `GlobalExceptionHandler` traduit l’exception en `404 Not Found`.
- `TaskController` reste concentré sur sa responsabilité HTTP.
- Le service reste indépendant des statuts HTTP.
- L’exception reste indépendante de Spring.
- Le futur format commun des erreurs sera ajouté dans un ticket séparé.

### Hors périmètre confirmé

- Gestion des valeurs de statut invalides.
- Gestion des corps JSON mal formés.
- Ajout d’un corps JSON commun pour les erreurs.
- Modification de `TaskResponse`.
- Modification de `TaskMapper`.

### Fichiers modifiés

- `src/main/java/com/brewen/familydashboard/task/service/TaskService.java`
- `src/main/java/com/brewen/familydashboard/task/controller/TaskController.java`
- `src/main/java/com/brewen/familydashboard/common/exception/GlobalExceptionHandler.java`
- `src/test/java/com/brewen/familydashboard/task/service/TaskServiceTest.java`
- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_CONTEXT.md`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Centralize task not found handling`

### Prochaine étape

Créer et merger la Pull Request de FD-034.

Le ticket suivant sera :

```text
FD-035 - Gérer les statuts invalides dans les paramètres
```

---

## FD-035 - Gérer les statuts invalides dans les paramètres

### Branche

`feature/handle-invalid-task-status`

### Objectif

Sécuriser la réponse HTTP produite lorsqu’une valeur invalide est reçue dans le paramètre `status` de `GET /api/tasks`.

### Endpoint testé

```http
GET /api/tasks?status=UNKNOWN
```

### Valeurs valides de TaskStatus

```text
TODO
IN_PROGRESS
DONE
```

### Approche de test

Le comportement de conversion est pris en charge par Spring MVC.

Le test ajouté est donc un test de caractérisation écrit avant toute éventuelle modification du code de production.

### Résultat du test de caractérisation

Le test `shouldReturnBadRequestWhenStatusParameterIsInvalid` passe immédiatement.

Spring MVC tente de convertir la valeur :

```text
UNKNOWN
```

vers l’enum :

```java
TaskStatus
```

Comme cette valeur ne correspond à aucune constante de l’enum, la conversion échoue avant l’appel du controller.

Le comportement obtenu est :

```text
Paramètre status invalide
→ échec de conversion par Spring MVC
→ TaskController non exécuté
→ TaskService non appelé
→ 400 Bad Request
```

### Progression réalisée

- [x] Tester une valeur de statut inconnue
- [x] Vérifier la réponse `400 Bad Request`
- [x] Vérifier que TaskService n’est pas appelé
- [x] Déterminer si un handler explicite est nécessaire
- [x] Exécuter toute la suite Maven

### Test ajouté

`shouldReturnBadRequestWhenStatusParameterIsInvalid`

### Tests

- [x] Test du paramètre invalide réussi.
- [x] Réponse `400 Bad Request` vérifiée.
- [x] Absence d’interaction avec `TaskService` vérifiée.
- [x] Tests existants du `TaskController` réussis.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Le mécanisme natif de conversion de Spring MVC est conservé.
- Aucun traitement explicite n’est ajouté dans `TaskController`.
- Aucun traitement explicite n’est ajouté dans `GlobalExceptionHandler`.
- `TaskService` ne doit pas être appelé lorsque la conversion du paramètre échoue.
- L’enum `TaskStatus` reste inchangé.
- Les valeurs admises restent `TODO`, `IN_PROGRESS` et `DONE`.
- Le `400 Bad Request` natif est suffisant pour ce ticket.
- Un message d’erreur personnalisé pourra être ajouté avec le futur format commun des réponses d’erreur.

### Hors périmètre confirmé

- Statut invalide dans un corps JSON.
- Corps JSON syntaxiquement incorrect.
- Format commun des réponses d’erreur.
- Modification de `TaskStatus`.
- Modification de `TaskController`.
- Modification de `TaskService`.
- Modification de `GlobalExceptionHandler`.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add invalid task status parameter test`

### Prochaine étape

Créer et merger la Pull Request de FD-035.

Le ticket suivant sera :

```text
FD-036 - Gérer les corps JSON mal formés
```

---

## FD-036 - Gérer les corps JSON mal formés

### Branche

`feature/handle-malformed-task-json`

### Objectif

Sécuriser les réponses HTTP produites lorsque Spring ne peut pas désérialiser le corps JSON d’une requête du module Task.

### Scénarios couverts

- JSON syntaxiquement incorrect lors de la création d’une tâche.
- Valeur `TaskStatus` inconnue lors de la création d’une tâche.
- Valeur `TaskStatus` inconnue lors de la modification du statut.

### Approche de test

Spring MVC et Jackson géraient déjà ces erreurs de désérialisation.

Les tests ajoutés sont donc des tests de caractérisation.

Aucune modification du code de production n’a été nécessaire.

### Progression réalisée

- [x] Tester un JSON syntaxiquement incorrect
- [x] Tester un statut inconnu dans le JSON de création
- [x] Tester un statut inconnu dans le JSON de modification
- [x] Vérifier les réponses `400 Bad Request`
- [x] Vérifier que TaskService n’est jamais appelé
- [x] Déterminer si un handler explicite est nécessaire
- [x] Exécuter toute la suite Maven

### JSON syntaxiquement incorrect

Le test `shouldReturnBadRequestWhenTaskJsonIsMalformed` envoie à :

```http
POST /api/tasks
```

un objet JSON ne possédant pas d’accolade fermante.

Le comportement constaté est :

```text
JSON syntaxiquement incorrect
→ lecture impossible par Jackson
→ TaskRequest non construit
→ TaskController non exécuté
→ TaskService non appelé
→ 400 Bad Request
```

### Statut inconnu lors de la création

Le test `shouldReturnBadRequestWhenCreatingTaskWithUnknownStatus` envoie un JSON syntaxiquement valide contenant :

```json
{
  "status": "UNKNOWN"
}
```

Le comportement constaté est :

```text
JSON syntaxiquement valide
→ conversion de UNKNOWN vers TaskStatus impossible
→ TaskRequest non construit
→ TaskController non exécuté
→ TaskService non appelé
→ 400 Bad Request
```

### Statut inconnu lors de la modification

Le test `shouldReturnBadRequestWhenUpdatingTaskWithUnknownStatus` envoie à :

```http
PATCH /api/tasks/1/status
```

le corps suivant :

```json
{
  "status": "UNKNOWN"
}
```

Le comportement constaté est :

```text
JSON syntaxiquement valide
→ conversion de UNKNOWN vers TaskStatus impossible
→ TaskStatusRequest non construit
→ TaskController non exécuté
→ TaskService non appelé
→ 400 Bad Request
```

### Tests ajoutés

- `shouldReturnBadRequestWhenTaskJsonIsMalformed`
- `shouldReturnBadRequestWhenCreatingTaskWithUnknownStatus`
- `shouldReturnBadRequestWhenUpdatingTaskWithUnknownStatus`

### Tests

- [x] Test du JSON syntaxiquement incorrect réussi.
- [x] Test du statut inconnu lors de la création réussi.
- [x] Test du statut inconnu lors de la modification réussi.
- [x] Réponses `400 Bad Request` vérifiées.
- [x] Absence d’interaction avec `TaskService` vérifiée.
- [x] Tests existants du `TaskController` réussis.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Le mécanisme natif de désérialisation de Spring MVC et Jackson est conservé.
- Aucun traitement supplémentaire n’est ajouté dans `TaskController`.
- Aucun traitement supplémentaire n’est ajouté dans `GlobalExceptionHandler`.
- `TaskService` ne doit jamais être appelé lorsque le corps ne peut pas être désérialisé.
- Les DTO de requête restent inchangés.
- L’enum `TaskStatus` reste inchangé.
- Le statut HTTP natif `400 Bad Request` est suffisant pour ce ticket.
- Un gestionnaire explicite sera ajouté dans FD-037 pour produire un corps d’erreur uniforme et un message contrôlé.

### Hors périmètre confirmé

- Format commun des réponses d’erreur.
- Message d’erreur personnalisé.
- Modification de `TaskRequest`.
- Modification de `TaskStatusRequest`.
- Modification de `TaskStatus`.
- Modification de `TaskController`.
- Modification de `TaskService`.

### Fichiers modifiés

- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Add malformed task JSON tests`

### Prochaine étape

Créer et merger la Pull Request de FD-036.

Le ticket suivant sera :

```text
FD-037 - Ajouter un format commun pour les réponses d’erreur
```

---

## FD-037 - Ajouter un format commun pour les réponses d’erreur

### Branche

`feature/common-error-response`

### Objectif

Définir une structure uniforme pour les principales réponses d’erreur de l’API.

### Situation initiale

Les erreurs de l’API utilisaient plusieurs formats :

- les tâches inexistantes retournaient `404 Not Found` sans corps spécifique ;
- les paramètres invalides utilisaient la réponse native de Spring MVC ;
- les corps JSON illisibles utilisaient la réponse native de Spring MVC ;
- les erreurs de validation retournaient un `ValidationErrorResponse` contenant une liste `errors`.

### Situation finale

Les principales erreurs utilisent désormais le même contrat :

```text
timestamp
status
error
message
path
```

### Phase TDD

- [x] RED
- [x] GREEN
- [x] REFACTOR

### Progression réalisée

- [x] Uniformiser les erreurs de tâche inexistante
- [x] Uniformiser les paramètres de statut invalides
- [x] Uniformiser les corps JSON impossibles à désérialiser
- [x] Uniformiser les erreurs de validation
- [x] Vérifier les cinq propriétés du contrat
- [x] Vérifier les messages métier
- [x] Vérifier les chemins HTTP
- [x] Vérifier l’absence d’appel au service lorsque la requête est invalide
- [x] Exécuter toute la suite Maven

### DTO commun créé

Le record suivant a été ajouté :

```text
ApiErrorResponse
```

Il contient exactement :

```text
LocalDateTime timestamp
int status
String error
String message
String path
```

### Contrat JSON commun

Exemple de réponse :

```json
{
  "timestamp": "2026-10-11T13:37:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: 999",
  "path": "/api/tasks/999"
}
```

### Tâche inexistante

`GlobalExceptionHandler` traite :

```text
TaskNotFoundException
```

Le flux est :

```text
TaskService
→ TaskNotFoundException
→ GlobalExceptionHandler
→ ApiErrorResponse
→ 404 Not Found
```

Exemple de message :

```text
Task not found with id: 999
```

### Paramètre de statut invalide

`GlobalExceptionHandler` traite :

```text
MethodArgumentTypeMismatchException
```

Exemple de requête :

```http
GET /api/tasks?status=UNKNOWN
```

Réponse obtenue :

```json
{
  "timestamp": "généré au moment de la réponse",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid value 'UNKNOWN' for parameter 'status'",
  "path": "/api/tasks"
}
```

La conversion échoue avant l’exécution du controller et `TaskService` n’est pas appelé.

### Corps JSON impossible à désérialiser

`GlobalExceptionHandler` traite :

```text
HttpMessageNotReadableException
```

Les scénarios couverts sont :

- JSON syntaxiquement incorrect ;
- valeur `TaskStatus` inconnue lors de la création ;
- valeur `TaskStatus` inconnue lors de la modification du statut.

Le message commun retenu est :

```text
Malformed or unreadable JSON request
```

`TaskService` n’est jamais appelé lorsque le corps HTTP ne peut pas être désérialisé.

### Erreurs de validation

`GlobalExceptionHandler` traite :

```text
MethodArgumentNotValidException
```

Les messages définis dans les annotations de validation sont conservés.

Exemple avec un titre vide :

```json
{
  "timestamp": "généré au moment de la réponse",
  "status": 400,
  "error": "Bad Request",
  "message": "Le titre est obligatoire",
  "path": "/api/tasks"
}
```

Lorsque plusieurs champs sont invalides, leurs messages sont concaténés avec le séparateur :

```text
;
```

Les tests vérifient la présence de chaque message sans dépendre de leur ordre.

### Messages de validation conservés

```text
Le titre est obligatoire
Le statut est obligatoire
La priorité est obligatoire
```

### Tests renforcés ou ajoutés

- test du format commun pour une tâche inexistante ;
- test du format commun pour un paramètre de statut invalide ;
- test du format commun pour un JSON syntaxiquement incorrect ;
- test du format commun pour un statut inconnu lors de la création ;
- test du format commun pour un statut inconnu lors de la modification ;
- test du format commun pour un titre vide ;
- test de la présence de plusieurs messages de validation.

### Assertions communes

Les tests vérifient systématiquement :

```text
exactement cinq propriétés
timestamp présent
status numérique
error correspondant au statut HTTP
message contrôlé
path correspondant à la requête
```

### Tests

- [x] Erreurs `404 Not Found` validées.
- [x] Paramètres invalides validés.
- [x] JSON syntaxiquement incorrect validé.
- [x] Valeurs d’enum inconnues validées.
- [x] Erreurs de validation simples validées.
- [x] Erreurs de validation multiples validées.
- [x] Absence d’interaction avec `TaskService` vérifiée lorsque nécessaire.
- [x] Tests existants du controller réussis.
- [x] Tests du service réussis.
- [x] Suite Maven complète réussie avec `.\mvnw clean test`.
- [x] Aucun échec de test.
- [x] Aucune erreur de test.
- [x] Build Maven terminé avec `BUILD SUCCESS`.

### Décisions techniques

- Utilisation d’un record immutable `ApiErrorResponse`.
- Utilisation d’un contrat commun à cinq propriétés.
- Centralisation de la construction des réponses d’erreur dans `GlobalExceptionHandler`.
- Conservation des messages métier de validation.
- Concaténation des messages multiples avec `;`.
- Utilisation du chemin fourni par `HttpServletRequest`.
- Aucun détail technique ou stack trace n’est exposé au client.
- `TaskController` et `TaskService` restent indépendants du format JSON des erreurs.
- Les timestamps sont générés au moment du traitement de l’erreur.
- Les tests vérifient la présence du timestamp sans imposer une valeur exacte.
- `ValidationErrorResponse` est supprimé s’il n’est plus utilisé.

### Exceptions centralisées

```text
TaskNotFoundException
MethodArgumentTypeMismatchException
HttpMessageNotReadableException
MethodArgumentNotValidException
```

### Statuts actuellement couverts

```text
400 Bad Request
404 Not Found
```

### Fichiers modifiés

- `src/main/java/com/brewen/familydashboard/common/exception/ApiErrorResponse.java`
- `src/main/java/com/brewen/familydashboard/common/exception/GlobalExceptionHandler.java`
- `src/test/java/com/brewen/familydashboard/task/controller/TaskControllerTest.java`
- `documentation/PROJECT_CONTEXT.md`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Fichier supprimé si inutilisé

- `src/main/java/com/brewen/familydashboard/common/exception/ValidationErrorResponse.java`

### Commit

`Add common API error response`

### Prochaine étape

Créer et merger la Pull Request de FD-037.

Le prochain ticket sera :

```text
FM-001 - Concevoir le module FamilyMember
```

---

## FM-001 - Concevoir le module FamilyMember

### Branche

`feature/design-family-member`

### Objectif

Définir le rôle, les responsabilités, les limites et les principaux cas d’usage du module `FamilyMember`.

### Contexte

Family Dashboard doit pouvoir représenter plusieurs personnes appartenant au même foyer.

Le module `Task` utilise actuellement un champ texte :

```text
assignedTo
```

Cette solution convient au premier MVP du module Task, mais elle ne fournit pas une identité stable et réutilisable entre les différents modules.

`FamilyMember` doit devenir progressivement la référence fonctionnelle commune pour représenter une personne du foyer.

### Réalisé

- Création du document `FAMILY_MEMBER_DESIGN.md`.
- Définition du rôle fonctionnel de `FamilyMember`.
- Distinction entre membre familial et utilisateur authentifié.
- Définition des principales responsabilités du module.
- Identification des cas d’usage principaux.
- Identification des futures relations avec les autres modules.
- Définition des limites du premier MVP.
- Définition des principes de conception.
- Répartition des décisions restantes entre FM-002 et FM-003.
- Mise à jour du contexte permanent du projet.

### Rôle retenu

`FamilyMember` représente une personne appartenant au foyer géré par Family Dashboard.

Un membre constitue une identité fonctionnelle stable pouvant être référencée par plusieurs fonctionnalités.

### Distinction avec l’authentification

Un `FamilyMember` n’est pas obligatoirement un utilisateur authentifié.

```text
FamilyMember
→ personne représentée dans le dashboard familial

Utilisateur authentifié
→ personne autorisée à accéder à l’application
```

Cette distinction doit notamment permettre de représenter :

- un enfant sans compte de connexion ;
- une personne utilisant uniquement l’écran familial ;
- le propriétaire d’une wishlist ;
- une personne assignée à une tâche ;
- un membre concerné par un événement ou une notification.

### Responsabilités fonctionnelles retenues

Le module devra permettre progressivement de :

- créer un membre familial ;
- consulter la liste des membres ;
- consulter un membre par identifiant ;
- modifier les informations autorisées ;
- gérer l’activation ou la disponibilité d’un membre ;
- fournir une identité stable aux autres modules ;
- contrôler les données exposées par l’API ;
- préserver la cohérence des données liées au membre.

### Cas d’usage principaux

```text
Ajouter un membre
Consulter les membres
Consulter un membre par identifiant
Modifier un membre
Désactiver un membre
Référencer un membre depuis un autre module
```

### Relations fonctionnelles futures

#### Task

Une tâche pourra être assignée à un `FamilyMember`.

Le champ actuel :

```text
assignedTo
```

sera remplacé progressivement par une relation structurée.

Cette migration ne fait pas partie du premier ticket d’implémentation de `FamilyMember`.

#### Wishlist

Chaque membre pourra posséder une wishlist.

Le module `FamilyMember` doit donc être disponible avant le développement du MVP Wishlist.

#### FamilyEvent

Un événement pourra éventuellement concerner un ou plusieurs membres.

La forme technique de cette relation reste à définir.

#### Notifications

Les destinataires et leurs préférences pourront être associés à un membre.

#### Dashboard

Les données du dashboard pourront être regroupées ou filtrées par membre.

### Limites retenues pour le MVP

Le premier MVP ne doit pas inclure immédiatement :

- l’authentification ;
- les mots de passe ;
- les rôles de sécurité ;
- les autorisations individuelles ;
- un profil utilisateur complexe ;
- une photo stockée en base ;
- des préférences avancées ;
- une suppression en cascade ;
- la migration immédiate du champ `Task.assignedTo`.

### Principes de conception retenus

- Chaque membre devra posséder un identifiant technique stable.
- Un membre ne sera pas automatiquement lié à un compte de connexion.
- Le module devra être indépendant des mécanismes d’authentification.
- L’entité JPA ne devra pas être exposée directement par l’API.
- Des DTO de requête et de réponse devront être prévus.
- Un mapper devra centraliser les conversions.
- Les données obligatoires devront être validées.
- Les erreurs devront utiliser `ApiErrorResponse`.
- La stratégie de suppression devra protéger les données liées.
- Une stratégie de désactivation devra être étudiée.
- Le développement devra être protégé par des tests automatisés.

### Décisions reportées à FM-002

FM-002 devra définir précisément :

- les propriétés du modèle ;
- les types Java ;
- les champs obligatoires ;
- les contraintes de validation ;
- les longueurs maximales ;
- le nom d’affichage ;
- la stratégie d’activation ;
- les éventuelles informations visuelles ;
- les champs d’audit ;
- les données exposées par l’API.

### Décisions reportées à FM-003

FM-003 devra définir précisément :

- la relation avec `Task` ;
- la relation avec `Wishlist` ;
- les relations éventuelles avec `FamilyEvent` ;
- la stratégie de migration de `assignedTo` ;
- la conservation des données liées ;
- le comportement lors de la désactivation d’un membre ;
- les règles de suppression.

### Tests

Aucun test Java n’a été ajouté dans FM-001.

Ce ticket est uniquement documentaire et architectural.

Les documents Markdown ont été relus afin de vérifier :

- la cohérence du rôle du module ;
- la distinction entre membre et utilisateur ;
- la séparation entre FM-001, FM-002 et FM-003 ;
- la place de `FamilyMember` avant la Wishlist ;
- le caractère progressif de la migration de `Task.assignedTo`.

### Décisions techniques

- Aucun code Java n’est ajouté dans FM-001.
- Aucune entité JPA n’est créée.
- Aucune relation JPA n’est créée.
- Aucun repository n’est créé.
- Aucun service n’est créé.
- Aucun controller n’est créé.
- Aucun DTO n’est créé.
- Aucune migration de données n’est effectuée.
- Le document de conception devient la référence pour les tickets suivants.

### Fichiers modifiés

- `documentation/FAMILY_MEMBER_DESIGN.md`
- `documentation/PROJECT_CONTEXT.md`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Design FamilyMember module`

### Prochaine étape

Créer et merger la Pull Request de FM-001.

Le ticket suivant sera :

```text
FM-002 - Définir le modèle FamilyMember
```
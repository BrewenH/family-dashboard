# Family Dashboard

## 1. Objectif du projet

Family Dashboard est une application familiale destinée à centraliser les informations utiles du quotidien.

À terme, l’application sera affichée sur un écran dans la maison grâce à un Raspberry Pi connecté en HDMI.

Fonctionnalités envisagées :

- gestion des tâches familiales ;
- événements et agenda familial ;
- dashboard central ;
- météo ;
- liste de courses ;
- concerts et festivals ;
- notifications ;
- widgets utiles au quotidien ;
- wishlist familiale.

## 2. Organisation technique

Le projet est organisé en deux applications distinctes :

```text
family-dashboard/
├── family-dashboard-back/
└── family-dashboard-front/
## 3. Stack technique

### Backend

- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- Jakarta Validation
- H2 pour le développement initial
- PostgreSQL envisagé plus tard
- Swagger avec Springdoc OpenAPI
- Lombok
- JUnit 5
- Mockito

### Frontend futur

- Angular
- TypeScript
- HTML
- SCSS
- HttpClient
- Routing
- Observables

### Déploiement futur

- Raspberry Pi
- Écran HDMI
- Navigateur en mode kiosque
- Backend et frontend accessibles sur le réseau familial

## 4. Conventions du projet

- Le code backend est écrit en anglais.
- L’interface utilisateur sera en français.
- Les messages de validation destinés à l’utilisateur peuvent être en français.
- L’architecture est organisée par fonctionnalité.
- Les solutions doivent rester simples, pédagogiques et maintenables.
- Les évolutions doivent être découpées en petits tickets.
- Un seul ticket est traité à la fois.
- Le passage au ticket suivant se fait uniquement lorsque le ticket actuel est validé.
- Les messages de commit sont rédigés en anglais.
- La branche `main` doit rester stable.
- Chaque fonctionnalité ou évolution importante utilise une branche dédiée.
- Les modifications sont intégrées dans `main` avec une Pull Request.

## 5. Architecture backend

Le backend utilise une architecture organisée par fonctionnalité.

Exemple pour le module `Task` :

```text
task/
├── controller/
├── dto/
├── model/
├── repository/
└── service/
```

Responsabilités :

- Controller : reçoit les requêtes HTTP et construit les réponses.
- Service : contient la logique métier.
- Repository : assure l’accès aux données.
- Model : contient les entités et les enums.
- DTO : représente les données échangées avec l’API.

Les fonctionnalités communes sont placées dans :

```text
common/
└── exception/
```

Architecture globale envisagée :

```text
familydashboard/
├── common/
├── dashboard/
├── task/
├── familymember/
├── wishlist/
├── event/
├── concert/
├── weather/
├── shopping/
└── user/
```

La structure pourra évoluer progressivement selon les besoins du projet.

## 6. Méthode de développement

Le projet doit désormais suivre une démarche TDD pour les nouvelles fonctionnalités.

Cycle obligatoire :

```text
RED

Écrire un test qui décrit le comportement attendu.
Lancer le test.
Vérifier que le test échoue pour la bonne raison.
```

```text
GREEN

Écrire uniquement le minimum de code nécessaire.
Relancer le test.
Vérifier que le test passe.
```

```text
REFACTOR

Améliorer la lisibilité et la structure du code.
Ne pas modifier le comportement fonctionnel.
Relancer tous les tests après chaque modification.
```

Pour une fonctionnalité déjà existante, les tests ajoutés sont considérés comme des tests de caractérisation.

Ils servent à documenter et sécuriser le comportement actuel avant un éventuel refactoring.

## 7. Workflow Git

Le workflow Git est réalisé principalement avec le widget Git et la section Commit d’IntelliJ.

### Début d’un ticket

1. Vérifier que la branche courante est `main`.
2. Mettre `main` à jour depuis le widget Git.
3. Créer une branche dédiée au ticket.
4. Activer le checkout de la nouvelle branche.

Exemple :

```text
feature/task-service-tests
```

### Pendant le développement

1. Appliquer le cycle TDD.
2. Vérifier régulièrement les fichiers modifiés.
3. Lancer les tests concernés.
4. Lancer les tests globaux avant le commit.

Commande Maven de validation globale :

```powershell
.\mvnw clean test
```

### Fin d’un ticket

1. Ouvrir la section Commit d’IntelliJ.
2. Vérifier les fichiers modifiés.
3. Cocher uniquement les fichiers du ticket.
4. Vérifier le diff.
5. Utiliser le message de commit prévu.
6. Sélectionner `Commit and Push`.
7. Créer une Pull Request vers `main`.
8. Vérifier les fichiers de la Pull Request.
9. Merger la Pull Request.

### Nettoyage après une Pull Request

Dans le widget Git en haut à gauche :

1. Sélectionner `main`.
2. Choisir `Checkout`.
3. Sélectionner `main`, puis `Update`.
4. Vérifier le merge dans la fenêtre Git et son onglet Log.
5. Sélectionner la branche feature locale terminée.
6. Choisir `Delete`.
7. Faire `Fetch`.

Le terminal est réservé aux diagnostics ou aux opérations particulières, par exemple :

- problème DNS ;
- référence distante obsolète ;
- erreur de connexion à GitHub ;
- vérification détaillée de l’état Git ;
- opération non disponible clairement dans IntelliJ.

## 8. Format des commits

Les messages de commit sont écrits en anglais.

Exemples :

```text
Add task status filter
Add TaskService write unit tests
Add task priority sorting unit tests
Add automatic updatedAt lifecycle handling
```

Un commit doit rester cohérent avec un ticket ou une étape clairement identifiable.

Avant chaque commit, il faut vérifier :

- les fichiers inclus ;
- les différences enregistrées ;
- le message du commit ;
- le résultat des tests ;
- la branche courante.

## 9. Règles d’accompagnement de Copilot

Copilot doit :

- travailler ticket par ticket ;
- expliquer ce qui est fait ;
- expliquer pourquoi cela est fait ;
- indiquer précisément où écrire le code ;
- avancer progressivement ;
- éviter de proposer trop de fichiers à la fois ;
- expliquer les concepts techniques ;
- appliquer la démarche TDD ;
- commencer les nouvelles fonctionnalités par un test en échec ;
- indiquer les tests à lancer ;
- donner le message exact du commit ;
- guider le workflow Git avec IntelliJ ;
- indiquer les fichiers attendus dans la section Commit ;
- produire une page de journal à la fin de chaque ticket ;
- maintenir le backlog à jour ;
- signaler les améliorations possibles sans les ajouter prématurément ;
- distinguer les warnings non bloquants des véritables erreurs ;
- ne pas modifier inutilement un code déjà fonctionnel.

## 10. Module Task actuel

### Modèle

L’entité `Task` contient notamment :

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

### Statuts

```text
TODO
IN_PROGRESS
DONE
```

### Priorités

```text
LOW
MEDIUM
HIGH
```

### Endpoints disponibles

```http
GET /api/tasks
GET /api/tasks?status=TODO
GET /api/tasks?sortByPriority=true
GET /api/tasks?status=TODO&sortByPriority=true
POST /api/tasks
GET /api/tasks/{id}
DELETE /api/tasks/{id}
PATCH /api/tasks/{id}/status
```

### DTO de réponse et mapping

Le module Task utilise désormais un DTO de réponse indépendant de l’entité JPA.

```text
Task
→ TaskMapper
→ TaskResponse
→ réponse HTTP JSON
```

`TaskResponse` est un record Java immutable contenant :

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

`TaskMapper` assure la conversion entre l’entité `Task` et le DTO `TaskResponse`.

Les endpoints suivants ne retournent plus directement l’entité JPA :

```http
GET /api/tasks
GET /api/tasks/{id}
POST /api/tasks
PATCH /api/tasks/{id}/status
```

L’endpoint DELETE continue de retourner `ResponseEntity<Void>` :

```http
DELETE /api/tasks/{id}
```

Règle durable :

- le controller ne doit pas exposer directement les entités JPA ;
- le service peut continuer à manipuler les entités métier ;
- le mapping vers les DTO de réponse est réalisé à la limite de la couche web ;
- les nouvelles propriétés internes d’une entité ne doivent pas être automatiquement exposées par l’API ;
- toute évolution de `TaskResponse` doit être sécurisée par des tests.

### Gestion des tâches inexistantes

L’absence d’une tâche est représentée par l’exception métier :

```text
TaskNotFoundException
```

Flux appliqué :

```text
TaskRepository
→ TaskService
→ TaskNotFoundException
→ GlobalExceptionHandler
→ 404 Not Found
```

Les méthodes concernées du service retournent directement leur résultat nominal :

```text
Task getTaskById(Long id)

void deleteTaskById(Long id)

Task updateTaskStatus(Long id, TaskStatus status)
```

Règles durables :

- `TaskService` lève `TaskNotFoundException` lorsqu’une tâche n’existe pas ;
- le service ne connaît pas le statut HTTP `404 Not Found` ;
- `TaskController` ne construit pas localement les réponses d’absence ;
- `GlobalExceptionHandler` centralise la traduction HTTP de l’exception ;
- l’exception métier reste indépendante de Spring ;
- les tests du service vérifient l’exception et son message ;
- les tests MockMvc vérifient la réponse HTTP produite par le gestionnaire global.

Le corps commun des réponses d’erreur sera défini dans une évolution ultérieure.

### Fonctionnalités disponibles

- Création d’une tâche.
- Récupération de toutes les tâches.
- Récupération d’une tâche par identifiant.
- Suppression d’une tâche.
- Modification du statut.
- Filtrage par statut.
- Tri par priorité.
- Combinaison du filtre et du tri.

### Qualité actuelle

- Validation de `TaskRequest`.
- Titre obligatoire.
- Statut obligatoire.
- Priorité obligatoire.
- Gestion globale des erreurs de validation.
- Messages de validation en français.
- Tri métier `HIGH`, `MEDIUM`, `LOW`.
- `createdAt` automatique avec `@PrePersist`.
- `updatedAt` automatique avec `@PreUpdate`.
- Utilisation de Lombok pour réduire le code répétitif.
- Tests unitaires de lecture du `TaskService`.
- Tests unitaires d’écriture du `TaskService`.
- Tests unitaires du tri par priorité.

### Format commun des réponses d’erreur

Les principales erreurs de l’API utilisent le record immutable :

```text
ApiErrorResponse
```

Structure :

```text
LocalDateTime timestamp
int status
String error
String message
String path
```

Exemple :

```json
{
  "timestamp": "2026-10-11T13:37:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: 999",
  "path": "/api/tasks/999"
}
```

`GlobalExceptionHandler` centralise actuellement :

```text
TaskNotFoundException
→ 404 Not Found

MethodArgumentTypeMismatchException
→ 400 Bad Request

HttpMessageNotReadableException
→ 400 Bad Request

MethodArgumentNotValidException
→ 400 Bad Request
```

Règles durables :

- les controllers ne construisent pas directement les corps d’erreur ;
- les services restent indépendants de la couche HTTP ;
- les détails techniques et les stack traces ne sont pas exposés ;
- le message doit être compréhensible par le consommateur de l’API ;
- le chemin correspond à l’URI de la requête ;
- les erreurs de validation conservent les messages des annotations ;
- plusieurs messages de validation sont concaténés avec `;` ;
- chaque évolution du format commun doit être protégée par des tests MockMvc.

### Tests automatisés existants

Les tests du `TaskService` couvrent notamment :

- récupération de toutes les tâches ;
- filtrage par statut ;
- récupération par identifiant ;
- identifiant inexistant ;
- création d’une tâche ;
- suppression d’une tâche existante ;
- refus de suppression d’une tâche inexistante ;
- modification du statut ;
- modification d’un identifiant inexistant ;
- tri `HIGH`, `MEDIUM`, `LOW` ;
- conservation de l’ordre si le tri est désactivé ;
- combinaison du filtre et du tri ;
- placement d’une priorité absente en dernière position.

### Limites actuelles

- Les controllers ne sont pas encore testés avec MockMvc.
- Les callbacks JPA ne sont pas encore couverts par des tests d’intégration.
- Le champ `assignedTo` reste temporairement un texte simple.
- Le warning Mockito lié à l’auto-attachement reste identifié comme non bloquant.

## Module FamilyMember

`FamilyMember` représentera une personne appartenant au foyer géré par Family Dashboard.

Un membre familial n’est pas obligatoirement un utilisateur authentifié.

Cette séparation permet de représenter des personnes sans compte de connexion, notamment des enfants ou des membres utilisant uniquement le dashboard familial.

Règles de conception retenues :

- un membre possède une identité technique stable ;
- le module sert de référence aux autres fonctionnalités ;
- le module doit exister avant la Wishlist ;
- la relation avec `Task` sera introduite progressivement ;
- le champ texte `assignedTo` n’est pas supprimé pendant le premier MVP ;
- l’entité JPA ne devra pas être exposée directement par l’API ;
- les erreurs utiliseront `ApiErrorResponse` ;
- la suppression physique n’est pas choisie automatiquement ;
- une stratégie de désactivation devra être étudiée ;
- les propriétés exactes seront définies dans FM-002 ;
- les relations techniques seront définies dans FM-003.
- displayName est obligatoire 
- displayName est limité à 50 caractères 
- active vaut true par défaut 
- la désactivation est préférée à la suppression 
- les timestamps sont automatiques 
- l’entité ne sera pas exposée directement 
- les relations sont reportées à FM-003

Le document de conception détaillé est :

```text
documentation/FAMILY_MEMBER_DESIGN.md
```

### Modèle fonctionnel retenu

Le modèle MVP envisagé contient :

```text
id: Long
displayName: String
color: String ou enum à confirmer
active: boolean
createdAt: LocalDateTime
updatedAt: LocalDateTime
```

`displayName` est obligatoire et représente le nom visible dans le dashboard.

Le modèle ne distingue pas immédiatement prénom et nom de famille.

Le client pourra fournir à la création :

```text
displayName
color
```

L’application gérera :

```text
id
active
createdAt
updatedAt
```

Un membre sera actif par défaut.

La désactivation sera préférée à la suppression physique afin de préserver les futures données liées.

Le document détaillé est :

```text
documentation/FAMILY_MEMBER_MODEL.md
```

Le type définitif de `color` et les relations avec les autres modules restent à confirmer.

## 11. Wishlist familiale

Une fonctionnalité Wishlist familiale a été ajoutée à la roadmap.

### Objectif

Chaque membre de la famille doit pouvoir disposer d’une wishlist permanente.

Cette wishlist doit être accessible aux autres membres afin de proposer des idées de cadeaux pour :

- les anniversaires ;
- Noël ;
- les fêtes ;
- les occasions particulières.

### MVP

La première version restera simple :

- une wishlist par membre ;
- ajout d’un souhait en texte libre ;
- consultation de la wishlist d’un membre ;
- suppression d’un souhait ;
- disponibilité permanente de la liste.

Exemple :

```text
Wishlist de Brewen

- Un livre sur Spring Boot
- Un casque audio
- Un jeu de société
- Une plante pour le bureau
```

### Évolutions futures

Un souhait pourra ensuite contenir :

```text
nom
description
lien produit
image
prix indicatif
niveau d’envie
commentaire
état de réservation
état acheté
```

Fonctionnalités futures envisagées :

- ajout d’un lien vers un article ;
- ajout d’une image ;
- ajout d’un prix indicatif ;
- ajout d’une priorité ou d’un niveau d’envie ;
- réservation d’un cadeau ;
- masquage de la réservation au propriétaire de la wishlist ;
- affichage de la réservation aux autres membres ;
- annulation d’une réservation ;
- marquage d’un souhait comme acheté ;
- prévention des achats en double.

### Dépendance fonctionnelle

La Wishlist doit être développée après la création d’un modèle `FamilyMember` minimal.

Relation envisagée :

```text
FamilyMember
     |
     | possède
     v
Wishlist
     |
     | contient
     v
WishlistItem
```

Une authentification complète n’est pas nécessaire pour le premier MVP.

### Position dans la roadmap

```text
1. Terminer la sécurisation du module Task.
2. Créer un modèle FamilyMember minimal.
3. Créer le MVP Wishlist.
4. Intégrer la Wishlist au futur Dashboard.
5. Créer l’interface Angular correspondante.
```

## 12. Documentation permanente

Le dossier suivant conserve le contexte du projet :

```text
documentation/
├── PROJECT_CONTEXT.md
├── PROJECT_JOURNAL.md
└── BACKLOG.md
```

Rôle des fichiers :

- `PROJECT_CONTEXT.md` contient le contexte durable et les règles du projet.
- `PROJECT_JOURNAL.md` contient l’historique chronologique des développements.
- `BACKLOG.md` contient les tickets terminés, en cours et futurs.

À la fin de chaque ticket :

1. Ajouter une entrée dans `PROJECT_JOURNAL.md`.
2. Mettre à jour le statut du ticket dans `BACKLOG.md`.
3. Vérifier si une décision durable doit être ajoutée dans `PROJECT_CONTEXT.md`.
4. Inclure ces modifications documentaires dans le commit du ticket lorsque cela reste cohérent.

## 13. Travail depuis plusieurs ordinateurs et plusieurs Copilot

Le projet est développé alternativement depuis :

- un ordinateur professionnel ;
- un ordinateur personnel ;
- une conversation Copilot professionnelle ;
- une conversation Copilot personnelle.

Les conversations Copilot ne constituent pas la mémoire officielle du projet.

La source de vérité est composée de :

```text
GitHub
├── code source
└── documentation
    ├── PROJECT_CONTEXT.md
    ├── PROJECT_JOURNAL.md
    └── BACKLOG.md
```

### Rôle de chaque fichier

- `PROJECT_CONTEXT.md` contient les règles durables, l’architecture et les décisions permanentes.
- `PROJECT_JOURNAL.md` contient l’historique chronologique et l’état précis des tickets.
- `BACKLOG.md` contient les tickets terminés, en cours et futurs.

### Règle obligatoire de synchronisation

À chaque changement significatif du projet, Copilot doit déterminer quels documents doivent être mis à jour.

À la fin de chaque ticket :

1. Mettre à jour `PROJECT_JOURNAL.md`.
2. Passer le ticket de `[~]` à `[x]` dans `BACKLOG.md`.
3. Vérifier si `PROJECT_CONTEXT.md` doit être mis à jour.
4. Inclure les fichiers documentaires concernés dans le commit.
5. Pousser la branche sur GitHub.
6. Merger la Pull Request avant de reprendre le ticket suivant sur un autre ordinateur.

### Démarrage d’un ticket

Lorsqu’un ticket commence, son statut dans `BACKLOG.md` passe de :

```markdown
- [ ] Ticket à faire
```

à :

```markdown
- [~] Ticket en cours
```

Une entrée temporaire peut être ajoutée dans `PROJECT_JOURNAL.md` si le développement risque de continuer sur un autre ordinateur.

### Changement d’ordinateur en cours de ticket

Avant de quitter un ordinateur :

1. Exécuter les tests disponibles.
2. Mettre à jour l’entrée temporaire du journal.
3. Indiquer la branche active.
4. Indiquer la phase TDD actuelle.
5. Indiquer les fichiers modifiés.
6. Indiquer le test en échec ou la prochaine action exacte.
7. Faire un commit intermédiaire cohérent.
8. Pousser la branche sur GitHub.

Format d’une entrée temporaire :

```markdown
## Ticket en cours : FD-XXX

### Branche

`feature/nom-de-branche`

### Phase TDD actuelle

- [x] RED
- [ ] GREEN
- [ ] REFACTOR

### Réalisé

- ...

### État actuel

- ...

### Tests

- ...

### Fichiers modifiés

- ...

### Prochaine action exacte

- ...
```

Un commit contenant volontairement un test rouge peut être poussé sur une branche feature pour permettre la reprise sur un autre ordinateur.

Il ne doit jamais être mergé dans `main` tant que la suite de tests n’est pas verte.

### Reprise sur un autre ordinateur

Avant de continuer :

1. Faire `Fetch` dans IntelliJ.
2. Récupérer `main` ou la branche feature active.
3. Faire `Checkout`.
4. Faire `Update`.
5. Lire `PROJECT_CONTEXT.md`.
6. Lire la dernière entrée de `PROJECT_JOURNAL.md`.
7. Vérifier le ticket `[~]` dans `BACKLOG.md`.
8. Continuer à partir de la phase TDD et de la prochaine action indiquées.

### Règle concernant les branches

Un ticket actif correspond à une seule branche distante.

Il ne faut pas créer deux implémentations concurrentes du même ticket sur les deux ordinateurs.

Si le ticket a commencé sur un ordinateur, l’autre ordinateur doit récupérer et continuer la même branche.

### Responsabilité de Copilot

Pour chaque ticket, Copilot doit fournir :

- la modification à effectuer dans `BACKLOG.md` au démarrage ;
- une entrée temporaire de journal si le ticket est interrompu ;
- l’entrée définitive du journal à la fin ;
- la mise à jour finale du backlog ;
- la mise à jour éventuelle du contexte permanent ;
- la liste des fichiers documentaires à inclure dans le commit ;
- le message exact du commit ;
- l’état nécessaire pour reprendre depuis l’autre ordinateur.
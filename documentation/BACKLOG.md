# Backlog du projet Family Dashboard

Ce fichier présente les tickets terminés, les prochaines étapes et les idées produit du projet.

Les tickets doivent être traités progressivement, un par un, avec une démarche TDD pour les nouvelles fonctionnalités.

---

## Légende

```text
[x] Terminé
[ ] À faire
[~] En cours
```

---

## Module d’initialisation

- [x] FD-001 - Créer le projet Spring Boot
- [x] FD-002 - Ajouter un endpoint de contrôle avec GET /api/health

---

## Module Task : conception et structure

- [x] FD-003 - Concevoir et préparer le module Task
- [x] FD-004 - Créer l’entité Task
- [x] FD-005 - Créer TaskRepository
- [x] FD-006 - Créer TaskService
- [x] FD-007 - Ajouter GET /api/tasks

---

## Module Task : opérations principales

- [x] FD-008 - Créer une tâche avec POST /api/tasks
- [x] FD-009 - Récupérer une tâche avec GET /api/tasks/{id}
- [x] FD-010 - Supprimer une tâche avec DELETE /api/tasks/{id}
- [x] FD-011 - Modifier le statut avec PATCH /api/tasks/{id}/status

---

## Module Task : validation et gestion des erreurs

- [x] FD-012 - Ajouter les validations sur TaskRequest
- [x] FD-013 - Ajouter une gestion globale des erreurs de validation
- [x] FD-032 - Tester les réponses 404 du TaskController avec MockMvc
- [x] FD-033 - Créer TaskNotFoundException
- [x] FD-034 - Centraliser les erreurs de tâche inexistante
- [x] FD-035 - Gérer les statuts invalides dans les paramètres
- [x] FD-036 - Gérer les corps JSON mal formés
- [x] FD-037 - Ajouter un format commun pour les réponses d’erreur

### Cas couverts dans FD-032

- [x] Tester GET /api/tasks/{id} avec un identifiant inexistant
- [x] Tester DELETE /api/tasks/{id} avec un identifiant inexistant
- [x] Tester PATCH /api/tasks/{id}/status avec un identifiant inexistant
- [x] Vérifier les interactions avec TaskService
- [x] Vérifier les réponses HTTP 404 Not Found

### Périmètre prévu pour FD-033

- Créer `TaskNotFoundException`.
- Faire hériter l’exception de `RuntimeException`.
- Construire un message contenant l’identifiant de la tâche.
- Ajouter les tests unitaires de l’exception.
- Ne pas encore modifier `TaskService`.
- Ne pas encore modifier `TaskController`.
- Ne pas encore modifier `GlobalExceptionHandler`.

### Périmètre prévu pour FD-034

- Utiliser `TaskNotFoundException` dans la logique métier.
- Centraliser sa traduction en réponse HTTP.
- Faire retourner `404 Not Found` par le gestionnaire global.
- Adapter les tests du service.
- Adapter les tests du controller.
- Supprimer progressivement la construction locale des réponses 404.

### Format commun des erreurs

Le format commun est maintenant implémenté avec `ApiErrorResponse`.

```text
timestamp
status
error
message
path
```

Cas actuellement couverts :

- [x] Tâche inexistante
- [x] Paramètre de statut invalide
- [x] Corps JSON syntaxiquement incorrect
- [x] Valeur d’enum inconnue dans un corps JSON
- [x] Erreurs de validation
- [x] Erreurs de validation multiples

---

## Module Task : recherche et tri

- [x] FD-014 - Filtrer les tâches par statut
- [x] FD-015 - Trier les tâches par priorité

### Améliorations restantes

- [ ] Ajouter éventuellement un filtre par personne assignée
- [ ] Ajouter éventuellement un filtre par date d’échéance
- [ ] Ajouter éventuellement un filtre par priorité
- [ ] Étudier une pagination lorsque le volume de tâches augmentera
- [ ] Étudier un tri par date d’échéance
- [ ] Étudier la combinaison de plusieurs filtres

---

## Module Task : audit

- [x] FD-016 - Initialiser automatiquement createdAt avec @PrePersist
- [x] FD-017 - Initialiser automatiquement updatedAt avec @PreUpdate

### Tests d’intégration

- [x] Tester createdAt avec un test d’intégration JPA
- [x] Tester updatedAt avec un test d’intégration JPA
- [x] Vérifier que createdAt reste inchangé après une modification
- [x] Vérifier que updatedAt est actualisé après une modification
- [x] Supprimer l’utilisation de Thread.sleep() dans les tests temporels

### Améliorations restantes

- [ ] Étudier une classe AuditableEntity commune
- [ ] Ajouter plus tard l’auteur d’une création
- [ ] Ajouter plus tard l’auteur d’une modification
- [ ] Étudier une Clock injectable si la gestion du temps devient plus complexe

---

## Module Task : tests automatisés

### Tests unitaires du service

- [x] FD-018 - Ajouter les tests unitaires de lecture du TaskService
- [x] FD-019 - Ajouter les tests unitaires d’écriture du TaskService
- [x] FD-020 - Ajouter les tests unitaires du tri par priorité

### Tests MockMvc du controller

- [x] FD-021 - Ajouter le premier test du TaskController avec MockMvc
- [x] FD-022 - Tester POST /api/tasks avec MockMvc
- [x] FD-023 - Tester GET /api/tasks/{id} avec MockMvc
- [x] FD-024 - Tester DELETE /api/tasks/{id} avec MockMvc
- [x] FD-025 - Tester PATCH /api/tasks/{id}/status avec MockMvc
- [x] FD-032 - Tester les réponses 404 du TaskController avec MockMvc

### Tests d’intégration JPA

- [x] FD-026 - Ajouter les tests d’intégration de TaskRepository
- [x] FD-027 - Tester le cycle de vie JPA de Task

### Dette technique liée aux tests

- [ ] Traiter la configuration explicite de l’agent Mockito
- [ ] Supprimer le warning d’auto-attachement Mockito
- [ ] Vérifier la compatibilité des tests avec les futures versions du JDK
- [ ] Définir une stratégie de tests pour la future intégration continue
- [ ] Étudier la génération d’un rapport de couverture
- [ ] Définir un niveau minimal de couverture pertinent
- [ ] Vérifier l’isolation de tous les tests d’intégration

---

## Module Task : DTO de réponse et mapping

Cette phase permet de ne plus exposer directement l’entité JPA `Task` dans les réponses HTTP.

- [x] FD-028 - Créer TaskResponse
- [x] FD-029 - Créer un mapper entre Task et TaskResponse
- [x] FD-030 - Retourner TaskResponse depuis TaskController
- [x] FD-031 - Adapter les tests à TaskResponse

### Résultat de FD-028

- Création du record immutable `TaskResponse`.
- Définition explicite des données exposées par l’API.
- Séparation entre l’entité JPA et le contrat public.
- Ajout d’un test définissant la structure du DTO.

### Structure actuelle de TaskResponse

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

### Résultat de FD-029

- Création de `TaskMapper`.
- Conversion d’une entité `Task` vers `TaskResponse`.
- Centralisation du mapping.
- Ajout d’un test unitaire du mapper.
- Conservation d’un mapper manuel simple.
- Aucun ajout de MapStruct pour le moment.

### Résultat de FD-030

Les endpoints suivants retournent désormais `TaskResponse` :

```http
GET /api/tasks
GET /api/tasks/{id}
POST /api/tasks
PATCH /api/tasks/{id}/status
```

L’endpoint suivant reste sans corps de réponse :

```http
DELETE /api/tasks/{id}
```

### Résultat de FD-031

- Adaptation des tests du controller.
- Vérification des neuf propriétés JSON.
- Vérification des timestamps.
- Vérification du nombre exact de propriétés.
- Protection contre l’exposition involontaire d’un nouveau champ interne.
- Conservation des tests directs et MockMvc.

---

## Module FamilyMember

Ce module doit être créé avant la Wishlist afin de représenter le propriétaire de chaque liste.

### Conception

- [x] FM-001 - Concevoir le module FamilyMember
- [x] FM-002 - Définir le modèle FamilyMember
- [ ] FM-003 - Définir les relations avec les futurs modules

### Backend MVP

- [ ] FM-004 - Créer l’entité FamilyMember
- [ ] FM-005 - Créer FamilyMemberRepository
- [ ] FM-006 - Créer FamilyMemberService
- [ ] FM-007 - Créer FamilyMemberController
- [ ] FM-008 - Ajouter un membre de la famille
- [ ] FM-009 - Consulter les membres de la famille
- [ ] FM-010 - Consulter un membre par identifiant
- [ ] FM-011 - Ajouter les validations
- [ ] FM-012 - Ajouter la gestion des erreurs
- [ ] FM-013 - Ajouter les tests unitaires
- [ ] FM-014 - Ajouter les tests du controller
- [ ] FM-015 - Ajouter les tests d’intégration

---

## FM-002 - Définir le modèle FamilyMember

### Branche

`feature/design-family-member-model`

### Objectif

Définir précisément les propriétés, les types Java envisagés, les contraintes de validation et les données publiques du futur modèle `FamilyMember`.

### Contexte

FM-001 a défini `FamilyMember` comme une personne appartenant au foyer et disposant d’une identité fonctionnelle stable.

FM-002 précise le modèle MVP sans créer encore de code Java.

### Réalisé

- Création de `FAMILY_MEMBER_MODEL.md`.
- Définition des propriétés du modèle MVP.
- Définition des types Java envisagés.
- Définition des contraintes de validation.
- Choix d’un nom d’affichage unique.
- Définition de la stratégie d’activation.
- Définition des champs d’audit.
- Définition des données fournies à la création.
- Définition des données exposées par l’API.
- Identification des données volontairement absentes du MVP.
- Définition des opérations HTTP envisagées.
- Préparation des décisions restantes pour FM-003.
- Mise à jour du document général de conception.
- Mise à jour du contexte permanent du projet.

### Modèle MVP retenu

```text
id
displayName
color
active
createdAt
updatedAt
```

### Propriétés retenues

#### id

```text
Type Java envisagé : Long
Généré par l’application : oui
Fourni par le client : non
Exposé par l’API : oui
Modifiable : non
```

`id` représente l’identité technique stable du membre.

#### displayName

```text
Type Java envisagé : String
Obligatoire : oui
Fourni à la création : oui
Exposé par l’API : oui
Modifiable : oui
Longueur maximale envisagée : 50 caractères
```

Exemples :

```text
Brewen
Maman
Papa
Léa
Mamie
```

Le MVP ne distingue pas immédiatement :

```text
firstName
lastName
```

Contraintes envisagées :

```java
@NotBlank(message = "Le nom d’affichage est obligatoire")
@Size(
        max = 50,
        message = "Le nom d’affichage ne doit pas dépasser 50 caractères"
)
```

#### color

```text
Type Java envisagé : String ou enum à confirmer
Obligatoire : non
Fourni à la création : facultatif
Exposé par l’API : oui
Modifiable : oui
```

La couleur permettra d’identifier visuellement un membre dans le dashboard.

Valeurs envisagées :

```text
BLUE
GREEN
PURPLE
ORANGE
PINK
```

La décision entre une chaîne contrôlée et un enum est reportée avant l’implémentation Java.

#### active

```text
Type Java envisagé : boolean
Obligatoire : oui
Valeur initiale : true
Fourni à la création : non
Exposé par l’API : oui
Modification : opération dédiée
```

Le statut distingue :

```text
active = true
→ membre disponible pour de nouvelles opérations

active = false
→ membre désactivé mais conservé
```

La désactivation permettra de conserver les données historiques liées au membre.

#### createdAt

```text
Type Java envisagé : LocalDateTime
Géré par l’application : oui
Fourni par le client : non
Exposé par l’API : oui
Modifiable : non
```

Initialisation envisagée :

```text
@PrePersist
```

#### updatedAt

```text
Type Java envisagé : LocalDateTime
Géré par l’application : oui
Fourni par le client : non
Exposé par l’API : oui
Modifiable directement : non
```

Initialisation et actualisation envisagées :

```text
@PrePersist
@PreUpdate
```

### Requête de création envisagée

Le futur DTO :

```text
FamilyMemberRequest
```

contiendra :

```text
displayName
color
```

Le client ne fournira pas :

```text
id
active
createdAt
updatedAt
```

Ces valeurs seront contrôlées par l’application.

### Réponse API envisagée

Le futur DTO :

```text
FamilyMemberResponse
```

contiendra :

```text
id
displayName
color
active
createdAt
updatedAt
```

L’entité JPA ne devra pas être retournée directement par le controller.

### Données volontairement absentes du MVP

```text
email
mot de passe
rôle de sécurité
date de naissance
adresse
numéro de téléphone
photo binaire
préférences détaillées
identifiants de connexion
```

Ces informations ne sont pas nécessaires au premier usage fonctionnel du module.

### Opérations envisagées

#### Création

```http
POST /api/family-members
```

#### Consultation de la liste

```http
GET /api/family-members
```

#### Consultation par identifiant

```http
GET /api/family-members/{id}
```

#### Modification

Une opération future permettra de modifier :

```text
displayName
color
```

La méthode HTTP exacte devra être confirmée avant l’implémentation.

#### Désactivation

Une opération dédiée permettra de passer un membre de :

```text
active = true
```

à :

```text
active = false
```

La suppression physique ne sera pas l’opération par défaut.

### Validation envisagée

#### Nom d’affichage absent ou vide

```text
400 Bad Request
```

Message :

```text
Le nom d’affichage est obligatoire
```

#### Nom d’affichage trop long

```text
400 Bad Request
```

Message :

```text
Le nom d’affichage ne doit pas dépasser 50 caractères
```

#### Couleur invalide

Le comportement dépendra du type retenu avant l’implémentation :

```text
String contrôlée
ou
enum FamilyMemberColor
```

### Gestion des erreurs envisagée

Le module utilisera le contrat commun :

```text
timestamp
status
error
message
path
```

Une future exception métier pourra représenter un membre inexistant :

```text
FamilyMemberNotFoundException
```

Message envisagé :

```text
Family member not found with id: 42
```

### Décisions reportées à FM-003

FM-003 devra définir :

- la relation future avec `Task` ;
- la relation avec `Wishlist` ;
- les relations éventuelles avec `FamilyEvent` ;
- la cardinalité de chaque relation ;
- les directions des relations JPA ;
- la stratégie de migration de `Task.assignedTo` ;
- le comportement des données liées lors de la désactivation ;
- les règles de suppression ;
- les contraintes référentielles ;
- la stratégie de chargement des relations.

### Tests

Aucun test Java n’a été ajouté dans FM-002.

Ce ticket est documentaire et prépare l’implémentation future.

Les documents ont été relus afin de vérifier :

- la cohérence avec FM-001 ;
- la simplicité du modèle MVP ;
- la séparation avec l’authentification ;
- le contrôle des données publiques ;
- la stratégie de désactivation ;
- l’absence de relations JPA prématurées ;
- la distinction entre FM-002 et FM-003.

### Décisions techniques

- Le modèle utilise un seul `displayName`.
- `displayName` est obligatoire.
- Sa longueur maximale envisagée est de 50 caractères.
- Une couleur visuelle facultative est prévue.
- Le type définitif de `color` reste à confirmer.
- Un membre est actif par défaut.
- Le client ne choisit pas `active` lors de la création.
- Les timestamps sont gérés automatiquement.
- Les DTO sont indépendants de l’entité JPA.
- La suppression physique n’est pas le comportement par défaut.
- Les informations d’authentification sont absentes du modèle.
- Les relations restent hors du modèle jusqu’à FM-003.

### Fichiers modifiés

- `documentation/FAMILY_MEMBER_MODEL.md`
- `documentation/FAMILY_MEMBER_DESIGN.md`
- `documentation/PROJECT_CONTEXT.md`
- `documentation/PROJECT_JOURNAL.md`
- `documentation/BACKLOG.md`

### Commit

`Define FamilyMember model`

### Prochaine étape

Créer et merger la Pull Request de FM-002.

Le ticket suivant sera :

```text
FM-003 - Définir les relations avec les futurs modules
```

### Évolution du module Task

- [ ] Remplacer progressivement le champ texte assignedTo
- [ ] Relier une tâche à un FamilyMember
- [ ] Prévoir la migration des données existantes
- [ ] Adapter TaskRequest
- [ ] Adapter TaskResponse
- [ ] Adapter TaskMapper
- [ ] Adapter TaskService
- [ ] Adapter TaskController
- [ ] Adapter les tests

---

## Module Wishlist : MVP

Le MVP doit rester simple et permettre de gérer des idées de cadeaux en texte libre.

### Conception

- [ ] WL-001 - Concevoir le module Wishlist
- [ ] WL-002 - Concevoir l’entité Wishlist
- [ ] WL-003 - Concevoir l’entité WishlistItem
- [ ] WL-004 - Définir la relation avec FamilyMember

### Persistance

- [ ] WL-005 - Créer l’entité Wishlist
- [ ] WL-006 - Créer l’entité WishlistItem
- [ ] WL-007 - Créer WishlistRepository
- [ ] WL-008 - Créer WishlistItemRepository

### Service et API

- [ ] WL-009 - Créer WishlistService
- [ ] WL-010 - Créer WishlistController
- [ ] WL-011 - Créer automatiquement une wishlist pour un membre
- [ ] WL-012 - Ajouter un souhait en texte libre
- [ ] WL-013 - Consulter la wishlist d’un membre
- [ ] WL-014 - Supprimer un souhait
- [ ] WL-015 - Modifier le texte d’un souhait

### Qualité

- [ ] WL-016 - Ajouter les validations des souhaits
- [ ] WL-017 - Ajouter la gestion des erreurs
- [ ] WL-018 - Ajouter les tests unitaires du service
- [ ] WL-019 - Ajouter les tests du controller
- [ ] WL-020 - Ajouter les tests d’intégration

---

## Module Wishlist : améliorations futures

### Informations produit

- [ ] Ajouter un nom distinct de la description
- [ ] Ajouter un lien vers un produit
- [ ] Ajouter un prix indicatif
- [ ] Ajouter une image
- [ ] Ajouter un niveau d’envie
- [ ] Ajouter un commentaire
- [ ] Ajouter un statut de disponibilité

### Réservation de cadeau

- [ ] Permettre à un membre de réserver un souhait
- [ ] Empêcher deux réservations concurrentes
- [ ] Masquer la réservation au propriétaire de la wishlist
- [ ] Afficher la réservation aux autres membres
- [ ] Permettre l’annulation d’une réservation
- [ ] Marquer un cadeau comme acheté
- [ ] Conserver la surprise pour le propriétaire

### Intégrations externes

- [ ] Étudier la récupération du titre depuis un lien produit
- [ ] Étudier la récupération d’une image
- [ ] Étudier la récupération du prix
- [ ] Vérifier les conditions d’utilisation des sites marchands
- [ ] Gérer les liens devenus indisponibles

---

## Module FamilyEvent

### Conception

- [ ] Concevoir le module FamilyEvent
- [ ] Définir le modèle FamilyEvent
- [ ] Définir les relations avec FamilyMember

### Backend

- [ ] Créer l’entité FamilyEvent
- [ ] Créer FamilyEventRepository
- [ ] Créer FamilyEventService
- [ ] Créer FamilyEventController
- [ ] Créer un événement familial
- [ ] Consulter les événements
- [ ] Modifier un événement
- [ ] Supprimer un événement
- [ ] Filtrer les événements par date

### Qualité

- [ ] Ajouter les validations
- [ ] Ajouter la gestion des erreurs
- [ ] Ajouter les tests unitaires
- [ ] Ajouter les tests du controller
- [ ] Ajouter les tests d’intégration

---

## Dashboard global

### Conception

- [ ] Concevoir le premier endpoint Dashboard
- [ ] Définir les informations affichées
- [ ] Définir l’ordre et la priorité des widgets

### Backend

- [ ] Créer DashboardResponse
- [ ] Créer DashboardService
- [ ] Créer DashboardController
- [ ] Afficher les tâches à faire
- [ ] Afficher les tâches prioritaires
- [ ] Afficher les événements à venir
- [ ] Intégrer les wishlists
- [ ] Ajouter les tests du Dashboard

---

## Liste de courses

- [ ] Concevoir le module Shopping
- [ ] Ajouter un article
- [ ] Modifier un article
- [ ] Supprimer un article
- [ ] Marquer un article comme acheté
- [ ] Consulter la liste de courses
- [ ] Ajouter les validations
- [ ] Ajouter la gestion des erreurs
- [ ] Ajouter les tests

---

## Météo

- [ ] Concevoir le widget météo
- [ ] Définir un modèle interne indépendant de l’API externe
- [ ] Ajouter une première météo simulée
- [ ] Choisir une API météo
- [ ] Intégrer les données réelles
- [ ] Gérer les erreurs externes
- [ ] Ajouter les tests

---

## Concerts et festivals

- [ ] Concevoir le module Concert
- [ ] Créer un modèle ConcertEvent indépendant des API externes
- [ ] Ajouter des données simulées
- [ ] Choisir une API réelle
- [ ] Rechercher les événements autour du domicile
- [ ] Ajouter le filtrage par date et distance
- [ ] Ajouter les tests

---

## Notifications

- [ ] Concevoir le système de notifications
- [ ] Notifier les tâches proches de leur échéance
- [ ] Notifier les événements à venir
- [ ] Définir les préférences par membre
- [ ] Ajouter les tests

---

## Frontend Angular

### Initialisation

- [ ] Créer le projet Angular
- [ ] Configurer l’environnement de développement
- [ ] Configurer l’URL du backend
- [ ] Créer la structure par fonctionnalité
- [ ] Ajouter le routing

### Interface Dashboard

- [ ] Créer le layout principal
- [ ] Créer le widget des tâches
- [ ] Créer le widget des événements
- [ ] Créer le widget météo
- [ ] Créer le widget Wishlist
- [ ] Ajouter un mode sombre
- [ ] Adapter l’interface à un écran mural
- [ ] Vérifier l’accessibilité

### Intégration API Task

- [ ] Créer TaskService côté Angular
- [ ] Afficher les tâches
- [ ] Créer une tâche
- [ ] Modifier un statut
- [ ] Supprimer une tâche
- [ ] Ajouter la gestion des erreurs
- [ ] Ajouter les tests frontend

### Intégration API FamilyMember

- [ ] Créer FamilyMemberService côté Angular
- [ ] Afficher les membres
- [ ] Ajouter un membre
- [ ] Afficher le détail d’un membre
- [ ] Ajouter les tests frontend

### Intégration API Wishlist

- [ ] Créer WishlistService côté Angular
- [ ] Afficher la wishlist d’un membre
- [ ] Ajouter un souhait
- [ ] Modifier un souhait
- [ ] Supprimer un souhait
- [ ] Ajouter les tests frontend

---

## Déploiement Raspberry Pi

- [ ] Choisir l’architecture de déploiement
- [ ] Préparer le Raspberry Pi
- [ ] Configurer le réseau familial
- [ ] Déployer le backend
- [ ] Déployer le frontend
- [ ] Configurer le démarrage automatique
- [ ] Configurer le navigateur en mode kiosque
- [ ] Configurer l’écran HDMI
- [ ] Ajouter les logs
- [ ] Prévoir les sauvegardes
- [ ] Documenter la maintenance

---

## Documentation du projet

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

---

## Ordre de travail recommandé

Ordre immédiat :

```text
1. Créer TaskNotFoundException.
2. Centraliser les erreurs de tâche inexistante.
3. Gérer les statuts invalides dans les paramètres.
4. Gérer les corps JSON mal formés.
5. Ajouter un format commun pour les réponses d’erreur.
6. Concevoir FamilyMember.
7. Développer le backend MVP de FamilyMember.
8. Relier progressivement Task à FamilyMember.
9. Concevoir et développer le MVP Wishlist.
10. Reprendre FamilyEvent et le Dashboard global.
11. Créer le frontend Angular.
```

---

## État actuel du projet

```text
Dernier ticket terminé : FM-002
Ticket en cours : aucun
Prochain ticket : FM-003
Branche de référence : feature/design-family-member-model
Documentation : à jour
```

## Ticket suivant recommandé

```text
FM-003 - Définir les relations avec les futurs modules
```

Ce ticket devra poursuivre la conception avant l’implémentation Java.

Premier objectif envisagé :

```text
Définir les relations fonctionnelles et techniques entre FamilyMember, Task, Wishlist et les futurs modules.
```

Questions principales :

```text
- Une tâche peut-elle être assignée à zéro ou un membre ?
- Une wishlist appartient-elle obligatoirement à un membre ?
- Un événement peut-il concerner plusieurs membres ?
- Un membre désactivé reste-t-il visible dans les historiques ?
- Les relations doivent-elles être unidirectionnelles ?
- Comment migrer progressivement Task.assignedTo ?
- Quelles règles appliquer lors de la suppression ou de la désactivation ?
```
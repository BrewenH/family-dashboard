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
- [x] FM-003 - Définir les relations avec les futurs modules

### Backend MVP

- [x] FM-004 - Créer l’entité FamilyMember
- [x] FM-005 - Créer FamilyMemberRepository
- [x] FM-006 - Créer FamilyMemberService
- [x] FM-007 - Créer FamilyMemberController
- [ ] FM-008 - Ajouter un membre de la famille
- [ ] FM-009 - Consulter les membres de la famille
- [ ] FM-010 - Consulter un membre par identifiant
- [ ] FM-011 - Ajouter les validations
- [ ] FM-012 - Ajouter la gestion des erreurs
- [ ] FM-013 - Ajouter les tests unitaires
- [ ] FM-014 - Ajouter les tests du controller
- [ ] FM-015 - Ajouter les tests d’intégration

### Résultat de FM-004

L’entité `FamilyMember` contient :

```text
id: Long
displayName: String
color: String
active: boolean
createdAt: LocalDateTime
updatedAt: LocalDateTime
```

Mapping JPA :

```text
Table : family_members
Identifiant : génération IDENTITY
displayName : obligatoire, 50 caractères maximum
color : facultative
active : obligatoire, true par défaut
createdAt : obligatoire et non modifiable
updatedAt : obligatoire
```

Cycle de vie :

```text
@PrePersist
→ initialise createdAt et updatedAt avec la même valeur

@PreUpdate
→ actualise uniquement updatedAt
```

Relations :

```text
Aucune relation JPA ajoutée dans FM-004
```

### Résultat de FM-005

L'interface `FamilyMemberRepository` a été créée.

```text
Héritage : JpaRepository<FamilyMember, Long>
Opérations validées : save(), findById()
Tests : Tests d'intégration JPA validés (DataJpaTest)


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

### Résultat de FM-006

La couche métier `FamilyMemberService` est implémentée.

```text
Responsabilité : Isolation de la logique métier et orchestration du repository.
Méthodes validées : saveFamilyMember()
Tests : Tests unitaires Mockito validés.


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

### Résultat de FM-007

Le point d'entrée API pour les membres est opérationnel.

```text
Endpoint : POST /api/family-members
Statut retourné : 201 Created
Tests : MockMvc validés.


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

### Relations définies dans FM-003

#### Task

```text
Une Task
→ zéro ou un FamilyMember

Un FamilyMember
→ zéro à plusieurs Task
```

Décisions :

- [x] Relation initialement unidirectionnelle.
- [x] Référence portée par `Task`.
- [x] Chargement paresseux privilégié.
- [x] Aucune collection de tâches dans `FamilyMember`.
- [x] Conservation temporaire de `assignedTo`.
- [x] Migration progressive des données.

#### Wishlist

```text
Un FamilyMember
→ zéro ou une Wishlist

Une Wishlist
→ exactement un FamilyMember
```

Décisions :

- [x] Référence portée par `Wishlist`.
- [x] Une wishlist au maximum par membre dans le MVP.
- [x] Aucune suppression automatique après désactivation.

#### FamilyEvent

```text
Plusieurs FamilyMember
↔
Plusieurs FamilyEvent
```

Décisions :

- [x] Besoin fonctionnel identifié.
- [ ] Structure technique à définir dans le module FamilyEvent.
- [ ] Choisir entre relation directe et entité d’association.

#### Règles communes

- [x] Désactivation préférée à la suppression.
- [x] Conservation des données historiques.
- [x] Réactivation possible avec le même identifiant.
- [x] Aucune cascade générale de suppression.
- [x] Relations ajoutées progressivement.
- [x] Entités liées non exposées directement par l’API.

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

## Architecture hardware et déploiement Raspberry Pi

### Conception hardware

- [ ] HW-001 - Définir les besoins matériels
- [ ] HW-002 - Choisir le Raspberry Pi et la mémoire
- [ ] HW-003 - Choisir l’écran et le mode d’interaction
- [ ] HW-004 - Définir le stockage et les sauvegardes
- [ ] HW-005 - Définir l’architecture logicielle de déploiement
- [ ] HW-006 - Définir le réseau et l’accès distant
- [ ] HW-007 - Définir l’alimentation, le boîtier et le refroidissement
- [ ] HW-008 - Finaliser l’architecture hardware cible

### Préparation du matériel

- [ ] Acheter ou préparer le Raspberry Pi retenu
- [ ] Préparer le SSD
- [ ] Installer Raspberry Pi OS 64 bits
- [ ] Configurer le démarrage sur SSD
- [ ] Installer le refroidissement
- [ ] Installer le Raspberry Pi dans son boîtier
- [ ] Vérifier la stabilité de l’alimentation
- [ ] Configurer la date, l’heure et le fuseau système

### Réseau

- [ ] Configurer Ethernet
- [ ] Configurer le Wi-Fi de secours
- [ ] Définir un nom d’hôte local
- [ ] Configurer une réservation DHCP
- [ ] Vérifier l’accès depuis le réseau familial
- [ ] Configurer l’accès SSH par clés
- [ ] Étudier WireGuard ou Tailscale pour la maintenance distante
- [ ] Vérifier qu’aucun port applicatif n’est exposé directement sur Internet

### Déploiement logiciel

- [ ] Installer Java 21 pour ARM64
- [ ] Choisir entre systemd et Docker
- [ ] Choisir la base de données de production
- [ ] Configurer la base de données
- [ ] Déployer le backend Spring Boot
- [ ] Déployer le frontend Angular
- [ ] Choisir entre Spring Boot et Nginx pour servir Angular
- [ ] Configurer les variables d’environnement
- [ ] Configurer le démarrage automatique des services
- [ ] Configurer les politiques de redémarrage

### Affichage en mode kiosque

- [ ] Installer Chromium
- [ ] Configurer Chromium en mode kiosque
- [ ] Configurer l’ouverture automatique de session
- [ ] Ouvrir automatiquement le dashboard
- [ ] Masquer les éléments du navigateur
- [ ] Configurer le rechargement après une erreur
- [ ] Configurer l’écran HDMI
- [ ] Confirmer l’orientation paysage ou portrait
- [ ] Désactiver la mise en veille non souhaitée
- [ ] Adapter la luminosité à l’usage domestique

### Écran et interaction

- [ ] Choisir la taille de l’écran
- [ ] Confirmer la résolution cible
- [ ] Décider si l’écran doit être tactile
- [ ] Définir la distance de lecture
- [ ] Définir la hauteur et l’emplacement d’installation
- [ ] Vérifier les contraintes d’accessibilité
- [ ] Fournir les contraintes hardware au frontend Angular

### Logs et supervision

- [ ] Configurer les logs du backend
- [ ] Configurer la rotation des logs
- [ ] Superviser `GET /api/health`
- [ ] Journaliser les démarrages
- [ ] Journaliser les échecs de sauvegarde
- [ ] Prévoir une alerte locale en cas de service indisponible
- [ ] Vérifier l’espace disque disponible

### Sauvegardes

- [ ] Choisir la cible de sauvegarde
- [ ] Configurer une sauvegarde quotidienne
- [ ] Conserver plusieurs versions
- [ ] Sauvegarder la base de données
- [ ] Sauvegarder la configuration
- [ ] Sauvegarder les éventuels médias futurs
- [ ] Chiffrer les sauvegardes distantes
- [ ] Tester la restauration
- [ ] Documenter la procédure de restauration

### Alimentation et disponibilité

- [ ] Choisir une alimentation adaptée
- [ ] Étudier un mini-onduleur ou un UPS HAT
- [ ] Configurer un arrêt propre
- [ ] Tester une coupure électrique
- [ ] Tester le redémarrage automatique
- [ ] Vérifier le redémarrage de tous les services
- [ ] Vérifier le retour automatique au mode kiosque

### Maintenance

- [ ] Documenter la connexion SSH
- [ ] Documenter les mises à jour du système
- [ ] Documenter le déploiement d’une nouvelle version
- [ ] Sauvegarder avant chaque mise à jour
- [ ] Prévoir une procédure de retour arrière
- [ ] Vérifier l’état de santé après une mise à jour
- [ ] Documenter le remplacement du Raspberry Pi
- [ ] Documenter le remplacement du SSD

### Cible de référence provisoire

```text
Raspberry Pi 5
8 Go de RAM recommandés
SSD USB 3 de 128 Go recommandé
Raspberry Pi OS 64 bits
Java 21
Spring Boot
Angular
PostgreSQL local à confirmer
Écran HDMI Full HD
Orientation paysage
Chromium en mode kiosque
Ethernet prioritaire
Wi-Fi secondaire
Refroidissement actif
Sauvegarde quotidienne sur support distinct
```

Cette cible doit être validée progressivement par les tickets HW-001 à HW-008.

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
1. Poursuivre le backend FamilyMember.
2. Développer le backend MVP de FamilyMember.
3. Relier progressivement Task à FamilyMember.
4. Traiter HW-001 à HW-003 avant de figer le frontend.
5. Concevoir le MVP Wishlist.
6. Développer le backend MVP de la Wishlist.
7. Reprendre FamilyEvent et le Dashboard global.
8. Concevoir le frontend Angular selon les contraintes de l’écran.
9. Traiter HW-004 à HW-008.
10. Préparer et tester le déploiement Raspberry Pi.
```

Séquence hardware :

```text
Avant le layout Angular définitif
→ HW-001
→ HW-002
→ HW-003

Avant le déploiement réel
→ HW-004
→ HW-005
→ HW-006
→ HW-007
→ HW-008
```

---

## État actuel du projet

```text
Dernier ticket terminé : FM-004
Ticket en cours : aucun
Prochain ticket : FM-005
Branche de référence : feature/family-member-entity
Documentation : à jour
```

## Ticket suivant recommandé

```text
FM-005 - Créer FamilyMemberRepository
```

Ce ticket devra respecter la démarche TDD.

Premier objectif envisagé :

```text
Créer le repository Spring Data JPA permettant de persister et de retrouver les membres familiaux.
```

Premiers comportements à couvrir :

```text
- Sauvegarder un membre
- Générer son identifiant
- Initialiser ses timestamps via Hibernate
- Retrouver un membre par identifiant
- Vérifier la valeur active par défaut
```
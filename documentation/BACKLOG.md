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
- [ ] FD-035 - Gérer les statuts invalides dans les paramètres
- [ ] FD-036 - Gérer les corps JSON mal formés
- [ ] FD-037 - Ajouter un format commun pour les réponses d’erreur

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

### Format d’erreur futur envisagé

Le format commun des erreurs pourra contenir :

```text
timestamp
status
error
message
path
```

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

- [ ] FM-001 - Concevoir le module FamilyMember
- [ ] FM-002 - Définir le modèle FamilyMember
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
Dernier ticket terminé : FD-034
Ticket en cours : aucun
Prochain ticket : FD-035
Branche de référence : feature/centralize-task-not-found
Documentation : à jour
```

---

## Ticket suivant recommandé

```text
FD-035 - Gérer les statuts invalides dans les paramètres
```

Ce ticket devra respecter la démarche TDD.

Premier objectif envisagé :

```text
Sécuriser la réponse HTTP produite lorsqu’une valeur de TaskStatus invalide est reçue dans le paramètre status de GET /api/tasks.
```

Périmètre initial :

```text
- Écrire le test MockMvc avant le traitement
- Envoyer une valeur de statut inconnue
- Vérifier la réponse HTTP attendue
- Centraliser le traitement de l’erreur de conversion
- Ne pas encore traiter les corps JSON mal formés
- Ne pas encore créer le format commun complet des erreurs
```
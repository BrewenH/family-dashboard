# Backlog du projet Family Dashboard

Ce fichier présente les tickets terminés, les prochaines étapes et les idées produit du projet.

Les tickets doivent être traités progressivement, un par un, avec la démarche TDD pour les nouvelles fonctionnalités.

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

## Module Task : validation et erreurs

- [x] FD-012 - Ajouter les validations sur TaskRequest
- [x] FD-013 - Ajouter une gestion globale des erreurs de validation

### Améliorations restantes

- [ ] Centraliser les erreurs de tâche inexistante
- [ ] Créer une exception TaskNotFoundException
- [ ] Gérer proprement un statut invalide dans les paramètres
- [ ] Gérer proprement les JSON mal formés
- [ ] Ajouter un format d’erreur commun avec timestamp, status et path

---

## Module Task : recherche et tri

- [x] FD-014 - Filtrer les tâches par statut
- [x] FD-015 - Trier les tâches par priorité

### Améliorations restantes

- [ ] Ajouter éventuellement un filtre par personne assignée
- [ ] Ajouter éventuellement un filtre par date d’échéance
- [ ] Ajouter éventuellement un filtre par priorité
- [ ] Étudier une pagination lorsque le volume de tâches augmentera

---

## Module Task : audit

- [x] FD-016 - Initialiser automatiquement createdAt avec @PrePersist
- [x] FD-017 - Initialiser automatiquement updatedAt avec @PreUpdate

### Améliorations restantes

- [ ] Tester createdAt avec un test d’intégration JPA
- [ ] Tester updatedAt avec un test d’intégration JPA
- [ ] Étudier une classe AuditableEntity commune
- [ ] Ajouter plus tard l’auteur d’une création ou d’une modification

---

## Module Task : tests automatisés

- [x] FD-018 - Ajouter les tests unitaires de lecture du TaskService
- [x] FD-019 - Ajouter les tests unitaires d’écriture du TaskService
- [x] FD-020 - Ajouter les tests unitaires du tri par priorité

### Prochaines étapes recommandées

- [ ] FD-021 - Ajouter les premiers tests du TaskController avec MockMvc
- [ ] FD-022 - Tester POST /api/tasks avec MockMvc
- [ ] FD-023 - Tester GET /api/tasks/{id} avec MockMvc
- [ ] FD-024 - Tester DELETE /api/tasks/{id} avec MockMvc
- [ ] FD-025 - Tester PATCH /api/tasks/{id}/status avec MockMvc
- [ ] FD-026 - Ajouter les tests d’intégration de TaskRepository
- [ ] FD-027 - Tester le cycle de vie JPA de Task

### Dette technique liée aux tests

- [ ] Traiter la configuration explicite de l’agent Mockito
- [ ] Supprimer le warning d’auto-attachement Mockito
- [ ] Vérifier la compatibilité des tests avec les futures versions du JDK
- [ ] Définir une stratégie de tests pour la future intégration continue

---

## Module Task : DTO de réponse et mapping

- [ ] Créer TaskResponse
- [ ] Créer un mapper entre Task et TaskResponse
- [ ] Ne plus retourner directement l’entité Task depuis le controller
- [ ] Vérifier les données réellement exposées par l’API
- [ ] Adapter les tests du service et du controller

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

### Évolution du module Task

- [ ] Remplacer progressivement le champ texte assignedTo
- [ ] Relier une tâche à un FamilyMember
- [ ] Prévoir la migration des données existantes
- [ ] Adapter les DTO et les tests

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

- [ ] Concevoir le module FamilyEvent
- [ ] Créer l’entité FamilyEvent
- [ ] Créer FamilyEventRepository
- [ ] Créer FamilyEventService
- [ ] Créer FamilyEventController
- [ ] Créer un événement familial
- [ ] Consulter les événements
- [ ] Modifier un événement
- [ ] Supprimer un événement
- [ ] Filtrer les événements par date
- [ ] Ajouter les validations
- [ ] Ajouter les tests automatisés

---

## Dashboard global

- [ ] Concevoir le premier endpoint Dashboard
- [ ] Créer DashboardResponse
- [ ] Créer DashboardService
- [ ] Créer DashboardController
- [ ] Afficher les tâches à faire
- [ ] Afficher les tâches prioritaires
- [ ] Afficher les événements à venir
- [ ] Intégrer plus tard les wishlists
- [ ] Ajouter les tests du Dashboard

---

## Liste de courses

- [ ] Concevoir le module Shopping
- [ ] Ajouter un article
- [ ] Modifier un article
- [ ] Supprimer un article
- [ ] Marquer un article comme acheté
- [ ] Consulter la liste de courses
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
- [ ] Créer le widget wishlist
- [ ] Ajouter un mode sombre
- [ ] Adapter l’interface à un écran mural
- [ ] Vérifier l’accessibilité

### Intégration API

- [ ] Créer TaskService côté Angular
- [ ] Afficher les tâches
- [ ] Créer une tâche
- [ ] Modifier un statut
- [ ] Supprimer une tâche
- [ ] Ajouter la gestion des erreurs
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
- [ ] DOC-008 - Documenter le transfert vers un autre Copilot

---

## Ordre de travail recommandé

Ordre immédiat :

```text
1. Terminer et merger la documentation permanente.
2. Vérifier que FD-020 est bien intégré dans main.
3. Ajouter les premiers tests du TaskController avec MockMvc.
4. Compléter progressivement les tests du controller.
5. Ajouter les tests d’intégration JPA.
6. Créer TaskResponse et le mapper.
7. Concevoir FamilyMember.
8. Développer le MVP Wishlist.
9. Reprendre FamilyEvent et le Dashboard global.
10. Créer le frontend Angular.
```

---

## Ticket suivant recommandé

```text
FD-021 - Ajouter les premiers tests du TaskController avec MockMvc
```

Ce ticket doit respecter la démarche TDD.

Premier objectif envisagé :

```text
Tester GET /api/tasks avec MockMvc.
```
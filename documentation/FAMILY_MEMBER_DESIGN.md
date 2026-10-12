# Conception du module FamilyMember

## 1. Objectif du document

Ce document définit le rôle fonctionnel et les limites du module `FamilyMember`.

Il prépare les tickets suivants sans figer prématurément l’implémentation technique.

```text
FM-001
→ rôle, responsabilités et cas d’usage

FM-002
→ propriétés, types et contraintes du modèle

FM-003
→ relations avec les autres modules

FM-004 et suivants
→ implémentation du backend
```

---

## 2. Rôle du module

`FamilyMember` représente une personne appartenant au foyer géré par Family Dashboard.

Cette personne peut être référencée par plusieurs fonctionnalités sans être nécessairement un utilisateur authentifié de l’application.

Un membre familial constitue une identité fonctionnelle stable, réutilisable dans les différents modules.

---

## 3. Distinction entre membre et utilisateur

Un `FamilyMember` ne représente pas automatiquement un compte de connexion.

```text
FamilyMember
→ personne représentée dans le dashboard familial

Utilisateur authentifié
→ personne autorisée à accéder à l’application
```

Les deux concepts pourront être reliés ultérieurement, mais ils ne doivent pas être confondus dans le MVP.

Cette séparation permet notamment de représenter :

- un enfant sans compte de connexion ;
- un membre utilisant uniquement l’écran familial ;
- un proche représenté dans une wishlist ;
- une personne assignée à une tâche sans authentification individuelle.

---

## 4. Responsabilités fonctionnelles

Le module devra permettre à terme de :

- créer un membre familial ;
- consulter tous les membres ;
- consulter un membre par identifiant ;
- modifier les informations d’un membre ;
- gérer la disponibilité ou l’activation d’un membre ;
- fournir une référence stable aux autres modules ;
- contrôler les données exposées par l’API ;
- conserver la cohérence des données liées au membre.

---

## 5. Cas d’usage principaux

### Ajouter un membre

Un membre du foyer peut être ajouté au dashboard.

Le système doit empêcher la création d’un membre ne respectant pas les futures règles de validation.

### Consulter les membres

Le dashboard doit pouvoir récupérer l’ensemble des membres utiles à l’affichage et à la sélection.

### Consulter un membre

Un membre doit pouvoir être retrouvé avec un identifiant stable.

### Modifier un membre

Les informations publiques ou fonctionnelles autorisées doivent pouvoir évoluer sans modifier l’identité technique du membre.

### Désactiver un membre

Le système devra pouvoir rendre un membre indisponible sans nécessairement supprimer immédiatement toutes les données qui lui sont associées.

La stratégie précise sera définie dans un ticket ultérieur.

---

## 6. Relations fonctionnelles futures

### Task

Une tâche pourra être assignée à un `FamilyMember`.

Le champ texte actuel :

```text
assignedTo
```

sera remplacé progressivement par une relation structurée.

La migration ne sera pas réalisée pendant la création initiale de `FamilyMember`.

### Wishlist

Chaque membre pourra posséder une wishlist.

Le module `FamilyMember` doit donc exister avant le développement du MVP Wishlist.

### FamilyEvent

Un événement familial pourra éventuellement concerner un ou plusieurs membres.

Cette relation reste à concevoir avant son implémentation.

### Notifications

Les préférences et destinataires des notifications pourront être associés à un membre.

### Dashboard

Les données affichées pourront être filtrées ou regroupées par membre.

---

## 7. Limites du MVP

Le premier MVP de `FamilyMember` ne devra pas inclure immédiatement :

- l’authentification ;
- la gestion des mots de passe ;
- les rôles de sécurité ;
- les autorisations individuelles ;
- un profil utilisateur complexe ;
- une photo stockée en base ;
- des préférences avancées ;
- une suppression en cascade des données ;
- la migration immédiate de toutes les tâches existantes.

Ces éléments pourront être étudiés après la mise en place du socle fonctionnel.

---

## 8. Principes de conception

### Identité stable

Chaque membre devra disposer d’un identifiant technique stable.

### Séparation des responsabilités

L’entité de persistance ne devra pas être exposée directement par l’API.

Le module devra prévoir :

```text
FamilyMemberRequest
FamilyMemberResponse
FamilyMemberMapper
```

### Validation

Les futures données obligatoires devront être validées à l’entrée de l’API.

### Gestion des erreurs

Les erreurs du module devront utiliser le format commun :

```text
timestamp
status
error
message
path
```

### Tests

Le module devra être développé progressivement avec :

- des tests unitaires du service ;
- des tests du mapper ;
- des tests MockMvc ;
- des tests d’intégration repository ;
- des tests des erreurs et validations.

---

## 9. Décisions retenues dans FM-001

- `FamilyMember` représente une personne du foyer.
- Un membre n’est pas obligatoirement un utilisateur authentifié.
- Le membre possède une identité stable réutilisable.
- Le module précède le développement de la Wishlist.
- La migration du champ `Task.assignedTo` sera progressive.
- La suppression physique n’est pas retenue automatiquement.
- Une stratégie de désactivation devra être envisagée.
- L’entité JPA ne devra pas être retournée directement par le controller.
- Les erreurs devront utiliser le format commun de l’API.
- Les propriétés exactes seront définies dans FM-002.
- Les relations techniques seront définies dans FM-003.

---

## 10. Questions reportées à FM-002

FM-002 devra définir précisément :

- le nom des propriétés ;
- leurs types Java ;
- les champs obligatoires ;
- les longueurs maximales ;
- la gestion éventuelle d’un nom d’affichage ;
- la gestion d’une couleur ou d’un avatar ;
- la stratégie d’activation ;
- les champs d’audit ;
- les DTO de requête et de réponse envisagés.

---

## 11. Questions reportées à FM-003

FM-003 devra préciser :

- la relation future avec `Task` ;
- la relation avec `Wishlist` ;
- les relations éventuelles avec `FamilyEvent` ;
- la stratégie de migration du champ `assignedTo` ;
- le comportement des relations lors de la désactivation d’un membre ;
- les règles de suppression et de conservation des données.
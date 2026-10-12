# Modèle FamilyMember

## 1. Objectif du document

Ce document définit le modèle fonctionnel du futur objet `FamilyMember`.

Il précise :

- les propriétés du MVP ;
- les types Java envisagés ;
- les contraintes de validation ;
- la stratégie d’activation ;
- les données reçues par l’API ;
- les données retournées par l’API ;
- les données conservées uniquement dans le modèle de persistance.

Aucun code Java n’est créé dans FM-002.

---

## 2. Modèle MVP retenu

Le modèle initial contiendra :

```text
id
displayName
color
active
createdAt
updatedAt
```

---

## 3. Propriétés

### id

```text
Type Java envisagé : Long
Obligatoire en base : oui
Fourni à la création : non
Exposé par l’API : oui
Modifiable : non
```

`id` représente l’identité technique stable du membre.

Il sera généré automatiquement lors de la persistance.

---

### displayName

```text
Type Java envisagé : String
Obligatoire : oui
Fourni à la création : oui
Exposé par l’API : oui
Modifiable : oui
```

`displayName` représente le nom affiché dans le dashboard.

Exemples :

```text
Brewen
Maman
Papa
Léa
Mamie
```

Le MVP ne distingue pas immédiatement le prénom et le nom de famille.

#### Contraintes envisagées

```text
Valeur non nulle
Valeur non vide
Valeur non composée uniquement d’espaces
Longueur maximale : 50 caractères
```

Annotations envisagées pour le futur DTO de requête :

```java
@NotBlank(message = "Le nom d’affichage est obligatoire")
@Size(
        max = 50,
        message = "Le nom d’affichage ne doit pas dépasser 50 caractères"
)
```

---

### color

```text
Type Java envisagé : String
Obligatoire : non
Fourni à la création : facultatif
Exposé par l’API : oui
Modifiable : oui
```

`color` permettra d’identifier visuellement un membre dans le dashboard.

Le MVP stockera une valeur textuelle contrôlée par l’application.

Exemples possibles :

```text
BLUE
GREEN
PURPLE
ORANGE
PINK
```

FM-002 ne crée pas encore d’enum Java.

La décision entre une chaîne contrôlée et un enum sera confirmée avant l’implémentation.

#### Limites

La couleur ne doit pas contenir :

- de code HTML ;
- de style CSS complet ;
- de donnée binaire ;
- d’URL.

---

### active

```text
Type Java envisagé : boolean
Obligatoire : oui
Valeur initiale : true
Fourni directement à la création : non
Exposé par l’API : oui
Modifiable par une opération dédiée : oui
```

`active` indique si le membre peut encore être sélectionné pour de nouvelles opérations.

```text
active = true
→ membre disponible

active = false
→ membre désactivé mais conservé
```

La désactivation doit permettre de préserver les données historiques liées au membre.

Le client ne choisira pas librement la valeur de `active` pendant la création.

---

### createdAt

```text
Type Java envisagé : LocalDateTime
Obligatoire après persistance : oui
Fourni par le client : non
Exposé par l’API : oui
Modifiable : non
```

`createdAt` sera initialisé automatiquement lors de la création.

Le mécanisme envisagé est cohérent avec le module Task :

```text
@PrePersist
```

---

### updatedAt

```text
Type Java envisagé : LocalDateTime
Obligatoire après persistance : oui
Fourni par le client : non
Exposé par l’API : oui
Modifiable directement : non
```

`updatedAt` sera initialisé lors de la création puis actualisé automatiquement lors des modifications.

Mécanismes envisagés :

```text
@PrePersist
@PreUpdate
```

---

## 4. Données volontairement absentes du MVP

Le premier modèle ne contiendra pas :

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

Ces informations ne sont pas nécessaires pour identifier un membre dans le dashboard familial initial.

---

## 5. Requête de création envisagée

Le futur DTO de requête pourra être :

```text
FamilyMemberRequest
```

Structure envisagée :

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

Ces valeurs seront gérées par l’application.

---

## 6. Réponse API envisagée

Le futur DTO de réponse pourra être :

```text
FamilyMemberResponse
```

Structure envisagée :

```text
id
displayName
color
active
createdAt
updatedAt
```

L’entité JPA ne devra pas être retournée directement par le controller.

---

## 7. Opérations envisagées

### Création

```http
POST /api/family-members
```

Données reçues :

```text
displayName
color
```

### Consultation de la liste

```http
GET /api/family-members
```

Par défaut, la stratégie d’affichage des membres désactivés devra être précisée pendant la conception de l’API.

### Consultation par identifiant

```http
GET /api/family-members/{id}
```

### Modification

Une opération de modification pourra mettre à jour :

```text
displayName
color
```

La méthode HTTP exacte sera définie avant l’implémentation.

### Désactivation

Une opération dédiée devra permettre de passer :

```text
active = true
```

à :

```text
active = false
```

La suppression physique ne sera pas l’opération par défaut.

---

## 8. Validation envisagée

### displayName absent ou vide

Résultat attendu :

```text
400 Bad Request
```

Message envisagé :

```text
Le nom d’affichage est obligatoire
```

### displayName trop long

Résultat attendu :

```text
400 Bad Request
```

Message envisagé :

```text
Le nom d’affichage ne doit pas dépasser 50 caractères
```

### color invalide

Le comportement dépendra du type finalement retenu :

```text
String contrôlée
ou
enum FamilyMemberColor
```

La décision sera confirmée avant l’implémentation.

---

## 9. Gestion des erreurs envisagée

Les erreurs devront utiliser le format commun :

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

Exemple de message envisagé :

```text
Family member not found with id: 42
```

---

## 10. Modèle conceptuel

```text
FamilyMember
├── id: Long
├── displayName: String
├── color: String ou enum à confirmer
├── active: boolean
├── createdAt: LocalDateTime
└── updatedAt: LocalDateTime
```

---

## 11. Données internes et données publiques

### Données internes

```text
identité JPA
callbacks de cycle de vie
configuration de persistance
relations techniques futures
```

### Données publiques

```text
id
displayName
color
active
createdAt
updatedAt
```

Le contrat public doit rester contrôlé par `FamilyMemberResponse`.

---

## 12. Décisions retenues dans FM-002

- Le modèle utilise un seul `displayName`.
- `displayName` est obligatoire.
- Sa longueur maximale envisagée est de 50 caractères.
- Une couleur visuelle facultative est prévue.
- Le type définitif de la couleur reste à confirmer.
- Le membre possède un statut actif ou désactivé.
- Un membre est actif par défaut.
- Le client ne choisit pas librement `active` à la création.
- Les timestamps sont gérés automatiquement.
- L’API utilise des DTO indépendants de l’entité.
- La suppression physique n’est pas le comportement par défaut.
- Les informations d’authentification sont absentes du modèle.
- Les relations sont reportées à FM-003.

---

## 13. Questions restantes pour FM-003

- Une tâche peut-elle être assignée à zéro ou un membre ?
- Une wishlist appartient-elle obligatoirement à un membre ?
- Un membre désactivé reste-t-il visible dans les historiques ?
- Quelles contraintes référentielles appliquer ?
- Comment migrer progressivement `Task.assignedTo` ?
- Comment empêcher la suppression de données encore référencées ?
- Quelle stratégie de chargement JPA utiliser ?
- Quelles relations doivent être unidirectionnelles ?
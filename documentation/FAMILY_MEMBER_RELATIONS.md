# Relations du module FamilyMember

## 1. Objectif du document

Ce document définit les relations fonctionnelles et techniques futures entre `FamilyMember` et les autres modules de Family Dashboard.

Il précise :

- les cardinalités ;
- le propriétaire fonctionnel des relations ;
- les directions JPA envisagées ;
- les règles de désactivation ;
- les règles de suppression ;
- la stratégie de migration progressive ;
- les limites à respecter pendant l’implémentation.

Aucune relation JPA n’est créée dans FM-003.

---

## 2. Principes généraux

### Relations ajoutées progressivement

La création de l’entité `FamilyMember` ne doit pas entraîner immédiatement la modification de tous les modules existants.

Ordre envisagé :

```text
1. Créer FamilyMember sans relation.
2. Créer le repository et le service.
3. Créer l’API du module.
4. Ajouter les tests du module.
5. Introduire progressivement les relations.
6. Migrer les données existantes.
7. Supprimer les anciens champs devenus inutiles.
```

### Relations unidirectionnelles par défaut

Les relations JPA devront être unidirectionnelles lorsque la navigation dans les deux sens n’est pas indispensable.

Cette règle doit limiter :

- le couplage entre les entités ;
- le chargement involontaire de collections ;
- les problèmes de sérialisation ;
- les boucles dans `equals()`, `hashCode()` ou `toString()` ;
- les suppressions en cascade non souhaitées.

### Chargement explicite

Les collections liées à un membre ne devront pas être automatiquement chargées avec chaque membre.

Les requêtes devront récupérer uniquement les données nécessaires au cas d’usage.

### Entités non exposées

Les relations JPA ne devront pas être sérialisées directement dans les réponses HTTP.

Les API utiliseront des DTO contrôlés.

---

## 3. Relation entre FamilyMember et Task

### Besoin fonctionnel

Une tâche peut être assignée à un membre du foyer.

L’assignation doit être facultative afin de permettre la création de tâches non encore attribuées.

### Cardinalité

```text
Un FamilyMember
→ peut être assigné à zéro, une ou plusieurs tâches

Une Task
→ peut être assignée à zéro ou un FamilyMember
```

Notation :

```text
FamilyMember 1
←
Task 0..*
```

Du point de vue de `Task` :

```text
Task
→ assignedMember: FamilyMember facultatif
```

### Direction envisagée

La relation JPA initiale devra être portée uniquement par `Task`.

```text
Task
→ FamilyMember
```

`FamilyMember` ne contiendra pas immédiatement :

```java
List<Task> tasks;
```

Les tâches d’un membre seront récupérées avec une requête dédiée dans `TaskRepository`.

### Relation JPA envisagée

```text
Task
→ @ManyToOne
→ FamilyMember
```

La relation devra être facultative pendant la migration.

Conceptuellement :

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "assigned_member_id")
private FamilyMember assignedMember;
```

Cette annotation est seulement envisagée. Elle ne sera pas ajoutée dans FM-003.

### Chargement

Le chargement `LAZY` est privilégié afin de ne pas charger automatiquement un membre dans tous les cas d’usage internes de `Task`.

Les DTO détermineront explicitement les informations du membre retournées par l’API.

### Migration de assignedTo

Le champ existant :

```java
private String assignedTo;
```

ne sera pas supprimé immédiatement.

Migration envisagée :

```text
Étape 1
→ ajouter assignedMember en conservant assignedTo

Étape 2
→ adapter les DTO et le mapper

Étape 3
→ utiliser assignedMember pour les nouvelles données

Étape 4
→ migrer les anciennes valeurs assignedTo lorsque cela est possible

Étape 5
→ vérifier les données non associables automatiquement

Étape 6
→ supprimer assignedTo dans un ticket dédié
```

### Valeurs historiques non associables

Une ancienne valeur texte peut ne correspondre à aucun membre existant.

Exemples :

```text
Tout le monde
Invité
Brew
Nom avec une ancienne orthographe
```

Ces valeurs ne doivent pas être supprimées silencieusement.

La migration devra produire un rapport ou conserver temporairement la valeur historique.

### Désactivation d’un membre assigné

Si un membre est désactivé :

```text
Les tâches historiques restent liées au membre
Les nouvelles assignations au membre doivent être interdites
Les tâches actives existantes peuvent être réassignées
```

La relation ne doit pas être supprimée automatiquement.

### Suppression

La suppression physique d’un membre référencé par des tâches ne doit pas être autorisée par défaut.

Aucune cascade de suppression depuis `FamilyMember` vers `Task` ne doit être ajoutée.

---

## 4. Relation entre FamilyMember et Wishlist

### Besoin fonctionnel

Chaque membre pourra posséder une wishlist personnelle.

### Cardinalité MVP

```text
Un FamilyMember
→ possède zéro ou une Wishlist

Une Wishlist
→ appartient exactement à un FamilyMember
```

Notation :

```text
FamilyMember 1
↔
Wishlist 0..1
```

### Propriétaire de la relation

`Wishlist` devra porter la référence vers `FamilyMember`.

```text
Wishlist
→ owner: FamilyMember
```

Cette direction permet de conserver `FamilyMember` indépendant de l’implémentation interne de la wishlist.

### Relation JPA envisagée

Deux possibilités seront évaluées pendant la conception de Wishlist :

```text
@ManyToOne avec contrainte unique sur owner_id
```

ou :

```text
@OneToOne
```

Pour le MVP, une relation `@OneToOne` peut exprimer directement la règle d’une wishlist par membre.

Cependant, une contrainte unique explicite en base devra rester la garantie principale.

### Création de la wishlist

La wishlist pourra être créée :

```text
automatiquement lors de la création du membre
```

ou :

```text
à la première utilisation
```

La stratégie définitive sera choisie dans le module Wishlist.

### Désactivation du propriétaire

La désactivation d’un membre ne doit pas supprimer sa wishlist.

La wishlist pourra devenir non modifiable ou masquée des sélections courantes, tout en restant conservée.

### Suppression

Aucune suppression en cascade automatique de la wishlist ne devra être introduite sans règle métier explicite.

---

## 5. Relation entre FamilyMember et FamilyEvent

### Besoin fonctionnel

Un événement familial pourra concerner :

```text
aucun membre particulier
un membre
plusieurs membres
tous les membres
```

### Cardinalité envisagée

```text
Un FamilyEvent
→ concerne zéro, un ou plusieurs FamilyMember

Un FamilyMember
→ peut être concerné par zéro, un ou plusieurs FamilyEvent
```

Notation :

```text
FamilyEvent * ↔ * FamilyMember
```

### Complexité différée

La relation many-to-many ne doit pas être introduite avant la conception détaillée de `FamilyEvent`.

Une entité d’association pourra être préférable à une relation `@ManyToMany` directe si des informations supplémentaires sont nécessaires.

Exemples :

```text
participant obligatoire
participant facultatif
organisateur
invité
statut de participation
```

### Décision FM-003

FM-003 identifie le besoin mais ne choisit pas encore entre :

```text
@ManyToMany
```

et :

```text
entité d’association FamilyEventParticipant
```

Une entité d’association sera privilégiée si la relation possède des données métier.

---

## 6. Relation avec les notifications

### Besoin fonctionnel

Certaines notifications pourront être destinées à un membre.

Exemples :

```text
tâche proche de son échéance
événement familial à venir
rappel de wishlist
```

### Conception envisagée

Les préférences de notification ne feront pas partie du modèle MVP de `FamilyMember`.

Elles devront être portées par un module spécifique.

Conceptuellement :

```text
NotificationPreference
→ FamilyMember
```

Le membre reste la référence du destinataire, mais ne devient pas responsable de la logique d’envoi.

### Authentification

Un membre sans compte de connexion pourra exister sans canal de notification personnel.

La présence d’un `FamilyMember` ne garantit donc pas la présence :

```text
d’une adresse email
d’un appareil
d’un compte
d’un canal de notification
```

---

## 7. Utilisation dans le Dashboard

Le dashboard pourra utiliser `FamilyMember` pour :

- regrouper les tâches par membre ;
- filtrer les tâches ;
- afficher une couleur associée ;
- afficher les wishlists ;
- identifier les événements concernant un membre.

Le Dashboard ne devra pas posséder de relation JPA directe avec `FamilyMember`.

Il composera des données provenant des différents services applicatifs.

---

## 8. Désactivation d’un membre

### Objectif

La désactivation permet de retirer un membre des nouvelles sélections sans supprimer ses données historiques.

### Comportement envisagé

Un membre désactivé :

```text
n’apparaît plus par défaut dans les sélections
ne peut plus recevoir de nouvelle tâche
conserve ses tâches historiques
conserve sa wishlist
conserve ses participations aux événements
reste consultable dans les historiques
```

### Réactivation

La possibilité de réactiver un membre devra être prévue.

```text
active = false
→ active = true
```

Une réactivation ne recrée pas un nouveau membre et conserve le même identifiant.

---

## 9. Suppression d’un membre

### Suppression logique privilégiée

L’opération normale sera la désactivation.

La suppression physique sera réservée à des cas contrôlés.

### Suppression physique envisagée

Une suppression physique ne pourra être autorisée que si :

```text
aucune tâche ne référence le membre
aucune wishlist ne référence le membre
aucun événement ne référence le membre
aucune donnée historique nécessaire ne dépend du membre
```

### Absence de cascade

Aucune cascade de suppression générale ne devra être configurée depuis `FamilyMember`.

En particulier, la suppression d’un membre ne doit jamais supprimer automatiquement :

```text
les tâches
les souhaits
les événements
les historiques
```

---

## 10. Contraintes référentielles

Les futures clés étrangères devront empêcher les références invalides.

Principes envisagés :

```text
assigned_member_id peut être nul pendant la migration
wishlist.owner_id ne doit pas être nul
les références historiques restent valides après désactivation
les suppressions physiques référencées sont refusées
```

Les contraintes exactes seront définies lors de la création des tables concernées.

---

## 11. Stratégie de chargement

### Relations vers FamilyMember

Les relations JPA vers `FamilyMember` devront utiliser par défaut :

```text
FetchType.LAZY
```

lorsque cela est compatible avec le cas d’usage.

### Collections

Les collections de tâches, événements et autres données ne seront pas ajoutées immédiatement dans `FamilyMember`.

Les données seront récupérées par des requêtes explicites.

### API

Les DTO et les mappers décideront quelles informations du membre sont exposées.

Une réponse `TaskResponse` pourra éventuellement exposer un résumé :

```text
assignedMemberId
assignedMemberDisplayName
assignedMemberColor
```

La structure définitive sera définie pendant l’évolution du module Task.

---

## 12. Résumé des cardinalités

### Task

```text
FamilyMember 1
←
Task 0..*

Une Task possède zéro ou un membre assigné.
```

### Wishlist

```text
FamilyMember 1
↔
Wishlist 0..1

Une Wishlist appartient exactement à un membre.
```

### FamilyEvent

```text
FamilyMember *
↔
FamilyEvent *

La structure technique reste à définir.
```

### Notifications

```text
FamilyMember 1
←
NotificationPreference 0..*

Relation future portée par le module de notifications.
```

### Dashboard

```text
Aucune relation JPA directe.
Composition de données par les services.
```

---

## 13. Décisions retenues dans FM-003

- Une tâche peut être assignée à zéro ou un membre.
- La relation Task vers FamilyMember sera initialement unidirectionnelle.
- `FamilyMember` ne contiendra pas de collection de tâches.
- Le chargement de la relation Task vers FamilyMember sera paresseux.
- Le champ `assignedTo` sera migré progressivement.
- Les anciennes valeurs texte non associables seront conservées ou signalées.
- Une wishlist appartient à un membre.
- Un membre possède au maximum une wishlist dans le MVP.
- La désactivation ne supprime aucune donnée liée.
- Un membre désactivé ne peut plus recevoir de nouvelle tâche.
- Un membre pourra être réactivé avec le même identifiant.
- La suppression physique sera fortement restreinte.
- Aucune cascade générale de suppression ne sera ajoutée.
- La relation avec FamilyEvent reste à préciser dans son ticket de conception.
- Les préférences de notification seront portées par un module distinct.
- Le Dashboard composera les données sans relation JPA directe.
- Les entités liées ne seront pas exposées directement dans les réponses HTTP.

---

## 14. Ordre d’implémentation recommandé

```text
1. Créer FamilyMember sans relation.
2. Créer FamilyMemberRepository.
3. Créer FamilyMemberService.
4. Créer les DTO et le mapper.
5. Créer FamilyMemberController.
6. Ajouter les opérations du MVP.
7. Ajouter les validations et les erreurs.
8. Ajouter les tests.
9. Concevoir la migration de Task.assignedTo.
10. Ajouter la relation Task vers FamilyMember.
11. Migrer les données existantes.
12. Supprimer assignedTo dans un ticket dédié.
13. Développer le module Wishlist.
```
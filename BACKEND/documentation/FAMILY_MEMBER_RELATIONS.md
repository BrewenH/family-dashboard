# Relations du module FamilyMember

## 1. Principes Généraux
- **Unidirectionnalité** : Les relations sont portées par le module "enfant" pour limiter le couplage.
- **Chargement** : Utilisation systématique de `FetchType.LAZY`.
- **Intégrité** : La désactivation d'un membre ne supprime pas ses données historiques.

## 2. Relation avec le module Task
- **Cardinalité** : `FamilyMember (1) <--- (0..*) Task`.
- **Direction** : `Task` possède une référence vers `FamilyMember`.
- **Migration** : Le champ `assignedTo` (String) sera migré progressivement vers `assignedMember` (Entity).

## 3. Relation avec le module Wishlist
- **Cardinalité** : `FamilyMember (1) <---> (0..1) Wishlist`.
- **Direction** : `Wishlist` possède une référence vers son `owner` (FamilyMember).

## 4. Relation avec le module FamilyEvent
- **Cardinalité** : `FamilyMember (*) <---> (*) FamilyEvent`.
- **Implémentation** : Étudiée comme une entité d'association pour gérer les rôles (organisateur, invité, etc.).

## 5. Règles de suppression
- La suppression physique d'un membre est interdite s'il est référencé par d'autres données.
- La **désactivation** est la méthode privilégiée pour retirer un membre du dashboard.

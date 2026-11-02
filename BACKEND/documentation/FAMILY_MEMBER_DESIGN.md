# Conception du module FamilyMember

## 1. Objectif du document
Ce document définit le rôle fonctionnel et les limites du module `FamilyMember`.

## 2. Rôle du module
`FamilyMember` représente une personne appartenant au foyer. Un membre constitue une identité fonctionnelle stable, réutilisable dans les différents modules (Tâches, Wishlists, etc.).

## 3. Distinction Membre vs Utilisateur
- **FamilyMember** : Personne représentée dans le dashboard (ex: un enfant).
- **Utilisateur** : Personne disposant d'un compte d'accès et de droits de modification.
  L'identité technique du membre est indépendante de l'existence d'un compte utilisateur.

## 4. Responsabilités fonctionnelles
- Créer et consulter les membres.
- Gérer l'activation/désactivation d'un membre.
- Fournir une référence stable pour les assignations (ex: assigner une tâche).
- Contrôler les données exposées via des DTO.

## 5. Limites du MVP (V1)
Le premier MVP ne comprend pas :
- L'authentification et la gestion des mots de passe.
- La gestion des rôles de sécurité.
- Les photos de profil.
- La migration immédiate de tout l'historique des tâches.

## 6. Principes de conception
- **Identité stable** : Utilisation d'un ID technique unique.
- **Séparation** : L'entité JPA n'est jamais exposée directement à l'API.
- **Traçabilité** : Gestion automatique des dates de création et modification.
- **TDD** : Chaque fonctionnalité est validée par un test avant implémentation.

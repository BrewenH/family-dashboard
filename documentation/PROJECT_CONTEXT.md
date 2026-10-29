# Family Dashboard - Contexte du Projet

## 1. Objectif du projet
L'application centralise les informations quotidiennes du foyer, affichées en permanence sur un écran dédié via un Raspberry Pi connecté en HDMI.

## 2. Organisation Technique
- **Architecture** : Monolithe Modulaire (Séparation nette entre Backend et Frontend).
- **Backend** : Spring Boot 3.3.2, Java 21, Maven, Spring Data JPA, H2 (dev) / PostgreSQL (prod).
- **Frontend** : Angular (TSR - TypeScript, SCSS, RxJS).
- **Déploiement** : Raspberry Pi 5, OS 64-bit, mode kiosque Chromium.

## 3. Conventions de Développement
- **Langue** : Code et commits en anglais, Interface Utilisateur en français.
- **Structure** : Organisation par fonctionnalité (Controller $\rightarrow$ Service $\rightarrow$ Repository $\rightarrow$ Model $\rightarrow$ DTO).
- **TDD Strict** : Cycle obligatoire RED $\rightarrow$ GREEN $\rightarrow$ REFACTOR pour toute nouvelle fonctionnalité.
- **Git Workflow** :
  - Branche `main` stable.
  - Branches de fonctionnalités (`feature/...`).
  - Intégration via Pull Request avec description détaillée.

## 4. Stratégie "Thin Slice" (V1)
L'objectif est d'atteindre le **Seuil de Déployabilité** le plus rapidement possible. Nous ne développons plus tout le backend avant le frontend, mais nous créons des tranches verticales fonctionnelles.

**Priorités de la V1 (Chemin Critique) :**
1. Finaliser le socle `FamilyMember`.
2. Implémenter le lien technique `Task` $\leftrightarrow$ `FamilyMember` (Migration de `assignedTo`).
3. Intégration Frontend Angular minimal (Tâches et Membres uniquement).
4. Déploiement sur Raspberry Pi 5.

Les autres modules (`Wishlist`, `Events`, etc.) seront développés et intégrés itérativement après le premier déploiement.

## 5. Architecture Backend Standard
- **Controller** : Point d'entrée HTTP, désérialisation JSON, retour de DTO.
- **Service** : Logique métier, gestion des transactions (`@Transactional`), orchestration.
- **Repository** : Interface d'accès aux données via Spring Data JPA.
- **Model** : Entités JPA et Enums métier.
- **DTO** : Contrat public de l'API (Records immutables).

## 6. Gestion des Erreurs
Toutes les erreurs de l'API sont centralisées via un `GlobalExceptionHandler` et retournent un format uniforme via `ApiErrorResponse` :
- `timestamp`, `status`, `error`, `message`, `path`.

## 7. Règle de Synchronisation Multi-PC
Le dépôt GitHub et les fichiers de la documentation sont la seule source de vérité.
À la fin de chaque ticket : `Journal` $\rightarrow$ `Backlog` $\rightarrow$ `Commit` $\rightarrow$ `Push` $\rightarrow$ `Merge`.

# Modèle FamilyMember

## 1. Modèle MVP
L'objet `FamilyMember` est composé des propriétés suivantes :

| Propriété | Type Java | Obligatoire | Description |
| :--- | :--- | :---: | :--- |
| `id` | `Long` | Oui | Identifiant technique unique (Auto-incrémenté) |
| `displayName` | `String` | Oui | Nom d'affichage (max 50 caractères) |
| `color` | `String` | Non | Couleur d'identification visuelle |
| `active` | `boolean` | Oui | Statut d'activation (true par défaut) |
| `createdAt` | `LocalDateTime` | Oui | Date de création (automatique) |
| `updatedAt` | `LocalDateTime` | Oui | Date de modification (automatique) |

## 2. Contraintes de Validation (API)
- **displayName** : `@NotBlank`, `@Size(max=50)`.
- **color** : Chaîne contrôlée (ou Enum futur).

## 3. Cycle de Vie JPA
- `@PrePersist` : Initialise `createdAt` et `updatedAt` à la création.
- `@PreUpdate` : Actualise `updatedAt` à chaque modification.

## 4. Contrat d'Interface (DTO)
- **Requête** : `displayName`, `color`.
- **Réponse** : `id`, `displayName`, `color`, `active`, `createdAt`, `updatedAt`.

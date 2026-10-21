# Architecture hardware de Family Dashboard

## 1. Objectif du document

Ce document décrit la cible matérielle et l’architecture de déploiement envisagées pour Family Dashboard.

L’application doit pouvoir fonctionner comme un dashboard familial autonome, installé dans le foyer et affiché en permanence sur un écran dédié.

Ce document distingue :

```text
les décisions déjà retenues ;
les recommandations techniques ;
les choix restant à confirmer ;
les étapes de déploiement futures.
```

---

## 2. Objectif du système

Family Dashboard doit pouvoir être consulté depuis un écran familial partagé.

Le système doit permettre à terme d’afficher notamment :

```text
les tâches familiales ;
les membres du foyer ;
les événements à venir ;
la météo ;
les listes de souhaits ;
la liste de courses ;
les notifications importantes.
```

L’usage principal envisagé est un écran allumé dans une pièce commune du foyer.

Exemples d’emplacement :

```text
cuisine ;
entrée ;
salon ;
bureau familial.
```

---

## 3. Cible matérielle de référence

La cible matérielle recommandée est :

```text
Raspberry Pi 5
Mémoire : 8 Go recommandés
Stockage : SSD USB 3
Écran : HDMI 1080p
Réseau : Ethernet prioritaire
Système : Raspberry Pi OS 64 bits
Affichage : Chromium en mode kiosque
```

Cette cible reste à confirmer par les tickets hardware avant l’achat ou le déploiement définitif.

---

## 4. Raspberry Pi

### Modèle recommandé

```text
Raspberry Pi 5
```

Le Raspberry Pi devra pouvoir exécuter simultanément :

```text
le backend Spring Boot ;
le serveur du frontend Angular ;
la base de données locale ;
le navigateur Chromium en mode kiosque ;
les services de logs et de sauvegarde.
```

### Mémoire

Cible recommandée :

```text
8 Go de RAM
```

Configuration minimale envisageable pour le MVP :

```text
4 Go de RAM
```

La version 8 Go offre davantage de marge pour :

```text
la base de données ;
Chromium ;
Docker ;
les futures intégrations externes ;
les outils de supervision ;
les évolutions du backend.
```

### Architecture processeur

Le système devra utiliser une distribution 64 bits.

```text
Architecture : ARM64
Système : Raspberry Pi OS 64 bits
Java : version compatible ARM64
```

---

## 5. Stockage

### Stockage principal recommandé

```text
SSD USB 3
```

Le SSD est recommandé pour héberger :

```text
le système ou les données applicatives ;
la base de données ;
les logs ;
les sauvegardes temporaires ;
les images Docker si Docker est retenu.
```

### Carte microSD

Une carte microSD pourra être utilisée pour :

```text
le démarrage initial ;
les tests ;
une installation simplifiée du système.
```

Elle ne constitue pas le stockage recommandé à long terme pour une base de données écrite régulièrement.

### Risques liés à la carte microSD

```text
usure liée aux écritures ;
corruption après une coupure électrique ;
performances variables ;
durée de vie plus difficile à prévoir.
```

### Capacité envisagée

Capacité minimale raisonnable :

```text
64 Go
```

Capacité recommandée :

```text
128 Go
```

Le besoin réel dépendra notamment :

```text
des logs ;
des sauvegardes ;
des images Docker ;
des futures photos ou pièces jointes ;
de la durée de conservation des données.
```

---

## 6. Écran

### Connexion

```text
HDMI
```

### Résolution cible

```text
1920 × 1080 pixels
```

Une résolution Full HD fournit une base adaptée au dashboard sans imposer une charge graphique excessive.

### Orientation envisagée

Orientation de référence :

```text
paysage
```

L’orientation portrait reste possible, mais elle modifierait fortement :

```text
la disposition des widgets ;
la densité d’information ;
la taille des textes ;
la navigation.
```

L’orientation devra être confirmée avant de figer le layout Angular.

### Taille d’écran envisagée

Plage raisonnable :

```text
15 à 24 pouces
```

La taille devra être choisie en fonction :

```text
de la distance de lecture ;
de l’emplacement ;
du nombre de widgets ;
du type d’interaction ;
de la place disponible.
```

### Écran tactile

Le tactile est facultatif.

Deux modes sont envisagés :

```text
Mode consultation
→ écran non tactile
→ interactions depuis un téléphone ou un ordinateur

Mode interactif
→ écran tactile
→ création et modification directement sur le dashboard
```

Le choix du tactile devra être effectué avant la conception définitive des interactions Angular.

---

## 7. Affichage en mode kiosque

Le dashboard sera affiché dans :

```text
Chromium
```

avec un mode kiosque.

Objectifs :

```text
ouvrir automatiquement le dashboard ;
masquer la barre d’adresse ;
utiliser la totalité de l’écran ;
empêcher une navigation involontaire ;
recharger l’application après un redémarrage.
```

Le navigateur devra démarrer automatiquement après l’ouverture de la session graphique.

Le processus devra prévoir :

```text
un délai d’attente du réseau ;
un délai d’attente du backend ;
une relance en cas de fermeture ;
un rechargement si le frontend devient inaccessible.
```

---

## 8. Architecture logicielle cible

Architecture autonome envisagée :

```text
Raspberry Pi
├── Raspberry Pi OS 64 bits
├── Java 21
├── Backend Spring Boot
├── Frontend Angular
├── Base de données locale
├── Chromium en mode kiosque
├── Logs locaux
└── Sauvegardes
```

### Flux principal

```text
Chromium
→ frontend Angular
→ API REST Spring Boot
→ base de données locale
```

### Fonctionnement hors Internet

Les fonctions locales devront rester utilisables sans accès Internet :

```text
tâches ;
membres familiaux ;
wishlists ;
événements locaux ;
liste de courses.
```

Les fonctionnalités dépendant d’un service externe pourront être indisponibles :

```text
météo ;
concerts ;
festivals ;
autres API externes.
```

Le dashboard devra gérer cette indisponibilité sans rendre les fonctions locales inutilisables.

---

## 9. Déploiement du backend

Le backend utilisera :

```text
Spring Boot
Java 21
API REST
```

Deux stratégies de lancement restent à évaluer.

### Option A : service systemd

```text
JAR Spring Boot
→ lancé comme service systemd
```

Avantages :

```text
configuration simple ;
faible surcharge ;
intégration native au système ;
logs accessibles avec journalctl.
```

### Option B : conteneur Docker

```text
Backend
→ conteneur Docker
```

Avantages :

```text
environnement reproductible ;
déploiement simplifié ;
isolation des composants ;
utilisation possible de Docker Compose.
```

Points d’attention :

```text
consommation mémoire supplémentaire ;
gestion des volumes ;
maintenance des images ;
compatibilité ARM64.
```

Le choix définitif sera réalisé dans un ticket hardware dédié.

---

## 10. Déploiement du frontend

Le frontend Angular sera compilé en fichiers statiques.

Deux stratégies sont possibles :

```text
Option A
→ Spring Boot sert les fichiers Angular

Option B
→ Nginx sert les fichiers Angular
```

### Spring Boot

Avantages :

```text
moins de composants ;
déploiement simple ;
un seul service applicatif principal.
```

### Nginx

Avantages :

```text
service spécialisé dans les fichiers statiques ;
configuration du cache ;
compression ;
séparation frontend et backend.
```

Le choix sera confirmé lors de la conception du déploiement logiciel.

---

## 11. Base de données

La base de données de production reste à choisir.

### Option A : PostgreSQL local

Avantages :

```text
robustesse ;
contraintes relationnelles ;
outils de sauvegarde éprouvés ;
compatibilité avec les évolutions du projet.
```

Points d’attention :

```text
consommation de mémoire ;
configuration ;
maintenance ;
sauvegardes.
```

### Option B : base embarquée

Une base embarquée pourrait simplifier le MVP.

Points d’attention :

```text
accès concurrent ;
sauvegardes ;
migration ;
évolutivité ;
résistance aux coupures.
```

### Orientation recommandée

Pour une installation durable contenant plusieurs modules relationnels, PostgreSQL local constitue la cible recommandée.

La décision définitive devra toutefois être prise avec l’architecture de déploiement.

---

## 12. Réseau

### Connexion prioritaire

```text
Ethernet
```

Ethernet est recommandé lorsque l’emplacement du dashboard le permet.

Avantages :

```text
stabilité ;
latence réduite ;
moins de dépendance à la couverture Wi-Fi ;
maintenance distante plus fiable.
```

### Connexion secondaire

```text
Wi-Fi
```

Le Wi-Fi reste utile lorsque le câblage Ethernet n’est pas possible.

### Adresse réseau

Le dashboard devra disposer d’une adresse stable grâce à l’une des stratégies suivantes :

```text
réservation DHCP sur la box ;
adresse IP statique correctement documentée ;
nom d’hôte local.
```

Exemple de nom d’hôte :

```text
family-dashboard.local
```

### Accès depuis le réseau familial

Les téléphones et ordinateurs du foyer pourront accéder au dashboard depuis le réseau local.

L’exposition directe sur Internet n’est pas prévue pour le MVP.

---

## 13. Sécurité réseau

La première version sera limitée au réseau familial.

Principes :

```text
aucun port directement exposé sur Internet ;
aucun mot de passe stocké en clair ;
système régulièrement mis à jour ;
accès SSH contrôlé ;
compte système dédié si nécessaire ;
pare-feu local à évaluer.
```

Un accès distant éventuel devra utiliser une solution sécurisée.

Exemples à étudier :

```text
VPN WireGuard ;
Tailscale ;
accès distant via un réseau privé.
```

Une redirection directe des ports du backend sur la box devra être évitée.

---

## 14. Alimentation

Le Raspberry Pi devra utiliser une alimentation adaptée au modèle retenu.

Pour un Raspberry Pi 5, l’alimentation devra pouvoir supporter :

```text
le Raspberry Pi ;
le SSD USB ;
les périphériques ;
un éventuel écran tactile.
```

Une alimentation insuffisante peut provoquer :

```text
des ralentissements ;
des déconnexions USB ;
des redémarrages ;
des corruptions de données.
```

### Protection contre les coupures

Une protection contre les coupures électriques devra être étudiée.

Solutions possibles :

```text
mini-onduleur ;
UPS HAT compatible ;
arrêt propre sur batterie faible ;
prise protégée contre les surtensions.
```

---

## 15. Refroidissement et boîtier

Le Raspberry Pi 5 nécessite un refroidissement adapté pour un fonctionnement permanent.

Solutions envisagées :

```text
boîtier officiel ventilé ;
Active Cooler ;
boîtier avec dissipateur et ventilateur.
```

Le boîtier devra permettre :

```text
l’accès au stockage ;
une circulation d’air correcte ;
une fixation sécurisée ;
un accès raisonnable pour la maintenance.
```

Le matériel ne devra pas être enfermé derrière l’écran sans ventilation suffisante.

---

## 16. Démarrage automatique

Après une coupure électrique ou un redémarrage, le système devra restaurer automatiquement :

```text
la base de données ;
le backend ;
le serveur frontend ;
la session graphique ;
Chromium en mode kiosque.
```

Ordre de démarrage envisagé :

```text
1. Démarrage du système
2. Montage du stockage
3. Démarrage de la base
4. Démarrage du backend
5. Démarrage du frontend
6. Ouverture de Chromium
7. Affichage du dashboard
```

Les services devront être configurés avec des politiques de redémarrage en cas d’échec.

---

## 17. Logs et supervision

Le système devra journaliser :

```text
les démarrages ;
les erreurs applicatives ;
les erreurs de base de données ;
les échecs d’API externes ;
les tentatives de sauvegarde ;
les erreurs du mode kiosque.
```

Les logs devront être soumis à une rotation afin de ne pas remplir le disque.

Solutions à étudier :

```text
journalctl ;
Logback avec rolling files ;
Docker logs avec rotation ;
tableau de santé local.
```

Le backend possède déjà un endpoint de contrôle :

```http
GET /api/health
```

Cet endpoint pourra être utilisé par la supervision locale.

---

## 18. Sauvegardes

Les données familiales doivent pouvoir être restaurées après :

```text
une panne du SSD ;
une mauvaise manipulation ;
une mise à jour défectueuse ;
une corruption de base ;
un remplacement du Raspberry Pi.
```

### Principe

Une sauvegarde stockée uniquement sur le même SSD ne constitue pas une sauvegarde suffisante.

### Cibles possibles

```text
clé USB distincte ;
NAS familial ;
ordinateur du foyer ;
stockage distant chiffré.
```

### Éléments à sauvegarder

```text
base de données ;
configuration applicative ;
variables d’environnement ;
fichiers nécessaires au déploiement ;
éventuels médias futurs.
```

### Stratégie initiale envisagée

```text
sauvegarde quotidienne ;
conservation de plusieurs versions ;
test périodique de restauration ;
journalisation des échecs.
```

---

## 19. Mises à jour

Le système devra permettre de mettre à jour :

```text
Raspberry Pi OS ;
Java ;
le backend Spring Boot ;
le frontend Angular ;
la base de données ;
Chromium ;
les dépendances de sécurité.
```

Une mise à jour applicative devra prévoir :

```text
une sauvegarde avant déploiement ;
une procédure de retour arrière ;
une vérification de santé après déploiement ;
une conservation de la version précédente.
```

---

## 20. Maintenance à distance

La maintenance pourra être effectuée depuis un autre ordinateur du réseau familial.

Outils possibles :

```text
SSH ;
SCP ou SFTP ;
Git ;
scripts de déploiement ;
Docker Compose ;
journalctl.
```

L’accès SSH devra utiliser de préférence :

```text
des clés SSH ;
un compte non root ;
des droits limités ;
une configuration documentée.
```

---

## 21. Influence sur le frontend Angular

La conception hardware influence directement le frontend.

Décisions à confirmer avant de figer l’interface :

```text
taille de l’écran ;
résolution ;
orientation ;
distance de lecture ;
présence du tactile ;
mode de navigation ;
durée d’affichage ;
luminosité ;
mode sombre.
```

Le frontend devra notamment prévoir :

```text
des textes lisibles à distance ;
des zones tactiles suffisamment grandes si nécessaire ;
un nombre limité de widgets simultanés ;
une disposition responsive ;
une tolérance aux pertes de réseau ;
un affichage clair du mode hors ligne.
```

---

## 22. Cible de référence provisoire

La cible de référence actuelle est :

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

Cette cible est une base de travail et non encore une commande d’achat définitive.

---

## 23. Décisions confirmées

- Le dashboard doit fonctionner sur un écran dédié.
- Le Raspberry Pi constitue la cible matérielle principale.
- Le système doit être autonome pour les fonctions locales.
- L’écran utilisera une connexion HDMI.
- Un mode kiosque est prévu.
- Le système utilisera une architecture 64 bits.
- Le stockage durable devra privilégier un SSD.
- Ethernet est prioritaire lorsque possible.
- Les sauvegardes doivent être stockées sur un support distinct.
- Le système doit redémarrer automatiquement après une coupure.
- Les fonctions locales doivent rester utilisables sans Internet.
- Aucun service ne doit être exposé directement sur Internet pendant le MVP.

---

## 24. Décisions restant à confirmer

- Modèle exact du Raspberry Pi.
- Quantité définitive de mémoire.
- Taille de l’écran.
- Orientation finale.
- Utilisation ou non du tactile.
- Modèle et capacité exacte du SSD.
- Utilisation de Docker ou de services systemd.
- Utilisation de PostgreSQL ou d’une base embarquée.
- Utilisation de Nginx ou de Spring Boot pour servir Angular.
- Type de protection contre les coupures électriques.
- Solution de sauvegarde externe.
- Solution d’accès distant sécurisé.
- Type de boîtier et de refroidissement.

---

## 25. Ordre de travail hardware recommandé

```text
HW-001 - Définir les besoins matériels
HW-002 - Choisir le Raspberry Pi et la mémoire
HW-003 - Choisir l’écran et le mode d’interaction
HW-004 - Définir le stockage et les sauvegardes
HW-005 - Définir l’architecture logicielle de déploiement
HW-006 - Définir le réseau et l’accès distant
HW-007 - Définir l’alimentation, le boîtier et le refroidissement
HW-008 - Finaliser et documenter l’architecture hardware cible
```

---

## 26. Positionnement dans la feuille de route

Le développement backend peut continuer avant la validation définitive du matériel.

Ordre recommandé :

```text
Maintenant
→ poursuivre le backend FamilyMember

Avant de figer le frontend Angular
→ traiter HW-001 à HW-003

Avant le déploiement réel
→ traiter HW-004 à HW-008
```

La taille, l’orientation et le caractère tactile de l’écran devront être définis avant la conception définitive du layout Angular.
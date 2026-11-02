# Architecture Hardware de Family Dashboard

## 1. Cible Matérielle (Référence)
- **Unité Centrale** : Raspberry Pi 5 (8 Go RAM).
- **Stockage** : SSD USB 3 (128 Go).
- **OS** : Raspberry Pi OS 64-bit.
- **Affichage** : Écran HDMI 1080p, orientation paysage.
- **Mode d'affichage** : Chromium en mode kiosque.

## 2. Architecture Logicielle
- **Backend** : JAR Spring Boot lancé via systemd ou Docker.
- **Frontend** : Fichiers statiques Angular servis via Nginx ou Spring Boot.
- **Base de données** : PostgreSQL local.

## 3. Stratégie de Déploiement
- **Réseau** : Ethernet prioritaire, accès restreint au réseau familial.
- **Sauvegardes** : Sauvegarde quotidienne de la base de données sur support externe.
- **Maintenance** : Accès SSH sécurisé par clés.

## 4. Contraintes Kiosque
Le navigateur doit :
- Se lancer automatiquement au démarrage.
- Masquer l'interface utilisateur (URL, onglets).
- Se recharger automatiquement en cas de perte de connexion.

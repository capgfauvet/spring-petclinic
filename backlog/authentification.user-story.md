# US : Authentification vétérinaire / admin

En tant que vétérinaire ou administrateur, je veux pouvoir me connecter à l'application avec mes identifiants afin que les fonctionnalités sensibles (modification des vétérinaires, gestion des owners) soient protégées.

## Règles métier

**Règle 1 : Rôles**
Deux rôles sont définis :
- `ROLE_VET` : accès à ses propres rendez-vous et à la liste des owners
- `ROLE_ADMIN` : accès complet, y compris la gestion des vétérinaires

**Règle 2 : Pages protégées**
Les routes suivantes nécessitent une authentification :
- `/owners/**` : accessible à `ROLE_VET` et `ROLE_ADMIN`
- `/vets/new`, `/vets/*/edit`, `/vets/*/delete` : accessible à `ROLE_ADMIN` uniquement

**Règle 3 : Page publique**
La page d'accueil `/` et la liste des vétérinaires `/vets.html` restent accessibles sans authentification.

**Règle 4 : Formulaire de login**
Un formulaire de connexion standard avec un champ login et un champ mot de passe.
Les erreurs d'authentification affichent un message explicite.

**Règle 5 : Déconnexion**
Un bouton de déconnexion est disponible dans la barre de navigation pour les utilisateurs connectés.

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Accès à `/owners` sans être connecté | Redirection vers `/login` |
| 2 | Connexion avec identifiants valides (`ROLE_VET`) | Accès accordé |
| 3 | Connexion avec mauvais mot de passe | Message d'erreur sur le formulaire |
| 4 | Utilisateur `ROLE_VET` accède à `/vets/new` | Accès refusé (403) |
| 5 | Utilisateur `ROLE_ADMIN` accède à `/vets/new` | Accès accordé |

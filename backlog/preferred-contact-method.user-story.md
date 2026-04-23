# US : Méthode de contact préférée

## Description

En tant que membre du personnel de la clinique, je veux pouvoir enregistrer le mode de contact préféré d'un owner afin de le contacter de la manière la plus adaptée.

## Règles métier

**Règle 1 : Champ obligatoire**
Chaque owner doit avoir un mode de contact préféré. Le champ `preferredContactMethod` est requis.

**Règle 2 : Cohérence conditionnelle**
Le mode de contact doit être cohérent avec les données disponibles :
- `PHONE` → le champ `telephone` doit être renseigné
- `EMAIL` → le champ `email` doit être renseigné
- `POSTAL_MAIL` → les champs `address` et `city` doivent être renseignés

**Règle 3 : Messages d'erreur compréhensibles**
En cas d'incohérence, l'utilisateur doit voir un message explicite :
- _"Le téléphone est requis si le mode de contact préféré est PHONE"_
- _"L'email est requis si le mode de contact préféré est EMAIL"_

**Règle 4 : Affichage visible**
Le mode de contact préféré doit apparaître :
- dans le formulaire de création / édition d'un owner
- dans la fiche détail d'un owner

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Owner créé avec `PHONE` et téléphone renseigné | Sauvegarde OK |
| 2 | Owner créé avec `PHONE` mais sans téléphone | Erreur de validation |
| 3 | Owner créé avec `POSTAL_MAIL` avec adresse et ville | Sauvegarde OK |
| 4 | Owner créé avec `EMAIL` mais sans email | Erreur de validation |

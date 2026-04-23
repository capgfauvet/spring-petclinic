# US : Page de consultation des animaux

## Description

En tant que membre du personnel de la clinique, je veux pouvoir consulter la liste de tous les animaux enregistrés sans être obligé de passer par la fiche d'un owner, afin de retrouver rapidement un animal.

## Règles métier

**Règle 1 : Liste globale**
Une page dédiée `/pets` liste l'ensemble des pets de la clinique, indépendamment de leurs owners.

**Règle 2 : Informations affichées par animal**
Pour chaque animal, la liste affiche au minimum :
- Le nom du pet
- Le type (chien, chat, etc.)
- La date de naissance
- Le nom de l'owner associé (avec lien vers sa fiche)

**Règle 3 : Navigation**
Depuis la liste des animaux, un clic sur un animal ou son owner permet d'accéder aux fiches correspondantes.

**Règle 4 : Accessibilité depuis le menu**
La page doit être accessible depuis la barre de navigation principale.

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Accès à `/pets` | Liste de tous les pets affichée |
| 2 | Clic sur le nom de l'owner | Redirige vers la fiche owner |
| 3 | Aucun pet enregistré | Message indiquant qu'aucun animal n'est enregistré |

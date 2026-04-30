# US : Page d'accueil de la clinique

En tant que visiteur de l'application, je veux accéder à une page d'accueil présentant les chiffres clés de la clinique afin d'avoir une vue synthétique de son activité.

## Règles métier

**Règle 1 : Statistiques affichées**
La page d'accueil doit présenter :
- Le nombre total d'owners enregistrés
- Le nombre total de pets enregistrés
- La répartition des pets par type (chien, chat, etc.) sous forme de liste ou graphique
- Le top des vétérinaires ayant le plus de visites enregistrées

**Règle 2 : Données en temps réel**
Les chiffres doivent être calculés dynamiquement depuis la base de données, sans valeur statique codée en dur.

**Règle 3 : Accessibilité depuis le menu**
La page d'accueil doit être accessible depuis la barre de navigation principale.

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Accès à la racine `/` | La page d'accueil s'affiche avec les statistiques |
| 2 | Aucun pet enregistré | Les compteurs affichent 0, pas d'erreur |
| 3 | Vétérinaires sans visite | Apparaissent dans le classement avec 0 visite |

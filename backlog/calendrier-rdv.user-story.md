# US : Calendrier des rendez-vous du vétérinaire

En tant que vétérinaire, je veux accéder à un calendrier de mes rendez-vous afin de visualiser et gérer mon planning.

## Règles métier

**Règle 1 : Vue calendrier**
La page `/vets/{id}/calendar` affiche les visites associées au vétérinaire sous forme de calendrier hebdomadaire ou mensuel.

**Règle 2 : Informations d'une visite**
Chaque rendez-vous affiché dans le calendrier indique :
- La date et l'heure
- Le nom du pet concerné
- Le nom de l'owner
- La description de la visite

**Règle 3 : Navigation temporelle**
L'utilisateur peut naviguer vers la semaine ou le mois précédent / suivant.

**Règle 4 : Accès restreint**
Un vétérinaire ne peut consulter que son propre calendrier. Un admin peut consulter tous les calendriers.

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Accès à `/vets/{id}/calendar` | Calendrier du vétérinaire affiché |
| 2 | Aucune visite sur la période | Calendrier vide, pas d'erreur |
| 3 | Vétérinaire A accède au calendrier du vétérinaire B | Accès refusé (dépend de l'US auth) |
| 4 | Navigation vers la semaine suivante | Mise à jour de la vue |

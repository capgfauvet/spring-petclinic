# US : Modification des vétérinaires

## Description

En tant qu'administrateur de la clinique, je veux pouvoir créer, modifier et supprimer des vétérinaires afin de maintenir le référentiel à jour.

## Règles métier

**Règle 1 : Création**
Il doit être possible d'ajouter un nouveau vétérinaire avec :
- Prénom et nom (obligatoires)
- Une ou plusieurs spécialités (optionnel)

**Règle 2 : Modification**
Un vétérinaire existant peut être modifié (prénom, nom, spécialités).

**Règle 3 : Suppression**
Un vétérinaire peut être supprimé uniquement s'il n'a aucune visite planifiée ou passée associée.

**Règle 4 : Validation**
- Le prénom et le nom sont obligatoires (`@NotBlank`)
- La suppression d'un vétérinaire ayant des visites retourne un message d'erreur explicite

## Critères d'acceptance

| # | Cas | Résultat attendu |
|---|-----|-----------------|
| 1 | Création d'un vétérinaire valide | Sauvegarde OK, redirection vers la liste |
| 2 | Création sans nom | Erreur de validation |
| 3 | Modification du prénom | Sauvegarde OK |
| 4 | Suppression d'un vétérinaire sans visites | Suppression OK |
| 5 | Suppression d'un vétérinaire avec visites | Message d'erreur, pas de suppression |

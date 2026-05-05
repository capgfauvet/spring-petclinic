# Instructions Copilot

Directives Copilot globales pour l'application Spring PetClinic. Ces instructions s'appliquent à toutes les conversations Copilot dans ce workspace.

## Contexte du projet

- Stack : Java, Spring Boot, Thymeleaf, JPA, JUnit.
- Outils de build : les wrappers Maven et Gradle sont disponibles.
- Objectif principal : maintenir un code lisible, testable et sûr.

## Contexte produit

- Cette application est un système de gestion de clinique vétérinaire utilisé par le personnel de la clinique.
- Flux métier principaux : recherche/gestion des propriétaires, enregistrement/mise à jour des animaux, suivi des visites et liste des vétérinaires.
- Modèle de données typique : un propriétaire possède plusieurs animaux, un animal a plusieurs visites.
- Toute modification doit préserver l'utilisabilité quotidienne pour les workflows des agents d'accueil et des vétérinaires.

## Règles de codage

- Privilégier des changements petits et ciblés.
- Conserver la structure de packages et le style de nommage existants.
- Ajouter ou mettre à jour les tests lors de changements de comportement.
- Éviter de casser le comportement public sauf si explicitement demandé.
- Ne pas introduire de secrets, tokens ou valeurs spécifiques à un environnement.

## Qualité des pull requests

- Expliquer ce qui a changé et pourquoi.
- Lister les risques, hypothèses et étapes de test manuel lorsque c'est pertinent.
- En cas d'incertitude, proposer des options avec leurs compromis avant les refactorisations importantes.

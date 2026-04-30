---
name: clarify-before-act
description: 'Prompt générique de clarification interactive via #ask_questions. Invite l''agent à poser des questions pertinentes sur la tâche avant d''agir, à collecter les réponses de l''utilisateur et à les intégrer dans sa réponse finale.'
---

# Clarification interactive avant action

Prompt générique pour conduire un entretien de clarification structuré avant d'agir. Invite l'agent à poser des questions ciblées, collecter les réponses et les intégrer avant de produire une réponse.

## Objectif

Avant de traiter toute demande, conduire un entretien de clarification structuré pour lever les ambiguïtés, identifier les contraintes et aligner la solution sur les attentes réelles.

---

## Processus

**Étape 1 — Analyser la demande**

Lis attentivement la demande de l'utilisateur. Identifie :
- les éléments clairs et actionnables,
- les zones d'ombre, ambiguïtés ou hypothèses implicites,
- les décisions structurantes qui orientent l'implémentation.

**Étape 2 — Poser les questions de clarification**

Pose **uniquement** les questions indispensables pour avancer. Groupe-les par thème si plusieurs sujets sont concernés. Formule chaque question de manière précise et fermée ou à choix multiples lorsque c'est possible en utilisant #ask_questions.

> Critères de sélection des questions :
> - La réponse change concrètement ce que tu vas produire.
> - L'hypothèse par défaut présente un risque ou un coût non négligeable si elle est fausse.
> - La clarification prend moins de temps que corriger une mauvaise implémentation.

**Étape 3 — Récapituler les choix retenus**

Avant d'agir, énonce brièvement les décisions prises (issues des réponses ou de tes hypothèses par défaut).

**Étape 4 — Implémenter**

Exécute la tâche en respectant scrupuleusement les réponses collectées et les décisions récapitulées.

---

## Règles

- Ne pose pas plus de **5 questions** par session de clarification.
- Si une question a une réponse évidente dans le contexte du projet, formule ton hypothèse plutôt que de la poser.
- Si l'utilisateur dit « vas-y » ou « continue », utilise tes hypothèses par défaut et documente-les.
- Priorise les questions selon leur impact : périmètre > comportement métier > détails techniques.

---
name: conventional-commit
description: "Prompt et workflow pour générer des messages de commit conventionnels en utilisant un format XML structuré. Guide les utilisateurs pour créer des messages de commit standardisés et descriptifs conformément à la spécification Conventional Commits."
---

# Commit conventionnel

Workflow pour générer des messages de commit standardisés suivant la [spécification Conventional Commits][spec].

[spec]: https://www.conventionalcommits.org/en/v1.0.0/#specification

## Instructions

Ce fichier contient un template de prompt pour générer des messages de commit conventionnels. Il fournit des instructions, des exemples et des directives de formatage pour aider les utilisateurs à rédiger des messages de commit standardisés et descriptifs conformément à la spécification Conventional Commits.

## Workflow

**Suivre ces étapes :**

1. Exécuter `git status` pour examiner les fichiers modifiés.
2. Exécuter `git diff` ou `git diff --cached` pour inspecter les changements.
3. Indexer les changements avec `git add <fichier>`.
4. Construire le message de commit en utilisant la structure XML suivante.
5. Après avoir généré le message de commit, Copilot exécutera automatiquement la commande suivante dans le terminal intégré (sans confirmation nécessaire) :

```bash
git commit -m "type(scope): description"
```

6. Il suffit d'exécuter ce prompt et Copilot gérera le commit dans le terminal.

## Structure du message de commit

```xml
<commit-message>
	<type>feat|fix|docs|style|refactor|perf|test|build|ci|chore|revert</type>
	<scope>()</scope>
	<description>A short, imperative summary of the change</description>
	<body>(optional: more detailed explanation)</body>
	<footer>(optional: e.g. BREAKING CHANGE: details, or issue references)</footer>
</commit-message>
```

## Exemples

```xml
<examples>
	<example>feat(parser): add ability to parse arrays</example>
	<example>fix(ui): correct button alignment</example>
	<example>docs: update README with usage instructions</example>
	<example>refactor: improve performance of data processing</example>
	<example>chore: update dependencies</example>
	<example>feat!: send email on registration (BREAKING CHANGE: email service required)</example>
</examples>
```

## Validation

```xml
<validation>
	<type>Must be one of the allowed types. See <reference>https://www.conventionalcommits.org/en/v1.0.0/#specification</reference></type>
	<scope>Optional, but recommended for clarity.</scope>
	<description>Required. Use the imperative mood (e.g., "add", not "added").</description>
	<body>Optional. Use for additional context.</body>
	<footer>Use for breaking changes or issue references.</footer>
</validation>
```

## Étape finale

```xml
<final-step>
	<cmd>git commit -m "type(scope): description"</cmd>
	<note>Replace with your constructed message. Include body and footer if needed.</note>
</final-step>
```

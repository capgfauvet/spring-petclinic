---
description: "À utiliser lors de la création ou de l'édition de templates Thymeleaf, de vues HTML ou de feuilles de style SCSS. Couvre la composition de layout, la liaison de données, l'itération, l'i18n, la construction d'URL et le formatage des dates."
applyTo: "src/main/resources/templates/**/*.html, src/main/scss/**/*.scss"
---

# Instructions couche front-end

Conventions de codage pour les templates Thymeleaf et les feuilles de style SCSS de ce projet Spring Boot MVC.

## Namespace Thymeleaf

Toujours déclarer le namespace Thymeleaf sur l'élément racine `<html>` :

```html
<html xmlns:th="https://www.thymeleaf.org">
```

## Composition de layout

Toutes les pages doivent utiliser le layout maître partagé via l'inclusion de fragments :

```html
<body th:replace="~{fragments/layout :: layout (~{::body}, 'section-name')}">
```

- Le deuxième argument est le nom de la section de navigation active (ex : `'owners'`, `'vets'`).
- Ne jamais dupliquer le `<head>`, la navigation ou le pied de page, ils se trouvent dans `fragments/layout.html`.

## Liaison de données

Lier un objet formulaire avec `th:object`, puis utiliser le sélecteur court `*{}` pour les champs :

```html
<form th:object="${owner}" method="post">
  <input th:field="*{firstName}" />
  <span th:if="${#fields.hasErrors('firstName')}" th:errors="*{firstName}" />
</form>
```

## Itération

Utiliser `th:each` pour itérer sur des collections :

```html
<tr th:each="pet : ${owner.pets}">
  <td th:text="${pet.name}">name</td>
</tr>
```

## Construction d'URL

Toujours utiliser `@{}` pour les URLs, ne jamais coder les chemins en dur :

```html
<!-- Static path -->
<a th:href="@{/owners/find}">Find owner</a>

<!-- Path with variable -->
<a th:href="@{/owners/{id}/edit(id=${owner.id})}">Edit</a>

<!-- Dynamic segment (alternative syntax) -->
<a th:href="@{__${owner.id}__/edit}">Edit</a>
```

## Rendu conditionnel

```html
<div th:if="${condition}">...</div>
<div th:unless="${condition}">...</div>
```

## Internationalisation (i18n)

Utiliser `#{key}` pour tous les labels et messages visibles par l'utilisateur. Ne jamais coder les chaînes directement :

```html
<label th:text="#{owner.firstName}">First Name</label>
```

Les fichiers de messages se trouvent dans `src/main/resources/messages/`.

## Formatage des dates

Utiliser l'utilitaire `#temporals` pour l'affichage des dates :

```html
<td th:text="${#temporals.format(pet.birthDate, 'yyyy-MM-dd')}"></td>
```

Utiliser `@DateTimeFormat(pattern = "yyyy-MM-dd")` sur le champ Java correspondant pour la liaison de formulaire.

## Navigation sécurisée

Utiliser l'opérateur null-safe `?.` lorsqu'une propriété peut être nulle :

```html
<span th:text="${visit?.description}"></span>
```

## Règles générales

- Ne pas intégrer de logique métier dans les templates mais déléguer au controller/modèle.
- Ne pas utiliser JavaScript inline pour des données qui appartiennent au modèle.
- Garder les templates lisibles : une préoccupation par template, utiliser les fragments pour le balisage répété.
- Suivre la convention de nommage existante : sous-répertoires `owners/`, `pets/`, `vets/` sous `templates/`.

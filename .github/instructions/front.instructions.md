---
description: "Use when creating or editing Thymeleaf templates, HTML views, or SCSS stylesheets. Covers layout composition, data binding, iteration, i18n, URL building, and date formatting."
applyTo: "src/main/resources/templates/**/*.html, src/main/scss/**/*.scss"
---

# Front-End Layer Instructions

## Thymeleaf Namespace

Always declare the Thymeleaf namespace on the root `<html>` element:

```html
<html xmlns:th="https://www.thymeleaf.org">
```

## Layout Composition

All pages must use the shared master layout via fragment inclusion:

```html
<body th:replace="~{fragments/layout :: layout (~{::body}, 'section-name')}">
```

- The second argument is the active nav section name (e.g., `'owners'`, `'vets'`).
- Never duplicate the `<head>`, navigation, or footer — they live in `fragments/layout.html`.

## Data Binding

Bind a form object with `th:object`, then use the short `*{}` selector for fields:

```html
<form th:object="${owner}" method="post">
  <input th:field="*{firstName}" />
  <span th:if="${#fields.hasErrors('firstName')}" th:errors="*{firstName}" />
</form>
```

## Iteration

Use `th:each` to iterate over collections:

```html
<tr th:each="pet : ${owner.pets}">
  <td th:text="${pet.name}">name</td>
</tr>
```

## URL Building

Always use `@{}` for URLs — never hardcode paths:

```html
<!-- Static path -->
<a th:href="@{/owners/find}">Find owner</a>

<!-- Path with variable -->
<a th:href="@{/owners/{id}/edit(id=${owner.id})}">Edit</a>

<!-- Dynamic segment (alternative syntax) -->
<a th:href="@{__${owner.id}__/edit}">Edit</a>
```

## Conditional Rendering

```html
<div th:if="${condition}">...</div>
<div th:unless="${condition}">...</div>
```

## Internationalisation (i18n)

Use `#{key}` for all user-facing labels and messages. Never hardcode strings directly:

```html
<label th:text="#{owner.firstName}">First Name</label>
```

Message files are located in `src/main/resources/messages/`.

## Date Formatting

Use the `#temporals` utility for date display:

```html
<td th:text="${#temporals.format(pet.birthDate, 'yyyy-MM-dd')}"></td>
```

Use `@DateTimeFormat(pattern = "yyyy-MM-dd")` on the corresponding Java field for form binding.

## Safe Navigation

Use the null-safe `?.` operator when a property may be null:

```html
<span th:text="${visit?.description}"></span>
```

## General Rules

- Do not embed business logic in templates — delegate to the controller/model.
- Do not use inline JavaScript for data that belongs in the model.
- Keep templates readable: one concern per template, use fragment includes for repeated markup.
- Follow the existing naming convention: `owners/`, `pets/`, `vets/` subdirectories under `templates/`.

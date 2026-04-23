# Copilot Instructions

## Project context
- Stack: Java, Spring Boot, Thymeleaf, JPA, JUnit.
- Build tools: Maven and Gradle wrappers are available.
- Main goal: keep code readable, testable, and safe.

## Product context
- This application is a veterinary clinic management system used by clinic staff.
- Main business flows: search/manage owners, register/update pets, track visits, and list vets.
- Typical data model links: one owner to many pets, one pet to many visits.
- Any change should preserve day-to-day usability for front-desk and veterinarian workflows.

## Coding rules
- Prefer small, focused changes.
- Keep existing package structure and naming style.
- Add or update tests for behavior changes.
- Avoid breaking public behavior unless explicitly requested.
- Do not introduce secrets, tokens, or environment-specific values.

## Pull request quality
- Explain what changed and why.
- List risks, assumptions, and manual test steps when relevant.
- If uncertain, propose options with trade-offs before large refactors.

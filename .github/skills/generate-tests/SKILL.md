---
name: generate-tests
description: 'Générer des tests JUnit pour une classe Spring Boot (service, controller, repository). À utiliser pour ajouter des tests à du code nouveau ou existant dans ce projet.'
argument-hint: 'Nom de la classe ou chemin vers la classe à tester'
---

# Génération de tests

Workflow pour générer des classes de test JUnit 5 pour les controllers, services et repositories Spring Boot.

## Quand utiliser

- Un nouveau service, controller ou repository n'a pas de couverture de test.
- Une classe existante a besoin de cas de test supplémentaires après une modification.

## Procédure

1. Lire la classe cible pour comprendre ses méthodes publiques et ses dépendances.
2. Localiser le fichier de test correspondant sous `src/test/java` (miroir du package source).
3. Générer une classe de test JUnit 5 en suivant les patterns ci-dessous.
4. Placer les mocks avec `@MockitoExtension` pour les tests unitaires, ou utiliser `@SpringBootTest` + `@Transactional` pour les tests d'intégration.

## Conventions

- Nom de la classe de test : `<ClassName>Tests`.
- Une méthode `@Test` par comportement, nommée `should<Behaviour>When<Context>`.
- Utiliser `@MockitoExtension` + `@Mock`/`@InjectMocks` pour les tests unitaires (services).
- Utiliser `@SpringBootTest` + `MockMvc` pour les tests d'intégration de controllers.
- Asserter avec AssertJ (`assertThat(...)`), pas les assertions JUnit.
- Couvrir : le chemin nominal, le cas non-trouvé / null, et au moins une erreur de validation.

## Exemple (test unitaire de service)

```java
@ExtendWith(MockitoExtension.class)
class OwnerServiceTests {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void shouldReturnOwnerWhenFound() {
        given(ownerRepository.findById(1)).willReturn(Optional.of(new Owner()));
        assertThat(ownerService.findById(1)).isNotNull();
    }

    @Test
    void shouldThrowWhenOwnerNotFound() {
        given(ownerRepository.findById(99)).willReturn(Optional.empty());
        assertThatThrownBy(() -> ownerService.findById(99))
            .isInstanceOf(EntityNotFoundException.class);
    }
}
```

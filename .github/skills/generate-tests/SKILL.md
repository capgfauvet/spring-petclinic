---
name: generate-tests
description: 'Generate JUnit tests for a Spring Boot class (service, controller, repository). Use when adding tests for new or existing code in this project.'
argument-hint: 'Class name or path to the class to test'
---

# Generate Tests

## When to Use
- A new service, controller, or repository has no test coverage.
- An existing class needs additional test cases after a change.

## Procedure

1. Read the target class to understand its public methods and dependencies.
2. Locate the matching test file under `src/test/java` (mirror the source package).
3. Generate a JUnit 5 test class following the patterns below.
4. Place mocks with `@MockitoExtension` for unit tests, or use `@SpringBootTest` + `@Transactional` for integration tests.

## Conventions

- Test class name: `<ClassName>Tests`.
- One `@Test` method per behaviour, named `should<Behaviour>When<Context>`.
- Use `@MockitoExtension` + `@Mock`/`@InjectMocks` for unit tests (services).
- Use `@SpringBootTest` + `MockMvc` for controller integration tests.
- Assert with AssertJ (`assertThat(...)`), not JUnit assertions.
- Cover: happy path, not-found / null case, and at least one validation error.

## Example (service unit test)

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

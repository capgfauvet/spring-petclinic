---
description: "Use when creating or editing Java classes: controllers, entities, validators, formatters, or configuration. Covers Spring MVC conventions, JPA mapping, validation, dependency injection, and formatting patterns."
applyTo: "src/main/java/**/*.java"
---

# Back-End Layer Instructions

Conventions de codage pour les classes Java back-end de ce projet Spring Boot MVC : controllers, entités, validators, formatters et configuration.

## Dependency Injection

Always use constructor injection. Never use `@Autowired` on fields:

```java
// Correct
class OwnerController {
    private final OwnerRepository owners;

    OwnerController(OwnerRepository owners) {
        this.owners = owners;
    }
}
```

---

## Controllers

### Class Declaration

- Use `@Controller` (not `@RestController` unless returning JSON/XML directly).
- Controllers are **package-private** — no `public` modifier on the class.
- Declare the base path on the class with `@RequestMapping`.

```java
@Controller
@RequestMapping("/owners")
class OwnerController { ... }
```

### View Name Constants

Declare view name strings as `private static final String` constants at the top of the class:

```java
private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
```

### Security: Input Binding

Always use `@InitBinder` to block sensitive fields from being bound from the HTTP request:

```java
@InitBinder
public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
}
```

### Model Population

Use `@ModelAttribute` methods to pre-populate model data shared across handler methods:

```java
@ModelAttribute("owner")
public Owner findOwner(@PathVariable(required = false) Integer ownerId) {
    return ownerId != null ? this.owners.findById(ownerId) : new Owner();
}
```

### Validation

Always pair `@Valid` with `BindingResult` immediately after the validated object. Return the form view on errors:

```java
@PostMapping("/new")
public String processCreationForm(@Valid Owner owner, BindingResult result) {
    if (result.hasErrors()) {
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }
    this.owners.save(owner);
    return "redirect:/owners/" + owner.getId();
}
```

### Redirects vs Views

- After a successful `POST`, always redirect: `return "redirect:/owners/" + id;`
- After a failed `POST` (validation errors), return the view name string directly.

### Pagination

Use Spring Data's `Pageable` and `Page<T>` for list endpoints:

```java
@GetMapping("/list")
public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
    Page<Vet> paginated = this.vets.findAll(PageRequest.of(page - 1, 5));
    model.addAttribute("listVets", paginated);
    return "vets/vetList";
}
```

---

## Entities

### Inheritance Hierarchy

Reuse the existing base classes — do not introduce new base classes:

| Base Class | Adds | Use for |
|---|---|---|
| `BaseEntity` | `id` (`@Id`, `@GeneratedValue`) | Any persistent entity |
| `NamedEntity` | `name` + `@NotBlank` | Lookup/reference entities |
| `Person` | `firstName`, `lastName` + `@NotBlank` | People (Owner, Vet) |

### Class Annotations

```java
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity { ... }
```

### Relationships

```java
// One-to-many (parent side)
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JoinColumn(name = "owner_id")
@OrderBy("name ASC")
private Set<Pet> pets = new LinkedHashSet<>();

// Many-to-one (child side)
@ManyToOne
@JoinColumn(name = "type_id")
private PetType type;

// Many-to-many
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
           inverseJoinColumns = @JoinColumn(name = "specialty_id"))
private Set<Specialty> specialties = new LinkedHashSet<>();
```

### Field Validation

Use Jakarta Bean Validation annotations directly on entity fields:

```java
@NotBlank
@Column(name = "first_name")
private String firstName;

@NotBlank
@Pattern(regexp = "[0-9]{10,12}")
@Column(name = "telephone")
private String telephone;
```

---

## Validators

Implement Spring's `Validator` interface for multi-field or business-rule validation that cannot be expressed with Bean Validation annotations:

```java
public class PetValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Pet pet = (Pet) obj;
        if (!StringUtils.hasText(pet.getName())) {
            errors.rejectIfEmpty("name", "required", "required");
        }
    }
}
```

Register the validator in the controller via `@InitBinder`:

```java
@InitBinder("pet")
public void initPetBinder(WebDataBinder dataBinder) {
    dataBinder.setValidator(new PetValidator());
}
```

---

## Formatters

Implement `Formatter<T>` for converting domain objects to/from form strings. Mark with `@Component` so Spring auto-registers them:

```java
@Component
public class PetTypeFormatter implements Formatter<PetType> {

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        // look up from repository
    }
}
```

---

## General Rules

- Keep classes small and focused on a single responsibility.
- Follow existing package structure: group by feature (`owner/`, `vet/`), not by layer.
- Follow existing naming conventions: `XxxController`, `XxxRepository`, `XxxValidator`, `XxxFormatter`.
- Add or update tests for any behavior change.

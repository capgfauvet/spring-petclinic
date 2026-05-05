---
description: "À utiliser lors de la création ou de l'édition de classes Java : controllers, entités, validators, formatters ou configuration. Couvre les conventions Spring MVC, le mapping JPA, la validation, l'injection de dépendances et les patterns de formatage."
applyTo: "src/main/java/**/*.java"
---

# Instructions couche back-end

Conventions de codage pour les classes Java back-end de ce projet Spring Boot MVC : controllers, entités, validators, formatters et configuration.

## Injection de dépendances

Toujours utiliser l'injection par constructeur. Ne jamais utiliser `@Autowired` sur les champs :

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

### Déclaration de la classe

- Utiliser `@Controller` (pas `@RestController` sauf si JSON/XML est retourné directement).
- Les controllers sont **package-private**, donc pas de modificateur `public` sur la classe.
- Déclarer le chemin de base sur la classe avec `@RequestMapping`.

```java
@Controller
@RequestMapping("/owners")
class OwnerController { ... }
```

### Constantes de nom de vue

Déclarer les chaînes de nom de vue comme constantes `private static final String` en haut de la classe :

```java
private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
```

### Sécurité : liaison des champs

Toujours utiliser `@InitBinder` pour bloquer les champs sensibles de la liaison HTTP :

```java
@InitBinder
public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
}
```

### Population du modèle

Utiliser les méthodes `@ModelAttribute` pour pré-remplir les données du modèle partagées entre les méthodes :

```java
@ModelAttribute("owner")
public Owner findOwner(@PathVariable(required = false) Integer ownerId) {
    return ownerId != null ? this.owners.findById(ownerId) : new Owner();
}
```

### Validation

Toujours associer `@Valid` avec `BindingResult` immédiatement après l'objet validé. Retourner la vue du formulaire en cas d'erreurs :

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

### Redirections vs Vues

- Après un `POST` réussi, toujours rediriger : `return "redirect:/owners/" + id;`
- Après un `POST` échoué (erreurs de validation), retourner directement la chaîne du nom de vue.

### Pagination

Utiliser `Pageable` et `Page<T>` de Spring Data pour les endpoints de liste :

```java
@GetMapping("/list")
public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
    Page<Vet> paginated = this.vets.findAll(PageRequest.of(page - 1, 5));
    model.addAttribute("listVets", paginated);
    return "vets/vetList";
}
```

---

## Entités

### Hiérarchie d'héritage

Réutiliser les classes de base existantes / ne pas introduire de nouvelles classes de base :

| Classe de base | Ajoute | Utiliser pour |
|---|---|---|
| `BaseEntity` | `id` (`@Id`, `@GeneratedValue`) | Toute entité persistante |
| `NamedEntity` | `name` + `@NotBlank` | Entités de référence/lookup |
| `Person` | `firstName`, `lastName` + `@NotBlank` | Personnes (Owner, Vet) |

### Annotations de classe

```java
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity { ... }
```

### Relations

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

### Validation des champs

Utiliser les annotations Jakarta Bean Validation directement sur les champs de l'entité :

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

Implémenter l'interface `Validator` de Spring pour la validation multi-champs ou à règles métier qui ne peuvent pas être exprimées avec les annotations Bean Validation :

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

Enregistrer le validator dans le controller via `@InitBinder` :

```java
@InitBinder("pet")
public void initPetBinder(WebDataBinder dataBinder) {
    dataBinder.setValidator(new PetValidator());
}
```

---

## Formatters

Implémenter `Formatter<T>` pour convertir les objets du domaine vers/depuis des chaînes de formulaire. Annoter avec `@Component` pour que Spring les enregistre automatiquement :

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

## Règles générales

- Garder les classes petites et focalisées sur une seule responsabilité.
- Suivre la structure de packages existante : grouper par fonctionnalité (`owner/`, `vet/`), pas par couche.
- Suivre les conventions de nommage existantes : `XxxController`, `XxxRepository`, `XxxValidator`, `XxxFormatter`.
- Ajouter ou mettre à jour les tests pour tout changement de comportement.

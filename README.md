# Lab IA Agentique L1

> [!TIP]
> Ce TP s'appuie sur **Spring PetClinic**, une application Spring Boot MVC de référence.
> La documentation complète du repository d'origine est disponible dans [`pet-clinic.md`](pet-clinic.md).

## TP : Découverte des modes et fonctionnalités de Copilot

> [!NOTE]
> Le backlog du projet est disponible dans le dossier [`backlog/`](backlog/). Chaque fichier `.user-story.md` décrit une feature avec ses règles métier. Ces US servent de support tout au long du TP.

### Présentation du TP

Ce TP est une initiation aux différents modes et fonctionnalités de GitHub Copilot dans le cadre du développement d'une application Spring Boot MVC existante : **Spring PetClinic**.

L'objectif n'est pas de livrer une implémentation complète, mais de **pratiquer chaque mode de Copilot** sur des exercices concrets, de comparer les résultats et de développer un sens critique sur les propositions générées.

> [!IMPORTANT]
>
> - L'objectif est de **pratiquer Copilot**, pas de finir l'implémentation.
> - Prendre le temps de **lire et critiquer** ce que Copilot propose avant de l'accepter.
> - **Committer régulièrement** ses expérimentations à l'aide de la fonctionnalité Copilot Commit.

### Déroulement pratique

#### 1. Explorer le projet : Mode Ask

Le mode **Ask** permet d'interroger Copilot sur le code existant. C'est le point de départ indispensable avant toute implémentation : comprendre le projet avant d'agir dessus.

> [!NOTE]
> **Sélection de modèles** : Avant de commencer, dans l'interface Copilot Chat, sélectionner le modèle à utiliser pour cette exploration. La moitié du groupe utilise **GPT-\***, l'autre moitié **Claude Sonnet \***. Les résultats seront comparés à la fin de l'exercice.

##### 1.1 Exploration globale du projet

Commencer par demander à Copilot d'expliquer l'architecture générale de l'application, **sans cibler une feature particulière**.

- Ouvrir GitHub Copilot Chat (`Ctrl+Alt+I`)
- Sélectionner le scope `@workspace`
- Poser les questions suivantes une par une, lire les réponses, noter ce qui surprend

**Prompts suggérés :**

```
@workspace Explique l'architecture générale de ce projet Spring Boot MVC.
Quels sont les packages principaux et leur rôle ?
```

```
@workspace Quelles validations existent déjà dans ce projet ?
Liste les annotations utilisées et les validators custom.
```

```
@workspace Quels tests controller existent ? Donne un exemple de ce qu'ils testent.
```

<details>
<summary>Points clés attendus dans les réponses</summary>

- **Package `owner`** : `Owner`, `OwnerController`, `PetController`, `VisitController`, `PetValidator`
- **Package `vet`** : `Vet`, `VetController` (lecture seule actuellement)
- **Package `system`** : `WelcomeController`, `CrashController`
- **Validations existantes** : `@NotBlank`, `@Pattern(regexp = "\\d{10}")` sur `telephone`, `PetValidator` custom via `@InitBinder`
- **Tests** : `OwnerControllerTests`, `PetControllerTests`, `VetControllerTests`, `ValidatorTests`

</details>

##### 1.2 Exploration ciblée : Feature `preferredContactMethod`

Avec une vision globale du projet, cibler la feature à implémenter : le **mode de contact préféré** d'un owner (voir [`backlog/preferred-contact-method.user-story.md`](backlog/preferred-contact-method.user-story.md)).

**Prompts suggérés :**

```
@workspace Explique comment fonctionne la gestion des owners dans ce projet
et liste les fichiers à modifier pour ajouter un champ preferredContactMethod.
```

```
@workspace Où est défini Owner et quels sont ses champs actuels ?
Quels templates HTML affichent les données d'un owner ?
```

<details>
<summary>Fichiers impactés attendus</summary>

| Fichier | Modification attendue |
|---|---|
| `Owner.java` | Ajout du champ `preferredContactMethod` (enum) et `email` |
| `OwnerController.java` | Validation conditionnelle |
| `createOrUpdateOwnerForm.html` | Ajout du select et du champ email |
| `ownerDetails.html` | Affichage du mode de contact préféré |
| `OwnerControllerTests.java` | Nouveaux cas de test |
| `schema.sql` | Nouvelle colonne |

</details>

##### 1.3 Mise en commun

Comparer les réponses avec les personnes ayant utilisé l'autre modèle :

- Les réponses sont-elles équivalentes en termes de pertinence ?
- L'un des modèles a-t-il identifié des fichiers supplémentaires ?
- L'un des modèles a-t-il mieux contextualisé les règles métier ?

#### 2. Prompt Engineering

Le **Prompt Engineering** consiste à formuler ses demandes à Copilot de manière stratégique pour obtenir des réponses plus précises, plus utiles, et plus faciles à critiquer. Cette section présente **6 stratégies**, chacune appliquée à une feature différente du backlog.

> [!NOTE]
> Ces exercices se font principalement en mode **Ask**. Il ne s'agit pas encore d'implémenter : mais d'expérimenter la formulation des prompts et d'observer comment la qualité de la réponse change.

##### 2.1 Stratégie Questions / Réponses : Welcome Page

**Principe :** Avant de proposer une implémentation, demander à Copilot de poser des questions pour clarifier les besoins. Utile quand les specs sont floues.

**Feature :** [`backlog/welcome-page.user-story.md`](backlog/welcome-page.user-story.md)

**Exercice :**

Poser ce prompt dans Copilot Chat :

```
Avant de proposer une implémentation de la Welcome Page décrite dans le backlog,
pose-moi les questions nécessaires pour clarifier les éléments qui la composeront.
```

- Répondre aux questions de Copilot
- Observer comment les réponses orientent la proposition finale
- Refaire l'exercice **sans** cette étape de clarification et comparer la qualité des résultats

<details>
<summary>Questions typiques que Copilot devrait poser</summary>

- Quelles statistiques afficher (owners, pets, vets) ?
- Faut-il un graphique ou une liste pour la répartition par type d'animal ?
- Le classement des vétérinaires doit-il être limité (top 3, top 5) ?
- Les données doivent-elles être chargées en temps réel ou mises en cache ?

</details>

##### 2.2 Stratégie Avantages / Inconvénients : Authentification

**Principe :** Demander à Copilot de proposer plusieurs approches et de comparer leurs avantages/inconvénients. Utile pour prendre une décision architecturale éclairée.

**Feature :** [`backlog/authentification.user-story.md`](backlog/authentification.user-story.md)

**Exercice :**

```
Propose 2 manières d'implémenter l'authentification dans cette application Spring Boot MVC
et compare leurs avantages et inconvénients dans ce contexte.
```

- Lire les 2 approches proposées
- Évaluer si les critères de comparaison sont pertinents pour ce projet
- Choisir l'approche à implémenter et justifier le choix

<details>
<summary>Approches typiquement proposées</summary>

| Approche | Description | Avantage | Inconvénient |
|---|---|---|---|
| Spring Security + formulaire HTML | Config Java `SecurityFilterChain`, formulaire `login.html` | Natif Spring, facile à intégrer dans MVC | Config plus verbeuse |
| Spring Security + HTTP Basic | Authentification via header HTTP | Simple, adapté aux API REST | Pas de session, UX dégradée pour une app web |

</details>

##### 2.3 Stratégie Étapes atomiques : Page animaux

**Principe :** Demander à Copilot de décomposer l'implémentation en étapes atomiques, dans l'ordre le plus sûr (pas de cassure de l'existant). Utile pour les features qui touchent plusieurs couches.

**Feature :** [`backlog/page-animaux.user-story.md`](backlog/page-animaux.user-story.md)

**Exercice :**

```
Décompose l'implémentation de la page de liste des animaux en étapes atomiques,
dans l'ordre le plus sûr pour ne pas casser le comportement existant.
```

- Vérifier que chaque étape est indépendante et testable
- Identifier l'étape la plus risquée et demander à Copilot comment la sécuriser

<details>
<summary>Décomposition attendue</summary>

1. Vérifier si `PetRepository` expose déjà une méthode `findAll()` avec le owner chargé
2. Si non, ajouter la méthode avec une requête JPQL `JOIN FETCH`
3. Ajouter la méthode `showAllPets()` dans `PetController`
4. Créer le template `pets/petsList.html`
5. Ajouter le lien dans `fragments/layout.html`
6. Écrire un test `PetControllerTests` pour la nouvelle route

</details>

##### 2.4 Stratégie de Rôles : Calendrier RDV

**Principe :** Donner un rôle explicite à Copilot modifie le prisme de sa réponse. Un architecte pense différemment d'un développeur ou d'un reviewer.

**Feature :** [`backlog/calendrier-rdv.user-story.md`](backlog/calendrier-rdv.user-story.md)

> [!NOTE]
> Ces rôles (Architecte, Software Engineer, Code Reviewer) sont les mêmes que ceux qui seront configurés en section 4.3 comme agents Custom Copilot. Cette stratégie de prompt est la version manuelle de ce que les agents automatiseront.

**Exercice :**

Poser le même sujet avec trois rôles différents et comparer :

```
Tu es un architecte Java/Spring.
Propose une architecture pour implémenter le calendrier des RDV d'un vétérinaire.
Fais des recommandations sur la modélisation des données et les choix techniques.
```

```
Tu es un développeur Java/Spring senior.
Implémente la route GET /vets/{id}/calendar qui retourne les visites d'un vétérinaire
sur une période donnée. Donne le code complet du controller et du repository.
```

```
Tu es un code reviewer Java/Spring.
Analyse cette implémentation et liste les risques, les oublis et les améliorations possibles.
[coller le code produit par le rôle précédent]
```

- Observer comment chaque rôle filtre l'information et le niveau de détail
- Noter les points soulevés par le reviewer qui n'auraient pas été couverts spontanément

##### 2.5 Stratégie d'Auto-correction : Modification des vétérinaires

**Principe :** Demander à Copilot de critiquer sa propre proposition avant de la corriger. Cela simule une relecture croisée et détecte les oublis fréquents.

**Feature :** [`backlog/modification-veterinaires.user-story.md`](backlog/modification-veterinaires.user-story.md)

**Exercice :**

Commencer par demander une première implémentation :

```
Implémente la route POST /vets/new pour créer un nouveau vétérinaire
dans cette application Spring Boot MVC.
```

Puis demandez l'auto-correction :

```
Critique ta propre implémentation.
Liste les bugs potentiels, les oublis de validation et les tests manquants.
Corrige ta proposition en tenant compte de cette critique.
```

- Comparer la version initiale et la version corrigée
- Identifier les types d'oublis que Copilot détecte (et ceux qu'il manque)

<details>
<summary>Oublis typiques que Copilot devrait détecter</summary>

- Absence de `@Valid` sur le `@ModelAttribute` dans le controller
- Pas de gestion du cas où le `BindingResult` contient des erreurs
- Redirection manquante après sauvegarde réussie (pattern Post/Redirect/Get)
- Aucun test pour le cas de formulaire invalide
- `@InitBinder` manquant pour protéger le champ `id`

</details>

##### 2.6 Stratégie Zero / One / Few Shot : Calendrier JSON

**Principe :** Fournir des exemples dans le prompt améliore la cohérence et le format des réponses générées. Plus on donne d'exemples, plus la génération est précise.

**Feature :** [`backlog/calendrier-rdv.user-story.md`](backlog/calendrier-rdv.user-story.md)

**Exercice : Zero Shot (sans exemple) :**

```
Génère un exemple de réponse JSON pour l'endpoint GET /vets/1/calendar.
```

**Exercice : One Shot (un exemple) :**

```
Génère un exemple de réponse JSON pour l'endpoint GET /vets/1/calendar.
Voici le format attendu pour un événement :
{ "id": 1, "date": "2026-04-22", "petName": "Léo", "ownerName": "Jean Martin", "description": "Contrôle annuel" }
```

**Exercice : Few Shot (deux exemples) :**

```
Génère 5 événements de calendrier au format JSON pour le vétérinaire 1.
Voici deux exemples :
{ "id": 1, "date": "2026-04-22", "petName": "Léo", "ownerName": "Jean Martin", "description": "Contrôle annuel" }
{ "id": 2, "date": "2026-04-23", "petName": "Mimi", "ownerName": "Sophie Durand", "description": "Vaccination" }
```

- Comparer les trois outputs : cohérence du format, pertinence du contenu, respect du schema
- Que se passe-t-il si un champ `vetId` est ajouté dans l'exemple ?

#### 3. Mode Plan : Feature `preferredContactMethod`

Le **mode Plan** de Copilot (mode Agentique) génère un plan de mise en œuvre complet avant d'écrire du code. Il analyse le projet, identifie les fichiers impactés et propose une séquence d'actions.

> [!NOTE]
> Le mode Plan est distinct du mode Ask : il prend des initiatives, explore activement le codebase, et liste les modifications qu'il va effectuer **avant** de les appliquer. Il est possible de valider ou de rejeter chaque étape.

##### 3.1 Générer le plan

- Ouvrir Copilot Chat et sélectionner le **mode Agent** (icône dans la barre de chat)
- Soumettre le prompt suivant :

```
Planifie l'ajout d'un champ preferredContactMethod sur Owner
avec validation métier conditionnelle, affichage UI et tests.
Commence par lister les fichiers que tu vas modifier avant de toucher au code.
```

- Lire le plan généré **avant** d'approuver quoi que ce soit
- Vérifier que les fichiers listés correspondent à ceux identifiés en section 1.2

##### 3.2 Évaluer le plan

Avant d'appliquer, se poser ces questions :

- Le plan respecte-t-il les 5 règles métier de l'US [`backlog/preferred-contact-method.user-story.md`](backlog/preferred-contact-method.user-story.md) ?
- Les 4 cas de test minimum sont-ils prévus ?
- Une migration SQL est-elle incluse ?
- Y a-t-il des étapes à supprimer ou à ajouter ?

<details>
<summary>Plan attendu (référence)</summary>

1. Créer l'enum `ContactMethod { PHONE, EMAIL, POSTAL_MAIL }`
2. Ajouter `email` (optionnel) et `preferredContactMethod` à `Owner.java`
3. Créer `OwnerValidator.java` implémentant `org.springframework.validation.Validator`
4. Enregistrer le validator dans `OwnerController` via `@InitBinder`
5. Mettre à jour `createOrUpdateOwnerForm.html` (select + champ email)
6. Mettre à jour `ownerDetails.html`
7. Mettre à jour `schema.sql`
8. Ajouter 4 cas dans `OwnerControllerTests.java`

</details>

##### 3.3 Appliquer le plan

- Approuver le plan et laisser Copilot appliquer les modifications
- Après chaque fichier modifié, relire le code généré
- Identifier au moins une chose à corriger ou améliorer dans le code produit

#### 4. Custom Copilot

Les fonctionnalités **Custom Copilot** permettent de personnaliser le comportement de l'assistant : lui donner un contexte projet permanent (Instructions), créer des prompts réutilisables (Prompts), définir des agents avec des rôles spécialisés (Agents), et automatiser des actions récurrentes (Skills).

##### 4.1 Instructions : Contextualiser Copilot sur le projet

Les **Instructions** sont des directives permanentes qui s'appliquent à toutes les conversations Copilot dans le workspace. Elles permettent d'orienter les propositions sans répéter le contexte à chaque prompt.

**Exercice :**

Créer le fichier `.github/copilot-instructions.md` à la racine du projet avec le contenu suivant :

```markdown
Tu travailles sur une application Spring Boot MVC existante (Spring PetClinic).
Privilégie des changements minimaux et lisibles.
Ajoute des validations côté serveur quand une règle métier est introduite.
Propose des tests quand la logique métier change.
Respecte les patterns existants : @InitBinder pour les validators, Thymeleaf pour les vues.
```

Puis comparer :

1. **Sans instructions** : demander à Copilot d'ajouter un champ `email` à `Owner`
2. **Avec instructions** : refaire la même demande après avoir créé le fichier

- Observer si Copilot adapte son style (minimal vs sur-ingéniéré)
- Note-t-il spontanément qu'il faut un test ?
- Propose-t-il d'utiliser `@InitBinder` sans le demander explicitement ?

##### 4.2 Prompts : Créer un prompt réutilisable

Les **Prompts** sont des fichiers `.prompt.md` enregistrés dans VS Code qui peuvent être invoqués rapidement depuis le chat. Ils évitent de retaper des instructions complexes.

**Exercice :**

Créer un fichier de prompt pour la revue de code Spring. Dans VS Code, ouvrir la palette de commandes (`Ctrl+Shift+P`) et chercher **"Copilot: New Prompt File"**, ou créer manuellement un fichier `.prompt.md` dans le dossier des prompts VS Code.

Contenu suggéré :

```markdownmode: askTu es un reviewer Java/Spring.
Analyse le fichier sélectionné et liste :
1. Les violations de bonnes pratiques Spring MVC
2. Les validations manquantes
3. Les cas de test non couverts
4. Les risques de sécurité éventuels
Sois concis et priorise par criticité.
```

- Invoquer ce prompt sur `OwnerController.java`
- L'invoquer sur `VetController.java`
- Comparer les points soulevés dans les deux cas

##### 4.3 Agents : Rôles spécialisés

Les **Agents** Custom Copilot sont des configurations d'IA avec un rôle, des instructions et des outils spécifiques. Ils permettent de déléguer des tâches répétitives à un assistant pré-configuré.

> [!NOTE]
> La **stratégie de rôles** a déjà été pratiquée manuellement en section 2.4. Les agents Custom Copilot automatisent cette personnalisation : il n'est plus nécessaire de répéter le rôle dans chaque prompt.

Les trois agents à configurer correspondent aux rôles de la section 2.4 :

###### Agent Architecte

**Rôle :** Définir l'architecture à partir des specs d'une User Story.

**Instructions :**
```
Tu es un architecte Java/Spring Senior.
À partir d'une User Story, tu proposes :
- La modélisation des données (entités, champs, relations)
- Les couches impactées (controller, service, repository, view)
- Les choix techniques justifiés (annotations, patterns)
Tu ne produis pas de code, seulement des recommandations structurées.
```

**Exercice :** Lui soumettre l'US [`backlog/calendrier-rdv.user-story.md`](backlog/calendrier-rdv.user-story.md) et comparer sa réponse avec ce qui a été obtenu en 2.4.

###### Agent Software Engineer

**Rôle :** Produire une implémentation complète à partir d'un plan ou d'une spec.

**Instructions :**
```
Tu es un développeur Java/Spring Senior.
Tu produis une implémentation complète, compilable et testée.
Tu respectes les patterns existants du projet (voir .github/copilot-instructions.md).
Tu inclus systématiquement : le controller, le validator si besoin, la vue Thymeleaf, et au moins 2 tests.
```

**Exercice :** Lui demander d'implémenter l'étape 3 du plan généré en section 3 (création du `OwnerValidator`).

###### Agent Code Reviewer

**Rôle :** Réviser une implémentation et identifier les risques.

**Instructions :**
```
Tu es un code reviewer Java/Spring.
Tu analyses le code soumis et listes par ordre de criticité :
- Les incohérences avec les règles métier
- Les validations manquantes ou incorrectes
- Les oublis de tests
- Les messages d'erreur manquants ou peu clairs
- Les risques de sécurité (OWASP Top 10)
Tu critiques sans proposer de correction : c'est au développeur de corriger.
```

**Exercice :** Soumettre le code produit par l'Agent Software Engineer à cet agent. Comparer avec l'auto-correction de la section 2.5.

#### 5. Model Context Protocol (MCP)

Le **Model Context Protocol (MCP)** est un protocole ouvert qui permet aux modèles d'IA d'accéder à des outils et des sources de données externes de manière standardisée. Dans VS Code, Copilot peut utiliser des serveurs MCP pour enrichir ses réponses avec des données en temps réel.

##### 5.1 Rechercher une MCP utile pour JPA

**Exercice :**

Explorer le registre des serveurs MCP disponibles et identifier un serveur qui serait utile pour le développement JPA / base de données dans ce projet.

Pistes de recherche :
- [MCP Servers Registry](https://github.com/modelcontextprotocol/servers)
- [Awesome MCP Servers](https://github.com/punkpeye/awesome-mcp-servers)

Questions à se poser :
- Ce serveur MCP permettrait-il à Copilot de lire le schéma de la base de données directement ?
- Pourrait-il générer des requêtes JPQL ou valider des mappings JPA ?
- Quel est l'impact sur la pertinence des suggestions Copilot pour les repositories ?

##### 5.2 Configurer le serveur MCP (si disponible)

Si un serveur MCP pertinent a été identifié et est configurable localement :

1. Ajouter sa configuration dans `.vscode/mcp.json`
2. Reconnecter Copilot au serveur
3. Tester en demandant à Copilot de générer une requête JPQL sur la base des entités du projet

<details>
<summary>Exemple de configuration MCP pour une base de données</summary>

```json
{
  "servers": {
    "sqlite-db": {
      "type": "stdio",
      "command": "uvx",
      "args": ["mcp-server-sqlite", "--db-path", "./petclinic.db"]
    }
  }
}
```

> Ce type de configuration permet à Copilot de lire le schéma et d'exécuter des requêtes SQL via le MCP.

</details>

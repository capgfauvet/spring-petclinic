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

### Prérequis

- Dézipper le projet et l'ouvrir dans VSCode
- Faire un git init pour initialiser le projet afin de pouvoir créer des branches et commiter des fichiers

> [!TIP]
> Démarrer le projet : Exécuter la commande : `.\mvnw spring-boot:run` dans un bash ou `.\mvnw.cmd spring-boot:run` dans PowerShell.

> [!IMPORTANT]
> Commit à chaque fin d'exercice.

### Déroulement pratique

#### 0. Découvrir les Copilot instructions du projet

Ce projet embarque deux niveaux d'instructions Copilot :

- **Instructions globales**, [`.github/copilot-instructions.md`](.github/copilot-instructions.md) : contexte projet, règles générales et conventions appliquées à toutes les conversations Copilot.
- **Instructions contextuelles**, dossier [`.github/instructions/`](.github/instructions/) : règles spécialisées par couche, chargées automatiquement selon les fichiers ouverts dans l'éditeur (`back.instructions.md`, `front.instructions.md`, `database.instructions.md`).

Prendre le temps de lire ces fichiers avant de continuer : ils conditionnent la qualité et la cohérence des propositions de Copilot tout au long du TP.

**Exercice — Interroger Copilot sur la couche back avec les instructions contextuelles actives**

**Mode Ask**

1. Ouvrir un fichier Java du projet (ex. `src/main/java/.../owner/OwnerController.java`) pour que `back.instructions.md` soit actif.
2. Ouvrir Copilot Chat (`Ctrl+Alt+I`) en mode **Ask** et soumettre le prompt suivant :

```
Quelles sont les règles de codage à respecter pour la couche back-end de ce projet ?
Liste les conventions d'injection de dépendances, de validation et de mapping JPA appliquées ici.
```

#### 1. Explorer le projet : Mode Ask

> [!NOTE]
> Le mode **Ask** permet d'interroger Copilot sur le code existant. C'est le point de départ indispensable avant toute implémentation : comprendre le projet avant d'agir dessus.

Commencer par demander à Copilot d'expliquer l'architecture générale de l'application, **sans cibler une feature particulière**.

- Ouvrir GitHub Copilot Chat (`Ctrl+Alt+I`)
- Poser les questions suivantes une par une, lire ses réflexions, lire la réponse 

**Prompts suggérés :**

```
Explique l'architecture générale de ce projet Spring Boot MVC.
Quels sont les différentes couches architecturales et leurs inter-connections. Evalue les bonnes pratiques déjà mises en place dans le projet et à respecter.
```

> [!NOTE]
> **Pour aller plus loin:** : Comparer les résultats avec différents modèles (**GPT-\*** vs **Claude Sonnet \***).

#### 2. Implémentation de la Welcome Page

**Feature :** [`backlog/welcome-page.user-story.md`](backlog/welcome-page.user-story.md)

**Mode Agent**

Avant de proposer une implémentation, demander à Copilot de poser des questions pour clarifier les besoins. Utile quand les specs sont floues.

![Badge stratégie prompt : Questions-Réponses](https://img.shields.io/badge/Prompt--Engineering-Stratégie--Questions--Réponses-informational)

```
Avant de proposer une implémentation de la Welcome Page décrite dans le backlog ( #file:welcome-page.user-story.md  ), pose-moi les questions nécessaires pour clarifier les éléments qui la composeront. Mets à jour l'US en suivant.
```

Faire l'implémentation toujours via le Mode Agent, et vérifier dans l'application la Page Welcome.


#### 3. Implémentation d'une méthode contact préféré

**Feature :** [`backlog/preferred-contact-method.user-story.md`](backlog/preferred-contact-method.user-story.md)

Demander à Copilot de proposer plusieurs approches et de comparer leurs avantages/inconvénients. Utile pour prendre une décision architecturale éclairée.

> [!WARNING]
> A reprendre

![Badge stratégie prompt : Avantages-Inconvénients](https://img.shields.io/badge/Prompt--Engineering-Stratégie--Avantages--Inconvénients-informational)

```
Tu es architecte Java/Spring sur ce projet.
Pour la feature preferredContactMethod, compare 2 approches :
A) Validation via annotations Bean Validation sur le modèle Owner
B) Validation via un validator Spring dédié branché au controller
Pour chaque approche, donne : avantages, inconvénients, lisibilité, maintenabilité, risques de régression et impact sur les tests
Termine par une recommandation argumentée pour ce projet, avec un plan minimal en 5 étapes.
```

#### 4. Implémentation de la page des animaux

**Feature :** [`backlog/page-animaux.user-story.md`](backlog/page-animaux.user-story.md)

**Mode Plan**

Demander à Copilot de planifier l'implémentation de l'US sur la Page des Animaux.

```
Décompose l'implémentation de la page de l'US #file:page-animaux.user-story.md en étapes atomiques.
```

Utiliser le Handoff **Start Implémentation**.

#### 5. Implémentation du calendrier des RDV des vétérinaires

**Feature :** [`backlog/calendrier-rdv.user-story.md`](backlog/calendrier-rdv.user-story.md)

![Badge stratégie prompt : Rôle](https://img.shields.io/badge/Prompt--Engineering-Stratégie--Rôle-informational)

Donner un rôle explicite à Copilot modifie le prisme de sa réponse. Un architecte pense différemment d'un développeur ou d'un reviewer.

**Mode Agent : software-engineer**

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

**Mode Agent : code-reviewer**

#### 6. Stratégie d'auto-correction : modification des vétérinaires

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

**Oublis typiques que Copilot devrait détecter**

- Absence de `@Valid` sur le `@ModelAttribute` dans le controller
- Pas de gestion du cas où le `BindingResult` contient des erreurs
- Redirection manquante après sauvegarde réussie (pattern Post/Redirect/Get)
- Aucun test pour le cas de formulaire invalide
- `@InitBinder` manquant pour protéger le champ `id`

#### 7. Stratégie Zero / One / Few Shot : calendrier JSON

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

**Plan attendu (référence)**

1. Créer l'enum `ContactMethod { PHONE, EMAIL, POSTAL_MAIL }`
2. Ajouter `email` (optionnel) et `preferredContactMethod` à `Owner.java`
3. Créer `OwnerValidator.java` implémentant `org.springframework.validation.Validator`
4. Enregistrer le validator dans `OwnerController` via `@InitBinder`
5. Mettre à jour `createOrUpdateOwnerForm.html` (select + champ email)
6. Mettre à jour `ownerDetails.html`
7. Mettre à jour `schema.sql`
8. Ajouter 4 cas dans `OwnerControllerTests.java`

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

```markdown
mode: ask
Tu es un reviewer Java/Spring.
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

**Exemple de configuration MCP pour une base de données**

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

##### 5.3 Utilisation du MCP via un prompt

**Objectif pédagogique :** utiliser MCP comme un outil d'observabilité de la donnée (et non de correction automatique) pour détecter des anomalies utiles au développement.

**Prompt réutilisable :** [`.github/prompts/mcp-db-data-quality-alert.prompt.md`](.github/prompts/mcp-db-data-quality-alert.prompt.md)

> [!NOTE]
> Le jeu de données contient volontairement un cas de doublon sur les owners afin de valider visuellement que le monitoring détecte une anomalie réelle.

Dans le chat, éxécuter le prompt [`mcp-db-data-quality-alert.prompt.md`](.github/prompts/mcp-db-data-quality-alert.prompt.md).

```
/mcp-db-data-quality-alert Trouve les homonymes et les doublons de contacts dans les owners, puis génère un rapport de monitoring des anomalies.
```

- Utiliser les checkpoints / fork lors d'une discussion
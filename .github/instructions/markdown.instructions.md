---
description: "Google Markdown style guide: writing standards and best practices"
applyTo: "**/*.md"
---

# Markdown Style Guide

Instructions for writing clean, consistent, and maintainable Markdown documents following the [Google Markdown style guide](https://google.github.io/styleguide/docguide/style.html).

## Core Principles

1. **Source text is readable and portable.**
2. **The Markdown corpus is maintainable over time and across teams.**
3. **The syntax is simple and easy to remember.**

Prefer minimal, accurate documentation over sprawling content. Delete cruft frequently.

---

## Document Layout

Every document should follow this skeleton:

```markdown
# Document Title

Short introduction (1–3 sentences).

## First Topic

Content.

## See also

- https://link-to-more-info
```

Rules:

- The **first heading must be a single `# H1`** used as the document title. It should match (or closely match) the filename.
- After the H1, provide a **short introduction** (1–3 sentences) giving a high-level overview so a newcomer understands the purpose immediately.
- All subsequent headings start at **`## H2`** or deeper.
- End with a **"See also"** section for miscellaneous links.

---

## Character Line No-Limit

- **No character limit** per line is enforced.
- Do not artificially wrap lines: each paragraph or sentence should be a single continuous line, with no forced line breaks.

---

## Trailing Whitespace

- **Never** use trailing whitespace to create line breaks.
- If a hard line break is absolutely necessary, use a **trailing backslash** `\` (sparingly).
- Prefer using two newlines (paragraph break) instead of `<br />`.

---

## Headings

### ATX-Style Only

Use `#` prefix headings. Do **not** use underline (`===` / `---`) headings.

```markdown
## Heading 2
```

Not:

```markdown
## Heading 2
```

### Single H1

Use exactly **one** `# H1` heading per document (the title). All other headings must be `##` or deeper.

### Unique, Descriptive Names

Each heading must be unique and fully descriptive, even for sub-sections, so that auto-generated anchor links are intuitive.

Prefer:

```markdown
## Foo

### Foo summary

### Foo example

## Bar

### Bar summary

### Bar example
```

Not:

```markdown
## Foo

### Summary

### Example

## Bar

### Summary

### Example
```

### Spacing Around Headings

- Always leave **a blank line before and after** a heading.
- Always put **a space** after the `#` characters.

```markdown
...text before.

## Heading 2

Text after...
```

### Capitalization of Titles and Headers

Follow the [Google Developer Documentation Style Guide](https://developers.google.com/style/capitalization#capitalization-in-titles-and-headings) for heading capitalization (sentence case recommended).

---

## Lists

### Lazy Numbering for Long Lists

For long or frequently changing lists, use **lazy numbering** (all `1.`):

```markdown
1.  Foo.
1.  Bar.
    1.  Foofoo.
    1.  Barbar.
1.  Baz.
```

For short, stable lists, use explicit sequential numbers for readability:

```markdown
1.  Foo.
2.  Bar.
3.  Baz.
```

### Nested List Spacing (4-Space Indent)

Use a **4-space indent** for nested and wrapped content in both ordered and unordered lists:

```markdown
1.  Two spaces after the number → 4-space text indent.
    Wrapped text also at 4 spaces.
2.  Next item.

- Three spaces after bullet → 4-space text indent.
  Wrapped text also at 4 spaces.
  1.  Nested numbered item (8-space indent for wrap).
- Back to top-level bullet.
```

For trivially short, non-nested, single-line lists, one space after the bullet is acceptable:

```markdown
- Foo
- Bar
- Baz
```

---

## Code

### Inline Code

Use **backticks** for:

- Short code quotations, field names, and CLI commands:
  `` `really_cool_script.sh arg` ``
- File types mentioned generically: `` `README.md` ``
- Fake paths or example URLs that should not auto-link:
  `` `https://example.com/search?q=$TERM` ``

### Code Blocks

- Use **fenced code blocks** (triple backticks), never indented code blocks.
- **Always declare the language** on the opening fence:

  ````markdown
  ```python
  def foo(self, bar):
      self.bar = bar
  ```
  ````

- **Escape newlines** in long shell commands with a trailing backslash `\`:

  ```shell
  bazel run :target -- --flag --foo=longlonglongvalue \
  --bar=anotherlonglonglongvalue
  ```

- When **nesting a code block inside a list**, indent it to align with the list text (4-space base):

  ````markdown
  - Bullet.

    ```c++
    int foo;
    ```

  - Next bullet.
  ````

---

## Links

### Keep Links Short

Long URLs hurt readability. Shorten whenever possible.

### Explicit Paths for Internal Links

Use path-based links for Markdown cross-references:

```markdown
[Link text](/path/to/other/page.md)
```

Do not use fully qualified URLs for internal docs.

### Avoid Deep Relative Paths

- Same-directory relative links are fine: `[text](sibling.md)`
- Avoid `../` chains: prefer absolute paths from the repo root.

### Informative Link Titles

Write the sentence naturally, then wrap the most relevant phrase as the link.

Good:

```markdown
See the [Markdown guide](markdown.md) for more info.
```

Bad:

```markdown
See the Markdown guide for more info: [link](markdown.md).
```

### Reference Links

Use **reference-style links** when:

- The URL is long enough to disrupt reading flow.
- The same URL is referenced multiple times.
- Inside tables (to keep cells compact).

Place reference definitions **just before the next heading** (end of the section where they are first used). Definitions used across multiple sections go at the **end of the document**.

```markdown
See the [Markdown style guide][style] for details.

[style]: https://google.github.io/styleguide/docguide/style.html

## Next Section
```

---

## Images

- Use images **sparingly**; prefer simple screenshots.
- Always provide **meaningful alt text** so non-sighted readers can understand the content.
- Before adding an image, consider whether the same information could be conveyed with text.

---

## Tables

Use tables only for **genuinely tabular data** that benefits from two-dimensional scanning.

- Avoid tables when data could be a list or prose.
- Watch for: poor column distribution, empty cells, unbalanced dimensions, or long prose in cells: these are signs a list is better.
- Use **reference links** inside tables to keep cells short and readable.

Good:

```markdown
| Transport | Favored by | Advantages                 |
| --------- | ---------- | -------------------------- |
| Swallow   | Coconuts   | [Fast when unladen][speed] |
| Bicycle   | Miss Gulch | [Weatherproof][tornado]    |
```

---

## Strongly Prefer Markdown to HTML

- **Do not** use HTML tags in Markdown files.
- If something seems impossible in pure Markdown, reconsider whether you truly need it.
- HTML reduces readability, portability, and breaks integrations that render Markdown as plain text.

---

## Capitalization

Preserve the **original capitalization** of product names, tools, and binaries. For example, write `Markdown` not `markdown` when referring to the language/tool.

---

## Minimum Viable Documentation

- Identify what you truly need: release docs, API docs, testing guidelines.
- A **small set of fresh, accurate docs** beats sprawling content in disrepair.
- Delete obsolete documentation frequently and in small batches.

---

## Quick-Reference Checklist

When writing or reviewing a `.md` file, verify:

- [ ] Single `# H1` title matching (or close to) the filename.
- [ ] Short intro (1–3 sentences) before first `## H2`.
- [ ] ATX-style headings only (`#`, not underlines).
- [ ] Unique, descriptive heading names.
- [ ] Blank line before and after every heading.
- [ ] No trailing whitespace; use `\` if a hard break is needed.
- [ ] Fenced code blocks with language declared.
- [ ] 4-space nested list indentation.
- [ ] Informative link titles (no "click here" / "link").
- [ ] Reference links for long URLs and tables.
- [ ] Images have meaningful alt text.
- [ ] No raw HTML.
- [ ] Correct product-name capitalization.

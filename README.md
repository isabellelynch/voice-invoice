<!-- TODO (Isabelle): add the CI badge here once the repo is on GitHub:
![CI](https://github.com/isabellelynch/voice-invoice/actions/workflows/ci.yml/badge.svg) -->

# Voice Invoice

A mobile-friendly prototype that turns a spoken or typed job description into a reviewable invoice draft.

<!-- TODO: add a screenshot or GIF here -->

## Problem

<!-- TODO: write this in your own words. For example: I run a small baking business, and
invoicing after a job is a chore. Tradespeople have the same problem, often with their hands full.
What if you could just say what you did? -->

## Features

- Voice input where the browser supports it, with typing always available
- A transcript parser that turns spoken amounts ("eighty five euro fifty") into line items
- Anything unclear (amounts, customer names, tax) is flagged for the user rather than guessed
- Server-side money calculations with `BigDecimal` and a documented rounding rule
- Drafts saved to a database, with protection against double-click duplicates
- Print-ready invoice view marked **DRAFT — NOT SENT**

## Architecture

React + TypeScript (Vite) → Spring Boot REST API (Java 21) → H2 database (Flyway migrations)

<!-- TODO: add docs/architecture.md with the diagram from the build plan -->

## Run locally

<!-- TODO: replace with the exact commands that work for you, tested from a fresh clone -->

```bash
# API (http://localhost:8080)
cd api
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run

# Web app (open the URL Vite prints)
cd web
npm install
npm run dev
```

Voice input works in Chrome and Edge on `localhost`. Other browsers can type instead.

## Tests

```bash
cd api && ./mvnw test
```

<!-- TODO: list what the tests cover and how many there are -->

## Design decisions

<!-- TODO: explain each in 1–2 sentences, in your own words:
- Why the server calculates all totals
- Why BigDecimal with HALF_UP rounding, per line
- Why the parser flags instead of guessing
- Parser complexity (linear passes + edit distance) and what you'd change at scale
- How idempotency keys prevent duplicate drafts
- Why voice only ever creates drafts -->

## Limitations

- Prototype only: not accounting or tax software. Tax rates are demo values, not verified.
- Fictional data only. No authentication.
- Voice input depends on browser support.

## What I'd do next

<!-- TODO -->

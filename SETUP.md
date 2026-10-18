# Getting started (do this first)

This folder has everything except the Spring Boot build files, which you generate yourself in
step 2 (so you get the current Spring Boot version and the Maven wrapper). About 30 minutes.

Commands are for Windows Command Prompt. On Mac/Linux use `./mvnw` instead of `mvnw.cmd`.

## 1. Install the tools

- **JDK 21** – Eclipse Temurin 21 from adoptium.net. Check with `java -version`.
- **Node.js LTS** from nodejs.org. Check with `node -v`.
- **Git** (you have this).
- **IntelliJ IDEA Community** (or VS Code with the Java extension pack).

## 2. Generate the Spring Boot build files

1. Go to **start.spring.io** and choose:
   - Project: **Maven** · Language: **Java** · Spring Boot: the newest **4.x** that isn't SNAPSHOT or M/RC
   - Group: `dev.isabellelynch` · Artifact: `api` · Package name: `dev.isabellelynch.invoice`
   - Packaging: Jar · Java: **21**
   - Dependencies: **Spring Web**, **Spring Data JPA**, **Validation**, **H2 Database**, **Flyway Migration**
2. Click **Generate** and unzip the download somewhere temporary.
3. Copy these from the generated project into this project's `api` folder:
   - `pom.xml`
   - `mvnw`, `mvnw.cmd` and the `.mvn` folder
   - `src/main/java/dev/isabellelynch/invoice/ApiApplication.java`
   - `src/test/java/dev/isabellelynch/invoice/ApiApplicationTests.java`
4. **Do not** copy the generated `application.properties`. Keep the one already in `api/src/main/resources`.

## 3. Start the API

```
cd api
mvnw.cmd spring-boot:run
```

Open http://localhost:8080/api/customers. You should see three fictional customers
(John Murphy, Joan Murphy, Mary O'Brien). That means Spring, H2 and Flyway are all working.

## 4. Run the tests (they should FAIL, on purpose)

```
mvnw.cmd test
```

The tests are the spec for the parts you write yourself. They fail with
`UnsupportedOperationException: TODO ...` until you implement them. Work through them in this order:

1. `money/InvoiceCalculator` → `InvoiceCalculatorTest`
2. `parser/NumberWords` → `NumberWordsTest`
3. `parser/CustomerMatcher.editDistance` → `CustomerMatcherTest`, then add a `match` method and its tests
4. `parser/TranscriptParser` → `TranscriptParserTest`
5. `invoice/InvoiceService.createDraft` (try it with Postman: POST /api/invoices/drafts)

Each TODO comment explains the steps. Your build plan doc has more detail. Run the tests after
every small change, and commit each time a test class goes green.

## 5. Start the web app

In a second terminal:

```
cd web
npm install
npm run dev
```

Open the URL it prints (usually http://localhost:5173) in **Chrome**. You should see the customer
dropdown filled from the API. "Read transcript" will show an error until TranscriptParser is done.
The TODO at the top of `web/src/App.tsx` lists the frontend steps.

## 6. Put it on GitHub

```
cd ..   (the voice-invoice folder)
git init
git add .
git commit -m "chore: scaffold Spring Boot API and React client"
```

Create an empty public repo called `voice-invoice` on GitHub (no README), then:

```
git remote add origin https://github.com/isabellelynch/voice-invoice.git
git branch -M main
git push -u origin main
```

The GitHub Actions CI will go red until your tests pass. That's expected; push again when they're green.

## What's done for you vs what's yours

**Done (plumbing):** database schema and seed data, JPA entities and repositories, customer API,
invoice read endpoints, transcript endpoint wiring, error handling, Vite proxy, speech-recognition
hook, starter page, Dockerfile, CI, README outline.

**Yours (the parts your CV and interviews are about):** the calculator, number-word parser,
edit distance and customer matching, transcript parser, draft saving with idempotency, the draft
editor and print view, and the README write-up. If you get stuck, ask me to explain a concept
or review your code. Write it yourself so you can explain every line.

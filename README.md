# 🛠️ Trouve ton artisan API

Back-end REST API built with **Spring Boot** to connect homeowners with craftsmen from the **Auvergne-Rhône-Alpes** region. It exposes the craftsmen and categories catalog, handles the contact form and sends a message to the selected artisan by email.

> **Status:** early development — the endpoint contract below mirrors the [Express version](https://github.com/loickcherimont/api-trouve-ton-artisan-express) of this API.

## 🖥️ Tech Stack

**Backend:**
- **Java 25 + Spring Boot 4.1.1** — Web framework (`spring-boot-starter-webmvc`), REST routes, JSON APIs
- **Spring Data JPA + Hibernate** — ORM for PostgreSQL
- **PostgreSQL** — Database, run via Docker Compose (`compose.yaml`), auto-started by `spring-boot-docker-compose`
- **Spring Mail** — Email sending
- **springdoc-openapi** — Interactive API documentation (Swagger UI)
- **Lombok** — Boilerplate reduction
- **Bean Validation** — Request payload validation

**Testing:**
- **JUnit 5 / AssertJ**, **Mockito** — Unit tests (`*Test`)
- **Testcontainers** — Integration tests (`*IT`) against a real PostgreSQL container
- **JaCoCo** — Code coverage report with an 88% line-coverage gate
- **Postman CLI** — API contract tests driven by the Postman collection

**DevOps:**
- **Maven wrapper (`./mvnw`)** — Build, no local Maven install needed
- **Docker Compose** — Local database
- **GitHub Actions** — CI pipeline (build, tests, coverage, API tests)
- **Git / GitHub** — Version control

## 🚀 Setup

### Prerequisites

- **Java 25** (JDK)
- **Docker** running (database + Testcontainers)

### Quick Start

```bash
# 1. Clone repository
git clone https://github.com/loickcherimont/trouve-ton-artisan-springboot-api.git
cd trouve-ton-artisan-springboot-api/

# 2. Run the API (the dev profile seeds the database)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
# PostgreSQL is started automatically from compose.yaml — no manual step

# API is now running on http://localhost:8080
```

**Access the API:**
- **Swagger UI (interactive docs)** → http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** → http://localhost:8080/v3/api-docs

### Run the tests

```bash
./mvnw test                     # unit tests only (Surefire) — no Docker needed
./mvnw verify                   # unit + integration tests, then the coverage gate
./mvnw test -Dtest=CategoryServiceTest     # run a single unit test class
```

Two kinds of tests, distinguished by their file name:

| File name | Kind | Runner | Needs Docker? |
|-----------|------|--------|---------------|
| `*Test` | Unit test (JUnit 5 / Mockito) | Surefire (`./mvnw test`) | No |
| `*IT` | Integration test (Testcontainers, real PostgreSQL) | Failsafe (`./mvnw verify`) | Yes |

`./mvnw verify` runs the unit tests first, then the integration tests, then generates the JaCoCo report and **fails if line coverage drops below 88%**.

## 🤖 Continuous Integration

Every push and pull request on `main` runs the pipeline defined in `.github/workflows/ci.yml`. It has **two jobs that run in parallel**, and the workflow is **green only if both succeed**:

1. **Build, test & coverage** — runs `./mvnw clean verify`: compiles the project, runs the unit tests (`*Test`) and the integration tests (`*IT`, real PostgreSQL via Testcontainers), then applies the JaCoCo coverage gate. Surefire, Failsafe and JaCoCo reports are uploaded as artifacts.
2. **Postman API tests (Postman CLI)** — starts PostgreSQL from `compose.yaml`, launches the app with the `dev` profile (which seeds the data), waits until `GET /api/categories` answers, then replays `Trouve_Ton_Artisan_API.postman_collection.json` with the Postman CLI. Any failing assertion makes the job fail.

The Postman collection is run as a **local file**, so **no Postman API key is required**. When a job fails, open the failing step in the GitHub Actions log, and download the reports (JUnit XML, JaCoCo HTML, Postman report) from the run summary.

## ⚙️ Environment & profiles

Main config lives in `src/main/resources/application.yml` (PostgreSQL driver, `ddl-auto=update`). Profiles tune it:

- **`dev`** — points at the Docker Compose database (`jdbc:postgresql://localhost:5432/trouve_ton_artisan_db`), forces `spring.sql.init.mode=always` and runs `data.sql` to seed the four categories.
- **`prod`** — disables the OpenAPI documentation (`springdoc.*.enabled=false`); never expose Swagger UI in production.

Select a profile with:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Local database settings live in `compose.yaml` (image `postgres:18`, user/password `compose-postgres`, database `trouve_ton_artisan_db`). They are **dev placeholders only** — never reuse them in production.

## ▶️ Usage

Only `/api/categories` is implemented so far. The other routes are part of the target contract (mirroring the Express version) but are **not live yet**.

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/api/categories` | List all professional categories | ✅ Implemented |
| GET | `/api/craftsmen` | List all craftsmen (filters: `inTopThree`, `fullNameContains`) | 🚧 Planned |
| GET | `/api/craftsmen/{id}` | Get a single craftsman by id | 🚧 Planned |
| POST | `/api/contact` | Submit the contact form (sends an email to the artisan) | 🚧 Planned |

### 1. List All Categories

```bash
curl http://localhost:8080/api/categories
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Alimentation",
    "slug": "alimentation"
  },
  {
    "id": 2,
    "name": "Bâtiment",
    "slug": "batiment"
  },
  {
    "id": 3,
    "name": "Fabrication",
    "slug": "fabrication"
  },
  {
    "id": 4,
    "name": "Services",
    "slug": "services"
  }
]
```

### 2. List All Artisans 🚧 (planned)

```bash
curl http://localhost:8080/api/craftsmen
```

**With filters — only the top 3 artisans of the home page:**

```bash
curl "http://localhost:8080/api/craftsmen?inTopThree=true"
```

**Search by name:**

```bash
curl "http://localhost:8080/api/craftsmen?fullNameContains=vallis"
```

### 3. Get a Single Artisan 🚧 (planned)

```bash
curl http://localhost:8080/api/craftsmen/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "fullName": "Vallis Bellemare",
  "mark": 4,
  "city": "Vienne",
  "about": "Artisan passionné...",
  "email": "v.bellemare@exemple.fr",
  "website": null,
  "inTopThree": false,
  "specialtiesId": 1
}
```

**Response (404 Not Found):** `{ "error": 404, "message": "Artisan <ID> n'existe pas" }`

### 4. Submit the Contact Form 🚧 (planned)

```bash
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jeanne Dupont",
    "email": "jeanne.dupont@mail.fr",
    "subject": "Devis plomberie",
    "message": "Bonjour, j'aimerais un devis pour...",
    "to": "v.bellemare@exemple.fr"
  }'
```

**Response (200 OK):**
```json
{
  "message": "Merci pour votre message. Vous recevrez une réponse sous 48h."
}
```

## 📄 API Documentation

Interactive documentation (request/response examples) once the API is running:
→ http://localhost:8080/swagger-ui.html

## 🧪 Project structure

```
src/main/java/com/github/loickcherimont/trouve_ton_artisan/
├── model/        # JPA entities
├── repository/   # Spring Data JPA repositories
├── service/      # Business logic
├── controller/   # REST controllers (DTOs in /dto, errors in /exception)
└── ...
```

Conventions: **Test-Driven Development**, layer architecture above, never expose JPA entities from controllers — map through DTOs.

## 🔗 Related Project

Frontend React application consuming this API:
→ [devoir-trouve-ton-artisan-reactjs](https://github.com/loickcherimont/devoir-trouve-ton-artisan-reactjs)

Original Express version of this API:
→ [api-trouve-ton-artisan-express](https://github.com/loickcherimont/api-trouve-ton-artisan-express)

## 🔑 License

<div align="center">Copyright &copy; 2026 | Loick CHERIMONT | All Rights Reserved.</div>

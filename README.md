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
- **JUnit 5 / AssertJ**, **Mockito** — Unit tests
- **Testcontainers** — Integration tests against a real PostgreSQL container

**DevOps:**
- **Maven wrapper (`./mvnw`)** — Build, no local Maven install needed
- **Docker Compose** — Local database
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

# 2. Run the API (development)
./mvnw spring-boot:run
# PostgreSQL is started automatically from compose.yaml — no manual step

# API is now running on http://localhost:8080
```

**Access the API:**
- **Swagger UI (interactive docs)** → http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** → http://localhost:8080/v3/api-docs

### Run the tests

```bash
./mvnw test                          # full suite (starts a PostgreSQL Testcontainer)
./mvnw test -Dtest=SomeTest          # single test class
```

## ⚙️ Environment & profiles

Main config lives in `src/main/resources/application.yml`. The `dev` / `prod` profile files (`application-dev.yml`, `application-prod.yml`) exist but are currently empty — select a profile with:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Database credentials for local dev are defined in `compose.yaml` (`myuser`/`secret`, database `mydatabase`) and are **placeholders only** — never reuse them in production.

## ▶️ Usage

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/categories` | List all professional categories | ✅ Public |
| GET | `/api/craftsmen` | List all craftsmen (filters: `inTopThree`, `fullNameContains`) | ✅ Public |
| GET | `/api/craftsmen/{id}` | Get a single craftsman by id | ✅ Public |
| POST | `/api/contact` | Submit the contact form (sends an email to the artisan) | ✅ Public |

### 1. List All Categories

```bash
curl http://localhost:8080/api/categories
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Bâtiment",
    "slug": "batiment"
  }
]
```

### 2. List All Artisans

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

### 3. Get a Single Artisan

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

**Response (404 Not Found):** `{ "error": 404, "message": "Artisan not found" }`

### 4. Submit the Contact Form

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

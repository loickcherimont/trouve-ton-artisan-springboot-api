# AGENTS.md

## Project overview

Fictional REST API helping people find a craftsman (artisans, categories, contact form by email).

### Stack

- Java 25, Spring Boot 4.1.1, Maven wrapper (`./mvnw`)
- PostgreSQL via `compose.yaml`; `spring-boot-docker-compose` starts/stops it automatically with the app
- Spring Data JPA / Hibernate, Bean Validation, Spring Mail
- springdoc-openapi 3.1.0 (Swagger UI)
- Tests: JUnit 5 / AssertJ, Mockito, Testcontainers (PostgreSQL)
- Lombok (annotation processor wired in `pom.xml`)

## Build and test commands

- `./mvnw spring-boot:run` — dev server on `http://localhost:8080`; auto-starts Postgres from `compose.yaml` (Docker must be running)
- `./mvnw test` — unit tests only (Surefire, pattern `*Test`, no Docker needed)
- `./mvnw verify` — unit tests + integration tests (Failsafe, pattern `*IT`, Testcontainers requires Docker)
- `./mvnw test -Dtest=SomeTest` — single test class
- `./mvnw package` — build the jar

## Code style guidelines

- Build the API following Test-Driven Development practises
- No linter/formatter is configured — match the style of surrounding code
- Never expose JPA entities from controllers: map to/from DTOs at the boundary

### Architecture

- Package `com.github.loickcherimont.trouve_ton_artisan`, entrypoint `TrouveTonArtisanApplication`
- Layers: `model`, `repository`, `service`, `controller`, `dto`, `exception`
- Profiles `dev`/`prod` exist (`application-dev.yml`, `application-prod.yml`)
- Endpoints: Swagger UI `/swagger-ui.html`, OpenAPI JSON `/v3/api-docs`

## Testing instructions

- Naming: `*Test` = unit tests (Surefire, run with `./mvnw test`), `*IT` = integration tests (Failsafe, run with `./mvnw verify`)
- `@SpringBootTest`/`@DataJpaTest` classes must `@Import(TestcontainersConfiguration.class)` — it provides the PostgreSQL container via `@ServiceConnection`, and must be named `*IT`
- `TestTrouveTonArtisanApplication` boots the real app against a Testcontainers-managed Postgres for manual testing (no local DB needed)
- Plain unit tests (JUnit/Mockito) need no container — prefer them over context-loading tests

## Security considerations

- `compose.yaml` credentials (`myuser`/`secret`) are dev-only placeholders; never reuse them elsewhere
- Springdoc docs are enabled by default — set `springdoc.api-docs.enabled=false` and `springdoc.swagger-ui.enabled=false` in production config

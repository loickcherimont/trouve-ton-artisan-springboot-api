# AGENTS.md

## Overview

<!-- TODO: to complete with stack, dependencies used to build the project -->
This project is an REST API for to about craftmen

## Commands

```bash
./mvnw clean verify          # compile + test
./mvnw spring-boot:run       # run locally (needs PostgreSQL)
```

Single test: `./mvnw test -Dtest=TrouveTonArtisanSpringbootApiApplicationTests`

## Conventions

- Lombok for boilerplate (`@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor`)
- Package structure technical layered architecture by domain: `artisan/`, `category/`, `specialty/`, `shared/**`

## Testing

## Security

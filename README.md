# VideoClub API — Java 17 + Spring Boot 3

A **learning-oriented REST API** derived from the [original VideoClub UML](images/uml.png). It currently implements **seven UML controller operations** with Spring MVC, services, Spring Data JPA and MySQL. Unit tests and an H2-based JPA integration test run in GitHub Actions.

> **Current scope ≠ future architecture.** The [advanced domain v2 diagram](docs/uml-v2-proposal.md) and [roadmap](ROADMAP.md) are **proposals**, not features available in the application. This is not a production-ready rental platform.

**Portfolio overview:** [VideoClub Backend Lab — all three implementations](docs/showcase/README.md) · [Español](docs/showcase/README.es.md)

**Other implementations:** [Express 5 + TypeScript](https://github.com/samantonio91/videoclub-api-express-ts) · [NestJS + TypeScript](https://github.com/samantonio91/videoclub-api-nestjs-ts). These are comparative learning projects sharing a domain, not three independent commercial systems.

## Current architecture

```text
HTTP request
  → VideoClubController
  → ClienteService / JuegoService
  → Spring Data JPA repository
  → MySQL (development) / H2 (tests)
```

**Model in the repository today:** `Cliente`, abstract `Producto`, `Juego`, `Pelicula` and `Alquiler`. `Juego` and `Pelicula` use JPA `JOINED` inheritance from `Producto`. `Alquiler` has references to customer and product, but a complete rental/return HTTP workflow is **not implemented**. The `GeneroPelicula` model from the original UML is still pending. The service instances are managed by Spring rather than using a handwritten static singleton.

### Implemented API contract (Spanish names retained from the UML)

| UML method | HTTP | Endpoint |
| --- | --- | --- |
| AgregarCliente | POST | `/api/clientes` |
| ModificarCliente | PUT | `/api/clientes/{codigo}` |
| EliminarCliente | DELETE | `/api/clientes/{codigo}` |
| MostrarClientes | GET | `/api/clientes?documento=ABC&nombre=Ana` |
| BuscarCliente | GET | `/api/clientes/{codigo}` |
| AgregarJuego | POST | `/api/juegos` |
| ModificarJuego | PUT | `/api/juegos/{codigo}` |

Examples:

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H 'Content-Type: application/json' \
  -d '{"documento":"ABC123","nombre":"Ana García","telefono":"5550101","email":"ana@example.com"}'

curl -X POST http://localhost:8080/api/juegos \
  -H 'Content-Type: application/json' \
  -d '{"codigo":101,"titulo":"Example Game","precio":199.0,"plataforma":"PC","genero":"Adventure"}'
```

Customer/game path IDs are database IDs (`Long`), while `Producto.codigo` is a domain field. Not-found operations return 404; invalid inputs return 400; deletion of a customer referenced by rentals returns 409. Two earlier routes are retained for compatibility: `POST /api/save-cliente` and `GET /api/get-client?cliente_id=1`.

## Run locally

Prerequisites: **JDK 17**, **MySQL**, and the included Gradle wrapper.

1. Create a local `videoclub` MySQL database.
2. Configure `DB_USERNAME` and `DB_PASSWORD` in your local environment.
3. Start the service: `./gradlew bootRun` (Windows: `gradlew.bat bootRun`).
4. Run tests: `./gradlew test` (H2 test configuration; MySQL is not required to run tests).

**Schema warning:** JPA `JOINED` inheritance, `Cliente.documento` and the rental date fields modified the original schema. Do not assume `ddl-auto=update` is a safe production migration strategy. A versioned [Flyway baseline is planned](https://github.com/samantonio91/videoclub-api-java/issues/5). Do not commit secrets.

## Current tests

- Service-level tests for customer and game operations.
- JPA integration tests for inheritance and filtering.
- GitHub Actions executing Gradle tests on pull requests and on `main`.

No claim is made here about production traffic, deployment, rental volume or comprehensive end-to-end coverage.

## Advanced Spring roadmap (not implemented yet)

The [issue-linked roadmap](ROADMAP.md) covers Bean Validation and response DTOs, Flyway, stock copies, transactional rentals and returns, reservations, optimistic/pessimistic locking, Testcontainers, Spring Security, OpenAPI, observability and optional notifications/payments.

The [proposed v2 UML](docs/uml-v2-proposal.md) adds an inventory copy to distinguish a catalog product from an individual rentable item. It includes a proposed concurrent rental sequence. The **original UML remains unchanged**:

![Original VideoClub UML diagram](images/uml.png)

[README en español](README.es.md) · [Roadmap](ROADMAP.md) · [Advanced UML proposal](docs/uml-v2-proposal.md)

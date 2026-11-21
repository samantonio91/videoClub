# Java VideoClub — Roadmap / Plan de mejoras

> **Current implementation:** seven original UML REST operations, JPA domain foundations, unit and H2-backed integration tests.
> **Estado actual:** siete operaciones REST implementadas. La segunda versión del modelo es únicamente una propuesta de diseño.

The roadmap exists to support genuine iterative development with focused pull requests and test results. No artificial dates, invented commits, or claims of completed future functionality.

| Priority | Issue | Scope | Status |
| --- | --- | --- | --- |
| P0 | [#3 Documentation, bilingual README and proposed UML v2](https://github.com/samantonio91/videoclub-api-java/issues/3) | Clarify shipped vs proposed behavior; original diagram preserved | In review |
| P1 | [#4 DTOs, validation and consistent HTTP errors](https://github.com/samantonio91/videoclub-api-java/issues/4) | Bean Validation, ControllerAdvice, avoid exposing JPA entities | Planned |
| P1 | [#5 Versioned Flyway migrations](https://github.com/samantonio91/videoclub-api-java/issues/5) | MySQL schema baseline and local Docker DB | Planned |
| P1 | [#11 Testcontainers and integration CI](https://github.com/samantonio91/videoclub-api-java/issues/11) | Exercise actual MySQL locking/transaction behavior | Planned |
| P1 | [#7 Inventory copies and availability](https://github.com/samantonio91/videoclub-api-java/issues/7) | Copies distinct from catalog products; status and queries | Planned |
| P1 | [#8 Transactional rentals and returns](https://github.com/samantonio91/videoclub-api-java/issues/8) | Atomic checkouts/returns; parallel-request safety | Planned |
| P2 | [#9 Reservations and expiry](https://github.com/samantonio91/videoclub-api-java/issues/9) | Reservation states and bounded holds | Planned |
| P2 | [#10 Spring Security and role-based access](https://github.com/samantonio91/videoclub-api-java/issues/10) | Authentication, authorization, ownership | Planned |
| P2 | [#6 Consistent English naming](https://github.com/samantonio91/videoclub-api-java/issues/6) | Refactor classes and packages; preserve or version API contracts | Planned |
| P2 | [#12 OpenAPI, Docker and observability](https://github.com/samantonio91/videoclub-api-java/issues/12) | Documented API and measurable monitoring | Planned |
| P3 | [#13 Optional payments and notifications spike](https://github.com/samantonio91/videoclub-api-java/issues/13) | ADR, payment sandbox, idempotency, event/outbox analysis | Planned |
| P2 | [#14 Portfolio comparison case study](https://github.com/samantonio91/videoclub-api-java/issues/14) | Explain Java vs Express vs NestJS and tested decisions | Planned |

## Milestones / Hitos propuestos

### Milestone A — Reliable API contracts

Issues #4, #5 and #11. Explicit DTOs; controlled SQL migrations; integration tests with MySQL. No JPA entity serialization assumptions or destructive schema updates.

### Milestone B — Real rentals domain

Issues #7 and #8. A catalog product has separately rentable copies. A rental is tied to **one copy** and a customer; a copy can have many historical rentals but **at most one active rental**. A database-level lock/constraint and transactional service enforce the invariant. `@Transactional` alone is not sufficient.

### Milestone C — Reservations and access control

Issues #9 and #10. Define reservation expiry, claims and roles. The ownership/security model must protect customer personal data.

### Milestone D — Production-like engineering evidence

Issue #12 plus optional #13. Add OpenAPI, Docker, health and correlation IDs. Measure actual outcomes in tests; do not claim commercial-grade production or fictitious performance.

### Milestone E — Recruiter-facing comparison

Issues #6 and #14. Separate external API compatibility from internal English naming, then produce a bilingual architecture comparison to the [Express](https://github.com/samantonio91/videoclub-api-express-ts) and [NestJS](https://github.com/samantonio91/videoclub-api-nestjs-ts) implementations.

See [v2 UML proposal](docs/uml-v2-proposal.md). This roadmap may evolve as actual requirements and tests reveal constraints.

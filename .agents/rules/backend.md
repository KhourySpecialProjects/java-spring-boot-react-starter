---
paths:
  - "backend/**"
---

# Backend rules

- Keep the layers: `controller` handles HTTP, `service` holds business logic,
  `dao` is the only caller of a `repository` and turns entities into DTOs.
  Entities never leave the `dao` layer.
- Inject dependencies through the constructor into `private final` fields.
- Every controller lives under `/api/v1`. Give each method
  `@Operation(operationId = "...")`, which becomes the function name in the frontend
  client, and `@ApiResponse` with a description.
- Name DTO schemas after the resource with `@Schema(name = "Message")` and list
  `requiredProperties`, so the client types match the other starters.
- Every repository query is `@Query(value = "...", nativeQuery = true)`. See
  "Queries" in `AGENTS.md`.
- Change tables only through entities and `mise run db:dev:migrate:generate <name>`.
- After changing a controller or DTO, run `mise run contract` and commit
  `backend/openapi.json`.

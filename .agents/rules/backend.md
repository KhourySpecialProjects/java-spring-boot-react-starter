---
paths:
  - "backend/**"
---

# Backend rules

- Keep the layers: `controller` handles HTTP, `service` holds business logic,
  `dao` is the only caller of a `repository` and turns entities into DTOs.
  Entities never leave the `dao` layer.
- Inject dependencies through the constructor into `private final` fields.
- Change the schema by editing an entity, then run
  `mise run db:dev:migration <name>` and review the generated SQL. Never edit a
  migration that has already been merged.
- After changing a controller or DTO, run `mise run contract` and commit the
  updated `contract/openapi.json`.

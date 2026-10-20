# Working in this repo

Rules for anyone changing code here, human or agent. Before editing `backend/` or
`frontend/`, also read the matching file in `.agents/rules/`.

## Running things

- The app always runs in Docker. `mise run dev:up` starts db, backend and web;
  `mise run dev:down` stops them and keeps the data.
- mise loads `.env`, and Docker Compose passes it into the containers. Running
  `./gradlew bootRun` or `pnpm dev` on your machine will fail on startup. That is
  expected, not a bug to work around. Do not add default values to get past it.
- Lint, format and the OpenAPI export run on your machine through mise because they
  never touch the database.
- `mise tasks` lists every task. Run `mise run check` before you finish. It is what
  CI runs.

## Layout

```
browser -> web (vite / nginx) -> /api -> backend (Spring) -> db (Postgres)

backend/   controller -> service -> dao -> repository -> entity
           middleware/ runs around every request (request logging)
frontend/  React app, API client generated from backend/openapi.json
```

## Writing code

- Read the code you are changing, and the code that calls it.
- Make the smallest change that solves the problem. No speculative abstractions,
  config, or dependencies.
- Follow the patterns already in the repo before inventing new ones.
- Names carry the meaning. Comments explain why, not what.
- Prefer guard clauses and early returns over nested `if`s.
- Delete dead and commented-out code. Git has it.
- Do not swallow errors. Handle them or let them surface.
- In the frontend, write modern TypeScript: arrow functions, `type` instead of
  `interface`, no `enum` or `namespace`.

## The database

The JPA entities in `backend/src/main/java/com/example/app/entity/` are the source of
truth for every table. Change the database only this way:

1. Edit or add an entity.
2. Run `mise run db:dev:migrate:generate <name>`. Hibernate compares the entities
   with the database and writes the SQL to the next
   `backend/src/main/resources/db/migration/V<N>__<name>.sql`.
3. Read the SQL. Hibernate never drops columns or tables, so add those statements
   yourself. Then run `mise run db:dev:migrate:up`.
4. `mise run db:dev:migrate:status` shows whether the database is missing migrations.

Migrations only go forward. Never edit a migration that is already on `main`. Add a
new one instead.

## Queries

Every query must read as SQL. Write it as a native query on a `JpaRepository`:

```java
@Query(value = "SELECT * FROM messages WHERE id = :id", nativeQuery = true)
Optional<Message> findByIdSQL(@Param("id") Integer id);

@Modifying
@Query(value = "INSERT INTO messages (text) VALUES (:text)", nativeQuery = true)
void insertMessage(@Param("text") String text);
```

- Never use derived query methods (`findByTextOrderByCreatedAtDesc`) or the built-in
  `findAll`, `findById`, `save` and `delete*` methods.
- Never use JPQL, the Criteria API or string concatenation. Pass values as `:named`
  parameters.

## The API contract

The controllers and DTOs are the source of truth for the API. `backend/openapi.json`
is generated from them and committed. `frontend/src/api/generated/` is generated from
that file and gitignored. Never edit either by hand. After changing a controller or
DTO, run `mise run contract` and commit `backend/openapi.json`.

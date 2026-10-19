# Spring Boot + React starter

A Spring Boot backend, a React frontend and Postgres, all running in Docker.
The frontend talks to the backend through a client generated from the
backend's OpenAPI spec.

```
browser -> web :8080 -> /api/* -> backend :3000 -> db :5432
           (vite in dev, nginx in deploy)
```

## Requirements

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [mise](https://mise.jdx.dev/getting-started.html). It installs Java 25,
  Node and pnpm for you. Run `mise install` once after cloning.

## Quick start

```sh
mise install
mise run setup   # creates .env, installs frontend deps, generates the API client
mise run dev     # http://localhost:8080
```

> [!IMPORTANT]
> In development, always run the app through Docker with `mise run dev`.
> Compose is the only thing that loads `.env` into the app. Running
> `./gradlew bootRun` or `pnpm dev` directly on your machine skips it, so the
> backend can't find the database and the frontend can't find the backend.

The frontend reloads on save. The backend does not. Run `mise run dev:restart`
after changing Java code.

## Tasks

Run `mise tasks` for the full list.

| Task | What it does |
| --- | --- |
| `mise run setup` | Create `.env` from `.env.example`, install frontend deps, generate the API client |
| `mise run dev` | Run web, backend and db in Docker |
| `mise run dev:restart` | Restart the backend after Java changes |
| `mise run dev:down` / `dev:logs` | Stop the containers / follow their logs |
| `mise run stack` | Run the deploy images (built jar + nginx) to check a production build |
| `mise run db:dev:psql` | Open `psql` in the dev database |
| `mise run db:dev:migration <name>` | Generate a migration from your entity changes |
| `mise run db:dev:reset` | Delete the dev database and start over |
| `mise run contract` | Export the OpenAPI spec and regenerate the frontend client |
| `mise run check` | Format check + lint for both apps (what CI runs) |
| `mise run fmt:fix` / `lint:fix` | Fix formatting / auto-fixable lint errors |

## Layout

```
backend/                   Spring Boot, Gradle, Java 25
  src/main/java/com/example/app/
    controller/            HTTP routes
    service/               business logic
    dao/                   the only caller of repositories, maps entities to DTOs
    dto/                   request and response shapes (these end up in the API client)
    repository/            Spring Data JPA interfaces
    entity/                JPA entities, one per table
  src/main/resources/
    application.yaml       config, read from env vars
    db/migration/          Flyway migrations
contract/openapi.json      API spec, exported from the backend
frontend/                  React + Vite
  src/api/generated/       API client (generated, gitignored)
  nginx.conf               deploy server: serves the build, proxies /api
compose.yaml               deploy-like stack
compose.override.yaml      dev overrides, merged automatically
```

A request goes `controller -> service -> dao -> repository`. The DAO hands DTOs
back up, so entities never leave the data layer.

## Changing the database

1. Edit or add an entity in `backend/.../entity/`.
2. Run `mise run db:dev:migration add_something`. It starts the backend once,
   compares your entities to the dev database, and writes the SQL needed to
   match them into the next `V<N>__add_something.sql`.
3. Read the file. Hibernate never drops columns or tables, so write those by
   hand. Adjust anything else it got wrong.
4. Run `mise run dev:restart`. Flyway applies the migration on startup.

Migrations only go forward. Never edit a migration after it has been merged.
Add a new one instead. To start over locally, run `mise run db:dev:reset`.

## Changing the API

1. Change a controller or DTO in the backend.
2. Run `mise run contract`. It exports `contract/openapi.json` from the running
   backend and regenerates `frontend/src/api/generated/`.
3. Commit `contract/openapi.json`. The generated client is gitignored and
   rebuilt from it.

The function names in the client come from `@Operation(operationId = ...)` on
each controller method.

## Environment variables

`.env.example` lists every variable. `mise run setup` copies it to `.env`,
which is gitignored. To add one:

1. Add it to `.env.example` and your `.env`.
2. Read it in `application.yaml` as `${MY_VAR}`, without a default.

Compose passes `.env` to the `db` and `backend` containers.

## Making it yours

- Rename the Java package: move `backend/src/main/java/com/example/app` and
  update the `package` lines, then change `group` in `backend/build.gradle.kts`.
- Rename the project in `backend/settings.gradle.kts`, `frontend/package.json`
  and `frontend/index.html`.
- Delete the `messages` example (entity, repository, DAO, DTO, service,
  controller, and the migration if nothing is deployed yet), then run
  `mise run contract`.

## Linting and formatting

- Backend: [Spotless](https://github.com/diffplug/spotless) with
  google-java-format, and [Error Prone](https://errorprone.info/), which runs
  inside `javac` and fails the build on common bugs.
- Frontend: [oxlint](https://oxc.rs/docs/guide/usage/linter) and
  [oxfmt](https://oxc.rs/docs/guide/usage/formatter), plus `tsc` for types.

CI (`.github/workflows/ci.yml`) runs lint and format for each app on every pull
request.

## Agents

`AGENTS.md` and `.agents/rules/` hold the rules coding agents follow in this
repo. They are a starting point. Add rules there as your team agrees on them.

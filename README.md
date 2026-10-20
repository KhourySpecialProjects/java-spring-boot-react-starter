# Spring Boot React starter

React, Spring Boot and Postgres. `mise tasks` lists every task.

## Install mise

```sh
curl https://mise.run | sh
```

Then follow the printed step to activate it in your shell. See
<https://mise.jdx.dev/getting-started.html> for other installers. You also need
Docker.

## Set up

```sh
mise install
cp .env.example .env
mise run setup
```

## Run

Everything runs in Docker.

```sh
mise run dev:up        # start db, backend and web
mise run dev:restart   # after changing Java code
mise run dev:down      # stop everything, data is kept
```

Open <http://localhost:5173>. The API is on <http://localhost:3000/api/v1>.

## Migrations

Tables are defined by the entities in `backend/src/main/java/com/example/app/entity/`. Change them there, then generate a migration.

```sh
mise run db:dev:migrate:generate add_something   # write SQL from your model changes
mise run db:dev:migrate:up                       # apply pending migrations
mise run db:dev:migrate:status                   # is the database behind?
```

Read the generated SQL before committing it. Hibernate never writes `DROP` statements, so add those yourself. There is no down migration: fix mistakes with a new one.

`mise run db:dev:studio` opens Prisma Studio on <http://localhost:5555>.

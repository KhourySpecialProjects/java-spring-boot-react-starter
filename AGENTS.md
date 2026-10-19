# Working in this repo

Rules for anyone changing code here, human or agent. `.agents/rules/` has
extra rules for `backend/` and `frontend/`. Read the one for the folder you are
editing. Add to these files when the team agrees on a new rule.

## Running things

- Run the app with `mise run dev`. It starts web, backend and db in Docker at
  http://localhost:8080.
- `.env` only reaches the app through Docker. `./gradlew bootRun` or `pnpm dev`
  on your machine will fail on startup. That is expected, not a bug to work
  around. Do not add default values to get past it.
- `mise tasks` lists every task. Run `mise run check` before you finish. It is
  what CI runs. `mise run fmt:fix` fixes formatting.

## Layout

```
browser -> web (nginx / vite) -> /api -> backend (Spring) -> db (Postgres)

backend/   controller -> service -> dao -> repository -> entity
contract/  openapi.json, exported from the backend
frontend/  React app, API client generated from contract/
```

## Code

- Read the code you are changing, and the code that calls it.
- Make the smallest change that solves the problem. No speculative abstractions,
  config, or dependencies.
- Follow the patterns already in the repo before inventing new ones.
- Names carry the meaning. Comments explain why, not what.
- Prefer guard clauses and early returns over nested `if`s.
- Delete dead and commented-out code. Git has it.
- Do not swallow errors. Handle them or let them surface.
- Never hand-edit generated files: `contract/openapi.json` and
  `frontend/src/api/generated/`.

# Architecture

Local development:

```
browser -> Vite :5173 -> /api -> Spring :3000 -> Postgres :5432 (all in Docker)
```

Deployed (`compose.yaml`):

```
browser -> nginx :8080 -> /api -> Spring :3000 -> Postgres
```

The frontend only calls relative `/api/...` URLs. Vite forwards them in development and
nginx does it when deployed.

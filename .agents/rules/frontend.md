---
paths:
  - "frontend/**"
---

# Frontend rules

- Call the backend through the generated client in `src/api/generated`, not
  hand-written `fetch` calls.
- If the client is missing an endpoint, change the backend and run
  `mise run contract`. Do not edit the generated files.
- Call the API with relative `/api/...` URLs. Vite proxies them in dev and nginx
  proxies them in deploy.

FROM node:24-slim AS base
RUN corepack enable
WORKDIR /app
COPY frontend/package.json frontend/pnpm-lock.yaml ./
RUN pnpm install --frozen-lockfile

# Dev server. compose.override.yaml bind-mounts the source over /app/src.
FROM base AS local
COPY frontend ./
COPY contract /contract
CMD ["sh", "-c", "pnpm exec openapi-ts && pnpm exec vite"]

FROM base AS build
COPY frontend ./
COPY contract /contract
RUN pnpm exec openapi-ts && pnpm run build

FROM nginxinc/nginx-unprivileged:1.30-alpine AS deploy
COPY frontend/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 8080

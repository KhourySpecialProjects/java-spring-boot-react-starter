import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// Set by compose.override.yaml. There is no fallback on purpose: run the app
// with `mise run dev`, not `pnpm dev` on your machine.
const backendUrl = process.env.BACKEND_URL;

export default defineConfig(({ command }) => {
  if (command === "serve" && !backendUrl) {
    throw new Error("BACKEND_URL is not set. Run the app with `mise run dev`.");
  }

  return {
    plugins: [react()],
    server: {
      host: true,
      port: 8080,
      strictPort: true,
      proxy: { "/api": backendUrl ?? "" },
    },
  };
});

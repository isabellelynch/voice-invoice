import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// The browser calls /api/... on the Vite port and Vite forwards it to Spring Boot,
// so there is no CORS to configure during development.
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})

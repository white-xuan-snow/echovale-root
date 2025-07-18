import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import {System} from "./src/constant/system.ts";
import wasm from "@applemusic-like-lyrics/lyric/node_modules/vite-plugin-wasm";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue(), wasm()],
  server: {
    host: System.Server.Host,
    port: System.Server.Port,
    cors: System.Server.Cors,
    proxy: {
      '/api': {
        target: System.Server.Target,
        changeOrigin: System.Server.ChangeOrigin,
        rewrite: System.Server.Rewrite,
      }
    }
  },
  optimizeDeps: {
    exclude: ['@applemusic-like-lyrics/lyric']
  }
})

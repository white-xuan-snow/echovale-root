/// <reference types="vite/client" />

declare global {
  interface Window {
    initTAC: (baseUrl: string, config: any) => Promise<any>;
  }
}

export {}

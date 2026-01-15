const BACK_END = "http://localhost:8080"
const PROXY = '/api'

const SERVER= {
    Port: 5173,
    Host: '0.0.0.0',
    Target: BACK_END,
    BackEnd: BACK_END,
    Cors: true,
    ChangeOrigin: true,
    Proxy: PROXY,
    Rewrite: (path: string) => path.replace(/^\/api/, ''),
}


export const System = {
    Server: SERVER,
} 
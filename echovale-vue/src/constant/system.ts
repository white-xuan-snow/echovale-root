const BACK_END = "http://localhost:8080"
const PROXY = '/api'
const REPLACE = '/^' + PROXY + '/'

const SERVER= {
    Port: 5173,
    Host: '0.0.0.0',
    Target: BACK_END,
    BackEnd: BACK_END,
    Cors: true,
    ChangeOrigin: true,
    Proxy: PROXY,
    Rewrite: (path: string) => path.replace(REPLACE, ''),
}


export const System = {
    Server: SERVER,
}

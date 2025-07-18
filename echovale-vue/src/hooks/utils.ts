export function debounce(func: Function, delay: number) {
    let timeout: number
    return function () {
        clearTimeout(timeout)
        timeout = setTimeout(func, delay)
    }
}



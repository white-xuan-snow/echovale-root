import {useWindowStore} from "../stores/windowStore.ts";


export const init = () => {
    const windowStore = useWindowStore();
    windowStore.init();
}
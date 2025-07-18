import {defineStore} from "pinia";
import {ref, type Ref, watch} from "vue";

export const useWindowStore = defineStore("windowStore", () => {

    const innerWidth: Ref<number> = ref(0);
    const innerHeight: Ref<number> = ref(0);


    function init() {
        watch(() => window.innerHeight, () => {
            innerWidth.value = window.innerWidth;
        })
        watch(() => window.innerHeight, () => {
            innerHeight.value = window.innerHeight;
        })
    }


    return { innerWidth, innerHeight, init };
})
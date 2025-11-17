import {defineStore} from "pinia";
import {useTheme} from "vuetify/framework";
import {computed} from "vue";


export const useThemeStore = defineStore("themeStore", () => {

    const theme = useTheme();


    const fontThemeColor = computed(() => ({
        color: theme.current.value.colors.background
    }))


    const toggleTheme = () => {
        theme.change
    }


    return {
        theme,
        fontThemeColor,
        toggleTheme,

    }
})


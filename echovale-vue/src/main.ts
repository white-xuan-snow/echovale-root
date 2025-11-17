import { createApp } from 'vue'
import './style.css'
import 'vuetify/dist/vuetify.min.css'
import 'element-plus/dist/index.css'
import { createPinia } from "pinia";
import Vuetify from "./plugins/vuetify";
import ElementPlus from "element-plus";
import { FontAwesomeIcon } from "./plugins/fontawesome.ts";
import 'font-awesome/css/font-awesome.min.css';
import router from "./router/index";

import App from './App.vue'

const app = createApp(App)
const pinia = createPinia()

app.use(Vuetify)
app.use(ElementPlus)
app.use(pinia)
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router)

app.mount("#app")


const windowStore = useWindowStore()
windowStore.init()


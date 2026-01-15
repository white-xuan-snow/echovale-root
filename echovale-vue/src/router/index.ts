import { createRouter, createWebHistory } from 'vue-router'
import Index from '../pages/Index.vue'
import Player from '../pages/Player.vue'
import Login from '../pages/Login.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/home',
      name: 'home',
      component: Index
    },
    {
      path: '/player',
      name: 'player',
      component: Player
    }
  ]
})

export default router

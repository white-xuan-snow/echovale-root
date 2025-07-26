import { createRouter, createWebHistory } from 'vue-router'
import Index from '../pages/Index.vue'
import Player from '../pages/Player.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
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

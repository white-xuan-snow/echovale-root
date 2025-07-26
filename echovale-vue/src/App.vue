<script setup lang="ts">
import Player from "./pages/Player.vue";
import Index from "./pages/Index.vue";


</script>



<template>
  <router-view v-slot="{ Component }">
    <keep-alive include="IndexPage,PlayerPage">
      <component :is="Component" />
    </keep-alive>
  </router-view>
</template>

<style scoped>



</style>

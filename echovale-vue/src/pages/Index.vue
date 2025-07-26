<script setup lang="ts">
import { defineOptions } from 'vue'
defineOptions({
  name: 'IndexPage'
})
import Top from "../components/Top.vue";
import LeftTab from "../components/LeftTab.vue";
import Bottom from "../components/Bottom.vue";
</script>

<template>
  <v-container style="height: 100vh; width: 100vw;">
    <v-row class="top-container">
      <!-- 左边栏 -->
      <v-col class="left-tab">
        <LeftTab />
      </v-col>
      
      <!-- 主要内容 -->
      <v-col class="right-content">
        <!-- 搜索 -->
        <v-row><Top></Top></v-row>
        <!-- 内容 -->
        <v-row>123</v-row>
      </v-col>
    </v-row>
    
    <!-- 底栏 player -->
    <Bottom />
  </v-container>
</template>

<style scoped>
.top-container {
  width: 100vw;
  height: calc(100% - 80px); /* 减去Bottom组件高度 */
}

.left-tab {
  min-width: 200px;
  max-width: 200px;
  padding: 0;
}

.right-content {
  width: calc(100% - 200px);
}



</style>

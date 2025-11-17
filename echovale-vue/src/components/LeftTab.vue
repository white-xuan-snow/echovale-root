<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {
  faHouse,
  faMusic,
  faHeart,
  faClockRotateLeft,
  faBookmark
} from "@fortawesome/free-solid-svg-icons";

// TODO: 预留API获取歌单数据
const playlists = [
  {id: 1, name: "我的最爱", cover: "src/assets/me.jpg"},
  {id: 2, name: "工作音乐", cover: "src/assets/me.jpg"},
  {id: 3, name: "放松时光", cover: "src/assets/me.jpg"}
];



</script>

<template>
  <v-card class="left-tab-container" flat>
    <v-list class="nav-list">
      <!-- 第一部分: 无标题 -->
      <v-list-item>Echovale 回音谷</v-list-item>
      <v-list-item></v-list-item>
      <v-divider></v-divider>
      <v-list-item value="recommend" active-color="primary">
        <template v-slot:prepend>
          <font-awesome-icon :icon="faHouse" />
        </template>
        <v-list-item-title>推荐</v-list-item-title>
      </v-list-item>

      <v-list-item value="featured" active-color="primary">
        <template v-slot:prepend>
          <font-awesome-icon :icon="faMusic" />
        </template>
        <v-list-item-title>精选</v-list-item-title>
      </v-list-item>

      <v-divider class="my-2"></v-divider>

      <!-- 第二部分: 我的 -->
      <v-list-subheader>我的</v-list-subheader>
      
      <v-list-item value="favorites" active-color="primary">
        <template v-slot:prepend>
          <font-awesome-icon :icon="faHeart" />
        </template>
        <v-list-item-title>喜欢</v-list-item-title>
      </v-list-item>

      <v-list-item value="recent" active-color="primary">
        <template v-slot:prepend>
          <font-awesome-icon :icon="faClockRotateLeft" />
        </template>
        <v-list-item-title>最近</v-list-item-title>
      </v-list-item>

      <v-list-item value="collections" active-color="primary">
        <template v-slot:prepend>
          <font-awesome-icon :icon="faBookmark" />
        </template>
        <v-list-item-title>收藏</v-list-item-title>
      </v-list-item>

      <v-divider class="my-2"></v-divider>

      <!-- 第三部分: 创建的歌单 -->
      <v-list-subheader>创建的歌单</v-list-subheader>
      
      <v-list-item 
        v-for="playlist in playlists" 
        :key="playlist.id"
        :value="'playlist-' + playlist.id"
        active-color="primary"
      >
        <template v-slot:prepend>
          <v-img 
            :src="playlist.cover" 
            cover 
            aspect-ratio="1" 
            width="24"
            class="mr-2"
          />
        </template>
        <v-list-item-title>{{ playlist.name }}</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<style scoped>
.left-tab-container {
  position: fixed;
  height: calc(100vh - 80px);
  width: 160px;
  overflow-y: auto;
}

.nav-list {
  padding-top: 0;
}

/* 滚动条样式 */
.left-tab-container::-webkit-scrollbar {
  width: 6px;
}
.left-tab-container::-webkit-scrollbar-thumb {
  background-color: rgba(0,0,0,0.2);
  border-radius: 3px;
}
</style>

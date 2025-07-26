<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {
  faArrowLeft, 
  faMoon, 
  faSearch, 
  faSun,
  faGear
} from "@fortawesome/free-solid-svg-icons";
import {ref, type Ref} from "vue";

// TODO: 从API获取用户数据和主题状态
const isThemeSun: Ref<boolean> = ref(true)
const userInfo = ref({
  avatar: "/src/assets/vue.svg",
  name: "用户名"
})

const toggleTheme = () => {
  isThemeSun.value = !isThemeSun.value
  // TODO: 调用API更新主题偏好
}
</script>

<template>
  <v-container class="top-bar">
    <v-row class="d-flex justify-space-between align-center">
      <!-- 左侧区域 -->
      <v-col class="d-flex align-center">
        <!-- 返回按钮 -->
        <v-btn
          class="back-btn mr-2"
          rounded="md"
          variant="text"
          height="3rem"
          width="3rem"
        >
          <font-awesome-icon :icon="faArrowLeft" size="lg" />
        </v-btn>

        <!-- 搜索框 -->
        <v-text-field
          variant="outlined"
          density="compact"
          placeholder="搜索音乐、歌手、专辑"
          hide-details
          single-line
          class="search-field"
        >
          <template v-slot:prepend-inner>
            <font-awesome-icon
              :icon="faSearch"
              class="search-icon"
            />
          </template>
        </v-text-field>
      </v-col>

      <!-- 右侧功能区 -->
      <v-col class="d-flex justify-end align-center">
        <!-- 主题切换 -->
        <v-btn
          @click="toggleTheme"
          variant="text"
          class="mx-1"
          size="small"
        >
          <transition name="fade" mode="out-in">
            <font-awesome-icon
              v-if="isThemeSun"
              key="themeSun"
              :icon="faSun"
              size="lg"
            />
            <font-awesome-icon
              v-else
              key="themeMoon"
              :icon="faMoon"
              size="lg"
            />
          </transition>
        </v-btn>

        <!-- 设置按钮 -->
        <v-btn
          variant="text"
          class="mx-1"
          size="small"
        >
          <font-awesome-icon :icon="faGear" size="lg" />
        </v-btn>

        <!-- 用户信息 -->
        <v-menu offset-y>
          <template v-slot:activator="{ props }">
            <div
              class="user-info d-flex align-center ml-2"
              v-bind="props"
            >
              <v-avatar size="36" class="mr-2">
                <v-img
                  :src="userInfo.avatar"
                  cover
                />
              </v-avatar>
              <span class="user-name">{{ userInfo.name }}</span>
            </div>
          </template>

          <v-list>
            <v-list-item value="profile">
              <v-list-item-title>个人资料</v-list-item-title>
            </v-list-item>
            <v-list-item value="logout">
              <v-list-item-title>退出登录</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.top-bar {
  padding: 0.5rem 1rem;
}

.search-field {
  min-width: 300px;
  max-width: 500px;
  flex-grow: 1;
}

.back-btn {
  min-width: 0 !important;
}

.search-icon {
  color: rgba(0, 0, 0, 0.6);
}

.user-info {
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 12px;
  transition: background-color 0.2s;
}

.user-info:hover {
  background-color: rgba(0, 0, 0, 0.05);
}

.user-name {
  font-size: 0.875rem;
  font-weight: 500;
  margin-right: 0.5rem;
}
</style>

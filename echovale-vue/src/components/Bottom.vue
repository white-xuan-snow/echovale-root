<script setup lang="ts">
import { usePlayerStore } from '../stores/playerStore'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {
  faPlay,
  faPause,
  faStepBackward,
  faStepForward,
  faHeart,
  faRandom,
  faRedo,
  faListUl,
  faVolumeUp
} from '@fortawesome/free-solid-svg-icons'
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const player = usePlayerStore()
const router = useRouter()

const artistText = computed(() => {
  return player.currentSong.artists.join(', ')
})

const goToPlayer = (e: MouseEvent) => {
  const target = e.target as HTMLElement
  // 排除所有按钮、滑块和控制区域的点击事件
  if (target.closest('button, .v-slider, .right-controls, .player-controls')) {
    return
  }
  router.push({ path: '/player' }).catch(err => {
    console.log('路由跳转失败:', err)
  })
}
</script>

<template>
  <div class="bottom-player" @click="goToPlayer">
    <!-- 顶部进度条 -->
    <div class="progress-bar-container">
      <v-slider
          class="top-progress-bar"
          v-model="player.currentTime"
          :max="player.currentSong.duration"
          @update:modelValue="player.seek"
          hide-details
      />
    </div>

    <div class="player-content">
      <!-- 左侧：歌曲信息 -->
      <div class="song-info d-flex align-center">
        <v-img
            :src="player.currentSong.cover"
            width="56"
            height="56"
            class="song-cover"
            :rounded="8"
        />
        <div class="song-text-wrapper">
          <div class="song-text">
            <span class="song-title">{{ player.currentSong.title }}</span>
            <span class="song-artist"> - {{ artistText }}</span>
          </div>
        </div>
      </div>

      <!-- 中间：播放控制 -->
      <div class="player-controls d-flex align-center justify-center">
        <v-btn variant="text" icon @click.stop="player.changePlayMode">
          <font-awesome-icon
              :icon="player.playMode === 'random' ? faRandom : faRedo"
              :class="{ 'active-mode': player.playMode !== 'order' }"
              size="sm"
          />
        </v-btn>
        <v-btn variant="text" icon @click.stop>
          <font-awesome-icon :icon="faStepBackward" />
        </v-btn>
        <v-btn variant="text" size="large" icon @click.stop="player.togglePlay">
          <font-awesome-icon :icon="player.isPlaying ? faPause : faPlay" size="lg" />
        </v-btn>
        <v-btn variant="text" icon @click.stop>
          <font-awesome-icon :icon="faStepForward" />
        </v-btn>
        <v-btn variant="text" icon @click.stop>
          <font-awesome-icon :icon="faHeart" />
        </v-btn>
      </div>

      <!-- 右侧：功能选项 -->
      <div class="right-controls d-flex align-center justify-end">
        <v-menu offset-y>
          <template v-slot:activator="{ props }">
            <v-btn variant="text" v-bind="props">
              {{ player.currentSong.quality }}
            </v-btn>
          </template>
          <v-list>
            <v-list-item value="standard">
              <v-list-item-title>标准品质</v-list-item-title>
            </v-list-item>
            <v-list-item value="high">
              <v-list-item-title>高品质</v-list-item-title>
            </v-list-item>
            <v-list-item value="lossless">
              <v-list-item-title>无损音质</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <div class="volume-control d-flex align-center">
          <font-awesome-icon :icon="faVolumeUp" />
          <v-slider
              v-model="player.volume"
              @update:modelValue="player.setVolume"
              max="1"
              step="0.01"
              hide-details
              style="min-width: 80px"
          />
        </div>

        <v-btn variant="text" icon @click.stop>
          <font-awesome-icon :icon="faListUl" />
        </v-btn>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bottom-player {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 72px;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-top: 1px solid rgba(0, 0, 0, 0.08);
  z-index: 1000;
  cursor: pointer;
}

/* --- Progress Bar --- */
.progress-bar-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  transform: translateY(-50%);
  z-index: 1;
}

.top-progress-bar {
  --v-slider-track-size: 2px;
  --v-slider-thumb-size: 0;
  transition: all 0.2s ease-in-out;
}

.bottom-player:hover .top-progress-bar {
  --v-slider-track-size: 5px;
  --v-slider-thumb-size: 14px;
}

/* --- Main Layout --- */
.player-content {
  height: 100%;
  padding: 0 20px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.song-info, .right-controls {
  flex-grow: 0; /* Do not grow into the empty space */
  flex-shrink: 1; /* Allow shrinking if the window is too narrow */
  min-width: 0; /* Crucial for flex-shrink to work with text content */
  /* Set a max width to prevent content from ever overlapping the center controls.
     We estimate the center controls to be about 300px wide. */
  max-width: calc(50% - 150px);
}

.player-controls {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  flex-shrink: 0; /* Prevent center controls from being squeezed */
}

/* --- Component Styling --- */
.song-cover {
  margin-right: 16px;
  flex-shrink: 0;
  border-radius: 8px;
  overflow: hidden;
}

/* Marquee Effect */
.song-text-wrapper {
  overflow: hidden;
  white-space: nowrap;
}

.song-text {
  display: inline-block;
  animation: marquee 12s linear infinite;
  /* Scroll by default */
  animation-play-state: running;
}

/* Pause on hover */
.song-text-wrapper:hover .song-text {
  animation-play-state: paused;
}

.song-title {
  font-weight: 500;
}

.song-artist {
  font-size: 0.8rem;
  color: #555;
  margin-left: 4px;
}

.volume-control {
  width: 120px;
  gap: 8px;
}

.active-mode {
  color: rgb(var(--v-theme-primary));
}

@keyframes marquee {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}
</style>

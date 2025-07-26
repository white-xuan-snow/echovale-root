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

const progress = computed(() => {
  return (player.currentTime / player.currentSong.duration) * 100
})

const goToPlayer = (e: MouseEvent) => {
  const target = e.target as HTMLElement
  // 排除所有按钮和控制元素
  if (target.closest('button, .play-controls, .right-controls, .progress-bar')) {
    return
  }
  router.push({ path: '/player' }).catch(err => {
    console.log('路由跳转失败:', err)
  })
}
</script>

<template>
  <div class="bottom-player elevation-4" @click="goToPlayer">
    <div class="player-content">
      <!-- 歌曲信息 -->
      <div class="song-info">
        <v-img
          :src="player.currentSong.cover"
          width="48"
          height="48"
          class="song-cover"
        />
        <div class="song-text">
          <div class="song-title">{{ player.currentSong.title }}</div>
          <div class="song-artist">{{ artistText }}</div>
        </div>
      </div>

      <!-- 播放控制 -->
      <div class="player-controls">
        <div class="play-controls">
          <v-btn variant="text" @click.stop="player.changePlayMode">
            <font-awesome-icon 
              :icon="player.playMode === 'random' ? faRandom : faRedo" 
              :class="{ 'active-mode': player.playMode !== 'order' }"
            />
          </v-btn>
          <v-btn variant="text" @click.stop>
            <font-awesome-icon :icon="faStepBackward" />
          </v-btn>
          <v-btn variant="text" @click.stop="player.togglePlay">
            <font-awesome-icon :icon="player.isPlaying ? faPause : faPlay" size="lg" />
          </v-btn>
          <v-btn variant="text" @click.stop>
            <font-awesome-icon :icon="faStepForward" />
          </v-btn>
          <v-btn variant="text" @click.stop>
            <font-awesome-icon :icon="faHeart" />
          </v-btn>
        </div>
        <div class="progress-bar">
          <v-slider
            v-model="player.currentTime"
            :max="player.currentSong.duration"
            @update:modelValue="player.seek"
            hide-details
          />
        </div>
      </div>

      <!-- 右侧控制 -->
      <div class="right-controls">
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

        <div class="volume-control">
          <font-awesome-icon :icon="faVolumeUp" />
          <v-slider
            v-model="player.volume"
            @update:modelValue="player.setVolume"
            max="1"
            step="0.01"
            hide-details
          />
        </div>

        <v-btn variant="text" @click.stop>
          <font-awesome-icon :icon="faListUl" />
        </v-btn>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bottom-player {
  position: fixed;
  bottom: 10px;
  left: 10px;
  right: 0;
  height: 80px;
  width: calc(100% - 20px);
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  z-index: 100;
  cursor: pointer;
  transition: all 0.3s ease;
}

.bottom-player:hover {
  background-color: rgba(230,230,250, 0.5);
}

.player-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.song-info {
  display: flex;
  align-items: center;
  min-width: 200px;
}

.song-cover {
  border-radius: 4px;
  margin-right: 12px;
}

.song-text {
  overflow: hidden;
}

.song-title {
  font-weight: 500;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.song-artist {
  font-size: 12px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.player-controls {
  flex: 1;
  max-width: 500px;
  padding: 0 20px;
}

.mode-controls {
  text-align: center;
  margin-bottom: 4px;
}

.play-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
  height: 100%;
  padding: 10px 0 0 0;
}

.progress-bar {
}

.right-controls {
  display: flex;
  align-items: center;
  min-width: 200px;
  justify-content: flex-end;
  gap: 16px;
}

.volume-control {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 120px;
}

.active-mode {
  color: var(--v-primary);
}
</style>

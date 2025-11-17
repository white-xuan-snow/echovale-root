<script setup lang="ts">
import { defineOptions } from 'vue'
defineOptions({
  name: 'PlayerPage'
})

import {computed, nextTick, onMounted, onUnmounted, ref, type Ref, watch} from "vue";
import {EplorRenderer, type LyricLine, type LyricLineMouseEvent} from "@applemusic-like-lyrics/core";

interface SpringParams {
  mass: number
  stiffness: number
  damping: number
  soft: boolean
}
// import {debounce} from "../hooks/utils.ts";
import {debounce} from "lodash"
import {LyricPlayer} from "@applemusic-like-lyrics/core";
import {meTTML, ttml, yrc} from "../constant/testResource.ts";
import {parseTTML} from "@applemusic-like-lyrics/lyric";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {usePlayerStore} from "../stores/playerStore";
import {useRouter} from "vue-router";
import {
  faBackward,
  faForward,
  faHeart,
  faPause,
  faPlay,
  faRepeat, faVolumeHigh,
  faVolumeLow, faXmark
} from "@fortawesome/free-solid-svg-icons";
import {useThemeStore} from "../stores/themeStore.ts";

const themeStore = useThemeStore()

const fontThemeColor = themeStore.fontThemeColor

// theme.global.name.value = 'dark'




function setCanvasSize(canvas: HTMLCanvasElement) {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  console.log("[Player].[setCanvasSize]  width:", canvas.width);
  console.log("[Player].[setCanvasSize] height:", canvas.height);
}

  let player: LyricPlayer
  let eplorRender: EplorRenderer
  const playerStore = usePlayerStore()
  let music = playerStore.getAudioPlayer()

  // 背景帧率
  const fps = 60




let currentTime: Ref<number> = ref(0)
let duration: Ref<number> = ref(0)
let volume: Ref<number> = ref(0)



function loadEplorRender() {
  // 图片
  const album = 'src/assets/me.jpg'
  // 流体速率
  const flowSpeed = 10
  // 明度
  const brightness = 200
  // 对比度
  const contrast = 200
  // 渲染精度
  const renderScale = 0.8

  // 流体背景canvas容器
  const eplorCanvas = document.getElementById("eplor-canvas") as HTMLCanvasElement;
  // 设置容器大小
  setCanvasSize(eplorCanvas)
  // 监听并改变容器大小
  window.addEventListener("resize", debounce(() => setCanvasSize(eplorCanvas), 250))

  // 设置明度
  eplorCanvas.style.filter = `brightness(${brightness}%)`
  // 设置对比度
  eplorCanvas.style.filter = `contrast(${contrast}%)`

  // 创建流体背景对象
  eplorRender = new EplorRenderer(eplorCanvas);

  // 设置图片
  eplorRender.setAlbum(album)
  // 设置fps
  eplorRender.setFPS(fps)
  // 设置流体速率
  eplorRender.setFlowSpeed(flowSpeed)
  // 设置渲染精度
  eplorRender.setRenderScale(renderScale)

  console.log("[Player].[loadEplorRender] Loaded EplorRender.")
}

function loadLyricPlayer() {
  // const lines = parseLrc(lrc)
  // const lines = parseTTML(ttml)
  // const lines = parseYrc(yrc)
  const lines = parseTTML(meTTML)
  // const lyricLines = convertLyricLine(lines)

  player = new LyricPlayer()
  player.setLyricLines(lines.lines)
  player.setEnableBlur(true)
  player.setEnableScale(true)
  player.setAlignPosition(0.3)
  player.setEnableSpring(true)
  player.setAlignAnchor("center")
  player.setCurrentTime(0)

  const springParamsX: SpringParams = {
    mass: 5,
    stiffness: 500,
    damping: 70,
    soft: false
  }
  const springParamsY: SpringParams = {
    mass: 1,
    stiffness: 100,
    damping: 1,
    soft: true
  }
  const springParamsScale: SpringParams = {
    mass: 0.5,
    stiffness: 50,
    damping: 10,
    soft: true
  }

  player.setLinePosYSpringParams(springParamsX)
  player.setLinePosXSpringParams(springParamsY)
  player.setLineScaleSpringParams(springParamsScale)
  player.addEventListener('line-click', (evt) => redirectLyricPosition(evt))
  player.addEventListener('line-touched', (evt) => redirectLyricPosition(evt))

  // 根据当前模式动态附加歌词播放器到正确的容器
  updateLyricPlayerContainer()

  setLyricPlayerAnimation(fps)
  loadMusic()
}

// 根据当前布局模式更新歌词播放器容器
function updateLyricPlayerContainer() {
  if (!player) {
    console.error("[Player].[updateLyricPlayerContainer] 歌词播放器未初始化")
    return
  }

  const playerElement = player.getElement()

  // 从当前容器中移除播放器元素
  if (playerElement.parentNode) {
    playerElement.parentNode.removeChild(playerElement)
  }

  // 根据当前模式选择正确的容器
  const containerId = isPortraitMode.value ? "lyric-player-portrait" : "lyric-player"

  // 使用更健壮的容器查找方法
  const findContainer = (): HTMLDivElement | null => {
    // 尝试多种选择器方法
    let container = document.getElementById(containerId) as HTMLDivElement
    if (!container) {
      container = document.querySelector(`#${containerId}`) as HTMLDivElement
    }
    if (!container) {
      container = document.querySelector(`[id="${containerId}"]`) as HTMLDivElement
    }
    return container
  }

  let lyricPlayerContainer = findContainer()

  // 如果第一次没找到，尝试多次查找
  if (!lyricPlayerContainer) {
    let attempts = 0
    const maxAttempts = 5

    const tryFindContainer = () => {
      attempts++
      lyricPlayerContainer = findContainer()

      if (lyricPlayerContainer) {
        lyricPlayerContainer.appendChild(playerElement)
        console.log("[Player].[updateLyricPlayerContainer] 歌词播放器已附加到容器:", containerId, "尝试次数:", attempts)
      } else if (attempts < maxAttempts) {
        // 继续尝试
        setTimeout(tryFindContainer, 50)
      } else {
        console.error("[Player].[updateLyricPlayerContainer] 最终找不到容器:", containerId, "尝试次数:", attempts)
        // 作为备用方案，尝试附加到body或创建新容器
        console.warn("[Player].[updateLyricPlayerContainer] 使用备用方案")
        const fallbackContainer = document.createElement('div')
        fallbackContainer.id = containerId + '-fallback'
        fallbackContainer.className = 'lyric-player'
        fallbackContainer.style.cssText = 'position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 1;'
        document.body.appendChild(fallbackContainer)
        fallbackContainer.appendChild(playerElement)
      }
    }

    setTimeout(tryFindContainer, 50)
    return
  }

  if (lyricPlayerContainer) {
    lyricPlayerContainer.appendChild(playerElement)
    console.log("[Player].[updateLyricPlayerContainer] 歌词播放器已附加到容器:", containerId)
  } else {
    console.error("[Player].[updateLyricPlayerContainer] 找不到容器:", containerId)
  }
}



function redirectLyricPosition(evt: Event) {
  const e = evt as LyricLineMouseEvent
  evt.preventDefault()
  evt.stopImmediatePropagation()
  evt.stopPropagation()
  console.log("[Player].[loadLyricPlayer].[line-click Listener]", e.line, e.lineIndex)
  music.currentTime = e.line.getLine().startTime / 1000
  player.resetScroll()
  player.calcLayout(false, true)
  playerStore.isPlaying = true
  music.play()
  player.resume()
}

let interval: number = 0

let minNum: Ref<number> = ref(0)
let secNum: Ref<number> = ref(0)
let minStr: Ref<string> = ref('')
let secStr: Ref<string> = ref('')

let progress: Ref<string> = ref('')


function setLyricPlayerAnimation(fps: number) {
  // 初始化
  let timeout = 1000 / fps
  if (interval !== 0) clearInterval(interval)
  interval = setInterval(() => {
    if (isMusicProgressUpdate.value) return
    if (!playerStore.isPlaying) player.pause()
    duration.value = music.duration
    currentTime.value = music.currentTime * 1000
    player.setCurrentTime(currentTime.value)
    player.update(timeout)
    // console.log("[Player].[setLyricPlayerAnimation]", currentTime.value)

    // 计算位数
    const sec = currentTime.value / 1000
    minNum.value = Math.floor(sec / 60)
    minStr.value = minNum.value.toString().padStart(2, '0')
    secNum.value = Math.floor(sec % 60)
    secStr.value = secNum.value.toString().padStart(2, '0')
    progress.value = (sec / duration.value * 100).toFixed(2)
    // console.log("[Player].[setLyricPlayerAnimation] progress: ", progress.value)


  }, timeout)
}



function convertLyricLine(lines: LyricLine[]) {
  return lines.map((line, i, lines) => ({
    words: [
      {
        word: line.words[0]?.word ?? "",
        startTime: line.words[0]?.startTime ?? 0,
        endTime: lines[i + 1]?.words?.[0]?.startTime ?? Infinity,
      },
    ],
    startTime: line.words[0]?.startTime ?? 0,
    endTime: lines[i + 1]?.words?.[0]?.startTime ?? Infinity,
    translatedLyric: "",
    romanLyric: "",
    isBG: false,
    isDuet: false,
  }));
}

  function loadMusic() {
    watchMusicVolume()
  }

  // TODO Web Audio API 丝滑过渡防跳音
  function watchMusicVolume() {
    console.log("[Player].[loadMusic] volume", playerStore.volume)
    playerStore.setVolume(playerStore.volume)
    volume.value = playerStore.volume * 100
    watch(volume, (newValue) => {
      playerStore.setVolume(newValue / 100)
      console.log("[Player].[watchMusicVolume]", newValue / 100)
    })
  }

let isMusicProgressUpdate: Ref<boolean> = ref(false)
let lastMusicTime: Ref<number> = ref(0)
const updateMusicProgress = () => {
  isMusicProgressUpdate.value = true
  const updateProgress = Number.parseFloat(progress.value)
  const updateTime = Math.floor(updateProgress / 100 * duration.value)
  if (Math.abs(updateTime - lastMusicTime.value) < 1) {
    lastMusicTime.value = updateTime
    isMusicProgressUpdate.value = false
    return
  }
  music.currentTime = updateTime
  lastMusicTime.value = updateTime
  isMusicProgressUpdate.value = false
  console.log("[Player].[updateMusicProgress]", progress.value)
}

const router = useRouter()
const closeMusicPlayer = () => {
  router.back()
}

const progressSliderTrackSize: Ref<number> = ref(10)
const volumeSliderTrackSize: Ref<number> = ref(10)

// 响应式布局相关状态
const isPortraitMode: Ref<boolean> = ref(false)
const windowWidth: Ref<number> = ref(window.innerWidth)
const windowHeight: Ref<number> = ref(window.innerHeight)

// 监听窗口尺寸变化
const handleResize = debounce(() => {
  const oldMode = isPortraitMode.value
  windowWidth.value = window.innerWidth
  windowHeight.value = window.innerHeight
  // 修改竖屏激活比例为长/宽=0.8（即高度/宽度 > 1.25）
  isPortraitMode.value = windowHeight.value / windowWidth.value > 1.25

  // 如果布局模式发生变化，更新歌词播放器容器
  if (oldMode !== isPortraitMode.value && player) {
    updateLyricPlayerContainer()
  }

  console.log("[Player].[handleResize] 窗口尺寸:", windowWidth.value, "x", windowHeight.value, "竖屏模式:", isPortraitMode.value, "比例:", (windowHeight.value / windowWidth.value).toFixed(2))
}, 100)

// 计算竖屏模式下的图片宽度（占15%）
const portraitImageWidth = computed(() => {
  return windowWidth.value * 0.15
})

const togglePlay = () => {
  console.log("[Player].[togglePlay]", playerStore.isPlaying)

  playerStore.isPlaying = !playerStore.isPlaying
  if (playerStore.isPlaying) {
    music.play()
    player.resume()
  }
  else {
    music.pause()
    player.pause()
  }
}


onMounted(async () => {
  console.log("[Player].[onMounted]")

  // 初始化窗口尺寸检测
  handleResize()
  window.addEventListener('resize', handleResize)

  // 加载流体背景
  loadEplorRender()

  // 等待DOM完全渲染后再加载歌词播放器
  await nextTick()

  // 加载歌词播放器
  loadLyricPlayer()



})

onUnmounted(() => {
  // 移除窗口监听器
  window.removeEventListener('resize', handleResize)
  eplorRender.dispose()
  player.dispose()
  console.log("[Player].[onUnmounted]")
})


</script>

<template>

  <transition name="player-mask" appear>
    <div class="player-mask">
      <canvas id="eplor-canvas" style="z-index: -2; left: 0; top: 0; position: fixed; pointer-events: none; width: 100vw; height: 100vh;" />

      <!-- 横屏模式（默认） -->
      <v-container v-if="!isPortraitMode" class="pa-0 d-flex align-center" style="width: 100vw; height: 100vh; z-index: 0;">
        <v-container class="player-font" :style="fontThemeColor" fluid style="height: 100%">
          <v-row no-gutters class="d-flex justify-space-evenly" style="width: 100vw; height: 100%">
            <v-col cols="5" style="max-width: 54vh; min-width: 30vw;">
              <v-container class="d-flex flex-column justify-space-between" style="max-width: 36vw; min-width: 30vw; min-height: 90vh; max-height: 98vh;">
                <!-- 返回按钮 -->
                <v-row>
                  <v-container class="d-flex justify-center align-center" style="padding-top: 0;">
                    <div class="player-back-btn d-flex justify-center align-center"
                         @click="closeMusicPlayer">
                      <font-awesome-icon :icon="faXmark" />
                    </div>
                  </v-container>
                </v-row>
                <v-row>
                  <v-container class="pa-0 d-flex justify-center">
                    <v-img aspect-ratio="1" max-width="54vh" cover class="img-display" src="src/assets/me.jpg"></v-img>
                  </v-container>
                </v-row>
                <v-row style="flex: 1;">
                  <v-container style="padding: 1rem 0; max-width: 54vh;">
                    <v-card class="music-info" color="transparent" >
                      <v-card-title class="pa-0">ME!</v-card-title>
                      <v-card-subtitle class="pa-0">Taylor Swift, Brendon Urie</v-card-subtitle>
                    </v-card>
                  </v-container>
                </v-row>
                <v-row>
                  <v-container class="pa-0" style="max-width: 54vh;">
                    <v-slider :class="'ma-0' "
                              track-color="rgba(220,220,220,.5)"
                              track-fill-color="rgba(245,245,245,.5)"
                              :track-size="progressSliderTrackSize"
                              v-model="progress"
                              @update:modelValue="updateMusicProgress"
                              @mouseenter="progressSliderTrackSize = 16"
                              @mouseleave="progressSliderTrackSize = 10"
                              hide-details />
                  </v-container>
                </v-row>
                <v-row>
                  <v-container class="progress-number pa-0" style="max-width: 54vh;">
                    <span>{{ minStr }}:{{ secStr }}</span>
                    <span></span>
                    <span>{{duration}}</span>
                  </v-container>
                </v-row>
                <v-row>
                  <v-container style="max-width: 54vh;">
                    <v-row style="display: flex; flex-direction: row; justify-content: space-between;">
                      <v-col class="pa-0" cols="2" style="display: flex; justify-content: start; align-self: center;"><font-awesome-icon size="lg" :icon="faRepeat" /></v-col>
                      <v-col cols="2"><font-awesome-icon size="2x" :icon="faBackward" /></v-col>
                      <transition name="fade" mode="out-in">
                        <v-col cols="4" @click="togglePlay">
                          <font-awesome-icon size="2x" v-if="playerStore.isPlaying" key="pause" :icon="faPause" />
                          <font-awesome-icon size="2x" v-else key="play" :icon="faPlay" />
                        </v-col>
                      </transition>
                      <v-col cols="2"><font-awesome-icon size="2x" :icon="faForward" /></v-col>
                      <transition name="fade" mode="out-in">
                        <v-col class="pa-0" cols="2" style="display: flex; justify-content: end; align-self: center;">
                          <font-awesome-icon size="lg" :icon="faHeart" />
                        </v-col>
                      </transition>
                    </v-row>
                  </v-container>
                </v-row>
                <v-row>
                  <v-col class="pa-0 flex-row-start" cols="1" style="max-width: 54vh;"><font-awesome-icon :icon="faVolumeLow" /></v-col>
                  <v-col class="d-flex align-center" style="padding-left: 0; max-width: 54vh;" cols="10">
                    <v-slider class="ma-0 volume-slider" style="height: 2rem;"
                              :track-size="volumeSliderTrackSize"
                              track-color="rgba(220,220,220,.5)"
                              track-fill-color="rgba(245,245,245,.5)"
                              :thumb-label="false"
                              @mouseenter="volumeSliderTrackSize = 16"
                              @mouseleave="volumeSliderTrackSize = 10"
                              v-model="volume"
                              hide-details />
                  </v-col>
                  <v-col class="pa-0 flex-row-end" cols="1" style="max-width: 54vh;"><font-awesome-icon :icon="faVolumeHigh" /></v-col>
                </v-row>

              </v-container>
            </v-col>
            <v-col cols="7" class="d-flex align-center justify-center" style="height: 100%;">
              <v-container class="lyric-player d-flex align-center justify-center" id="lyric-player" :style="fontThemeColor"></v-container>
            </v-col>
          </v-row>
        </v-container>
      </v-container>

      <!-- 竖屏模式 -->
      <v-container v-else class="pa-0 d-flex flex-column" style="width: 100vw; height: 100vh; z-index: 0;">
        <v-container class="player-font" :style="fontThemeColor">
          <!-- 返回按钮 -->
          <v-row>
            <v-container class="d-flex justify-center align-center" style="padding-top: 2rem;">
              <div class="player-back-btn d-flex justify-center align-center"
                   @click="closeMusicPlayer">
                <font-awesome-icon :icon="faXmark" />
              </div>
            </v-container>
          </v-row>

          <!-- 图片和歌名区域 -->
          <v-row class="d-flex justify-start" style="padding: 1rem 1rem 0.5rem 1rem;">
            <!-- 图片（占15%） -->
            <v-col cols="4" class="pa-0 d-flex justify-start">
              <v-img
                aspect-ratio="1"
                :width="portraitImageWidth"
                cover
                class="img-display portrait-img"
                style="z-index: 10;"
                src="src/assets/me.jpg">
              </v-img>
            </v-col>

            <!-- 歌名和歌手信息（上下布局） -->
            <v-col cols="8" class="pa-0 d-flex flex-column justify-center pl-10" style="padding-bottom: 0.5rem;">
              <v-card class="music-info portrait-music-info" color="transparent">
                <v-card-title class="pa-0 portrait-title">ME!</v-card-title>
                <v-card-subtitle class="pa-0 portrait-subtitle">Taylor Swift, Brendon Urie</v-card-subtitle>
              </v-card>
            </v-col>
          </v-row>

          <!-- 歌词区域 -->
          <v-row style="flex: 1 1 auto; min-height: 0; margin-top: 0; padding: 0 1rem;">
            <v-col cols="12" class="pa-0 d-flex align-start justify-start" style="padding-left: calc(25% + 1rem);">
              <div class="lyric-player portrait-lyric-player d-flex align-start justify-start"
                   id="lyric-player-portrait"
                   :style="fontThemeColor">
              </div>
            </v-col>
          </v-row>
        </v-container>
      </v-container>
    </div>
  </transition>
</template>





/* Player.vue 样式部分修改 */
<style scoped>

/* Player.vue 的 <style scoped> 块 */

.player-back-btn {
  /* 基础样式 (确保 transition 属性存在，实现平滑过渡) */
  /* 假设你当前的基础样式是： */
  width: 40px; /* 默认宽度 */
  height: 40px; /* 默认高度 */
  border-radius: 50%; /* 默认圆形 */
  /* 其他样式（如背景色、阴影等...）*/

  /* **关键：添加过渡效果** */
  transition: all 0.3s ease-in-out; /* 让 width, height, border-radius 等属性在 0.3s 内平滑变化 */
}


.player-back-btn {
  position: fixed;
  width: 70px; /* 宽度变长 (从小圆变为长条) */
  height: 8px; /* 高度变窄 (变为细条) */
  border-radius: 4px; /* 保持圆润 (小横条的圆角) */
  background-color: rgba(200,200,200,.5);
  transition: all 0.3s ease-in-out; /* 让 width, height, border-radius 等属性在 0.3s 内平滑变化 */
}
.player-back-btn > svg {
  opacity: 0;
  transform: scale(0);
  transition: opacity 0.4s ease-in-out;
}

.player-back-btn:hover {
  /* 悬停时的样式：变为圆润的小横条 */
  width: 2rem;
  height: 1.5rem;
  border-radius: 8px;

  /* 提示：如果你的图标是可见的，你可能还需要在 hover 时隐藏图标： */
}
.player-back-btn:hover > svg {
  opacity: 1;
  transform: scale(1);
}


.player-mask {
  position: fixed;
  left: 0;
  top: 0;
  z-index: 9999;
}

/* 打开动画 - 快速进入并强阻尼停止 */
.player-mask-enter-active {
  animation: slideInUp 0.6s cubic-bezier(0.2, 0.9, 0.2, 1) both;
}

/* 关闭动画 - 快速退出并强阻尼停止 */
.player-mask-leave-active {
  animation: slideOutDown 0.6s cubic-bezier(0.2, 0.9, 0.2, 1) both;
}

@keyframes slideInUp {
  0% {
    opacity: 0;
    transform: translateY(100vh);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideOutDown {
  0% {
    opacity: 1;
    transform: translateY(0);
  }
  100% {
    opacity: 0;
    transform: translateY(100vh);
  }
}
</style>




<style scoped>

  .lyric-player {
    height: 80vh;
    position: relative;
    z-index: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    min-height: 400px; /* 确保最小高度 */
    overflow: hidden; /* 防止内容溢出 */
  }

  .player-font * {
    font-family: "PingFang SC";
    font-weight: bold;
    /* text-shadow: 2px 2px 20px rgba(0, 0, 0, 0.5); */
  }




  .img-display {
    border-radius: 12px;
    box-shadow: 0 0 24px rgb(100,100,100);
  }

  .music-info {
    text-align: left;
    box-shadow: 0 0;
  }
  .progress-number {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }


  .flex-row-start {
    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }
  .flex-row-center {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .flex-row-end {
    display: flex;
    flex-direction: row;
    justify-content: end;
    align-items: center;
  }

  .flex-column-evenly {
    width: 36vw;
    height: 40vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: start;
  }

  /* 竖屏模式专用样式 */
  .portrait-img {
    border-radius: 8px;
    box-shadow: 0 0 12px rgba(100, 100, 100, 0.3);
  }

  .portrait-music-info {
    text-align: left;
  }

  .portrait-title {
    font-size: 1.5rem !important;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }

  .portrait-subtitle {
    font-size: 1rem !important;
    opacity: 0.8;
  }

  .portrait-lyric-player {
    height: calc(100vh - 180px);
    min-height: 320px;
    width: 100%;
    padding: 0;
    margin: 0;
    position: relative;
    overflow: hidden;
    display: flex;
    align-items: flex-start;
    justify-content: flex-start;
  }

  /* 竖屏模式下的响应式调整 */
  @media (max-width: 480px) {
    .portrait-title {
      font-size: 1.3rem !important;
    }

    .portrait-subtitle {
      font-size: 0.9rem !important;
    }

    .portrait-lyric-player {
      height: calc(100vh - 160px);
      min-height: 280px;
    }
  }

/* 确保歌词区域与图片左侧对齐 */
.portrait-lyric-container {
  width: 100%;
  margin-left: 0;
  padding-left: 0;
}

/* 强制设置 lyricPlayer-0-1-1 类的 padding-left 为 0 */
.lyricPlayer-0-1-1 {
  padding-left: 0 !important;
}

/* 确保歌曲名称样式不被覆盖 */
.portrait-title {
  font-size: 1.5rem !important;
  font-weight: bold !important;
  margin-bottom: 0.5rem !important;
  padding-left: 0 !important;
  margin-left: 0 !important;
}

.portrait-subtitle {
  font-size: 1rem !important;
  opacity: 0.8 !important;
  padding-left: 0 !important;
  margin-left: 0 !important;
}

#lyric-player :deep(.lyricLine-0-1-2) {
  letter-spacing: -0.05rem;
  font-weight: bolder;
  font-size: xxx-large;
  overflow: visible;
  padding-bottom: 1rem;
}





#lyric-player :deep(.lyricBgLine-0-1-4) {
  font-size: x-large !important;
}

#lyric-player-portrait :deep(.lyricLine-0-1-2) {
  letter-spacing: -0.05rem;
  line-height: 1.1;
  font-weight: bolder;
}

#lyric-player-portrait :deep(.lyricBgLine-0-1-4) {
  font-size: x-large;
}

/* 强制竖屏模式下歌词播放器容器的样式 */
#lyric-player-portrait :deep(.lyricPlayer-0-1-1) {
  padding: 0;
  width: 95vw !important;
}

/* 确保歌词行在竖屏模式下正确对齐 */
#lyric-player-portrait [class*="lyricLine-"] {
  text-align: left !important;
  padding-left: 0 !important;
  margin-left: 0 !important;
}





</style>

<style>
.v-slider-thumb {
  display: none;
}

[class*="lyricLine-0-1-"] * {
  text-align: left;
}

[class*="lyricDuetLine-0-1-"] * {
  text-align: right;
}

body::-webkit-scrollbar {
  width: 0;
  height: 0;
  background: transparent;
}



</style>

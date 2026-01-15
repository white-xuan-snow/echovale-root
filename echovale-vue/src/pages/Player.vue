<script setup lang="ts">
defineOptions({
  name: 'PlayerPage'
})

import {computed, nextTick, onMounted, onUnmounted, ref, type Ref, watch} from "vue";
import "@applemusic-like-lyrics/core/style.css"; // Import required styles

interface SpringParams {
  mass: number
  stiffness: number
  damping: number
  soft: boolean
}




const fps = 120
const lowFreqVolumeTimeout = 200

// 流体速率
const flowSpeed = 5
// 明度
const brightness = 200
// 对比度
const contrast = 200
// 渲染精度
const renderScale = 0.8






// import {debounce} from "../hooks/utils.ts";
import {debounce} from "lodash"
import { LyricLineMouseEvent, type LyricLine } from "@applemusic-like-lyrics/core";
import {meTTML, ttml, yrc} from "../constant/testResource.ts";
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


import {LyricPlayer} from "@applemusic-like-lyrics/core";
import {MeshGradientRenderer} from "@applemusic-like-lyrics/core";
import {parseTTML} from "@applemusic-like-lyrics/lyric";


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
  let meshRender: MeshGradientRenderer
  const playerStore = usePlayerStore()
  let music = playerStore.getAudioPlayer()

  // 背景帧率




let currentTime: Ref<number> = ref(0)
let duration: Ref<number> = ref(0)
let volume: Ref<number> = ref(0)

// Web Audio API related variables
let audioContext: AudioContext | null = null;
let analyser: AnalyserNode | null = null;
let source: MediaElementAudioSourceNode | null = null;

let renderInterval = 0;


function loadEplorRender() {
  // 图片
  const album = 'src/assets/me.jpg'

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
  meshRender = new MeshGradientRenderer(eplorCanvas);

  // 设置图片
  meshRender.setAlbum(album)
  // 设置fps
  meshRender.setFPS(fps)


  // 设置流体速率
  meshRender.setFlowSpeed(flowSpeed)
  // 设置渲染精度
  meshRender.setRenderScale(renderScale)

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


  player.setWordFadeWidth(1)



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
  player.calcLayout(false)
  playerStore.isPlaying = true
  music.play()
  player.resume()
}

let interval: number = 0

let minNum: Ref<number> = ref(0)
let secNum: Ref<number> = ref(0)
let minStr: Ref<string> = ref('')
let secStr: Ref<string> = ref('')
let remainingMinStr: Ref<string> = ref('00')
let remainingSecStr: Ref<string> = ref('00')

let progress: Ref<string> = ref('')


function setLyricPlayerAnimation(fps: number = 60) {
  // 初始化
  let lyricPlayerAnimationTimeout = 1000 / fps
  if (interval !== 0) clearInterval(interval)
  interval = setInterval(() => {
    if (isMusicProgressUpdate.value) return
    if (!playerStore.isPlaying) player.pause()
    duration.value = music.duration
    currentTime.value = music.currentTime * 1000
    player.setCurrentTime(currentTime.value)
    player.update(lyricPlayerAnimationTimeout)
    // console.log("[Player].[setLyricPlayerAnimation]", currentTime.value)

    // 计算位数
    const sec = currentTime.value / 1000
    minNum.value = Math.floor(sec / 60)
    minStr.value = minNum.value.toString().padStart(2, '0')
    secNum.value = Math.floor(sec % 60)
    secStr.value = secNum.value.toString().padStart(2, '0')
    progress.value = (sec / duration.value * 100).toFixed(2)
    // console.log("[Player].[setLyricPlayerAnimation] progress: ", progress.value)

    // 计算剩余时间
    if (duration.value) {
      const remainingTotalSeconds = Math.floor(duration.value - sec)
      if (remainingTotalSeconds >= 0) {
        const remainingMinutes = Math.floor(remainingTotalSeconds / 60)
        const remainingSeconds = remainingTotalSeconds % 60
        remainingMinStr.value = remainingMinutes.toString().padStart(2, '0')
        remainingSecStr.value = remainingSeconds.toString().padStart(2, '0')
      }
    }
  }, lyricPlayerAnimationTimeout)
}



// 在循环外部定义持久变量
let currentDisplayVolume = 0; // 用于渲染的平滑值
const DECAY_RATE = 0.08;      // 下降速度（数值越大下降越快）
const BEAT_THRESHOLD = 0.55;  // 触发阈值 (0.0 - 1.0)，可根据曲风微调

function analyzeBeat(analyser, audioContext) {
  if (!analyser || !audioContext) return;

  const bufferLength = analyser.frequencyBinCount;
  const dataArray = new Uint8Array(bufferLength);
  analyser.getByteFrequencyData(dataArray);

  // 1. 扩大频段检测范围 (40Hz - 180Hz) 覆盖大鼓和部分军鼓
  const sampleRate = audioContext.sampleRate;
  const freqPerBin = sampleRate / analyser.fftSize;
  const startIndex = Math.floor(40 / freqPerBin);
  const endIndex = Math.ceil(180 / freqPerBin);

  // 2. 获取该频段的最大能量点
  let maxRaw = 0;
  for (let i = startIndex; i <= endIndex; i++) {
    if (dataArray[i] > maxRaw) maxRaw = dataArray[i];
  }

  // 归一化当前能量 (0.0 - 1.0)
  const normEnergy = maxRaw / 255;

  // 3. 核心逻辑：阈值判断与非线性映射
  if (normEnergy > BEAT_THRESHOLD) {
    // --- 【大鼓点触发】 ---
    // 只要超过阈值，立即向 0.8 以上抬升
    // 使用快速插值，让它在 1-2 帧内跳到高位
    const targetHigh = 0.8 + (normEnergy - BEAT_THRESHOLD) * 0.4; // 动态高位，最高约 1.0
    currentDisplayVolume = targetHigh;
  } else {
    // --- 【小音量/回弹阶段】 ---
    // 如果小于阈值，目标是向 0.2 以下降
    const targetLow = normEnergy * 0.3; // 缩减小鼓点影响，使其保持在 0.2 以下

    // 缓慢回弹：当前值向低位目标靠近
    if (currentDisplayVolume > targetLow) {
      currentDisplayVolume -= DECAY_RATE; // 线性下降，产生“余震”感
      if (currentDisplayVolume < targetLow) currentDisplayVolume = targetLow;
    } else {
      currentDisplayVolume = targetLow;
    }
  }

  // 4. 最终限制在合法范围
  const finalValue = Math.max(0, Math.min(1, currentDisplayVolume));

  // 5. 传给渲染器
  meshRender.setLowFreqVolume(finalValue);

  console.log('meshRender.setLowFreqVolume:', finalValue)

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

    music.autoplay = true
    music.loop = true
  }

  function initAudioAnalyzer() {
    if (audioContext) {
      return;
    }
    try {
      audioContext = new AudioContext();
      source = audioContext.createMediaElementSource(music);
      analyser = audioContext.createAnalyser();

      // Configure the analyser
      analyser.fftSize = 2048; // A common size for detailed frequency analysis
      analyser.minDecibels = -90;
      analyser.maxDecibels = -10;
      analyser.smoothingTimeConstant = 0.85;

      // Connect the nodes: music -> source -> analyser -> speakers
      source.connect(analyser);
      analyser.connect(audioContext.destination);
      console.log("[Player].[initAudioAnalyzer] Web Audio API initialized successfully.");
    } catch (e) {
      console.error("[Player].[initAudioAnalyzer] Error initializing Web Audio API:", e);
    }
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
  // Initialize audio analyzer on first play to comply with browser autoplay policies
  if (!audioContext) {
    initAudioAnalyzer();
  }

  // Resume AudioContext if it's suspended
  if (audioContext && audioContext.state === 'suspended') {
    audioContext.resume();
  }

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
  meshRender.dispose()
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
                    <span>-{{ remainingMinStr }}:{{ remainingSecStr }}</span>
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
          <v-row style="flex: 1 1 auto; min-height: 0; margin-top: 0;">
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

<style scoped>
/* General Player Styles */
.player-mask {
  position: fixed;
  left: 0;
  top: 0;
  z-index: 9999;
}

.player-font * {
  font-family: "PingFang SC";
  font-weight: bold;
}

.img-display {
  border-radius: 12px;
  box-shadow: 0 0 24px rgb(100, 100, 100);
}

.music-info {
  text-align: left;
  box-shadow: none;
}

.progress-number {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

/* Back Button Styling */
.player-back-btn {
  position: fixed;
  width: 70px;
  height: 8px;
  border-radius: 4px;
  background-color: rgba(200, 200, 200, .5);
  transition: all 0.3s ease-in-out;
}

.player-back-btn > svg {
  opacity: 0;
  transform: scale(0);
  transition: opacity 0.4s ease-in-out;
}

.player-back-btn:hover {
  width: 2rem;
  height: 1.5rem;
  border-radius: 8px;
}

.player-back-btn:hover > svg {
  opacity: 1;
  transform: scale(1);
}

/* Layout Helpers */
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

/* Lyric Player Containers */
.lyric-player {
  height: 80vh;
  position: relative;
  z-index: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: 400px;
  /* overflow: hidden; */ /* REMOVED: This was causing descenders to be clipped */
}

.portrait-lyric-player {
  height: calc(100vh - 180px);
  min-height: 320px;
  width: 100%;
  padding: 0;
  margin: 0;
  position: relative;
  /* overflow: hidden; */ /* REMOVED: This was causing descenders to be clipped */
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}

/* Portrait Mode Specific Styles */
.portrait-img {
  border-radius: 8px;
  box-shadow: 0 0 12px rgba(100, 100, 100, 0.3);
}

.portrait-music-info {
  text-align: left;
}

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

.portrait-lyric-container {
  width: 100%;
  margin-left: 0;
  padding-left: 0;
}

/* Media query for smaller portrait screens */
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

/* Deep styles for AMLL component */
#lyric-player :deep(.lyricLine-0-1-2) {
  letter-spacing: -0.05rem;
  font-weight: bolder;
  font-size: xxx-large;
  overflow: visible !important; /* Ensure lines can overflow */
}

#lyric-player :deep(.lyricBgLine-0-1-4) {
  font-size: x-large !important;
}

#lyric-player-portrait :deep(.lyricLine-0-1-2) {
  letter-spacing: -0.05rem;
  line-height: 1.4; /* INCREASED: Provides more space for descenders */
  font-weight: bolder;
  overflow: visible !important; /* Ensure lines can overflow */
}

#lyric-player-portrait :deep(.lyricBgLine-0-1-4) {
  font-size: x-large;
}

#lyric-player-portrait :deep(.lyricPlayer-0-1-1) {
  padding: 0;
  width: 95vw !important;
}

#lyric-player-portrait [class*="lyricLine-"] {
  text-align: left !important;
  padding-left: 0 !important;
  margin-left: 0 !important;
}

/* Animations */
.player-mask-enter-active {
  animation: slideInUp 0.6s cubic-bezier(0.2, 0.9, 0.2, 1) both;
}

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

<style>
.v-slider-thumb {
  display: none;
}

[class*="_lyricLine_ut4sn_6"] * {
  text-align: left;
}

[class*="_lyricDuetLine_ut4sn_79"] * {
  text-align: right;
}

body::-webkit-scrollbar {
  width: 0;
  height: 0;
  background: transparent;
}



</style>

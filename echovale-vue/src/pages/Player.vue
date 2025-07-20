<script setup lang="ts">

import {computed, onMounted, onUnmounted, ref, type Ref, watch} from "vue";
import {EplorRenderer, type LyricLine, type LyricLineMouseEvent} from "@applemusic-like-lyrics/core";
// import {debounce} from "../hooks/utils.ts";
import {debounce} from "lodash"
import {LyricPlayer} from "@applemusic-like-lyrics/core";
import {meTTML, ttml, yrc} from "../constant/testResource.ts";
import {parseTTML, parseYrc} from "@applemusic-like-lyrics/lyric";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import type {SpringParams} from "@applemusic-like-lyrics/core/dist/utils/spring";
import {useTheme} from "vuetify/framework";
import {
  faBackward,
  faForward,
  faHeart,
  faPause,
  faPlay,
  faRepeat, faVolumeHigh,
  faVolumeLow, faXmark
} from "@fortawesome/free-solid-svg-icons";


const theme = useTheme()

// theme.global.name.value = 'dark'


const fontThemeColor = computed(() => ({
  color: theme.current.value.colors.background
}))

function setCanvasSize(canvas: HTMLCanvasElement) {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  console.log("[Player].[setCanvasSize]  width:", canvas.width);
  console.log("[Player].[setCanvasSize] height:", canvas.height);
}

let music: HTMLAudioElement
let player: LyricPlayer
let eplorRender: EplorRenderer


// 背景帧率
const fps = 120




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

  const lyricPlayerContainer = document.getElementById("lyric-player") as HTMLDivElement

  // console.log(lyricLines)

  player = new LyricPlayer()
  lyricPlayerContainer.appendChild(player.getElement())
  player.setLyricLines(lines.lines)
  player.setEnableBlur(true)
  player.setEnableScale(true)
  player.setAlignPosition(0.4)
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

  setLyricPlayerAnimation(fps)

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
  isPlaying.value = true
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
  let timeout = 1000 / fps
  music = document.getElementById("music") as HTMLAudioElement
  if (interval !== 0) clearInterval(interval)
  interval = setInterval(() => {
    if (isMusicProgressUpdate.value) return
    if (!isPlaying.value) player.pause()
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

const musciDefaultVolume = 0.5

// TODO Web Audio API 丝滑过渡防跳音
function watchMusicVolume() {
  music.volume = musciDefaultVolume
  volume.value = musciDefaultVolume * 100
  watch(volume, (newValue) => {
    music.volume = newValue / 100
    console.log("[Player].[watchMusicVolume]", music.volume)
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

const closeMusicPlayer = () => {

}

const progressSliderTrackSize: Ref<number> = ref(10)
const volumeSliderTrackSize: Ref<number> = ref(10)



let isPlaying: Ref<boolean> = ref(false)

const togglePlay = () => {
  console.log("[Player].[togglePlay]", isPlaying.value)

  isPlaying.value = !isPlaying.value
  if (isPlaying.value) {
    music.play()
    player.resume()
  }
  else {
    music.pause()
    player.pause()
  }
}


onMounted(() => {

  // 加载流体背景
  loadEplorRender()

  // 加载歌词播放器
  loadLyricPlayer()

  loadMusic()

})

onUnmounted(() => {
  eplorRender.dispose()
  player.dispose()
})


</script>

<template>
  <canvas id="eplor-canvas" style="z-index: -2; left: 0; top: 0; position: fixed; pointer-events: none; width: 100vw; height: 100vh;" />
  <v-container class="pa-0" style="width: 100vw; height: 100vh; z-index: 0;">
  <v-container class="player-font" :style="fontThemeColor">
    <v-row no-gutters class="d-flex justify-space-evenly" style="width: 100vw;">
<!--      music player-->
      <v-col cols="5" style="max-width: 54vh; min-width: 30vw; min-height: 90vh; max-height: 96vh;">
        <v-container class="d-flex flex-column justify-space-between" style="max-width: 36vw; min-width: 30vw; min-height: 90vh; max-height: 90vh;">
          <v-row>
            <v-container class="d-flex justify-center align-center" style="padding-top: 0;">
              <div class="player-back-btn d-flex justify-center align-center"
                   @click="closeMusicPlayer">
                <font-awesome-icon :icon="faXmark" />
              </div>
            </v-container>
          </v-row>
<!--          img-->
          <v-row>
            <v-container class="pa-0 d-flex justify-center">
              <v-img aspect-ratio="1" max-width="54vh" cover class="img-display" src="src/assets/me.jpg"></v-img>
            </v-container>
          </v-row>
          <!--          info-->
          <v-row style="flex: 1;">
            <v-container style="padding: 1rem 0; max-width: 54vh;">
              <v-card class="music-info" color="transparent" >
                <v-card-title class="pa-0">ME!</v-card-title>
                <v-card-subtitle class="pa-0">Taylor Swift, Brendon Urie</v-card-subtitle>
              </v-card>
            </v-container>
          </v-row>
          <!--          progress-->
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
          <!--          progress number-->
          <v-row>
            <v-container class="progress-number pa-0" style="max-width: 54vh;">
              <span>{{ minStr }}:{{ secStr }}</span>
              <span></span>
              <span>{{duration}}</span>
            </v-container>
          </v-row>
          <!--          controller-->
          <v-row>
            <v-container style="max-width: 54vh;">
              <v-row style="display: flex; flex-direction: row; justify-content: space-between;">
                <v-col class="pa-0" cols="2" style="display: flex; justify-content: start; align-self: center;"><font-awesome-icon size="lg" :icon="faRepeat" /></v-col>
                <v-col cols="2"><font-awesome-icon size="2x" :icon="faBackward" /></v-col>
                <transition name="fade" mode="out-in">
                <v-col cols="4" @click="togglePlay">
                    <font-awesome-icon size="2x" v-if="isPlaying" key="pause" :icon="faPause" />
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
          <!--          volume-->
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
<!--      lyric player-->
      <v-col cols="7">
        <v-container class="lyric-player" id="lyric-player" :style="fontThemeColor"></v-container>
      </v-col>
    </v-row>
  </v-container>
  </v-container>

  <audio id="music" src="src/assets/music/me.flac" autoplay controls loop style="display: none"></audio>
</template>

<style scoped>

  .lyric-player {
    height: 90vh;
    position: relative;
    z-index: 0;
    display: flex;
    justify-content: center;
  }

  .player-font * {
    font-family: "PingFang SC";
    font-weight: bold;
  }




  .img-display {
    border-radius: 12px;
    box-shadow: 0 0 24px rgb(100,100,100);
  }

  .player-back-btn {
    width: 2rem;
    height: 1.5rem;
    border-radius: 8px;
    background-color: rgba(200,200,200,.5);
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





</style>

<style>
.v-slider-thumb {
  display: none;
}

.lyricLine-0-1-2 * {
  text-align: left;
}

.lyricDuetLine-0-1-3 * {
  text-align: right;
}

</style>
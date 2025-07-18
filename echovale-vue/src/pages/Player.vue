<script setup lang="ts">

import {onMounted, onUnmounted} from "vue";
import {EplorRenderer} from "@applemusic-like-lyrics/core";
import {debounce} from "../hooks/utils.ts";
import {LyricPlayer} from "@applemusic-like-lyrics/core";
import {lrc, ttml} from "../constant/testResource.ts";
import {type LyricLine, parseLrc, parseTTML} from "@applemusic-like-lyrics/lyric";


function setCanvasSize(canvas: HTMLCanvasElement) {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;
  console.log("[Player].[setCanvasSize]  width:", canvas.width);
  console.log("[Player].[setCanvasSize] height:", canvas.height);
}


function loadEplorRender() {
  // 图片
  const album = 'src/assets/love.jpg'
  // 背景帧率
  const fps = 120
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
  const eplorRender = new EplorRenderer(eplorCanvas);

  // 设置图片
  eplorRender.setAlbum(album)
  // 设置fps
  eplorRender.setFPS(fps)
  // 设置流体速率
  eplorRender.setFlowSpeed(flowSpeed)
  // 设置渲染精度
  eplorRender.setRenderScale(renderScale)

  console.log("[Player].[loadEplorRender] Loaded EplorRender.")

  // 卸载时释放内存
  onUnmounted(() => {
    eplorRender.dispose()
    console.log("[Player].[onUnmounted] EplorRender Unmounted.")
  })
}

function loadLyricPlayer() {

  // const lines = parseLrc(lrc)
  const lines = parseTTML(ttml)
  // const lyricLines = convertLyricLine(lines.lines)

  const lyricPlayerContainer = document.getElementById("lyric-player") as HTMLDivElement

  // console.log(lyricLines)

  const player = new LyricPlayer()
  lyricPlayerContainer.appendChild(player.getElement())
  player.setLyricLines(lines.lines)
  player.setEnableBlur(true)
  player.setEnableScale(true)
  player.setAlignPosition(0.5)
  player.setEnableSpring(true)
  player.setAlignAnchor("center")

  setLyricPlayerAnimation(player, 60)
}


function setLyricPlayerAnimation(player: LyricPlayer, fps: number) {
  let timeout = 1000 / fps
  let interval: number
  const music = document.getElementById("music") as HTMLAudioElement
  interval = setInterval(() => {
    const currentTime = music.currentTime
    player.setCurrentTime(currentTime * 1000)
    player.update(20)
    console.log(currentTime)
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
    isDuet: false
  }));
}



onMounted(() => {

  // 加载流体背景
  loadEplorRender()

  // 加载歌词播放器
  loadLyricPlayer()

})

</script>

<template>
  <canvas id="eplor-canvas" style="z-index: -2; left: 0; top: 0; position: fixed; pointer-events: none; width: 100vw; height: 100vh;" />
  <v-container class="pa-0" style="width: 100vw; height: 100vh; z-index: -1">
    <v-row style="width: 100%; height: 100%">
      <v-col cols="4"></v-col>
      <v-col class="lyric-player" id="lyric-player" cols="8" style="height: 100vh; width: 100%; position: relative; z-index: -1; font-size: 10px;"></v-col>
    </v-row>
  </v-container>
  <audio id="music" src="src/assets/music/Fortnight.m4a" autoplay controls></audio>
</template>

<style scoped>
  .lyric-player * {
    font-size: 4rem !important;
    text-align: left;
  }
</style>
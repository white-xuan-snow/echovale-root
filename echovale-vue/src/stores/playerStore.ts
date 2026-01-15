import { defineStore } from 'pinia'
import { ref } from 'vue'

export const usePlayerStore = defineStore('player', () => {
  // 当前播放歌曲
  const currentSong = ref({
    id: 1,
    title: '我的音乐',
    artists: ['艺术家'],
    cover: '/src/assets/me.jpg',
    audioSrc: '/src/assets/music/me.flac',
    lrcSrc: '/src/assets/music/Fortnight.lrc',
    duration: 180, // 初始占位时长
    quality: 'standard'
  })

  const audioPlayer = ref(new Audio())
  try {
    audioPlayer.value.src = currentSong.value.audioSrc
    audioPlayer.value.load()
  } catch (error) {
    console.error('音频加载失败:', error)
    // 尝试使用兼容格式
    currentSong.value.audioSrc = '/src/assets/music/WhatIsLove.flac'
    audioPlayer.value.src = currentSong.value.audioSrc
  }

  // 播放状态
  const isPlaying = ref(false)
  const currentTime = ref(0)
  const duration = ref(0) // 音频的真实总时长
  const progress = ref(0)
  const volume = ref(0.5)
  const playMode = ref('order') // order/loop/random

  // 播放列表
  const playlist = ref([
    {
      id: 1,
      title: '歌曲1',
      artists: ['作者1'],
      cover: 'src/assets/me.jpg',
      duration: 180
    },
    {
      id: 2,
      title: '歌曲2',
      artists: ['作者2', '作者3'],
      cover: 'src/assets/love.jpg',
      duration: 210
    }
  ])

  // 操作方法
  const togglePlay = async () => {
    try {
      if (isPlaying.value) {
        await audioPlayer.value.pause()
      } else {
        await audioPlayer.value.play()
      }
      isPlaying.value = !isPlaying.value
    } catch (error) {
      console.error('播放控制失败:', error)
    }
  }

  // 确保所有组件使用同一个audio实例
  const getAudioPlayer = () => {
    return audioPlayer.value
  }

  const changePlayMode = () => {
    const modes = ['order', 'loop', 'random']
    const currentIndex = modes.indexOf(playMode.value)
    playMode.value = modes[(currentIndex + 1) % modes.length]
  }

  // 由用户交互（如拖动滑块）调用
  const seek = (time: number) => {
    if (audioPlayer.value && isFinite(audioPlayer.value.duration)) {
      audioPlayer.value.currentTime = time
      currentTime.value = time
      progress.value = (time / audioPlayer.value.duration) * 100
    }
  }

  // 由播放器本身在播放时持续调用，用于同步UI
  const setCurrentTime = (time: number) => {
    currentTime.value = time
  }

  // 在音频元数据加载完毕后调用
  const setDuration = (d: number) => {
    if (isFinite(d)) {
      duration.value = d
    }
  }

  const setVolume = (val: number) => {
    volume.value = val
    audioPlayer.value.volume = val
  }

  return {
    currentSong,
    isPlaying,
    currentTime,
    duration, // 导出 duration
    volume,
    playMode,
    playlist,
    togglePlay,
    changePlayMode,
    seek,
    setVolume,
    getAudioPlayer,
    setCurrentTime, // 导出 setCurrentTime
    setDuration // 导出 setDuration
  }
})

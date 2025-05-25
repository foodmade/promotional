<template>
  <el-scrollbar ref="scrollContainer" :vertical="false" class="scroll-container" @wheel.prevent="handleScroll">
    <slot />
  </el-scrollbar>
</template>

<script>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'

export default {
  name: 'ScrollPane',
  emits: ['scroll'],
  setup(props, { emit }) {
    const scrollContainer = ref(null)
    
    const handleScroll = (e) => {
      const eventDelta = e.wheelDelta || -e.deltaY * 40
      const $scrollWrapper = scrollContainer.value.wrap
      $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft - eventDelta / 4
      emit('scroll')
    }
    
    const scrollWrapper = computed(() => {
      return scrollContainer.value?.wrap
    })
    
    const emitScroll = () => {
      emit('scroll')
    }
    
    const handleDomScroll = () => {
      emitScroll()
    }
    
    onMounted(() => {
      if (scrollWrapper.value) {
        scrollWrapper.value.addEventListener('scroll', handleDomScroll)
      }
    })
    
    onBeforeUnmount(() => {
      if (scrollWrapper.value) {
        scrollWrapper.value.removeEventListener('scroll', handleDomScroll)
      }
    })
    
    return {
      scrollContainer,
      handleScroll
    }
  }
}
</script>

<style lang="scss" scoped>
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  
  :deep(.el-scrollbar__bar) {
    bottom: 0px;
  }
  
  :deep(.el-scrollbar__wrap) {
    height: 49px;
  }
}
</style> 
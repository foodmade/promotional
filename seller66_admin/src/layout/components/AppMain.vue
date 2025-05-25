<template>
  <section class="app-main">
    <router-view v-slot="{ Component }">
      <transition name="fade-transform" mode="out-in">
        <keep-alive :include="cachedViews">
          <component :is="Component" :key="key" />
        </keep-alive>
      </transition>
    </router-view>
  </section>
</template>

<script>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

export default {
  name: 'AppMain',
  setup() {
    const route = useRoute()
    
    const cachedViews = computed(() => {
      return ['Dashboard'] // 可以缓存的视图
    })
    
    const key = computed(() => route.path)
    
    return {
      cachedViews,
      key
    }
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
  padding: 104px 20px 20px;
  box-sizing: border-box;
}
</style> 
<template>
  <div class="app-wrapper" :class="classObj">
    <div v-if="device === 'mobile' && sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container" />
    <div class="main-container">
      <div class="fixed-header">
        <navbar />
        <tags-view v-if="showTagsView" />
      </div>
      <app-main />
    </div>
  </div>
</template>

<script>
import { Sidebar, Navbar, AppMain, TagsView } from './components'
import { computed, provide, ref } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'Layout',
  components: {
    Sidebar,
    Navbar,
    AppMain,
    TagsView
  },
  setup() {
    const store = useStore()
    const showTagsView = ref(true)
    
    // 提供给子组件的数据
    provide('showTagsView', showTagsView)
    
    // 响应式数据
    const device = ref('desktop')
    const sidebar = computed(() => store.getters.sidebar)
    
    // 根据侧边栏状态生成类名
    const classObj = computed(() => ({
      hideSidebar: !sidebar.value.opened,
      openSidebar: sidebar.value.opened,
      withoutAnimation: sidebar.value.withoutAnimation,
      mobile: device.value === 'mobile'
    }))
    
    // 移动端点击遮罩层关闭侧边栏
    const handleClickOutside = () => {
      store.commit('CLOSE_SIDEBAR', false)
    }
    
    return {
      device,
      sidebar,
      classObj,
      showTagsView,
      handleClickOutside
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/styles/index.scss";

.app-wrapper {
  position: relative;
  height: 100%;
  width: 100%;
  
  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - 210px);
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.mobile .fixed-header {
  width: 100%;
}

.sidebar-container {
  transition: width 0.28s;
  width: 210px !important;
  height: 100%;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1001;
  overflow: hidden;
  background-color: #304156;
}

.main-container {
  min-height: 100%;
  transition: margin-left 0.28s;
  margin-left: 210px;
  position: relative;
}

.hideSidebar {
  .main-container {
    margin-left: 54px;
  }
  
  .sidebar-container {
    width: 54px !important;
  }
}

/* 用于小屏幕 */
.mobile {
  .main-container {
    margin-left: 0px;
  }
  
  .sidebar-container {
    transition: transform .28s;
    width: 210px !important;
  }
  
  &.hideSidebar {
    .sidebar-container {
      pointer-events: none;
      transition-duration: 0.3s;
      transform: translate3d(-210px, 0, 0);
    }
  }
}

/* 处理兼容性问题 */
.withoutAnimation {
  .main-container,
  .sidebar-container {
    transition: none;
  }
}
</style> 
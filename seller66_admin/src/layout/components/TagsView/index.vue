<template>
  <div id="tags-view-container" class="tags-view-container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper" @scroll="handleScroll">
      <router-link
        v-for="tag in visitedViews"
        :key="tag.path"
        :class="isActive(tag) ? 'active' : ''"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
        class="tags-view-item"
        @click.middle="!isAffix(tag) ? closeSelectedTag(tag) : ''"
        @contextmenu.prevent="openMenu(tag, $event)"
      >
        {{ tag.title }}
        <el-icon v-if="!isAffix(tag)" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)">
          <Close />
        </el-icon>
      </router-link>
    </scroll-pane>
    <ul v-show="visible" :style="{left: left+'px', top: top+'px'}" class="contextmenu">
      <li @click="refreshSelectedTag(selectedTag)">刷新</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">关闭</li>
      <li @click="closeOthersTags">关闭其他</li>
      <li @click="closeAllTags(selectedTag)">关闭所有</li>
    </ul>
  </div>
</template>

<script>
import { nextTick, ref, watch, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import ScrollPane from './ScrollPane.vue'
import path from 'path-browserify'

export default {
  name: 'TagsView',
  components: { ScrollPane },
  setup() {
    const visible = ref(false)
    const top = ref(0)
    const left = ref(0)
    const selectedTag = ref({})
    const affixTags = ref([])
    const scrollPane = ref(null)
    const visitedViews = ref([])
    
    const route = useRoute()
    const router = useRouter()
    
    const isActive = (tag) => {
      return tag.path === route.path
    }
    
    const isAffix = (tag) => {
      return tag.meta && tag.meta.affix
    }
    
    // 生成路由的标题
    const generateTitle = (title) => {
      return title
    }
    
    // 初始化标签
    const initTags = () => {
      const routes = router.getRoutes()
      
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = route.path
          const tagQuery = route.query || {}
          const tagFullPath = route.fullPath || route.path
          
          affixTags.value.push({
            path: tagPath,
            query: tagQuery,
            fullPath: tagFullPath,
            name: route.name,
            meta: { ...route.meta }
          })
        }
      })
    }
    
    // 添加标签
    const addTags = () => {
      const { name } = route
      if (name) {
        const view = {
          path: route.path,
          query: route.query,
          fullPath: route.fullPath,
          name: name,
          meta: { ...route.meta },
          title: generateTitle(route.meta.title)
        }
        
        const isViewExists = visitedViews.value.some(v => v.path === view.path)
        
        if (!isViewExists) {
          visitedViews.value.push(view)
        }
      }
    }
    
    // 刷新选中的标签
    const refreshSelectedTag = (view) => {
      router.push({
        path: '/redirect' + view.fullPath
      })
    }
    
    // 关闭选中的标签
    const closeSelectedTag = (view) => {
      const index = visitedViews.value.findIndex(v => v.path === view.path)
      
      if (index > -1) {
        visitedViews.value.splice(index, 1)
      }
      
      if (isActive(view)) {
        toLastView(visitedViews.value, view)
      }
    }
    
    // 关闭其他标签
    const closeOthersTags = () => {
      visitedViews.value = visitedViews.value.filter(v => {
        return isAffix(v) || v.path === route.path
      })
      
      router.push({
        path: route.path
      })
    }
    
    // 关闭所有标签
    const closeAllTags = (view) => {
      visitedViews.value = visitedViews.value.filter(v => isAffix(v))
      
      if (affixTags.value.length > 0) {
        router.push({
          path: affixTags.value[0].path
        })
      } else {
        router.push('/')
      }
    }
    
    // 如果关闭的是当前标签，则跳转到最后一个标签
    const toLastView = (visitedViews, view) => {
      const latestView = visitedViews.slice(-1)[0]
      
      if (latestView) {
        router.push(latestView.fullPath)
      } else {
        // 如果没有标签，则跳转到首页
        if (view.name === 'Dashboard') {
          router.push('/')
        } else {
          router.push('/dashboard')
        }
      }
    }
    
    // 打开右键菜单
    const openMenu = (tag, e) => {
      const menuMinWidth = 105
      const offsetLeft = 15
      const maxLeft = window.innerWidth - menuMinWidth - 15 // 距离右边界最大偏移
      
      left.value = e.clientX - offsetLeft
      if (left.value > maxLeft) {
        left.value = maxLeft
      }
      
      top.value = e.clientY
      visible.value = true
      selectedTag.value = tag
    }
    
    // 关闭右键菜单
    const closeMenu = () => {
      visible.value = false
    }
    
    // 处理滚动
    const handleScroll = () => {
      closeMenu()
    }
    
    // 监听路由变化，添加标签
    watch(
      () => route.path,
      () => {
        addTags()
      }
    )
    
    // 在页面加载时初始化标签
    nextTick(() => {
      initTags()
      addTags()
    })
    
    // 挂载和卸载事件监听器
    const listener = () => {
      closeMenu()
    }
    
    onMounted(() => {
      document.addEventListener('click', listener)
    })
    
    onBeforeUnmount(() => {
      document.removeEventListener('click', listener)
    })
    
    return {
      visible,
      top,
      left,
      selectedTag,
      affixTags,
      scrollPane,
      visitedViews,
      isActive,
      isAffix,
      refreshSelectedTag,
      closeSelectedTag,
      closeOthersTags,
      closeAllTags,
      openMenu,
      handleScroll
    }
  }
}
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  
  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      color: #495060;
      background: #fff;
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;
      
      &:first-of-type {
        margin-left: 15px;
      }
      
      &:last-of-type {
        margin-right: 15px;
      }
      
      &.active {
        background-color: #42b983;
        color: #fff;
        border-color: #42b983;
        
        &::before {
          content: '';
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 2px;
        }
      }
      
      .el-icon-close {
        width: 16px;
        height: 16px;
        font-size: 10px;
        vertical-align: 2px;
        border-radius: 50%;
        text-align: center;
        transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
        transform-origin: 100% 50%;
        
        &:before {
          transform: scale(0.6);
          display: inline-block;
          vertical-align: -3px;
        }
        
        &:hover {
          background-color: #b4bccc;
          color: #fff;
        }
      }
    }
  }
  
  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
    
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      
      &:hover {
        background: #eee;
      }
    }
  }
}
</style> 
<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children, item) && (!onlyOneChild.children || onlyOneChild.noShowingChildren) && !item.alwaysShow">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown': !isNest}">
          <el-icon v-if="onlyOneChild.meta.icon"><component :is="onlyOneChild.meta.icon" /></el-icon>
          <template #title>{{ onlyOneChild.meta.title }}</template>
        </el-menu-item>
      </app-link>
    </template>

    <el-sub-menu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template #title>
        <el-icon v-if="item.meta && item.meta.icon"><component :is="item.meta.icon" /></el-icon>
        <span>{{ item.meta.title }}</span>
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        :is-collapse="isCollapse"
        class="nest-menu"
      />
    </el-sub-menu>
  </div>
</template>

<script>
import { ref } from 'vue'
import { isExternal } from '@/utils/validate'
import AppLink from './Link.vue'
import path from 'path-browserify'

export default {
  name: 'SidebarItem',
  components: { AppLink },
  props: {
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    },
    isCollapse: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const onlyOneChild = ref(null)
    
    // 判断路由是否只有一个显示的子路由
    const hasOneShowingChild = (children = [], parent) => {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          // 临时设置（如果只有一个子路由，则显示该子路由）
          onlyOneChild.value = item
          return true
        }
      })
      
      // 当只有一个子路由时，默认显示该子路由
      if (showingChildren.length === 1) {
        return true
      }
      
      // 如果没有子路由则显示父路由
      if (showingChildren.length === 0) {
        onlyOneChild.value = { ...parent, path: '', noShowingChildren: true }
        return true
      }
      
      return false
    }
    
    // 解析路由路径
    const resolvePath = (routePath) => {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(props.basePath)) {
        return props.basePath
      }
      return path.resolve(props.basePath, routePath)
    }
    
    return {
      onlyOneChild,
      hasOneShowingChild,
      resolvePath
    }
  }
}
</script> 
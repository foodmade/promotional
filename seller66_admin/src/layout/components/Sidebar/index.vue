<template>
  <div :class="{'has-logo': showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :active-text-color="variables.menuActiveText"
        :unique-opened="false"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in routes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
          :is-collapse="isCollapse"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useStore } from 'vuex'
import Logo from './Logo.vue'
import SidebarItem from './SidebarItem.vue'
import { constantRoutes } from '@/router'

export default {
  name: 'Sidebar',
  components: { SidebarItem, Logo },
  setup() {
    const route = useRoute()
    const store = useStore()
    
    const showLogo = true
    const routes = constantRoutes
    const variables = {
      menuBg: '#304156',
      menuText: '#bfcbd9',
      menuActiveText: '#409EFF'
    }
    
    const sidebar = computed(() => store.getters.sidebar)
    const isCollapse = computed(() => !sidebar.value.opened)
    
    // 获取当前活动菜单
    const activeMenu = computed(() => {
      const { meta, path } = route
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    })
    
    return {
      routes,
      showLogo,
      isCollapse,
      activeMenu,
      variables
    }
  }
}
</script> 
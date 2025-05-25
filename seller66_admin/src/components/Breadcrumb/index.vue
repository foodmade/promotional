<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path">
        <span v-if="item.redirect === 'noRedirect' || index === levelList.length - 1" class="no-redirect">{{ item.meta.title }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

export default {
  name: 'Breadcrumb',
  setup() {
    const levelList = ref([])
    const route = useRoute()
    const router = useRouter()
    
    const getBreadcrumb = () => {
      // 面包屑只显示有meta.title的路由
      let matched = route.matched.filter(item => item.meta && item.meta.title)
      
      // 如果不是首页
      if (matched[0].path !== '/dashboard') {
        matched = [{ path: '/dashboard', meta: { title: '首页' } }].concat(matched)
      }
      
      levelList.value = matched
    }
    
    const handleLink = (item) => {
      const { path } = item
      router.push(path)
    }
    
    // 路由变化时更新面包屑
    watch(
      () => route.path,
      () => getBreadcrumb(),
      { immediate: true }
    )
    
    return {
      levelList,
      handleLink
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style> 
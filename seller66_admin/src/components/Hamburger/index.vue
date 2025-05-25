<template>
  <div style="padding: 0 15px;" @click="toggleClick">
    <el-icon :size="20">
      <component :is="isActive ? 'Fold' : 'Expand'" />
    </el-icon>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    }
  },
  emits: ['toggleClick'],
  setup(props, { emit }) {
    const toggleClick = () => {
      emit('toggleClick')
    }
    
    return {
      toggleClick
    }
  }
}
</script>

<style scoped>
</style> 
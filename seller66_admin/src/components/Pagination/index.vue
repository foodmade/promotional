<template>
  <div :class="{'hidden':hidden}" class="pagination-container">
    <el-pagination
      :background="background"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :layout="layout"
      :page-sizes="pageSizes"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { computed, ref, watch } from 'vue'

export default {
  name: 'Pagination',
  props: {
    total: {
      required: true,
      type: Number
    },
    page: {
      type: Number,
      default: 1
    },
    limit: {
      type: Number,
      default: 20
    },
    pageSizes: {
      type: Array,
      default() {
        return [10, 20, 30, 50]
      }
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    background: {
      type: Boolean,
      default: true
    },
    hidden: {
      type: Boolean,
      default: false
    }
  },
  emits: ['pagination', 'update:page', 'update:limit'],
  setup(props, { emit }) {
    const currentPage = ref(props.page)
    const pageSize = ref(props.limit)
    
    // 监听props变化
    watch(
      () => props.page,
      val => {
        currentPage.value = val
      }
    )
    
    watch(
      () => props.limit,
      val => {
        pageSize.value = val
      }
    )
    
    // 监听内部变量变化，触发事件
    watch(
      () => currentPage.value,
      val => {
        emit('update:page', val)
        emitPagination()
      }
    )
    
    watch(
      () => pageSize.value,
      val => {
        emit('update:limit', val)
        emitPagination()
      }
    )
    
    // 页码变化
    const handleCurrentChange = (val) => {
      currentPage.value = val
    }
    
    // 每页条数变化
    const handleSizeChange = (val) => {
      pageSize.value = val
    }
    
    // 向父组件传递分页事件
    const emitPagination = () => {
      emit('pagination', {
        page: currentPage.value,
        limit: pageSize.value
      })
    }
    
    return {
      currentPage,
      pageSize,
      handleCurrentChange,
      handleSizeChange
    }
  }
}
</script>

<style scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}
.pagination-container.hidden {
  display: none;
}
</style> 
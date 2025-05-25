<template>
  <div class="dashboard-container">
    <div class="dashboard-text">你好, {{ name }}</div>
    <div class="dashboard-content">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card shadow="hover" class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>文章总数</span>
                <el-tag type="success">今日 +2</el-tag>
              </div>
            </template>
            <div class="card-body">
              <div class="card-value">128</div>
              <div class="card-icon">
                <el-icon :size="40"><document /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>用户访问</span>
                <el-tag type="warning">今日 +35</el-tag>
              </div>
            </template>
            <div class="card-body">
              <div class="card-value">3,256</div>
              <div class="card-icon">
                <el-icon :size="40"><user /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>评论数</span>
                <el-tag type="info">今日 +8</el-tag>
              </div>
            </template>
            <div class="card-body">
              <div class="card-value">286</div>
              <div class="card-icon">
                <el-icon :size="40"><chat-dot-round /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" class="dashboard-card">
            <template #header>
              <div class="card-header">
                <span>收藏数</span>
                <el-tag type="danger">今日 +15</el-tag>
              </div>
            </template>
            <div class="card-body">
              <div class="card-value">521</div>
              <div class="card-icon">
                <el-icon :size="40"><star /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <el-row :gutter="20" style="margin-top: 20px">
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <div class="card-header">
                <span>最近文章</span>
                <el-button class="button" type="text">查看全部</el-button>
              </div>
            </template>
            <el-table :data="recentArticles" style="width: 100%" :show-header="false">
              <el-table-column prop="title" label="标题">
                <template #default="scope">
                  <el-link :underline="false" type="primary">{{ scope.row.title }}</el-link>
                </template>
              </el-table-column>
              <el-table-column prop="date" label="日期" width="180">
                <template #default="scope">
                  <span>{{ scope.row.date }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <div class="card-header">
                <span>待办事项</span>
                <el-button class="button" type="text">添加</el-button>
              </div>
            </template>
            <el-table :data="todos" style="width: 100%" :show-header="false">
              <el-table-column width="40">
                <template #default="scope">
                  <el-checkbox v-model="scope.row.done" />
                </template>
              </el-table-column>
              <el-table-column prop="content" label="内容">
                <template #default="scope">
                  <span :class="{ 'todo-done': scope.row.done }">{{ scope.row.content }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="priority" label="优先级" width="90">
                <template #default="scope">
                  <el-tag :type="scope.row.priority | priorityFilter">{{ scope.row.priority }}</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { computed, ref } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'Dashboard',
  setup() {
    const store = useStore()
    
    const name = computed(() => {
      return store.getters.user ? store.getters.user.name : '管理员'
    })
    
    // 模拟最近文章数据
    const recentArticles = ref([
    ])
    
    // 模拟待办事项
    const todos = ref([
    ])
    
    return {
      name,
      recentArticles,
      todos
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  
  &-text {
    font-size: 30px;
    line-height: 46px;
    color: #2d2f33;
    margin-bottom: 30px;
  }
  
  &-card {
    height: 180px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .card-body {
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 80px;
      
      .card-value {
        font-size: 36px;
        font-weight: bold;
        color: #606266;
      }
      
      .card-icon {
        padding: 12px;
        border-radius: 50%;
        background-color: #f0f9eb;
        color: #67c23a;
      }
    }
  }
}

.todo-done {
  color: #909399;
  text-decoration: line-through;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 
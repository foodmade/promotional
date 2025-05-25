<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="标签名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter="handleFilter"
      />
      <el-button v-waves class="filter-item" type="primary" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" type="primary" @click="handleCreate">添加标签</el-button>
    </div>

    <el-card class="tag-card">
      <div class="tag-container">
        <el-tag
          v-for="tag in tagList"
          :key="tag.id"
          class="tag-item"
          :type="tag.type"
          closable
          @close="handleDeleteTag(tag.id)"
          @click="handleEdit(tag)"
        >
          {{ tag.name }} ({{ tag.count }})
        </el-tag>
      </div>
    </el-card>

    <!-- 添加/编辑标签对话框 -->
    <el-dialog
      :title="dialogStatus === 'create' ? '添加标签' : '编辑标签'"
      v-model="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :model="temp"
        :rules="rules"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="颜色">
          <el-select v-model="temp.type" class="filter-item">
            <el-option label="默认" value="" />
            <el-option label="成功" value="success" />
            <el-option label="信息" value="info" />
            <el-option label="警告" value="warning" />
            <el-option label="危险" value="danger" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'TagManagement',
  setup() {
    const tagList = ref([])
    const listLoading = ref(true)
    const dialogFormVisible = ref(false)
    const dialogStatus = ref('')
    const dataFormRef = ref(null)
    
    // 查询参数
    const listQuery = reactive({
      name: ''
    })
    
    // 临时数据
    const temp = reactive({
      id: undefined,
      name: '',
      type: '',
      count: 0
    })
    
    // 校验规则
    const rules = {
      name: [{ required: true, message: '标签名称不能为空', trigger: 'blur' }]
    }
    
    // 获取标签列表
    const getList = () => {
      listLoading.value = true
      
      // 模拟API请求
      setTimeout(() => {
        const mockData = [
          { id: 1, name: 'JavaScript', type: '', count: 42 },
          { id: 2, name: 'Vue', type: 'success', count: 28 },
          { id: 3, name: 'React', type: 'info', count: 18 },
          { id: 4, name: 'Node.js', type: 'warning', count: 12 },
          { id: 5, name: 'CSS', type: '', count: 25 },
          { id: 6, name: 'HTML', type: '', count: 30 },
          { id: 7, name: 'TypeScript', type: 'info', count: 15 },
          { id: 8, name: '前端', type: 'success', count: 50 },
          { id: 9, name: '后端', type: 'warning', count: 20 },
          { id: 10, name: '算法', type: 'danger', count: 8 }
        ]
        
        // 如果有搜索，过滤数据
        if (listQuery.name) {
          tagList.value = mockData.filter(item => 
            item.name.toLowerCase().includes(listQuery.name.toLowerCase())
          )
        } else {
          tagList.value = mockData
        }
        
        listLoading.value = false
      }, 500)
    }
    
    // 筛选
    const handleFilter = () => {
      getList()
    }
    
    // 重置表单
    const resetTemp = () => {
      temp.id = undefined
      temp.name = ''
      temp.type = ''
      temp.count = 0
    }
    
    // 添加标签
    const handleCreate = () => {
      resetTemp()
      dialogStatus.value = 'create'
      dialogFormVisible.value = true
      
      // 在下一个事件循环中重置验证表单
      setTimeout(() => {
        if (dataFormRef.value) {
          dataFormRef.value.clearValidate()
        }
      }, 0)
    }
    
    // 编辑标签
    const handleEdit = (row) => {
      temp.id = row.id
      temp.name = row.name
      temp.type = row.type
      temp.count = row.count
      
      dialogStatus.value = 'update'
      dialogFormVisible.value = true
      
      // 在下一个事件循环中重置验证表单
      setTimeout(() => {
        if (dataFormRef.value) {
          dataFormRef.value.clearValidate()
        }
      }, 0)
    }
    
    // 删除标签
    const handleDeleteTag = (id) => {
      ElMessageBox.confirm('确定要删除这个标签吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 模拟删除操作
        const index = tagList.value.findIndex(item => item.id === id)
        if (index > -1) {
          tagList.value.splice(index, 1)
        }
        
        ElMessage({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
    
    // 创建标签
    const createData = () => {
      dataFormRef.value.validate((valid) => {
        if (valid) {
          // 模拟API请求
          setTimeout(() => {
            temp.id = tagList.value.length + 1
            
            tagList.value.push({
              id: temp.id,
              name: temp.name,
              type: temp.type,
              count: 0
            })
            
            dialogFormVisible.value = false
            ElMessage({
              message: '创建成功',
              type: 'success'
            })
          }, 500)
        }
      })
    }
    
    // 更新标签
    const updateData = () => {
      dataFormRef.value.validate((valid) => {
        if (valid) {
          // 模拟API请求
          setTimeout(() => {
            const index = tagList.value.findIndex(item => item.id === temp.id)
            
            if (index > -1) {
              tagList.value.splice(index, 1, {
                ...tagList.value[index],
                name: temp.name,
                type: temp.type
              })
            }
            
            dialogFormVisible.value = false
            ElMessage({
              message: '更新成功',
              type: 'success'
            })
          }, 500)
        }
      })
    }
    
    onMounted(() => {
      getList()
    })
    
    return {
      tagList,
      listQuery,
      listLoading,
      dialogFormVisible,
      dialogStatus,
      dataFormRef,
      temp,
      rules,
      handleFilter,
      handleCreate,
      handleEdit,
      handleDeleteTag,
      createData,
      updateData
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.filter-container {
  padding-bottom: 20px;
  
  .filter-item {
    margin-bottom: 10px;
    margin-right: 10px;
  }
}

.tag-card {
  margin-bottom: 20px;
}

.tag-container {
  display: flex;
  flex-wrap: wrap;
  
  .tag-item {
    margin-right: 10px;
    margin-bottom: 10px;
    cursor: pointer;
    padding: 5px 10px;
    font-size: 14px;
  }
}

.dialog-footer {
  text-align: right;
}
</style> 
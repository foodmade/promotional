<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" @click="handleAddCategory">添加分类</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column align="center" label="ID" width="80">
        <template #default="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="name" label="分类名称">
        <template #default="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="slug" label="别名">
        <template #default="scope">
          <span>{{ scope.row.slug }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="count" label="文章数" width="100" align="center">
        <template #default="scope">
          <span>{{ scope.row.count }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            @click="handleEdit(scope.row)"
          >编辑</el-button>
          <el-button
            type="danger"
            size="small"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑分类对话框 -->
    <el-dialog
      :title="dialogStatus === 'create' ? '添加分类' : '编辑分类'"
      v-model="dialogFormVisible"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="别名" prop="slug">
          <el-input v-model="temp.slug" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" :rows="2" v-model="temp.description" />
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
  name: 'CategoryManagement',
  setup() {
    const list = ref([])
    const listLoading = ref(true)
    const dialogFormVisible = ref(false)
    const dialogStatus = ref('')
    const dataFormRef = ref(null)
    
    // 临时数据
    const temp = reactive({
      id: undefined,
      name: '',
      slug: '',
      description: '',
      count: 0
    })
    
    // 校验规则
    const rules = {
      name: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }],
      slug: [{ required: true, message: '分类别名不能为空', trigger: 'blur' }]
    }
    
    // 获取分类列表
    const getList = () => {
      listLoading.value = true
      
      // 模拟API请求
      setTimeout(() => {
        const mockData = [
          { id: 1, name: '前端', slug: 'frontend', description: '前端开发相关', count: 42 },
          { id: 2, name: '后端', slug: 'backend', description: '后端开发相关', count: 28 },
          { id: 3, name: '移动端', slug: 'mobile', description: '移动端开发相关', count: 18 },
          { id: 4, name: '设计', slug: 'design', description: 'UI/UX设计相关', count: 12 },
          { id: 5, name: '运维', slug: 'ops', description: '运维相关', count: 8 }
        ]
        
        list.value = mockData
        listLoading.value = false
      }, 500)
    }
    
    // 重置表单
    const resetTemp = () => {
      temp.id = undefined
      temp.name = ''
      temp.slug = ''
      temp.description = ''
      temp.count = 0
    }
    
    // 添加分类
    const handleAddCategory = () => {
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
    
    // 编辑分类
    const handleEdit = (row) => {
      temp.id = row.id
      temp.name = row.name
      temp.slug = row.slug
      temp.description = row.description
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
    
    // 删除分类
    const handleDelete = (index, row) => {
      ElMessageBox.confirm('确定要删除这个分类吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 模拟删除操作
        list.value.splice(index, 1)
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
    
    // 创建分类
    const createData = () => {
      dataFormRef.value.validate((valid) => {
        if (valid) {
          // 模拟API请求
          setTimeout(() => {
            temp.id = list.value.length + 1
            list.value.push({
              id: temp.id,
              name: temp.name,
              slug: temp.slug,
              description: temp.description,
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
    
    // 更新分类
    const updateData = () => {
      dataFormRef.value.validate((valid) => {
        if (valid) {
          // 模拟API请求
          setTimeout(() => {
            const index = list.value.findIndex(item => item.id === temp.id)
            if (index > -1) {
              list.value.splice(index, 1, {
                ...list.value[index],
                ...temp
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
      list,
      listLoading,
      dialogFormVisible,
      dialogStatus,
      dataFormRef,
      temp,
      rules,
      handleAddCategory,
      handleEdit,
      handleDelete,
      createData,
      updateData
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.filter-container {
  padding-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style> 
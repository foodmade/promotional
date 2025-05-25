<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.search"
        placeholder="邮箱或姓名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter="handleFilter"
      />
      <el-button class="filter-item" type="primary" icon="Search" @click="handleFilter">
        搜索
      </el-button>
      <el-button
        class="filter-item"
        type="success"
        icon="Upload"
        @click="handleExport"
      >
        导出订阅者
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column label="ID" prop="id" align="center" width="80" />
      <el-table-column label="姓名" min-width="120">
        <template #default="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="200">
        <template #default="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订阅时间" width="180">
        <template #default="scope">
          <span>{{ scope.row.subscribeDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.active ? 'success' : 'info'">
            {{ scope.row.active ? '已订阅' : '已退订' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            v-if="!scope.row.active"
            type="success"
            size="small"
            @click="handleChangeStatus(scope.row, true)"
          >
            激活
          </el-button>
          <el-button
            v-else
            type="info"
            size="small"
            @click="handleChangeStatus(scope.row, false)"
          >
            禁用
          </el-button>
          <el-button
            type="danger"
            size="small"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="listQuery.page"
      v-model:limit="listQuery.limit"
      @pagination="getList"
    />

    <!-- 导出确认对话框 -->
    <el-dialog
      v-model="exportDialogVisible"
      title="导出订阅者数据"
      width="30%"
    >
      <div class="export-options">
        <p>请选择导出格式：</p>
        <el-radio-group v-model="exportFormat">
          <el-radio label="csv">CSV 格式</el-radio>
          <el-radio label="excel">Excel 格式</el-radio>
        </el-radio-group>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="exportDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmExport" :loading="exportLoading">
            确认导出
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import Pagination from '@/components/Pagination/index.vue';
import ApiFactory from '@/api';

export default {
  name: 'SubscriberList',
  components: { Pagination },
  setup() {
    const list = ref([]);
    const total = ref(0);
    const listLoading = ref(true);
    const exportDialogVisible = ref(false);
    const exportFormat = ref('csv');
    const exportLoading = ref(false);
    
    const listQuery = reactive({
      page: 1,
      limit: 10,
      search: ''
    });

    // 获取订阅者列表
    const getList = async () => {
      listLoading.value = true;
      try {
        // 这里假设有一个订阅者API，实际需要创建
        // const subscriberApi = ApiFactory.getSubscriberApi();
        // const response = await subscriberApi.getSubscribers(listQuery);
        
        // 模拟API调用结果
        const response = {
          isSuccess: () => true,
          data: {
            items: [
              {
                id: 1,
                name: '张三',
                email: 'zhangsan@example.com',
                subscribeDate: '2025-04-15 14:30:22',
                active: true
              },
              {
                id: 2,
                name: '李四',
                email: 'lisi@example.com',
                subscribeDate: '2025-04-16 09:12:35',
                active: true
              },
              {
                id: 3,
                name: '王五',
                email: 'wangwu@example.com',
                subscribeDate: '2025-04-17 11:45:03',
                active: false
              }
            ],
            total: 3
          }
        };
        
        if (response.isSuccess()) {
          list.value = response.data.items || [];
          total.value = response.data.total || 0;
        } else {
          ElMessage({
            message: response.message || '获取订阅者列表失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取订阅者列表出错:', error);
        ElMessage({
          message: '获取订阅者列表出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        listLoading.value = false;
      }
    };

    // 筛选列表
    const handleFilter = () => {
      listQuery.page = 1;
      getList();
    };

    // 修改订阅状态
    const handleChangeStatus = (row, status) => {
      const statusText = status ? '激活' : '禁用';
      ElMessageBox.confirm(
        `确认${statusText}订阅者 "${row.name}" 吗？`,
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        try {
          // 模拟API调用
          // const subscriberApi = ApiFactory.getSubscriberApi();
          // const response = await subscriberApi.updateSubscriberStatus(row.id, status);
          
          // 模拟成功响应
          ElMessage({
            message: `${statusText}成功`,
            type: 'success',
            duration: 2000
          });
          
          // 更新本地状态
          row.active = status;
        } catch (error) {
          console.error(`${statusText}订阅者出错:`, error);
          ElMessage({
            message: `${statusText}订阅者出错，请稍后重试`,
            type: 'error',
            duration: 5000
          });
        }
      }).catch(() => {
        // 取消操作
      });
    };

    // 删除订阅者
    const handleDelete = (row) => {
      ElMessageBox.confirm(
        `确认删除订阅者 "${row.name}" 吗？`,
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        try {
          // 模拟API调用
          // const subscriberApi = ApiFactory.getSubscriberApi();
          // const response = await subscriberApi.deleteSubscriber(row.id);
          
          // 模拟成功响应
          ElMessage({
            message: '删除成功',
            type: 'success',
            duration: 2000
          });
          
          // 刷新列表
          getList();
        } catch (error) {
          console.error('删除订阅者出错:', error);
          ElMessage({
            message: '删除订阅者出错，请稍后重试',
            type: 'error',
            duration: 5000
          });
        }
      }).catch(() => {
        // 取消删除操作
      });
    };

    // 导出订阅者数据
    const handleExport = () => {
      exportDialogVisible.value = true;
    };

    // 确认导出
    const confirmExport = async () => {
      exportLoading.value = true;
      try {
        // 模拟导出过程
        await new Promise(resolve => setTimeout(resolve, 1500));
        
        ElMessage({
          message: `订阅者数据已成功导出为${exportFormat.value.toUpperCase()}格式`,
          type: 'success',
          duration: 2000
        });
        
        exportDialogVisible.value = false;
      } catch (error) {
        console.error('导出订阅者数据出错:', error);
        ElMessage({
          message: '导出订阅者数据出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        exportLoading.value = false;
      }
    };

    onMounted(() => {
      getList();
    });

    return {
      list,
      total,
      listLoading,
      listQuery,
      exportDialogVisible,
      exportFormat,
      exportLoading,
      getList,
      handleFilter,
      handleChangeStatus,
      handleDelete,
      handleExport,
      confirmExport
    };
  }
};
</script>

<style lang="scss" scoped>
.export-options {
  padding: 10px 0;
  
  p {
    margin-bottom: 15px;
  }
}
</style> 
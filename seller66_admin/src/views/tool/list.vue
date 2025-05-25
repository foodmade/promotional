<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.search"
        placeholder="工具名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter="handleFilter"
      />
      <el-button class="filter-item" type="primary" icon="Search" @click="handleFilter">
        搜索
      </el-button>
      <el-button
        class="filter-item"
        type="primary"
        icon="Plus"
        @click="handleCreate"
      >
        添加工具
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
      <el-table-column label="ID" prop="id" align="center" width="60" />
      <el-table-column label="Logo" width="180">
        <template #default="scope">
          <img :src="scope.row.image" style="height: 40px;" />
        </template>
      </el-table-column>
      <el-table-column label="工具名称" min-width="150">
        <template #default="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" width="120">
        <template #default="scope">
          <span>{{ scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" width="120">
        <template #default="scope">
          <el-rate
            v-model="scope.row.rating"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述" min-width="250">
        <template #default="scope">
          <el-tooltip :content="scope.row.description" placement="top" :show-after="500">
            <span>{{ scope.row.description.slice(0, 50) }}{{ scope.row.description.length > 50 ? '...' : '' }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="官网" align="center" width="100">
        <template #default="scope">
          <el-link type="primary" :href="scope.row.website" target="_blank">访问</el-link>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            @click="handleUpdate(scope.row)"
          >
            编辑
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
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import Pagination from '@/components/Pagination/index.vue';
import ApiFactory from '@/api';

export default {
  name: 'ToolList',
  components: { Pagination },
  setup() {
    const router = useRouter();
    const list = ref([]);
    const total = ref(0);
    const listLoading = ref(true);
    const listQuery = reactive({
      page: 1,
      limit: 10,
      search: ''
    });

    const getList = async () => {
      listLoading.value = true;
      try {
        const toolApi = ApiFactory.getToolApi();
        const response = await toolApi.getTools(listQuery);
        
        if (response.isSuccess()) {
          list.value = response.data.items || [];
          total.value = response.data.total || 0;
        } else {
          ElMessage({
            message: response.message || '获取工具列表失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取工具列表出错:', error);
        ElMessage({
          message: '获取工具列表出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        listLoading.value = false;
      }
    };

    const handleFilter = () => {
      listQuery.page = 1;
      getList();
    };

    const handleCreate = () => {
      router.push('/tool/create');
    };

    const handleUpdate = (row) => {
      router.push(`/tool/edit/${row.id}`);
    };

    const handleDelete = (row) => {
      ElMessageBox.confirm(
        `确认删除工具 "${row.name}" 吗？`,
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        try {
          const toolApi = ApiFactory.getToolApi();
          const response = await toolApi.deleteTool(row.id);
          
          if (response.isSuccess()) {
            ElMessage({
              message: '删除成功',
              type: 'success',
              duration: 2000
            });
            getList();
          } else {
            ElMessage({
              message: response.message || '删除工具失败',
              type: 'error',
              duration: 5000
            });
          }
        } catch (error) {
          console.error('删除工具出错:', error);
          ElMessage({
            message: '删除工具出错，请稍后重试',
            type: 'error',
            duration: 5000
          });
        }
      }).catch(() => {
        // 取消删除操作
      });
    };

    onMounted(() => {
      getList();
    });

    return {
      list,
      total,
      listLoading,
      listQuery,
      getList,
      handleFilter,
      handleCreate,
      handleUpdate,
      handleDelete
    };
  }
};
</script> 
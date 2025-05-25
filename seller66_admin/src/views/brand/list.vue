<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.search"
        placeholder="品牌名称"
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
        添加品牌
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
      style="width: 100%; margin-top: 20px;"
      :row-style="{ height: '60px' }"
    >
      <el-table-column label="ID" prop="id" align="center" width="60" />
      <el-table-column label="品牌Logo" width="120" align="center">
        <template #default="scope">
          <img
            :src="scope.row.logo"
            style="height: 40px; max-width: 80px; border-radius: 6px; box-shadow: 0 2px 8px #f0f1f2; object-fit: contain;"
          />
        </template>
      </el-table-column>
      <el-table-column label="品牌名称" min-width="160" align="left">
        <template #default="scope">
          <span style="font-weight: 500;">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" width="180">
        <template #default="scope">
          <el-rate
            v-model="scope.row.rating"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
            style="min-width: 140px;"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述" min-width="220" align="left" show-overflow-tooltip>
        <template #default="scope">
          <span style="color: #888;">{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            @click="handleUpdate(scope.row)"
            style="margin-right: 10px;"
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
import { ElMessage, ElMessageBox } from 'element-plus';
import Pagination from '@/components/Pagination/index.vue';
import ApiFactory from '@/api';

export default {
  name: 'BrandList',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        search: ''
      }
    };
  },
  methods: {
    async getList() {
      this.listLoading = true;
      try {
        const brandApi = ApiFactory.getBrandApi();
        const response = await brandApi.getBrands();
        
        if (response.isSuccess()) {
          this.list = response.data || [];
          this.total = response.total || 0;
        } else {
          ElMessage({
            message: response.message || '获取品牌列表失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取品牌列表出错:', error);
        ElMessage({
          message: '获取品牌列表出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        this.listLoading = false;
      }
    },

    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },

    handleCreate() {
      this.$router.push('/brand/create');
    },

    handleUpdate(row) {
      this.$router.push(`/brand/edit/${row.id}`);
    },

    handleDelete(row) {
      ElMessageBox.confirm(
        `确认删除品牌 "${row.name}" 吗？`,
        '提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        try {
          const brandApi = ApiFactory.getBrandApi();
          const response = await brandApi.deleteBrand(row.id);
          
          if (response.isSuccess()) {
            ElMessage({
              message: '删除成功',
              type: 'success',
              duration: 2000
            });
            this.getList();
          } else {
            ElMessage({
              message: response.message || '删除品牌失败',
              type: 'error',
              duration: 5000
            });
          }
        } catch (error) {
          console.error('删除品牌出错:', error);
          ElMessage({
            message: '删除品牌出错，请稍后重试',
            type: 'error',
            duration: 5000
          });
        }
      }).catch(() => {
        // 取消删除操作
      });
    }
  },
  mounted() {
    this.getList();
  }
};
</script>

<style scoped>
.app-container {
  padding: 24px;
  background: #fff;
  border-radius: 8px;
}
.filter-container {
  margin-bottom: 18px;
  display: flex;
  align-items: center;
  gap: 12px;
}
.el-table th, .el-table td {
  vertical-align: middle !important;
}
.el-table .el-button {
  min-width: 60px;
}
</style> 
<template>
  <div class="app-container">
    <div class="header">
      <el-button type="primary" @click="handleCreate">新增客服方式</el-button>
    </div>
    <el-table :data="supportList" border style="width: 100%; margin-top: 20px;">
      <el-table-column label="类型" prop="type" width="100" align="center" />
      <el-table-column label="标题" prop="title" width="140" align="center" />
      <el-table-column label="说明" prop="description" min-width="180" align="center" />
      <el-table-column label="二维码" width="160" align="center">
        <template #default="scope">
          <img :src="scope.row.qrcode" alt="二维码" style="height: 80px; width: 80px; object-fit: contain; border: 1px solid #eee; border-radius: 4px;" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <el-button size="small" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="editDialogVisible" title="编辑客服方式" width="500px" :close-on-click-modal="false">
      <SupportEdit :editData="editData" @success="handleEditSuccess" @cancel="editDialogVisible=false" />
    </el-dialog>
  </div>
</template>

<script>
import SupportEdit from './edit.vue';
import ApiFactory from '@/api';
export default {
  name: 'SupportList',
  components: { SupportEdit },
  data() {
    return {
      supportList: [],
      editDialogVisible: false,
      editData: null
    };
  },
  methods: {
    async fetchList() {
      const supportApi = ApiFactory.getSupportApi();
      const response = await supportApi.getSupportList();
      if (response.isSuccess()) {
        this.supportList = response.data;
      }
    },
    handleCreate() {
      this.editData = null;
      this.editDialogVisible = true;
    },
    handleEdit(row) {
      this.editData = { ...row };
      this.editDialogVisible = true;
    },
    handleDelete(row) {
      this.$confirm('确定要删除该客服方式吗？', '提示', { type: 'warning' })
        .then(() => {
          // TODO: 调用接口删除
          this.$message.success('删除成功');
          this.fetchList();
        })
        .catch(() => {});
    },
    async handleEditSuccess() {
      await this.fetchList();
      this.editDialogVisible = false;
    }
  },
  mounted() {
    this.fetchList();
  }
};
</script>

<style scoped>
.app-container {
  padding: 24px;
  background: #fff;
  border-radius: 8px;
}
.header {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 12px;
}
</style> 
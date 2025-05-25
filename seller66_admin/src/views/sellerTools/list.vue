<template>
  <div class="app-container seller-tools-list">
    <el-card shadow="never" class="mb16">
      <el-button type="primary" icon="Plus" @click="handleCreate" round>新增产品</el-button>
    </el-card>
    <el-table
      :data="list"
      v-loading="loading"
      border
      stripe
      highlight-current-row
      class="seller-table"
    >
      <el-table-column label="图片" width="80">
        <template #default="scope">
          <img :src="scope.row.image" class="tool-img" />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" min-width="120"/>
      <el-table-column prop="rating" label="评分" width="120">
        <template #default="scope">
          <el-rate v-model="scope.row.rating" :max="10" disabled show-score score-template="{value}" />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="最低价格" width="110">
        <template #default="scope">
          <span v-if="scope.row.price">￥{{ scope.row.price }}</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column prop="purchase_count" label="评论数" width="100"/>
      <el-table-column prop="free_trial_days" label="免费天数" width="100"/>
      <el-table-column prop="rank" label="排名" width="80"/>
      <el-table-column prop="type" label="类型" width="100"/>
      <el-table-column prop="short_description" label="描述" min-width="180">
        <template #default="scope">
          <el-tooltip :content="scope.row.short_description" placement="top">
            <span class="desc-ellipsis">{{ scope.row.short_description }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="scope">
          <el-space>
            <el-button size="small" @click="handleEdit(scope.row.id)" icon="Edit">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)" icon="Delete">删除</el-button>
          </el-space>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="pageNum"
      v-model:limit="pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import ApiFactory from '@/api';

const router = useRouter();
const list = ref([]);
const total = ref(0);
const pageNum = ref(1);
const pageSize = ref(10);
const loading = ref(false);

const getList = async () => {
  loading.value = true;
  const sellerToolsApi = ApiFactory.getSellerToolsApi();
  const res = await sellerToolsApi.getList({pageNum:pageNum.value,pageSize:pageSize.value});
  if (res.isSuccess()) {
    list.value = res.data;
    total.value = res.total;
  }
  loading.value = false;
};

const handleCreate = () => {
  router.push('/sellerTools/edit');
};
const handleEdit = (id) => {
  router.push(`/sellerTools/edit/${id}`);
};
const handleDelete = (id) => {
  ElMessageBox.confirm('确定删除该工具吗？', '提示', { type: 'warning' }).then(async () => {
    const api = ApiFactory.getSellerToolsApi();
    const res = await api.remove(id);
    if (res.isSuccess()) {
      ElMessage.success('删除成功');
      getList();
    }
  });
};

onMounted(getList);
</script>

<style scoped>
.seller-tools-list .mb16 { margin-bottom: 16px; }
.seller-table .tool-img {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 8px #eee;
  object-fit: cover;
}
.desc-ellipsis {
  display: inline-block;
  max-width: 140px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: bottom;
}
</style> 
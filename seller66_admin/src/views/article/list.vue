<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        placeholder="标题"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter="handleFilter"
      />
      <el-select
        v-model="listQuery.category"
        placeholder="分类"
        clearable
        style="width: 180px"
        class="filter-item"
      >
        <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-select
        v-model="listQuery.status"
        placeholder="状态"
        clearable
        style="width: 130px"
        class="filter-item"
      >
        <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" :icon="Search" @click="handleFilter">
        搜索
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        :icon="Plus"
        @click="handleCreate"
      >
        新增
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column align="center" label="ID" width="80">
        <template #default="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      
      <el-table-column width="180px" align="center" label="日期">
        <template #default="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      
      <el-table-column min-width="300px" label="标题">
        <template #default="scope">
          <router-link :to="'/article/edit/'+scope.row.id" class="link-type">
            <span>{{ scope.row.title }}</span>
          </router-link>
        </template>
      </el-table-column>
      
      <el-table-column width="120px" align="center" label="作者">
        <template #default="scope">
          <span>{{ scope.row.authorName }}</span>
        </template>
      </el-table-column>
      
      <el-table-column width="100px" label="访问量">
        <template #default="scope">
          <span>{{ scope.row.pageviews }}</span>
        </template>
      </el-table-column>
      
      <el-table-column class-name="status-col" label="状态" width="110">
        <template #default="scope">
          <el-tag :type="scope.row.status | statusFilter">
            {{ scope.row.status === 'published' ? '已发布' : '草稿' }}
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column align="center" label="操作" width="230">
        <template #default="scope">
          <router-link :to="'/article/edit/'+scope.row.id">
            <el-button type="primary" size="small" :icon="Edit">编辑</el-button>
          </router-link>
          <el-button
            type="danger"
            size="small"
            :icon="Delete"
            @click="handleDelete(scope.row.id)"
          >删除</el-button>
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

<script>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import Pagination from '@/components/Pagination/index.vue'
import ApiFactory from '@/api';

export default {
  name: 'ArticleList',
  components: { Pagination },
  data(){
    return{
      list: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      listLoading: true,
      listQuery:{
        title:'',
        tags:'',
      }
    }
  },
  methods:{
    async getList(){
      this.listLoading = true;
      const articleApi = ApiFactory.getArticleApi();
      this.listQuery.pageNum = this.pageNum;
      this.listQuery.pageSize = this.pageSize;
      const response = await articleApi.getArticles(this.listQuery);
      
      if(response.isSuccess()){
        console.log('getArticleList:'+JSON.stringify(response))
        this.list = response.data;
        this.total = response.total;
        console.log('total:'+this.total)
        this.listLoading = false;
      }else{
        this.listLoading = false;
      }
    },
    async handleFilter(){
      this.getList();
    },
    async handleDelete(id){
      ElMessageBox.confirm('确定要删除这篇文章吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const articleApi = ApiFactory.getArticleApi();
        const response = await articleApi.deleteArticle(id);
        if(response.isSuccess()){
          ElMessage.success('删除成功!');
          this.getList();
        }else{
          ElMessage.error(response.message || '删除失败');
        }
      })
    }
  },
  mounted(){
    this.getList();
  },
  setup() {
    const router = useRouter()
    
    // 新建文章
    const handleCreate = () => {
      router.push('/article/create')
    }
  
  
    return {
      handleCreate
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.filter-container {
  padding-bottom: 10px;
  
  .filter-item {
    margin-bottom: 10px;
    margin-right: 10px;
  }
}

.link-type,
.link-type:focus {
  color: #409EFF;
  cursor: pointer;
  text-decoration: none;
  
  &:hover {
    color: #66b1ff;
  }
}
</style> 
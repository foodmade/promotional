<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="Plus"
        @click="handleCreateTeamMember"
      >
        添加作者
      </el-button>
    </div>

    <div class="team-list">
      <el-row :gutter="20">
        <el-col 
          v-for="member in list" 
          :key="member.id" 
          :xs="24" 
          :sm="12" 
          :md="8" 
          :lg="6" 
          :xl="6"
          class="team-item-col"
        >
          <el-card class="team-item" shadow="hover">
            <div class="member-avatar">
              <img :src="member.avatar" alt="团队成员头像" v-if="member.avatar" />
              <div class="avatar-placeholder" v-else>{{ getInitials(member.name) }}</div>
            </div>
            <div class="member-info">
              <h3 class="member-name">{{ member.name }}</h3>
              <div class="member-title">{{ member.title }}</div>
              <div class="member-bio">{{ member.bio }}</div>
              <div class="member-socials" v-if="member.socialLinks && member.socialLinks.length">
                <a 
                  v-for="(social, index) in member.socialLinks" 
                  :key="index" 
                  :href="social.url" 
                  target="_blank" 
                  class="social-link"
                >
                  {{ social.name }}
                </a>
              </div>
            </div>
            <div class="member-actions">
              <el-button
                size="small"
                type="primary"
                icon="Edit"
                @click="handleEdit(member)"
              />
              <el-button
                size="small"
                type="danger"
                icon="Delete"
                @click="handleDelete(member)"
              />
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="pagination-container">
      <el-pagination
        v-show="total > 0"
        :current-page="listQuery.page"
        :page-sizes="[8, 16, 24, 32]"
        :page-size="listQuery.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import ApiFactory from '@/api';

export default {
  name: 'TeamList',
  setup() {
    const router = useRouter();
    const list = ref([]);
    const total = ref(0);
    const listLoading = ref(true);
    const listQuery = reactive({
      page: 1,
      limit: 16,
      search: '',
    });

    // 获取团队成员列表
    const getList = async () => {
      listLoading.value = true;
      try {
        const authorApi = ApiFactory.getAuthorApi();
        const response = await authorApi.getAuthorList({
          page: listQuery.page,
          limit: listQuery.limit,
          search: listQuery.search
        });

        if (response.isSuccess()) {
          list.value = response.data || [];
          total.value = response.total || 0;
        } else {
          ElMessage({
            message: response.message || '获取团队成员列表失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取团队成员列表出错:', error);
        ElMessage({
          message: '获取团队成员列表出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        listLoading.value = false;
      }
    };

    // 筛选
    const handleFilter = () => {
      listQuery.page = 1;
      getList();
    };

    // 新建团队成员
    const handleCreateTeamMember = () => {
      router.push('/author/create');
    };

    // 编辑团队成员
    const handleEdit = (row) => {
      router.push(`/author/edit/${row.id}`);
    };

    // 删除团队成员
    const handleDelete = (row) => {
      ElMessageBox.confirm('确定要删除该团队成员吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const authorApi = ApiFactory.getAuthorApi();
          const response = await authorApi.deleteAuthor(row.id);

          if (response.isSuccess()) {
            ElMessage({
              type: 'success',
              message: '删除成功!'
            });
            getList();
          } else {
            ElMessage({
              type: 'error',
              message: response.message || '删除失败'
            });
          }
        } catch (error) {
          console.error('删除团队成员出错:', error);
          ElMessage({
            type: 'error',
            message: '删除团队成员出错，请稍后重试'
          });
        }
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消删除'
        });
      });
    };

    // 获取名字首字母作为头像占位符
    const getInitials = (name) => {
      if (!name) return '';
      return name.substring(0, 2).toUpperCase();
    };

    // 分页
    const handleSizeChange = (val) => {
      listQuery.limit = val;
      getList();
    };

    const handleCurrentChange = (val) => {
      listQuery.page = val;
      getList();
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
      handleCreateTeamMember,
      handleEdit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange,
      getInitials
    };
  }
};
</script>

<style lang="scss" scoped>
.filter-container {
  padding-bottom: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.filter-item {
  display: inline-block;
  vertical-align: middle;
  margin-bottom: 10px;
}

.team-list {
  margin-top: 20px;
}

.team-item-col {
  margin-bottom: 20px;
}

.team-item {
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  
  .member-avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin: 0 auto 15px;
    overflow: hidden;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    
    .avatar-placeholder {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #409EFF;
      color: white;
      font-size: 28px;
      font-weight: bold;
    }
  }
  
  .member-info {
    text-align: center;
    flex: 1;
    
    .member-name {
      margin: 0 0 5px;
      font-size: 18px;
      font-weight: bold;
    }
    
    .member-title {
      color: #666;
      font-size: 14px;
      margin-bottom: 10px;
    }
    
    .member-bio {
      font-size: 13px;
      color: #606266;
      margin-bottom: 15px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
    
    .member-socials {
      display: flex;
      justify-content: center;
      flex-wrap: wrap;
      gap: 10px;
      
      .social-link {
        font-size: 12px;
        color: #409EFF;
        text-decoration: none;
        
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
  
  .member-actions {
    margin-top: 15px;
    display: flex;
    justify-content: center;
    gap: 10px;
  }
}

.pagination-container {
  padding: 20px 0;
}
</style> 
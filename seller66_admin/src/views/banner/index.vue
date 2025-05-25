<template>
  <div class="banner-container">
    <div class="banner-header">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="标题">
          <el-input v-model="searchForm.title" placeholder="请输入标题" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable style="width: 120px">
            <el-option label="全部" :value="''" />
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="handleAdd">新增Banner</el-button>
    </div>
    
    <el-table :data="bannerList" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="imageUrl" label="图片">
        <template #default="scope">
          <el-image 
            style="width: 100px; height: 60px"
            :src="scope.row.imageUrl"
            :preview-src-list="[scope.row.imageUrl]"
            :preview-teleported="true"
          />
        </template>
      </el-table-column>
      <el-table-column prop="link" label="跳转链接" show-overflow-tooltip />
      <el-table-column prop="sort" label="排序" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Banner表单对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form :model="bannerForm" :rules="rules" ref="bannerFormRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="bannerForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <el-upload
            class="avatar-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleUploadSuccess"
            :before-upload="beforeUpload">
            <img v-if="bannerForm.imageUrl" :src="bannerForm.imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="跳转链接" prop="link">
          <el-input v-model="bannerForm.link" placeholder="请输入跳转链接" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="bannerForm.sort" :min="0" :max="999" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="bannerForm.status"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import ApiFactory from '@/api/index'

export default {
  name: 'Banner',
  components: {
    Plus
  },
  data() {
    return {
      bannerList: [],
      dialogVisible: false,
      dialogTitle: '',
      searchForm: {
        title: '',
        status: ''
      },
      bannerForm: {
        id: null,
        title: '',
        imageUrl: '',
        link: '',
        sort: 0,
        status: 1
      },
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        imageUrl: [{ required: true, message: '请上传图片', trigger: 'change' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 获取Banner列表
    async fetchBannerList() {
      try {
        const api = await ApiFactory.getBannerApi();
        console.log('searchForm:'+JSON.stringify(this.searchForm))
        const res = await api.getBannerList(this.searchForm);
        this.bannerList = res.data || [];
      } catch (error) {
        ElMessage.error('获取Banner列表失败')
      }
    },

    // 新增Banner
    handleAdd() {
      this.dialogTitle = '新增Banner'
      Object.assign(this.bannerForm, {
        id: null,
        title: '',
        imageUrl: '',
        link: '',
        sort: 0,
        status: 1
      })
      this.dialogVisible = true
    },

    // 编辑Banner
    handleEdit(row) {
      this.dialogTitle = '编辑Banner'
      Object.assign(this.bannerForm, row)
      this.dialogVisible = true
    },

    // 删除Banner
    handleDelete(row) {
      ElMessageBox.confirm('确认删除该Banner吗？', '提示', {
        type: 'warning'
      }).then(async () => {
        try {
          await ApiFactory.getBannerApi().deleteBanner(row.id)
          ElMessage.success('删除成功')
          this.fetchBannerList()
        } catch (error) {
          ElMessage.error('删除失败')
        }
      })
    },

    // 提交表单
    async handleSubmit() {
      if (!this.$refs.bannerFormRef) return
      await this.$refs.bannerFormRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.bannerForm.id) {
              await ApiFactory.getBannerApi().updateBanner(this.bannerForm)
              ElMessage.success('更新成功')
            } else {
              await ApiFactory.getBannerApi().createBanner(this.bannerForm)
              ElMessage.success('创建成功')
            }
            this.dialogVisible = false
            this.fetchBannerList()
          } catch (error) {
            ElMessage.error(this.bannerForm.id ? '更新失败' : '创建失败')
          }
        }
      })
    },

    // 上传图片相关方法
    handleUploadSuccess(res) {
      this.bannerForm.imageUrl = res.data.url
    },

    async beforeUpload(file) {
        try {
        const uploadApi = ApiFactory.getUploadApi();
        const response = await uploadApi.uploadImage(file);
        console.log('uploadImage:'+JSON.stringify(response))
        if (response.isSuccess()) {
          this.bannerForm.imageUrl = response.data.url;
          ElMessage.success('图上传成功');
        } else {
          ElMessage.error(response.message || '上传失败');
        }
      } catch (error) {
        console.error('上传图片失败:', error);
        ElMessage.error('上传失败，请重试');
      }
    },

    handleSearch() {
      this.fetchBannerList();
    },

    handleReset() {
      this.searchForm.title = '';
      this.searchForm.status = '';
      this.fetchBannerList();
    }
  },
  mounted() {
    this.fetchBannerList()
  }
}
</script>

<style lang="scss" scoped>
.banner-container {
  padding: 20px;

  .banner-header {
    margin-bottom: 20px;
  }

  .avatar-uploader {
    :deep(.el-upload) {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);

      &:hover {
        border-color: var(--el-color-primary);
      }
    }
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 60px;
    text-align: center;
    line-height: 60px;
  }

  .avatar {
    width: 100px;
    height: 60px;
    display: block;
  }
}
</style> 
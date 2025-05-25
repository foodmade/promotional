<template>
  <div class="app-container">
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="form-container"
    >
      <el-form-item label="品牌名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入品牌名称" />
      </el-form-item>

      <el-form-item label="品牌Slug" prop="slug">
        <el-input v-model="form.slug" placeholder="请输入品牌Slug" />
        <div class="form-tip">用于URL友好链接，只能包含字母、数字和连字符</div>
      </el-form-item>

      <el-form-item label="品牌Logo" prop="logo">
        <el-upload
          class="logo-uploader"
          :show-file-list="false"
          :before-upload="beforeLogoUpload"
          :http-request="customLogoUpload"
        >
          <img v-if="form.logo" :src="form.logo" class="logo-preview" />
          <el-icon v-else class="logo-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>

      <el-form-item label="品牌描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="4"
          placeholder="请输入品牌描述"
        />
      </el-form-item>

      <el-form-item label="评分" prop="rating">
        <el-rate
          v-model="form.rating"
          show-score
          text-color="#ff9900"
          score-template="{value}"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">
          {{ isEdit ? '更新' : '创建' }}
        </el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus';
import ApiFactory from '@/api';
import { Plus } from '@element-plus/icons-vue';

export default {
  name: 'BrandEdit',
  components: { Plus },
  data() {
    return {
      form: {
        id: null,
        name: '',
        slug: '',
        logo: '',
        description: '',
        rating: 5
      },
      loading: false,
      rules: {
        slug: [
          { required: true, message: '请输入品牌Slug', trigger: 'blur' },
          { pattern: /^[a-z0-9-]+$/, message: '只能包含小写字母、数字和连字符', trigger: 'blur' }
        ],
        logo: [
          { required: true, message: '请上传Logo图片', trigger: 'blur' }
        ],
        rating: [
          { required: true, message: '请选择评分', trigger: 'change' }
        ]
      },
      isEdit: false
    };
  },
  methods: {
    async getBrandDetail(id) {
      this.loading = true;
      try {
        const brandApi = ApiFactory.getBrandApi();
        const response = await brandApi.getBrand(id);
        if (response.isSuccess()) {
          Object.assign(this.form, response.data);
        } else {
          ElMessage({
            message: response.message || '获取品牌详情失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取品牌详情出错:', error);
        ElMessage({
          message: '获取品牌详情出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        this.loading = false;
      }
    },
    // 上传前校验
    beforeLogoUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isImage) {
        ElMessage.error('只能上传图片文件!');
        return false;
      }
      if (!isLt2M) {
        ElMessage.error('图片大小不能超过2MB!');
        return false;
      }
      return true;
    },
    // 自定义上传方法
    async customLogoUpload(option) {
      try {
        const uploadApi = ApiFactory.getUploadApi();
        const response = await uploadApi.uploadImage(option.file);
        if (response.isSuccess()) {
          this.form.logo = response.data.url;
          ElMessage.success('Logo上传成功');
        } else {
          ElMessage.error(response.message || 'Logo上传失败');
        }
        option.onSuccess && option.onSuccess(response, option.file);
      } catch (error) {
        ElMessage.error('Logo上传失败，请重试');
        option.onError && option.onError(error);
      }
    },
    submitForm() {
      if (!this.$refs.formRef) return;
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          try {
            const brandApi = ApiFactory.getBrandApi();
            let response;
            if (this.isEdit) {
              response = await brandApi.updateBrand(this.form);
            } else {
              response = await brandApi.createBrand(this.form);
            }
            if (response.isSuccess()) {
              ElMessage({
                message: this.isEdit ? '品牌更新成功' : '品牌创建成功',
                type: 'success',
                duration: 2000
              });
              this.$router.push('/brand/list');
            } else {
              ElMessage({
                message: response.message || (this.isEdit ? '品牌更新失败' : '品牌创建失败'),
                type: 'error',
                duration: 5000
              });
            }
          } catch (error) {
            console.error(this.isEdit ? '更新品牌出错:' : '创建品牌出错:', error);
            ElMessage({
              message: this.isEdit ? '更新品牌出错，请稍后重试' : '创建品牌出错，请稍后重试',
              type: 'error',
              duration: 5000
            });
          } finally {
            this.loading = false;
          }
        }
      });
    },
    cancel() {
      this.$router.push('/brand/list');
    }
  },
  mounted() {
    this.isEdit = this.$route.params.id !== undefined;
    if (this.isEdit) {
      this.getBrandDetail(this.$route.params.id);
    }
  }
};
</script>

<style lang="scss" scoped>
.form-container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.form-tip {
  font-size: 12px;
  color: #909399;
  line-height: 1.5;
  margin-top: 4px;
}

.logo-uploader {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.logo-preview {
  width: 120px;
  height: 60px;
  object-fit: contain;
  border: 1px solid #eee;
  border-radius: 4px;
}
.logo-uploader-icon {
  font-size: 32px;
  color: #8c939d;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
  width: 120px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style> 
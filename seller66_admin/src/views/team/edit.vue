<template>
  <div class="app-container">
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      class="form-container"
    >
      <el-form-item label="作者姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入成员姓名" />
      </el-form-item>

      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" />
      </el-form-item>

      <el-form-item label="作者头像" prop="avatar">
        <el-upload
          class="avatar-uploader"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="(file) => beforeImageUpload(file.raw) && handleUpload(file.raw)"
        >
          <img v-if="form.avatar" :src="form.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <div class="el-upload__tip">建议尺寸：200x200px，支持jpg、png格式</div>
      </el-form-item>

      <el-form-item label="个人简介" prop="bio">
        <el-input
          v-model="form.bio"
          type="textarea"
          :rows="4"
          placeholder="请输入个人简介"
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
import { ref, reactive, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import ApiFactory from '@/api';

export default {
  name: 'TeamEdit',
  components: {
    Plus
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const formRef = ref(null);
    const loading = ref(false);

    // 判断是新建还是编辑
    const isEdit = computed(() => route.params.id !== undefined);

    // 表单数据
    const form = reactive({
      name: '',
      title: '',
      avatar: '',
      bio: '',
      socialLinks: [{ name: '', url: '' }]
    });

    // 表单验证规则
    const rules = {
      name: [
        { required: true, message: '请输入成员姓名', trigger: 'blur' },
        { min: 2, max: 30, message: '长度在2到30个字符之间', trigger: 'blur' }
      ],
      bio: [
        { required: true, message: '请输入个人简介', trigger: 'blur' },
        { min: 10, max: 300, message: '长度在10到300个字符之间', trigger: 'blur' }
      ]
    };

    // 获取团队成员详情
    const getTeamMemberDetail = async (id) => {
      loading.value = true;
      try {
        const authorApi = ApiFactory.getAuthorApi();
        const response = await authorApi.getAuthor(id);
        
        if (response.isSuccess()) {
          // 填充表单数据
          const memberData = response.data;
          Object.assign(form, memberData);
          
          // 确保社交链接是数组
          if (!Array.isArray(form.socialLinks) || form.socialLinks.length === 0) {
            form.socialLinks = [{ name: '', url: '' }];
          }
        } else {
          ElMessage({
            message: response.message || '获取团队成员详情失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('获取团队成员详情出错:', error);
        ElMessage({
          message: '获取团队成员详情出错，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        loading.value = false;
      }
    };

    // 上传前校验
    const beforeImageUpload = (file) => {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        ElMessage.error('只能上传图片文件!');
        return false;
      }
      if (!isLt2M) {
        ElMessage.error('图片大小不能超过 2MB!');
        return false;
      }
      return true;
    };

    // 处理图片上传
    const handleUpload = async (file) => {
      try {
        const uploadApi = ApiFactory.getUploadApi();
        const response = await uploadApi.uploadImage(file);
        
        if (response.isSuccess()) {
          form.avatar = response.data.url;
          ElMessage.success('头像上传成功');
        } else {
          ElMessage.error(response.message || '上传失败');
        }
      } catch (error) {
        console.error('上传图片失败:', error);
        ElMessage.error('上传失败，请重试');
      }
    };

    // 提交表单
    const submitForm = async () => {
      if (!formRef.value) return;

      await formRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true;
          try {
            
            const memberData = {
              ...form,
              id: route.params.id
            };
            
            const authorApi = ApiFactory.getAuthorApi();
            let response;
            console.log(memberData)
            if (isEdit.value) {
              // 更新团队成员
              response = await authorApi.updateAuthor(memberData);
            } else {
              // 创建团队成员
              response = await authorApi.createAuthor(memberData);
            }
            
            if (response.isSuccess()) {
              ElMessage({
                message: isEdit.value ? '团队成员更新成功' : '团队成员创建成功',
                type: 'success',
                duration: 2000
              });
              
              // 返回列表页
              router.push('/author/list');
            } else {
              ElMessage({
                message: response.message || (isEdit.value ? '团队成员更新失败' : '团队成员创建失败'),
                type: 'error',
                duration: 5000
              });
            }
          } catch (error) {
            console.error(isEdit.value ? '更新团队成员出错:' : '创建团队成员出错:', error);
            ElMessage({
              message: isEdit.value ? '更新团队成员出错，请稍后重试' : '创建团队成员出错，请稍后重试',
              type: 'error',
              duration: 5000
            });
          } finally {
            loading.value = false;
          }
        }
      });
    };

    // 取消操作
    const cancel = () => {
      router.push('/author/list');
    };

    onMounted(() => {
      if (isEdit.value) {
        getTeamMemberDetail(route.params.id);
      }
    });

    return {
      formRef,
      form,
      rules,
      loading,
      isEdit,
      submitForm,
      cancel,
      beforeImageUpload,
      handleUpload
    };
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

.social-item {
  margin-bottom: 10px;
}

.form-preview {
  margin-top: 10px;
  padding: 10px;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
  display: flex;
  justify-content: center;
}

.avatar-uploader {
  :deep(.el-upload) {
    border: 1px dashed #d9d9d9;
    border-radius: 50%;
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
  width: 120px;
  height: 120px;
  text-align: center;
  line-height: 120px;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  object-fit: cover;
  border-radius: 50%;
}
</style> 
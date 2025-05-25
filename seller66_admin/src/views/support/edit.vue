<template>
  <div>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="90px" class="form-container">
      <el-form-item label="类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择类型">
          <el-option label="微信客服" value="wechat" />
          <el-option label="QQ群客服" value="qq" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" />
      </el-form-item>
      <el-form-item label="说明" prop="description">
        <el-input v-model="form.description" placeholder="请输入说明" />
      </el-form-item>
      <el-form-item label="二维码" prop="qrCode">
        <el-upload
          class="qr-uploader"
          :show-file-list="false"
          :before-upload="beforeQrUpload"
          :http-request="customQrUpload"
        >
          <img v-if="form.qrcode" :src="form.qrcode" class="qr-preview" />
          <el-icon v-else class="qr-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">保存</el-button>
        <el-button @click="$emit('cancel')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus';
import ApiFactory from '@/api';
import { Plus } from '@element-plus/icons-vue';
export default {
  name: 'SupportEdit',
  components: { Plus },
  props: {
    editData: Object
  },
  data() {
    return {
      form: {
        id: null,
        type: '',
        title: '',
        description: '',
        qrcode: ''
      },
      loading: false,
      rules: {
        type: [{ required: true, message: '请选择类型', trigger: 'change' }],
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        description: [{ required: true, message: '请输入说明', trigger: 'blur' }],
        qrcode: [{ required: true, message: '请上传二维码', trigger: 'blur' }]
      }
    };
  },
  watch: {
    editData: {
      immediate: true,
      handler(val) {
        if (val) {
          this.form = { ...val };
        } else {
          this.form = { id: null, type: '', title: '', description: '', qrCode: '' };
        }
      }
    }
  },
  methods: {
    beforeQrUpload(file) {
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
    async customQrUpload(option) {
      try {
        const uploadApi = ApiFactory.getUploadApi();
        const response = await uploadApi.uploadImage(option.file);
        if (response.isSuccess()) {
          this.form.qrcode = response.data.url;
          ElMessage.success('二维码上传成功');
        } else {
          ElMessage.error(response.message || '二维码上传失败');
        }
        option.onSuccess && option.onSuccess(response, option.file);
      } catch (error) {
        ElMessage.error('二维码上传失败，请重试');
        option.onError && option.onError(error);
      }
    },
    submitForm() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          try {
            const supportApi = ApiFactory.getSupportApi();
            if (this.form.id) {
              await supportApi.updateSupport(this.form);
            } else {
              await supportApi.createSupport(this.form);
            }
            this.$emit('success');
            ElMessage.success('保存成功');
          } finally {
            this.loading = false;
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.form-container {
  padding: 10px 0;
}
.qr-uploader {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.qr-preview {
  width: 80px;
  height: 80px;
  object-fit: contain;
  border: 1px solid #eee;
  border-radius: 4px;
}
.qr-uploader-icon {
  font-size: 32px;
  color: #8c939d;
  border: 1px dashed #dcdfe6;
  border-radius: 4px;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style> 
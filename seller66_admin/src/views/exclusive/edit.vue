<template>
  <el-dialog :title="row && row.id ? '编辑独家优惠' : '新增独家优惠'" v-model="visible" width="500px" @close="$emit('close')">
    <el-form :model="form" ref="formRef" label-width="100px">
      <el-form-item label="Logo" prop="logo">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          :before-upload="beforeLogoUpload"
          :http-request="customLogoUpload"
        >
          <img v-if="form.logo" :src="form.logo" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="跳转链接" prop="link">
        <el-input v-model="form.link" placeholder="请输入跳转链接" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script>
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import ApiFactory from '@/api'

export default {
  name: 'ExclusiveOfferEditDialog',
  components: { Plus },
  props: {
    row: Object
  },
  data() {
    return {
      visible: true,
      formRef: null,
      form: {
        id: undefined,
        logo: '',
        link: ''
      }
    }
  },
  watch: {
    row: {
      immediate: true,
      handler(val) {
        if (val) {
          Object.assign(this.form, val)
        } else {
          Object.assign(this.form, {
            id: undefined,
            logo: '',
            link: ''
          })
        }
      }
    }
  },
  methods: {
    beforeLogoUpload(file) {
      return file.type.startsWith('image/')
    },
    async customLogoUpload(option) {
      const uploadApi = ApiFactory.getUploadApi()
      const res = await uploadApi.uploadImage(option.file)
      if (res.isSuccess()) {
        this.form.logo = res.data.url
        ElMessage.success('Logo上传成功')
      } else {
        ElMessage.error(res.message || 'Logo上传失败')
      }
      option.onSuccess && option.onSuccess(res, option.file)
    },
    async handleSubmit() {
      await this.$refs.formRef.validate()
      const api = ApiFactory.getExclusiveOfferApi()
      let res
      if (this.form.id) res = await api.update(this.form)
      else res = await api.create(this.form)
      if (res.isSuccess()) {
        ElMessage.success('保存成功')
        this.$emit('success')
        this.visible = false
      }
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.avatar {
  width: 80px;
  height: 80px;
  object-fit: contain;
  border: 1px solid #eee;
  border-radius: 4px;
}
.avatar-uploader-icon {
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
<template>
  <el-dialog :title="row && row.id ? '编辑弹窗' : '新增弹窗'" v-model="visible" width="600px" @close="$emit('close')">
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
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入标题" />
      </el-form-item>
      <el-form-item label="按钮文案" prop="buttonText">
        <el-input v-model="form.buttonText" placeholder="请输入按钮文案" />
      </el-form-item>
      <el-form-item label="链接地址" prop="offerLink">
        <el-input v-model="form.offerLink" placeholder="请输入链接地址" />
      </el-form-item>
      <el-form-item label="用户数" prop="userCount">
        <el-input-number v-model="form.userCount" :min="0" />
      </el-form-item>
      <el-form-item label="优惠金额" prop="discountAmount">
        <el-input-number v-model="form.discountAmount" :min="0" />
      </el-form-item>
      <el-form-item label="原价" prop="originalPrice">
        <el-input-number v-model="form.originalPrice" :min="0" />
      </el-form-item>
      <el-form-item label="优惠价" prop="specialPrice">
        <el-input-number v-model="form.specialPrice" :min="0" />
      </el-form-item>
      <el-form-item label="绑定页面" prop="popupId">
        <el-select v-model="form.popupId" placeholder="请选择绑定页面">
          <el-option label="amazon-seller-offer" value="amazon-seller-offer" />
          <el-option label="ebay-seller-offer" value="ebay-seller-offer" />
        </el-select>
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
  name: 'PromotionEditDialog',
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
        title: '',
        buttonText: '',
        offerLink: '',
        userCount: 0,
        discountAmount: 0,
        originalPrice: 0,
        specialPrice: 0,
        popupId: ''
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
            title: '',
            buttonText: '',
            offerLink: '',
            userCount: 0,
            discountAmount: 0,
            originalPrice: 0,
            specialPrice: 0,
            popupId: ''
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
      const api = ApiFactory.getPromotionPopupApi()
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
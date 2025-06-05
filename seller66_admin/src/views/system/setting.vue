<template>
  <div class="app-container">
    <el-tabs v-model="activeName" class="setting-tabs">
      <el-tab-pane label="基本设置" name="basic">
        <el-form
          ref="basicFormRef"
          :model="basicForm"
          label-width="120px"
          class="setting-form"
        >
          <el-form-item label="网站名称">
            <el-input v-model="basicForm.siteName" placeholder="请输入网站名称" />
          </el-form-item>
          <el-form-item label="网站描述">
            <el-input
              v-model="basicForm.siteDescription"
              type="textarea"
              :rows="2"
              placeholder="请输入网站描述"
            />
          </el-form-item>
          <el-form-item label="网站关键词">
            <el-input v-model="basicForm.siteKeywords" placeholder="请输入网站关键词，用英文逗号分隔" />
          </el-form-item>
          <el-form-item label="网站Logo">
            <el-upload
              class="avatar-uploader"
              :show-file-list="false"
              :auto-upload="false"
              :on-change="handleLogoChange"
            >
              <img v-if="basicForm.logoUrl" :src="basicForm.logoUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
            </el-upload>
            <div class="upload-tip">建议尺寸：120*120px，支持JPG、PNG格式</div>
          </el-form-item>
          <el-form-item label="备案信息">
            <el-input v-model="basicForm.icp" placeholder="请输入备案信息，例如：京ICP备12345678号" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveBasicSettings">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <!-- <el-tab-pane label="评论设置" name="comment">
        <el-form
          ref="commentFormRef"
          :model="commentForm"
          label-width="120px"
          class="setting-form"
        >
          <el-form-item label="开启评论">
            <el-switch v-model="commentForm.enableComment" />
          </el-form-item>
          <el-form-item label="评论审核">
            <el-switch v-model="commentForm.reviewComment" />
          </el-form-item>
          <el-form-item label="每页显示评论数">
            <el-input-number v-model="commentForm.commentPerPage" :min="5" :max="50" />
          </el-form-item>
          <el-form-item label="评论内容长度限制">
            <el-input-number v-model="commentForm.commentMaxLength" :min="50" :max="1000" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveCommentSettings">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane> -->

      <!-- <el-tab-pane label="邮件设置" name="email">
        <el-form
          ref="emailFormRef"
          :model="emailForm"
          label-width="120px"
          class="setting-form"
        >
          <el-form-item label="SMTP服务器">
            <el-input v-model="emailForm.smtpServer" placeholder="例如：smtp.gmail.com" />
          </el-form-item>
          <el-form-item label="SMTP端口">
            <el-input v-model="emailForm.smtpPort" placeholder="例如：465" />
          </el-form-item>
          <el-form-item label="账号">
            <el-input v-model="emailForm.smtpUser" placeholder="邮箱账号" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="emailForm.smtpPassword" type="password" placeholder="邮箱密码或授权码" show-password />
          </el-form-item>
          <el-form-item label="发件人">
            <el-input v-model="emailForm.sender" placeholder="发件人名称" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveEmailSettings">保存设置</el-button>
            <el-button @click="testEmailSettings">测试连接</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane> -->
    </el-tabs>
  </div>
</template>

<script>
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import ApiFactory from '@/api'

export default {
  name: 'SystemSetting',
  data() {
    return {
      activeName: 'basic',
      // 基本设置表单
      basicFormRef: null,
      basicForm: {
        id: '',
        siteName: '',
        siteDescription: '',
        siteKeywords: '',
        logoUrl: '',
        icp: ''
      },
      // 评论设置表单
      commentFormRef: null,
      commentForm: {
        enableComment: true,
        reviewComment: true,
        commentPerPage: 10,
        commentMaxLength: 500
      },
      // 邮件设置表单
      emailFormRef: null,
      emailForm: {
        smtpServer: 'smtp.example.com',
        smtpPort: '465',
        smtpUser: 'user@example.com',
        smtpPassword: '',
        sender: '博客管理员'
      }
    }
  },
  methods: {
    async handleLogoChange(file) {
      const api = ApiFactory.getUploadApi();
      const res = await api.uploadImage(file.raw);
      if (res.isSuccess() && res.data) {
        this.basicForm.logoUrl = res.data.url;
        ElMessage.success('Logo上传成功');
      } else {
        ElMessage.error(res.message || 'Logo上传失败');
      }
    },
    saveBasicSettings() {
      // 这里假设有一个API方法ApiFactory.getSettingApi().saveSettings(formArr)
      const api = ApiFactory.getSiteInfoApi();
      if (api && api.updateSiteInfo) {
        api.updateSiteInfo(this.basicForm).then(res => {
          if (res.isSuccess && res.isSuccess()) {
            ElMessage.success('基本设置保存成功');
          } else {
            ElMessage.error(res.message || '保存失败');
          }
        }).catch(() => {
          ElMessage.error('保存失败');
        });
      } else {
        // 没有API时仅做本地提示
        ElMessage.success('基本设置保存成功（模拟）');
      }
    }
  },
  components: {
    Plus
  },
  mounted() {
    // 页面加载时获取站点配置信息
    const api = ApiFactory.getSiteInfoApi();
    api.getSiteInfo().then(res => {
      if (res.isSuccess && res.isSuccess()) {
        this.basicForm = res.data;
      }
    });
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.setting-tabs {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.setting-form {
  max-width: 600px;
  margin-top: 20px;
}

.avatar-uploader {
  width: 120px;
  height: 120px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  overflow: hidden;
  cursor: pointer;
  position: relative;
  
  &:hover {
    border-color: #409EFF;
  }
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 100%;
  height: 100%;
  display: block;
}

.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 8px;
}
</style> 
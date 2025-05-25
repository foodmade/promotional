<template>
  <div>
    <PageLayout :title="$t('pages.contactUs.title')" :subtitle="$t('pages.contactUs.subtitle')">
      <div class="contact-container">
        <div class="contact-info">
          <div class="contact-section">
            <h3>{{ $t('pages.contactUs.generalInquiries') }}</h3>
            <p>
              <i class="el-icon-message"></i>
              <a href="mailto:info@revenuegeeks.com">{{ $t('pages.contactUs.generalEmail') }}</a>
            </p>
          </div>
          
          <div class="contact-section">
            <h3>{{ $t('pages.contactUs.partnership') }}</h3>
            <p>
              <i class="el-icon-connection"></i>
              <a href="mailto:partners@revenuegeeks.com">{{ $t('pages.contactUs.partnershipEmail') }}</a>
            </p>
          </div>
          
          <div class="contact-section">
            <h3>{{ $t('pages.contactUs.feedback') }}</h3>
            <p>
              <i class="el-icon-chat-line-round"></i>
              <a href="mailto:feedback@revenuegeeks.com">{{ $t('pages.contactUs.feedbackEmail') }}</a>
            </p>
          </div>
          
          <div class="contact-section">
            <h3>{{ $t('pages.contactUs.officeTitle') }}</h3>
            <p>
              <i class="el-icon-office-building"></i>
              {{ $t('pages.contactUs.officeAddress') }}
            </p>
            <p>
              <i class="el-icon-time"></i>
              {{ $t('pages.contactUs.officeHours') }}
            </p>
          </div>
        </div>
        
        <div class="contact-form">
          <h2>{{ $t('pages.contactUs.formTitle') }}</h2>
          <el-form :model="form" :rules="rules" ref="contactForm" @submit.native.prevent="submitForm">
            <el-form-item prop="name">
              <el-input 
                v-model="form.name" 
                :placeholder="$t('pages.contactUs.nameLabel')">
              </el-input>
            </el-form-item>
            
            <el-form-item prop="email">
              <el-input 
                v-model="form.email" 
                :placeholder="$t('pages.contactUs.emailLabel')">
              </el-input>
            </el-form-item>
            
            <el-form-item prop="subject">
              <el-input 
                v-model="form.subject" 
                :placeholder="$t('pages.contactUs.subjectLabel')">
              </el-input>
            </el-form-item>
            
            <el-form-item prop="message">
              <el-input 
                v-model="form.message" 
                type="textarea" 
                :rows="6" 
                :placeholder="$t('pages.contactUs.messageLabel')">
              </el-input>
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="submitForm" :loading="submitting">
                {{ $t('pages.contactUs.submitButton') }}
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'ContactUs',
  components: {
    PageLayout
  },
  data() {
    return {
      submitting: false,
      form: {
        name: '',
        email: '',
        subject: '',
        message: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入您的姓名', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入您的电子邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入有效的电子邮箱地址', trigger: 'blur' }
        ],
        subject: [
          { required: true, message: '请输入主题', trigger: 'blur' }
        ],
        message: [
          { required: true, message: '请输入您的消息', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.contactForm.validate((valid) => {
        if (valid) {
          this.submitting = true;
          
          // 模拟API调用
          setTimeout(() => {
            this.submitting = false;
            
            // 成功提交后显示消息并重置表单
            this.$message({
              message: this.$t('pages.contactUs.successMessage'),
              type: 'success'
            });
            
            this.$refs.contactForm.resetFields();
          }, 1500);
        }
      });
    }
  }
}
</script>

<style scoped>
.contact-container {
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  gap: 40px;
  margin-top: 20px;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.contact-section h3 {
  font-size: 18px;
  margin-bottom: 15px;
}

.contact-section p {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.contact-section i {
  margin-right: 10px;
  color: var(--primary-green);
  font-size: 18px;
}

.contact-section a {
  color: var(--text-primary);
  text-decoration: none;
  transition: color 0.3s;
}

.contact-section a:hover {
  color: var(--primary-green);
}

.contact-form {
  background-color: #f9f9f9;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.contact-form h2 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 22px;
}

@media (max-width: 768px) {
  .contact-container {
    grid-template-columns: 1fr;
  }
  
  .contact-info {
    order: 2;
  }
  
  .contact-form {
    order: 1;
  }
}
</style> 
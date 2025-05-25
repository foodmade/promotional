<template>
  <div class="login-container">
    <el-form
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      label-position="top"
    >
      <div class="title-container">
        <h3 class="title">系统登录</h3>
      </div>

      <el-form-item prop="username" label="用户名">
        <el-input
          v-model="loginForm.username"
          placeholder="请输入用户名"
        />
      </el-form-item>

      <el-form-item prop="password" label="密码">
        <el-input
          v-model="loginForm.password"
          placeholder="请输入密码"
          :type="passwordVisible ? 'text' : 'password'"
          show-password
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          :loading="loading"
          style="width: 100%"
          @click="handleLogin"
        >
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import ApiFactory from '@/api';
import { LoginResponse } from '@/utils/api-response';

export default {
  name: 'Login',
  setup() {
    const router = useRouter();
    const loginFormRef = ref(null);
    const loading = ref(false);
    const passwordVisible = ref(false);
    
    // 登录表单
    const loginForm = reactive({
      username: '',
      password: ''
    });
    
    // 表单校验规则
    const loginRules = {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, message: '密码长度至少为6个字符', trigger: 'blur' }
      ]
    };
    
    // 处理登录
    const handleLogin = async () => {
      if (!loginFormRef.value) return;
      
      try {
        // 验证表单
        await loginFormRef.value.validate();
        
        // 设置加载状态
        loading.value = true;
        
        // 调用登录API
        const authApi = ApiFactory.getAuthApi();
        const response = await authApi.login(loginForm.username, loginForm.password);
        
        // 使用LoginResponse处理登录响应
        const loginResponse = LoginResponse.fromResponse(response);
        
        // 处理登录结果
        if (loginResponse.isSuccess()) {
          ElMessage({
            message: '登录成功',
            type: 'success',
            duration: 2000
          });
          
          // 登录成功后跳转到首页
          router.push({ path: '/dashboard' });
        } else {
          // 登录失败，显示错误消息
          ElMessage({
            message: loginResponse.message || '登录失败',
            type: 'error',
            duration: 5000
          });
        }
      } catch (error) {
        console.error('登录过程出错:', error);
        // 防止页面刷新，只显示错误消息
        ElMessage({
          message: error.message || '登录失败，请稍后重试',
          type: 'error',
          duration: 5000
        });
      } finally {
        // 清除加载状态
        loading.value = false;
      }
    };
    
    return {
      loginForm,
      loginRules,
      loginFormRef,
      passwordVisible,
      loading,
      handleLogin
    };
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  width: 100%;
  background-color: #2d3a4b;
  display: flex;
  justify-content: center;
  align-items: center;
  
  .login-form {
    width: 400px;
    max-width: 100%;
    padding: 35px 35px 15px;
    margin: 0 auto;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    
    .title-container {
      margin-bottom: 30px;
      
      .title {
        font-size: 26px;
        color: #2d3a4b;
        text-align: center;
        font-weight: bold;
      }
    }
  }
}
</style> 
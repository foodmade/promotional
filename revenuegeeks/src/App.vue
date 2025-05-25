<template>
  <div id="app">
    <!-- 主路由视图 -->
    <router-view />
    
    <!-- 全局加载状态 -->
    <div v-if="popupLoading" class="global-popup-loading">
      <div class="loading-spinner">
        <i class="el-icon-loading"></i>
      </div>
      <div class="loading-text">{{ $t('popup.loading') }}</div>
    </div>
    
    <!-- 弹窗错误提示 -->
    <el-dialog
      :visible.sync="showErrorPopup"
      width="400px"
      center
      custom-class="error-popup"
    >
      <div class="error-content">
        <div class="error-icon">
          <i class="el-icon-warning-outline"></i>
        </div>
        <div class="error-text">{{ popupError }}</div>
        <el-button size="small" @click="hideErrorPopup">{{ $t('popup.close') }}</el-button>
      </div>
    </el-dialog>
    
    <!-- 全局特别优惠弹窗 -->
    <special-offer-popup
      v-if="popupConfig"
      :visible.sync="showPopup"
      :popup-id="popupConfig.popupId"
      :offer-data="popupConfig.offerData"
      @mounted="handlePopupMounted"
    />
  </div>
</template>

<script>
import SpecialOfferPopup from '@/components/SpecialOfferPopup.vue';
import PopupService from '@/plugins/PopupService';

export default {
  name: 'App',
  components: {
    SpecialOfferPopup
  },
  data() {
    return {
      showPopup: false,
      popupConfig: null,
      popupTimer: null,
      popupLoading: false,
      popupError: null,
      showErrorPopup: false
    };
  },
  watch: {
    // 监听错误状态变化，自动显示错误弹窗
    popupError(newVal) {
      this.showErrorPopup = newVal !== null;
    },
    
    // 监听弹窗配置变化，用于调试
    popupConfig: {
      handler(newVal) {
        console.log('弹窗配置已更新:', newVal ? JSON.stringify(newVal) : 'null');
      },
      deep: true
    },
    
    // 监听路由变化，处理弹窗显示逻辑
    '$route.path': {
      handler(newPath) {
        this.handleRouteChange(newPath);
      },
      immediate: true
    }
  },
  methods: {
    // 处理弹窗挂载事件，用于调试
    handlePopupMounted() {
      console.log('弹窗组件已挂载，配置:', JSON.stringify(this.popupConfig));
    },
    
    hideErrorPopup() {
      this.showErrorPopup = false;
      this.popupError = null;
    },
    
    handleRouteChange(path) {
      // 清除之前的定时器
      if (this.popupTimer) {
        clearTimeout(this.popupTimer);
        this.popupTimer = null;
      }
      
      // 重置弹窗状态
      this.showPopup = false;
      this.popupConfig = null;
      this.popupError = null;
      
      // 设置延迟后获取弹窗数据并显示
      this.popupTimer = setTimeout(async () => {
        try {
          // 显示加载状态
          this.popupLoading = true;
          
          // 异步获取弹窗配置
          const popupConfig = await PopupService.checkShouldShowPopup(path, this.$i18n);
          console.log(`popupConfig: ${JSON.stringify(popupConfig)}`);
          
          // 隐藏加载状态
          this.popupLoading = false;
          
          if (popupConfig) {
            this.popupConfig = popupConfig;
            
            // 给渲染一点时间
            setTimeout(() => {
              this.showPopup = true;
              console.log('弹窗显示状态已设置为:', this.showPopup);
            }, 100);
          }
        } catch (error) {
          console.error('获取弹窗数据失败:', error);
          this.popupLoading = false;
          this.popupError = this.$t('popup.error.loadFailed');
        }
      }, PopupService.config.delay);
    }
  },
  beforeDestroy() {
    // 清除定时器
    if (this.popupTimer) {
      clearTimeout(this.popupTimer);
    }
  }
};
</script>

<style>
:root {
  --primary-blue: #1E88E5;
  --primary-green: #26A69A;
  --accent-red: #E53935;
  --gray-light: #F5F7FA;
  --gray-medium: #E4E7EB;
  --gray-dark: #4A5568;
  --dark-mode: #1A202C;
  --font-sans: 'Roboto', 'SF Pro Display', 'PingFang SC', 'Microsoft YaHei', sans-serif;
  --font-serif: 'Open Sans', 'SF Pro Text', 'Noto Serif SC', serif;
  --border-radius-sm: 8px;
  --border-radius-md: 12px;
  --shadow-small: 0 2px 8px rgba(0,0,0,0.06);
  --shadow-medium: 0 4px 12px rgba(0,0,0,0.08);
  --transition-default: all 0.3s ease-in-out;
}

body {
  font-family: var(--font-sans);
  margin: 0;
  padding: 0;
  color: var(--gray-dark);
  line-height: 1.6;
  background-color: var(--gray-light);
}

#app {
  font-family: var(--font-sans);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* 全局加载状态样式 */
.global-popup-loading {
  position: fixed;
  bottom: 30px;
  right: 30px;
  background: white;
  padding: 15px 20px;
  border-radius: 8px;
  box-shadow: var(--shadow-medium);
  display: flex;
  align-items: center;
  gap: 10px;
  z-index: 2000;
  animation: slide-in-right 0.3s ease-out forwards;
}

.loading-spinner {
  font-size: 24px;
  color: var(--primary-blue);
}

.loading-text {
  font-size: 14px;
  color: #666;
}

/* 错误弹窗样式 */
.error-popup >>> .el-dialog {
  border-radius: 10px;
  overflow: hidden;
}

.error-content {
  padding: 20px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.error-icon {
  font-size: 40px;
  color: #f56c6c;
  margin-bottom: 20px;
}

.error-text {
  font-size: 16px;
  color: #666;
  margin-bottom: 20px;
}

@keyframes slide-in-right {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}
</style> 
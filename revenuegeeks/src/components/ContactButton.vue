<template>
  <div class="contact-button-container" :class="{ 'expanded': isExpanded }">
    <!-- 主按钮 -->
    <div 
      class="contact-button"
      @mouseenter="isExpanded = true"
      @mouseleave="isExpanded = false"
    >
      <i class="el-icon-service"></i>
      <span class="button-text">{{ $t('contact.service') || '联系客服' }}</span>
    </div>
    
    <!-- 展开的二维码面板 -->
    <div class="qrcode-panel">
      <div class="qrcode-title">{{ $t('contact.scanCode') || '扫码联系客服' }}</div>
      
      <!-- 二维码展示区域 - 同时显示两张 -->
      <div class="qrcode-container">
        <div class="qrcode-item" v-for="item in qrCodes" :key="item.id">
          <div class="qrcode-wrapper">
            <img :src="item.qrcode" :alt="item.title" class="qrcode-image" />
          </div>
          <div class="qrcode-name">{{ item.title }}</div>
          <div class="qrcode-desc">{{ item.description }}</div>
        </div>
      </div>
      
      <div class="qrcode-footer">{{ $t('contact.workTime') || '工作时间：9:00-18:00' }}</div>
    </div>
  </div>
</template>

<script>

import api from '@/api/modules/support';
export default {
  name: 'ContactButton',
  data() {
    return {
      isExpanded: false,
      qrCodes: []
    }
  },
  methods: {
    getQrCodes() {
      api.getSupportList().then(response => {
        this.qrCodes = response.rows;
      });
    }
  },
  mounted() {
    this.getQrCodes();
  }
}
</script>

<style scoped>
.contact-button-container {
  position: fixed;
  right: 20px;
  bottom: 100px;
  z-index: 999;
  display: flex;
  align-items: center;
  transition: var(--transition-default);
}

.contact-button {
  display: flex;
  align-items: center;
  background-color: var(--primary-blue);
  color: white;
  padding: 10px 15px;
  border-radius: 25px;
  box-shadow: var(--shadow-medium);
  cursor: pointer;
  transition: var(--transition-default);
  z-index: 2;
}

.contact-button:hover {
  background-color: #1976D2;
  transform: translateY(-2px);
}

.contact-button i {
  font-size: 20px;
  margin-right: 8px;
}

.qrcode-panel {
  position: absolute;
  right: 0;
  bottom: 60px;
  background: white;
  border-radius: var(--border-radius-md);
  padding: 20px;
  width: 360px;
  box-shadow: var(--shadow-medium);
  opacity: 0;
  transform: translateY(20px);
  transition: all 0.3s ease;
  pointer-events: none;
  text-align: center;
}

.expanded .qrcode-panel {
  opacity: 1;
  transform: translateY(0);
  pointer-events: auto;
}

.qrcode-title {
  font-size: 16px;
  font-weight: 500;
  color: var(--gray-dark);
  margin-bottom: 15px;
}

.qrcode-container {
  display: flex;
  justify-content: space-around;
  gap: 10px;
}

.qrcode-item {
  flex: 1;
}

.qrcode-wrapper {
  padding: 8px;
  border: 1px solid var(--gray-medium);
  border-radius: var(--border-radius-sm);
  display: inline-block;
  margin-bottom: 5px;
}

.qrcode-image {
  width: 120px;
  height: 120px;
  object-fit: cover;
}

.qrcode-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--gray-dark);
  margin-bottom: 5px;
}

.qrcode-desc {
  font-size: 12px;
  color: #666;
  margin-bottom: 10px;
  padding: 0 5px;
}

.qrcode-footer {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
  border-top: 1px dashed var(--gray-medium);
  padding-top: 10px;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .contact-button {
    padding: 10px;
  }
  
  .button-text {
    display: none;
  }
  
  .contact-button i {
    margin-right: 0;
  }
  
  .qrcode-panel {
    width: 300px;
    right: -10px;
  }
  
  .qrcode-image {
    width: 100px;
    height: 100px;
  }
}

/* 小屏幕适配 */
@media (max-width: 375px) {
  .qrcode-panel {
    width: 280px;
    padding: 15px;
  }
  
  .qrcode-image {
    width: 90px;
    height: 90px;
  }
  
  .qrcode-desc {
    font-size: 10px;
  }
}
</style> 
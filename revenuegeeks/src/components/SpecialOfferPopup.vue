<template>
  <el-dialog
    :visible.sync="visible"
    :show-close="false"
    width="580px"
    center
    custom-class="special-offer-popup"
    @close="closePopup"
  >
    <div class="popup-content" :class="{'show-content': contentVisible}">
      <!-- 关闭按钮 -->
      <div class="close-btn" @click="closePopup">
        <i class="el-icon-close"></i>
      </div>
      
      <!-- 顶部徽章 -->
      <div class="offer-badge">
        <span class="badge-text">{{ $t('popup.limitedTimeOffer') }}</span>
      </div>
      
      <!-- 弹窗Logo -->
      <div class="popup-logo bounce-in">
        <img :src="offerData.logo" :alt="offerData.title" />
      </div>
      
      <!-- 弹窗标题 -->
      <h2 class="popup-title slide-in-top">{{ offerData.title }}</h2>
      
      <!-- 倒计时 -->
      <div class="countdown-container fade-in">
        <div class="countdown-label">{{ $t('popup.offerExpires') }}</div>
        <div class="countdown-timer">
          <div class="timer-block">
            <div class="timer-number">{{ countdown.hours }}</div>
            <div class="timer-label">{{ $t('popup.hours') }}</div>
          </div>
          <div class="timer-separator">:</div>
          <div class="timer-block">
            <div class="timer-number">{{ countdown.minutes }}</div>
            <div class="timer-label">{{ $t('popup.minutes') }}</div>
          </div>
          <div class="timer-separator">:</div>
          <div class="timer-block">
            <div class="timer-number">{{ countdown.seconds }}</div>
            <div class="timer-label">{{ $t('popup.seconds') }}</div>
          </div>
        </div>
      </div>
      
      <!-- 折扣卡片 -->
      <div class="discount-card scale-in">
        <div class="discount-amount">{{ formatDiscount }}</div>
        <div class="discount-text">{{ $t('popup.discount') }}</div>
      </div>
      
      <!-- 专属价格区 -->
      <div class="price-container slide-in-bottom">
        <div class="original-price">{{ formatOriginalPrice }}</div>
        <div class="special-price">{{ formatSpecialPrice }}</div>
      </div>
      
      <!-- 弹窗按钮 -->
      <el-button 
        type="danger" 
        class="offer-btn pulse"
        @click="goToOffer"
      >
        {{ offerData.buttonText }}
        <i class="el-icon-right"></i>
      </el-button>
      
      <!-- 安全保障 -->
      <div class="security-guarantee swing-in">
        <i class="el-icon-lock"></i>
        <span>{{ $t('popup.securePayment') }}</span>
      </div>
      
      <!-- 社会证明 -->
      <div class="social-proof fade-in-delayed">
        <div class="user-avatars">
          <div class="avatar-stack">
            <div class="avatar" v-for="i in 5" :key="i" :style="`background-position: ${i * 20}% 0`"></div>
          </div>
        </div>
        <div class="proof-text">
          <span class="user-count-number">{{ offerData.userCount }}</span> {{ $t('popup.usersExplored') }}
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'SpecialOfferPopup',
  props: {
    popupId: {
      type: String,
      default: 'default-popup'
    },
    offerData: {
      type: Object,
      required: true,
      default: () => ({})
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 内容显示控制
      contentVisible: false,
      
      // 用于管理本地存储，记录关闭时间
      cookieKey: 'popup_last_shown',
      
      // 倒计时数据
      countdown: {
        hours: '23',
        minutes: '59',
        seconds: '59'
      },
      countdownTimer: null
    }
  },
  computed: {
    // 格式化折扣显示
    formatDiscount() {
      // 处理数字或字符串格式的折扣
      if (typeof this.offerData.discountAmount === 'number') {
        // 如果是小数，转换为百分比格式
        if (this.offerData.discountAmount < 1) {
          return `${Math.round(this.offerData.discountAmount * 100)}% OFF`;
        }
        // 如果是整数，直接显示
        return `${this.offerData.discountAmount}% OFF`;
      }
      // 如果已经是字符串，直接返回
      return this.offerData.discountAmount || '50% OFF'; // 默认值
    },
    // 格式化原价显示
    formatOriginalPrice() {
      if (typeof this.offerData.originalPrice === 'number') {
        return `$${this.offerData.originalPrice}/month`;
      }
      return this.offerData.originalPrice || '$399/month'; // 默认值
    },
    // 格式化特价显示
    formatSpecialPrice() {
      if (typeof this.offerData.specialPrice === 'number') {
        return `$${this.offerData.specialPrice}/month`;
      }
      return this.offerData.specialPrice || '$199/month'; // 默认值
    }
  },
  watch: {
    visible(newVal) {
      console.log('弹窗显示状态变更:', newVal, '数据:', JSON.stringify(this.offerData));
      if (newVal) {
        // 显示弹窗内容，延迟一点时间使动画更平滑
        setTimeout(() => {
          this.contentVisible = true;
          this.startCountdown();
        }, 300);
      } else {
        // 隐藏弹窗内容，清除倒计时
        this.contentVisible = false;
        if (this.countdownTimer) {
          clearInterval(this.countdownTimer);
        }
      }
    },
    offerData: {
      handler(newVal) {
        console.log('offerData变化:', JSON.stringify(newVal));
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    console.log('弹窗组件已挂载，数据:', JSON.stringify(this.offerData));
    // 通知父组件
    this.$emit('mounted');
    
    // 确保弹窗显示
    if (this.visible && !this.contentVisible) {
      setTimeout(() => {
        this.contentVisible = true;
        this.startCountdown();
      }, 300);
    }
  },
  methods: {
    closePopup() {
      // 更新本地存储，记录此次关闭时间
      localStorage.setItem(`${this.cookieKey}_${this.popupId}`, Date.now().toString());
      this.$emit('update:visible', false);
    },
    goToOffer() {
      // 点击按钮前往优惠链接
      window.open(this.offerData.offerLink, '_blank');
      this.closePopup();
    },
    startCountdown() {
      // 清除可能存在的倒计时
      if (this.countdownTimer) {
        clearInterval(this.countdownTimer);
      }
      
      // 设置一个随机结束时间，约为12-24小时
      const endTime = new Date();
      endTime.setHours(endTime.getHours() + 23);
      endTime.setMinutes(endTime.getMinutes() + 59);
      endTime.setSeconds(endTime.getSeconds() + 59);
      
      this.countdownTimer = setInterval(() => {
        const now = new Date();
        const diff = endTime - now;
        
        if (diff <= 0) {
          clearInterval(this.countdownTimer);
          this.countdown = { hours: '00', minutes: '00', seconds: '00' };
          return;
        }
        
        // 计算剩余的小时、分钟和秒数
        const hours = Math.floor(diff / (1000 * 60 * 60));
        const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((diff % (1000 * 60)) / 1000);
        
        this.countdown = {
          hours: hours < 10 ? `0${hours}` : hours,
          minutes: minutes < 10 ? `0${minutes}` : minutes,
          seconds: seconds < 10 ? `0${seconds}` : seconds
        };
      }, 1000);
    }
  },
  beforeDestroy() {
    if (this.countdownTimer) {
      clearInterval(this.countdownTimer);
    }
  }
}
</script>

<style scoped>
.special-offer-popup >>> .el-dialog {
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 10px 50px rgba(0, 0, 0, 0.3);
  border: none;
  background: linear-gradient(135deg, #ffffff, #f5f8fa);
}

.special-offer-popup >>> .el-dialog__body {
  padding: 0;
}

.special-offer-popup >>> .el-dialog__header {
  padding: 0;
  display: none;
}

.popup-content {
  position: relative;
  padding: 40px 30px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 520px;
  opacity: 0;
  transform: scale(0.95);
  transition: all 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  /* 调试用边框 */
  border: 1px dashed rgba(255, 0, 0, 0.2);
}

.show-content {
  opacity: 1;
  transform: scale(1);
}

.close-btn {
  position: absolute;
  top: 15px;
  right: 15px;
  width: 30px;
  height: 30px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 10;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.close-btn:hover {
  transform: rotate(90deg);
  background: #f44336;
  color: white;
}

.offer-badge {
  position: absolute;
  top: 15px;
  left: 0;
  background: linear-gradient(135deg, #ff9800, #ff5722);
  color: white;
  padding: 8px 20px;
  font-size: 0.85rem;
  font-weight: bold;
  border-radius: 0 20px 20px 0;
  box-shadow: 0 4px 10px rgba(255, 87, 34, 0.3);
  z-index: 2;
  transform: translateX(-5px);
  animation: slide-in-left 0.6s cubic-bezier(0.23, 1, 0.32, 1) both;
}

.badge-text {
  text-transform: uppercase;
  letter-spacing: 1px;
}

.popup-logo {
  margin-bottom: 20px;
  max-width: 180px;
  margin-top: 20px;
}

.popup-logo img {
  max-width: 100%;
  max-height: 60px;
}

.popup-title {
  font-size: 2.5rem;
  font-weight: 800;
  background: linear-gradient(135deg, #f44336, #ff9800);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  margin-bottom: 25px;
  text-align: center;
  line-height: 1.2;
  max-width: 90%;
}

/* 倒计时样式 */
.countdown-container {
  margin-bottom: 25px;
  width: 100%;
}

.countdown-label {
  font-size: 1rem;
  color: #555;
  margin-bottom: 10px;
  font-weight: 500;
}

.countdown-timer {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
}

.timer-block {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.timer-number {
  background: linear-gradient(to bottom, #2c3e50, #1a2533);
  color: white;
  font-size: 1.8rem;
  font-weight: bold;
  padding: 8px 12px;
  border-radius: 8px;
  min-width: 50px;
}

.timer-label {
  font-size: 0.75rem;
  color: #777;
  margin-top: 5px;
}

.timer-separator {
  font-size: 1.8rem;
  font-weight: bold;
  color: #2c3e50;
  margin: 0 2px;
  align-self: flex-start;
  margin-top: 8px;
}

/* 折扣卡片样式 */
.discount-card {
  background: linear-gradient(135deg, #4caf50, #8bc34a);
  color: white;
  padding: 12px 25px;
  border-radius: 12px;
  margin-bottom: 25px;
  box-shadow: 0 6px 15px rgba(76, 175, 80, 0.3);
  transform: rotate(-2deg);
}

.discount-amount {
  font-size: 2.2rem;
  font-weight: 800;
  line-height: 1;
}

.discount-text {
  font-size: 1rem;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/* 价格区域样式 */
.price-container {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 25px;
}

.original-price {
  font-size: 1.2rem;
  color: #999;
  text-decoration: line-through;
  position: relative;
}

.special-price {
  font-size: 1.8rem;
  font-weight: bold;
  color: #f44336;
}

/* 按钮样式 */
.offer-btn {
  width: 90%;
  font-size: 1.2rem;
  padding: 15px 0;
  margin-bottom: 20px;
  font-weight: bold;
  letter-spacing: 0.5px;
  border-radius: 50px;
  background: linear-gradient(135deg, #f44336, #ff9800);
  border: none;
  box-shadow: 0 6px 15px rgba(244, 67, 54, 0.3);
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.offer-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 20px rgba(244, 67, 54, 0.4);
  background: linear-gradient(135deg, #e53935, #ff8f00);
}

.offer-btn:active {
  transform: translateY(1px);
}

/* 安全保障样式 */
.security-guarantee {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #555;
  font-size: 0.9rem;
  margin-bottom: 15px;
}

.security-guarantee i {
  color: #4caf50;
}

/* 社会证明样式 */
.social-proof {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-top: 5px;
}

.user-avatars {
  position: relative;
}

.avatar-stack {
  display: flex;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: 2px solid white;
  margin-left: -10px;
  background-color: #ddd;
  background-image: url('https://i.pravatar.cc/100');
  background-size: cover;
}

.avatar:first-child {
  margin-left: 0;
}

.proof-text {
  font-size: 0.9rem;
  color: #666;
}

.user-count-number {
  font-weight: bold;
  color: #f44336;
}

/* 动画效果 */
.bounce-in {
  animation: bounce-in 0.8s cubic-bezier(0.215, 0.610, 0.355, 1.000) both;
  animation-delay: 0.2s;
}

.slide-in-top {
  animation: slide-in-top 0.6s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  animation-delay: 0.4s;
}

.slide-in-bottom {
  animation: slide-in-bottom 0.6s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  animation-delay: 0.6s;
}

.fade-in {
  animation: fade-in 0.8s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
  animation-delay: 0.5s;
}

.fade-in-delayed {
  animation: fade-in 0.8s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
  animation-delay: 1s;
}

.scale-in {
  animation: scale-in 0.7s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
  animation-delay: 0.8s;
}

.swing-in {
  animation: swing-in 0.6s cubic-bezier(0.175, 0.885, 0.320, 1.275) both;
  animation-delay: 0.9s;
}

.pulse {
  animation: pulse 2s infinite;
  animation-delay: 1.2s;
}

@keyframes bounce-in {
  0% {
    transform: scale(0);
    opacity: 0;
  }
  60% {
    transform: scale(1.1);
    opacity: 1;
  }
  100% {
    transform: scale(1);
  }
}

@keyframes slide-in-top {
  0% {
    transform: translateY(-50px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes slide-in-bottom {
  0% {
    transform: translateY(50px);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes slide-in-left {
  0% {
    transform: translateX(-100%);
    opacity: 0;
  }
  100% {
    transform: translateX(-5px);
    opacity: 1;
  }
}

@keyframes fade-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes scale-in {
  0% {
    transform: scale(0);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes swing-in {
  0% {
    transform: rotateX(-100deg);
    transform-origin: top;
    opacity: 0;
  }
  100% {
    transform: rotateX(0deg);
    transform-origin: top;
    opacity: 1;
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
    box-shadow: 0 6px 15px rgba(244, 67, 54, 0.3);
  }
  50% {
    transform: scale(1.05);
    box-shadow: 0 12px 20px rgba(244, 67, 54, 0.4);
  }
  100% {
    transform: scale(1);
    box-shadow: 0 6px 15px rgba(244, 67, 54, 0.3);
  }
}

@media (max-width: 768px) {
  .popup-title {
    font-size: 2rem;
  }
  
  .discount-amount {
    font-size: 1.8rem;
  }
  
  .timer-number {
    font-size: 1.4rem;
    min-width: 40px;
  }
  
  .special-offer-popup >>> .el-dialog {
    width: 95% !important;
    margin: 10px auto !important;
  }
}
</style> 
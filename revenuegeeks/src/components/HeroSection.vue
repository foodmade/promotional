<template>
  <section class="hero">
    <!-- 大图轮播 -->
    <el-carousel 
      :interval="5000" 
      height="900px" 
      indicator-position="outside"
      :autoplay="true"
      arrow="always"
      v-if="banners.length > 0"
    >
      <el-carousel-item v-for="(banner, index) in banners" :key="index">
        <div class="banner-container" :style="{backgroundImage: `url(${banner.imageUrl})`, cursor: banner.link ? 'pointer' : 'default'}" @click="handleBannerClick(banner)">
          <!-- 移除了banner内容区域 -->
        </div>
      </el-carousel-item>
    </el-carousel>
    
    <!-- 加载中状态 -->
    <div class="banner-loading" v-if="isLoading && banners.length === 0">
      <i class="el-icon-loading"></i>
      <p>{{ $t('hero.loading') || '加载中...' }}</p>
    </div>
    
    <!-- 加载失败时显示静态内容 -->
    <div class="hero-content" v-if="!isLoading && banners.length === 0">
      <h1>{{ $t('hero.title') }}</h1>
      <p>{{ $t('hero.subtitle') }}</p>
    </div>
  </section>
</template>

<script>
import bannerApi from '@/api/modules/banner';
export default {
  name: 'HeroSection',
  data() {
    return {
      banners: [],
      isLoading: false,
      error: null
    }
  },
  created() {
    this.fetchBanners();
  },
  methods: {
    async fetchBanners() {
      this.isLoading = true;
      try {
        // 实际项目中应该替换为真实的API调用
        const response = await bannerApi.getAllBanners();
        console.log('banner',response)
        this.banners = response.rows;
      } catch (err) {
        console.error('获取banner数据失败', err);
        this.error = err.message;
        this.isLoading = false;
      }
    },
    handleBannerClick(banner) {
      if (banner.link) {
        window.open(banner.link, '_blank');
      }
    }
  }
}
</script>

<style scoped>
.hero {
  margin-top: 80px;
  position: relative;
}

.hero-content {
  padding: 60px 20px;
  background: linear-gradient(135deg, var(--primary-blue), var(--primary-green));
  color: white;
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
}

.banner-container {
  height: 100%;
  width: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
}

.banner-loading {
  height: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: var(--gray-light);
}

.banner-loading i {
  font-size: 40px;
  color: var(--primary-blue);
  margin-bottom: 10px;
}

.hero h1 {
  font-size: 36px;
  margin-bottom: 20px;
  font-weight: 700;
}

.hero p {
  font-size: 18px;
  margin-bottom: 30px;
  opacity: 0.9;
}

@media (max-width: 768px) {
  .hero-content {
    padding: 40px 20px;
  }
  
  .el-carousel {
    height: 400px;
  }
  
  .el-carousel >>> .el-carousel__container {
    height: 400px !important;
  }
}

@media (max-width: 480px) {
  .el-carousel {
    height: 300px;
  }
  
  .el-carousel >>> .el-carousel__container {
    height: 300px !important;
  }
}
</style> 
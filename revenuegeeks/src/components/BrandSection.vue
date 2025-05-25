<template>
  <section class="brands">
    <h2>{{ $t('brands.title') }}</h2>
    
    <el-row v-loading="loading" :gutter="24" class="brand-grid">
      <el-col :xs="24" :sm="12" :md="12" :lg="6" v-for="brand in brands" :key="brand.id">
        <router-link :to="getRouteForBrand(brand)" class="brand-card">
          <img :src="brand.logo" :alt="brand.name">
          <h3>{{ brand.name }}</h3>
          <p>{{ brand.description }}</p>
          <div class="rating">
            <span v-for="star in 5" :key="star">
              {{ star <= brand.rating ? '★' : '☆' }}
            </span>
          </div>
        </router-link>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import api from '@/api/modules/brands';
export default {
  name: 'BrandSection',
  data() {
    return {
      loading: false,
      brands: []
    }
  },
  async mounted() {
    try {
      this.loading = true
      // 这里使用 await 来模拟从API获取数据
      const response = await api.getAll();
      console.log(response)
      this.brands = response.rows
    } catch (error) {
      this.$message.error('获取品牌数据失败，请稍后重试');
    } finally {
      this.loading = false
    }
  },
  methods: {
    getRouteForBrand(brand) {
   
      // 默认返回文章页面
      return { path: `/article/${brand.slug}` };
    }
  }
}
</script>

<style scoped>
.brands {
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 20px;
  text-align: center;
}

.brands h2 {
  font-size: 28px;
  margin-bottom: 40px;
  color: var(--gray-dark);
}

.brand-grid {
  margin-bottom: -24px;
}

.brand-card {
  background-color: white;
  border-radius: var(--border-radius-md);
  box-shadow: var(--shadow-small);
  padding: 24px;
  transition: var(--transition-default);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  color: var(--gray-dark);
  height: 100%;
  margin-bottom: 24px;
}

.brand-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-medium);
}

.brand-card img {
  max-width: 140px;
  height: 60px;
  object-fit: contain;
  margin-bottom: 16px;
}

.brand-card h3 {
  margin: 10px 0 5px;
  font-size: 18px;
}

.brand-card p {
  margin: 0;
  font-size: 14px;
  color: var(--gray-dark);
  opacity: 0.8;
}

.rating {
  display: flex;
  margin-top: 10px;
}

.rating span {
  color: #FFB400;
  margin-right: 3px;
}

@media (max-width: 768px) {
  .brands {
    padding: 40px 20px;
  }
  
  .brands h2 {
    font-size: 24px;
    margin-bottom: 30px;
  }
}

@media (max-width: 480px) {
  .brands {
    padding: 30px 15px;
  }
}
</style> 
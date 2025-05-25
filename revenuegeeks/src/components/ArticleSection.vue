<template>
  <section class="articles">
    <h2>{{ $t('articles.title') }}</h2>
    
    <el-row v-loading="loading" :gutter="30" class="article-grid">
      <el-col :xs="24" :sm="12" :md="6" v-for="article in articles" :key="article.id">
        <div class="article-card">
          <div class="article-image">
            <img :src="article.image" :alt="article.title">
            <span class="article-tag" :class="'tag-' + article.tag">
              {{ $t(`articles.tags.${article.tag}`) }}
            </span>
          </div>
          <div class="article-content">
            <h3><router-link :to="'/article/' + article.slug">{{ article.title }}</router-link></h3>
          </div>
        </div>
      </el-col>
    </el-row>

  </section>
</template>

<script>
import api from '@/api/modules/articles';

export default {
  name: 'ArticleSection',
  data() {
    return {
      loading: false,
      articles: []
    }
  },
  async mounted() {
    try {
      this.loading = true
      // 实际项目中从API获取数据
      const response = await api.getAll({page: 1, pageSize: 8})
      this.articles = response.rows
    } catch (error) {
      console.error('获取文章数据失败:', error)
    } finally {
      this.loading = false
    }
  }
}
</script>

<style scoped>
.articles {
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 20px;
}

.articles h2 {
  font-size: 28px;
  margin-bottom: 40px;
  text-align: center;
  color: var(--gray-dark);
}

.article-card {
  background-color: white;
  border-radius: var(--border-radius-md);
  overflow: hidden;
  box-shadow: var(--shadow-small);
  transition: var(--transition-default);
  height: 100%;
  margin-bottom: 30px;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-medium);
}

.article-image {
  width: 100%;
  height: 180px;
  overflow: hidden;
  position: relative;
}

.article-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: var(--transition-default);
}

.article-card:hover .article-image img {
  transform: scale(1.05);
}

.article-tag {
  position: absolute;
  top: 12px;
  left: 12px;
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
  color: white;
}

.tag-comparison {
  background-color: var(--primary-blue);
}

.tag-coupon {
  background-color: var(--primary-green);
}

.tag-guide {
  background-color: #9C27B0;
}

.tag-review {
  background-color: var(--accent-red);
}

.article-content {
  padding: 20px;
}

.article-card h3 {
  margin: 0 0 10px;
  font-size: 18px;
  line-height: 1.4;
}

.article-card a {
  color: var(--gray-dark);
  text-decoration: none;
}

.article-card a:hover {
  color: var(--primary-blue);
}

.article-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
  font-size: 14px;
  color: var(--gray-dark);
  opacity: 0.7;
}

.view-more {
  text-align: center;
  margin-top: 30px;
}

@media (max-width: 768px) {
  .articles {
    padding: 40px 20px;
  }
  
  .articles h2 {
    font-size: 24px;
    margin-bottom: 30px;
  }
}

@media (max-width: 480px) {
  .articles {
    padding: 30px 15px;
  }
  
  .article-meta {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .article-meta span:first-child {
    margin-bottom: 5px;
  }
}
</style> 
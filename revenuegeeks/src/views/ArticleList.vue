<template>
  <div class="article-list-page">
    <app-header />
    
    <div class="main-content">
      <!-- 页面标题区域 -->
      <div class="page-header">
        <div class="header-content">
          <h1 class="main-title">{{ pageTitle }}</h1>
          <p class="subtitle">{{ pageSubtitle }}</p>
        </div>
        
        <!-- 背景装饰 -->
        <div class="header-decoration">
          <div class="decoration-shape shape1"></div>
          <div class="decoration-shape shape2"></div>
          <div class="decoration-shape shape3"></div>
        </div>
      </div>
      
      <!-- 文章过滤控制区 -->
      <!-- <div class="filter-container">
        <div class="filter-tabs">
          <div 
            v-for="(tab, index) in filterTabs" 
            :key="index"
            :class="['filter-tab', { active: activeTab === tab.value }]"
            @click="activeTab = tab.value"
          >
            <i :class="tab.icon"></i>
            {{ tab.label }}
          </div>
        </div>
        
        <div class="search-box">
          <i class="el-icon-search"></i>
          <input type="text" :placeholder="$t('articles.searchPlaceholder')" v-model="searchQuery">
        </div>
      </div> -->    
      
      <!-- 文章卡片网格 -->
      <div class="articles-grid">
        <div 
          v-for="article in filteredArticles" 
          :key="article.id"
          class="article-card visible"
          @click="viewArticle(article.slug)"
        >
          <!-- 文章封面图 -->
          <div class="article-cover">
            <img :src="article.image" :alt="article.title">
            <!-- <div class="article-category" :class="article.type">
              <i :class="getCategoryIcon(article.type)"></i>
              {{ $t(`articles.tags.${article.type}`) }}
            </div> -->
          </div>
          
          <!-- 文章内容 -->
           <div class="article-content">
            <h3 class="article-title">{{ article.title }}</h3>
            <p class="article-excerpt">{{ article.excerpt }}</p>
            
            <!-- 文章元数据 -->
             <div class="article-meta">
              <div class="meta-author">
                <img :src="article.avatar" :alt="article.authorName" class="author-avatar">
                <span>{{ article.authorName }}</span>
              </div>
              
              <div class="meta-info">
                <div class="meta-date">
                  <i class="el-icon-date"></i>
                  {{ formatDate(article.createTime) }}
                </div>
              </div>
            </div> 
            
            <!-- 阅读按钮 -->
            <div class="read-more">
              <span>{{ $t('articles.readMore') }}</span>
              <i class="el-icon-right"></i>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 加载更多按钮 -->
      <div class="load-more-container" v-if="hasMoreArticles">
        <button class="load-more-btn" @click="loadMoreArticles" :disabled="isLoading">
          <span v-if="!isLoading">{{ $t('articles.loadMore') }}</span>
          <i v-else class="el-icon-loading"></i>
        </button>
      </div>
      
      <!-- 空状态提示 -->
      <div class="empty-state" v-if="filteredArticles.length === 0">
        <i class="el-icon-document"></i>
        <p>{{ $t('articles.noArticlesFound') }}</p>
        <button @click="resetFilters">{{ $t('articles.resetFilters') }}</button>
      </div>
    </div>
    
    <app-footer />
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import api from '@/api/modules/articles'
import menuApi from '@/api/modules/menu'

export default {
  name: 'ArticleList',
  components: {
    AppHeader,
    AppFooter
  },
  data() {
    return {
      articles: [],
      activeTab: 'all',
      searchQuery: '',
      isLoading: false,
      page: 1,
      perPage: 4,
      hasMoreArticles: true,
      filterTabs: [
        { label: this.$t('articles.filterAll'), value: 'all', icon: 'el-icon-menu' },
        { label: this.$t('articles.filterComparisons'), value: 'comparison', icon: 'el-icon-data-analysis' },
        { label: this.$t('articles.filterReviews'), value: 'review', icon: 'el-icon-star-on' },
        { label: this.$t('articles.filterGuides'), value: 'guide', icon: 'el-icon-reading' },
        { label: this.$t('articles.filterCoupons'), value: 'coupon', icon: 'el-icon-price-tag' }
      ],
      pageTitle: '',
      pageSubtitle: ''
    }
  },
  computed: {
    // 获取当前路径的slug参数
    currentSlug() {
      console.log(`this.$route.params.slug:${this.$route.params.slug}`);
      return this.$route.params.slug || 'all';
    },
    // 获取当前页面全路径
    currentFullPath() {
      return this.$route.fullPath;
    },
    // 检查是否为搜索模式
    isSearchMode() {
      return this.$route.name === 'ArticleSearch' && this.$route.query.keyword;
    },
    // 获取搜索关键词
    searchKeyword() {
      return this.$route.query.keyword || '';
    },
    filteredArticles() {
      return this.articles.filter(article => {
        // 根据活动标签筛选
        const matchesTab = this.activeTab === 'all' || article.type === this.activeTab;
        
        // 根据搜索查询筛选
        const matchesSearch = !this.searchQuery || 
          article.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          article.excerpt.toLowerCase().includes(this.searchQuery.toLowerCase());
        
        return matchesTab && matchesSearch;
      });
    }
  },
  watch: {
    // 监听路由变化，重新加载文章
    '$route': {
      handler() {
        this.page = 1;
        this.articles = [];
        this.fetchPageTitleAndSubtitle();
        this.fetchArticles();
      },
      immediate: true
    }
  },
  mounted() {
     // 添加直接显示文章卡片，移除延迟动画
    this.makeArticlesVisible();
    this.fetchPageTitleAndSubtitle();
  },
  methods: {
    async fetchArticles() {
      this.isLoading = true;
      try {
        // 发送API请求,带上分页参数和当前类型
        const params = {
          pageNum: this.page,
          pageSize: this.perPage
        };
        
        // 如果有特定的slug，添加到请求参数中
        if (this.currentSlug && this.currentSlug !== 'all' && !this.isSearchMode) {
          params.slug = this.currentSlug;
        }
        
        // 如果是从搜索页面来的，添加搜索关键词
        if (this.isSearchMode) {
          params.keyword = this.searchKeyword;
        }
        
        const response = await api.getAll(params);
        
        const newArticles = response.rows;
        const total = response.total;
        
        // 添加到现有文章列表
        if(this.page === 1) {
          // 第一页直接替换
          this.articles = newArticles;
        } else {
          // 其他页追加
          this.articles = [...this.articles, ...newArticles];
        }
        
        // 根据总数判断是否还有更多文章
        this.hasMoreArticles = this.articles.length < total;
        
        // 确保新加载的文章也是可见的
        this.$nextTick(() => {
          this.makeArticlesVisible();
        });

        // 成功加载后增加页码
        this.page++;
      } catch (error) {
        console.error('获取文章列表失败:', error);
      } finally {
        this.isLoading = false;
      }
    },
    async fetchPageTitleAndSubtitle() {
      try {
        const path = this.currentFullPath;
        const res = await menuApi.getMenuTitleAndSubtitleByPath(path);
        this.pageTitle = res.title || '';
        this.pageSubtitle = res.subtitle || '';
      } catch (e) {
        this.pageTitle = '';
        this.pageSubtitle = '';
      }
    },
    loadMoreArticles() {
      this.fetchArticles();
    },
    viewArticle(slug) {
      this.$router.push({ name: 'Article', params: { slug } });
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}/${month}/${day}`;
    },
    resetFilters() {
      this.activeTab = 'all';
      this.searchQuery = '';
    },
    getCategoryIcon(type) {
      const icons = {
        comparison: 'el-icon-data-analysis',
        review: 'el-icon-star-on',
        guide: 'el-icon-reading',
        coupon: 'el-icon-price-tag'
      };
      return icons[type] || 'el-icon-document';
    },
    makeArticlesVisible() {
      // 直接让所有文章卡片可见，不使用滚动监听
      const cards = document.querySelectorAll('.article-card');
      cards.forEach(card => {
        card.classList.add('in-view');
        card.style.opacity = '1';
        card.style.transform = 'translateY(0)';
      });
    }
  }
}
</script>

<style scoped>
/* 重置部分基本样式，防止冲突 */
* {
  box-sizing: border-box;
}

.article-list-page {
  background-color: #f8fafc;
  min-height: 100vh;
  --primary-color: #1e88e5;
  --secondary-color: #26a69a;
  --accent-color: #ff5722;
  width: 100%;
  overflow-x: hidden;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px 60px;
  width: 100%;
  box-sizing: border-box;
}

/* 页面标题区域 */
.page-header {
  position: relative;
  padding: 60px 20px;
  margin-bottom: 40px;
  background: linear-gradient(135deg, var(--primary-color), #3949ab);
  border-radius: 16px;
  color: white;
  overflow: hidden;
  text-align: center;
}

.header-content {
  position: relative;
  z-index: 2;
}

.main-title {
  font-size: 3rem;
  font-weight: 800;
  margin-bottom: 20px;
  background: linear-gradient(to right, #ffffff, #e3f2fd);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: title-shine 3s ease-in-out infinite alternate;
}

.subtitle {
  font-size: 1.2rem;
  max-width: 700px;
  margin: 0 auto;
  line-height: 1.6;
  opacity: 0.9;
}

.header-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.decoration-shape {
  position: absolute;
  border-radius: 50%;
  opacity: 0.15;
}

.shape1 {
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, #ffffff, transparent 70%);
  top: -100px;
  left: -50px;
  animation: float 8s ease-in-out infinite;
}

.shape2 {
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, #ffffff, transparent 70%);
  bottom: -50px;
  right: 100px;
  animation: float 6s ease-in-out infinite 1s;
}

.shape3 {
  width: 150px;
  height: 150px;
  background: radial-gradient(circle, #ffffff, transparent 70%);
  top: 50px;
  right: -30px;
  animation: float 7s ease-in-out infinite 0.5s;
}

/* 过滤器区域 */
.filter-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 0 10px;
}

.filter-tabs {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.filter-tab {
  padding: 8px 16px;
  background: white;
  border-radius: 50px;
  font-weight: 500;
  color: #64748b;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
  display: flex;
  align-items: center;
  gap: 6px;
}

.filter-tab i {
  font-size: 16px;
}

.filter-tab:hover {
  background: #f0f7ff;
  color: var(--primary-color);
  transform: translateY(-2px);
}

.filter-tab.active {
  background: var(--primary-color);
  color: white;
  box-shadow: 0 4px 10px rgba(30, 136, 229, 0.3);
}

.search-box {
  position: relative;
  width: 280px;
}

.search-box input {
  width: 100%;
  padding: 10px 16px 10px 40px;
  border: none;
  border-radius: 50px;
  background: white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-box input:focus {
  outline: none;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.search-box i {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #64748b;
  font-size: 18px;
}

/* 文章网格 - 使用flexbox代替grid */
.articles-grid {
  display: flex !important;
  flex-wrap: wrap !important;
  gap: 20px !important;
  margin-bottom: 50px !important;
  width: 100% !important;
}

.article-card {
  background: white !important;
  border-radius: 12px !important;
  overflow: hidden !important;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.07) !important;
  transition: all 0.3s ease !important;
  cursor: pointer !important;
  flex: 0 0 calc(25% - 15px) !important; /* 四列布局，考虑间距 */
  margin-bottom: 20px !important;
  opacity: 1 !important; 
  transform: translateY(0) !important;
  min-width: 0 !important; /* 防止卡片撑开区域 */
}

@media screen and (max-width: 1280px) {
  .article-card {
    flex: 0 0 calc(33.333% - 14px) !important; /* 三列布局 */
  }
}

@media screen and (max-width: 1024px) {
  .article-card {
    flex: 0 0 calc(50% - 10px) !important; /* 两列布局 */
  }
}

@media screen and (max-width: 768px) {
  .article-card {
    flex: 0 0 100% !important; /* 单列布局 */
  }
}

.article-card.visible, 
.article-card.in-view {
  opacity: 1;
  transform: translateY(0);
}

.article-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.article-cover {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.article-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.article-card:hover .article-cover img {
  transform: scale(1.05);
}

.article-category {
  position: absolute;
  top: 15px;
  left: 15px;
  padding: 5px 12px;
  border-radius: 50px;
  font-size: 0.8rem;
  font-weight: 600;
  color: white;
  display: flex;
  align-items: center;
  gap: 6px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.article-category.comparison {
  background: linear-gradient(135deg, #3949ab, #1e88e5);
}

.article-category.review {
  background: linear-gradient(135deg, #f57c00, #ffb74d);
}

.article-category.guide {
  background: linear-gradient(135deg, #388e3c, #66bb6a);
}

.article-category.coupon {
  background: linear-gradient(135deg, #d32f2f, #ef5350);
}

.article-content {
  padding: 25px;
}

.article-title {
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 12px;
  color: #334155;
  line-height: 1.4;
  transition: color 0.3s ease;
}

.article-card:hover .article-title {
  color: var(--primary-color);
}

.article-excerpt {
  color: #64748b;
  margin-bottom: 20px;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-top: 15px;
  border-top: 1px solid #f1f5f9;
}

.meta-author {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  object-fit: cover;
}

.meta-author span {
  font-size: 0.9rem;
  font-weight: 500;
  color: #475569;
}

.meta-info {
  display: flex;
  gap: 15px;
  color: #94a3b8;
  font-size: 0.85rem;
}

.meta-date, .meta-time {
  display: flex;
  align-items: center;
  gap: 5px;
}

.read-more {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--primary-color);
  font-weight: 600;
  font-size: 0.95rem;
  transition: all 0.3s ease;
  position: relative;
}

.read-more::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 0;
  height: 2px;
  background: var(--primary-color);
  transition: width 0.3s ease;
}

.article-card:hover .read-more::after {
  width: 100%;
}

.article-card:hover .read-more i {
  transform: translateX(4px);
}

.read-more i {
  transition: transform 0.3s ease;
}

/* 加载更多按钮 */
.load-more-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.load-more-btn {
  background: white;
  border: 2px solid var(--primary-color);
  color: var(--primary-color);
  font-weight: 600;
  padding: 10px 30px;
  border-radius: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 160px;
  min-height: 45px;
}

.load-more-btn:hover:not(:disabled) {
  background: var(--primary-color);
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(30, 136, 229, 0.3);
}

.load-more-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: #64748b;
}

.empty-state i {
  font-size: 3rem;
  margin-bottom: 20px;
  opacity: 0.4;
}

.empty-state p {
  font-size: 1.2rem;
  margin-bottom: 20px;
}

.empty-state button {
  background: var(--primary-color);
  color: white;
  border: none;
  padding: 8px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.empty-state button:hover {
  background: #1976d2;
  transform: translateY(-2px);
}

/* 动画 */
@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

@keyframes title-shine {
  0% {
    background-position: -200% center;
  }
  100% {
    background-position: 200% center;
  }
}

.article-fade-enter-active, .article-fade-leave-active {
  transition: all 0.5s;
}

.article-fade-enter, .article-fade-leave-to {
  opacity: 0;
  transform: translateY(30px);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .filter-container {
    flex-direction: column;
    align-items: stretch;
    gap: 20px;
  }
  
  .search-box {
    width: 100%;
  }
  
  .page-header {
    padding: 40px 15px;
  }
  
  .main-title {
    font-size: 2.2rem;
  }
  
  .article-meta {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .meta-info {
    width: 100%;
    justify-content: space-between;
  }
}
</style> 
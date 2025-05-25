<template>
  <div class="article-page">
    <app-header />
    
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner"></div>
      <p class="loading-text">{{ $t('article.loading') }}</p>
    </div>
    
    <div v-else-if="error" class="error-container">
      <h2 class="error-title">{{ $t('article.error.title') }}</h2>
      <p class="error-message">{{ error }}</p>
      <button @click="fetchArticleData" class="retry-button">{{ $t('article.error.retry') }}</button>
    </div>
    
    <div v-else-if="article" class="article-container">
      <div class="article-header">
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta-new">
          <span class="publish-date">{{ article.publishDate }}</span>
          <span class="read-time">{{ article.readTime }}{{ $t('article.readTime') }}</span>
        </div>
      </div>
      
      <div class="article-content">
        <div class="article-main">
          <div class="article-content-wrapper">
            <div v-html="article.content"></div>
          </div>
          
          <div class="article-tags" v-if="tagList.length">
            <span class="tag-label">{{ $t('article.tags') }}</span>
            <span v-for="(tag, index) in tagList" :key="index" class="tag-item">{{ tag }}</span>
          </div>
          
          <div class="article-author-box">
            <img :src="article.avatar" :alt="$t('article.authorInfo')" class="author-box-avatar" />
            <div class="author-box-info">
              <h4 class="author-box-name">{{ article.authorName }}</h4>
              <p class="author-box-bio">{{ article.bio }}</p>
            </div>
          </div>
        </div>
        
        <div class="article-sidebar">
          <div class="sidebar-widget article-toc">
            <h3 class="widget-title">{{ $t('article.tableOfContents') }}</h3>
            <ul v-if="articleToc.length" class="toc-list">
              <li 
                v-for="(heading, index) in articleToc" 
                :key="index" 
                class="toc-item"
                :class="{ 
                  'level-2': heading.level === 2,
                  'level-3': heading.level === 3,
                  'active': activeHeadingId === heading.id
                }"
              >
                <a @click.prevent="scrollToHeading(heading.id)" class="toc-link" href="#">
                  {{ heading.text }}
                </a>
              </li>
            </ul>
            <p v-else class="toc-empty">{{ $t('article.noTableOfContents') }}</p>
          </div>
          
          <div ref="stickyOffers" :class="['sidebar-widget', 'related-posts', {'sticky': isSticky}]">
            <h3 class="widget-title">{{ $t('article.exclusiveOffers') }}</h3>
            <div class="offers-list">
              <div v-for="(offer, index) in offers" :key="index" class="offer-item">
                <div class="offer-logo">
                  <img :src="offer.logo" :alt="offer.name">
                </div>
                <a 
                  :href="offer.link" 
                  target="_blank" 
                  rel="noopener noreferrer" 
                  class="show-offer-btn"
                >
                  {{ $t('article.showOffer') }}
                </a>
              </div>
            </div>
          </div>
          
          <div class="sidebar-widget subscribe-form">
            <h3 class="widget-title">{{ $t('article.subscribe.title') }}</h3>
            <p class="widget-description">{{ $t('article.subscribe.description') }}</p>
            <form @submit.prevent="subscribe" class="subscribe-form-container">
              <input type="email" v-model="email" :placeholder="$t('article.subscribe.placeholder')" required :disabled="subscribeLoading" />
              <button type="submit" class="subscribe-button" :disabled="subscribeLoading">
                <span v-if="!subscribeLoading">{{ $t('article.subscribe.button') }}</span>
                <span v-else>{{ $t('article.subscribe.loading') }}</span>
              </button>
              <div v-if="subscribeSuccess" class="subscribe-message success">{{ $t('article.subscribe.success') }}</div>
              <div v-if="subscribeError" class="subscribe-message error">{{ subscribeError }}</div>
            </form>
          </div>
        </div>
      </div>
    </div>
    
    <section v-if="!loading && !error && article" class="more-articles">
      <div class="container">
        <h2 class="section-title">{{ $t('article.moreArticles') }}</h2>
        <div class="articles-grid">
          <article-card 
            v-for="(post, index) in morePosts" 
            :key="index" 
            :article="post" 
          />
        </div>
      </div>
    </section>
    
    <app-footer />
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import ArticleCard from '@/components/ArticleCard.vue'
import api from '@/api/modules/articles'
import commonApi from '@/api/modules/common'

export default {
  name: 'Article',
  components: {
    AppHeader,
    AppFooter,
    ArticleCard
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      // 确保在路由进入时滚动到顶部
      window.scrollTo(0, 0);
    });
  },
  beforeRouteUpdate(to, from, next) {
    // 路由参数更新时也滚动到顶部
    window.scrollTo(0, 0);
    next();
  },
  props: {
    slug: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      email: '',
      article: null,
      relatedPosts: [],
      morePosts: [],
      loading: true,
      error: null,
      subscribeLoading: false,
      subscribeSuccess: false,
      subscribeError: null,
      articleToc: [],
      activeHeadingId: null,
      offers: [],
      isSticky: false,
      stickyWidthCache: null,
      lastWindowWidth: null
    }
  },
  created() {
    this.fetchArticleData();
    this.initOffers();
    // 确保页面从顶部开始显示
    window.scrollTo(0, 0);
  },
  watch: {
    slug(newSlug) {
      if (newSlug) {
        window.scrollTo(0, 0);
        this.fetchArticleData();
      }
    },
    article() {
      // 文章内容变化时，等待DOM更新后重新计算位置
      this.$nextTick(() => {
        // 重置宽度缓存，确保使用最新宽度
        this.stickyWidthCache = null;
        this.handleScrollForSticky();
      });
    }
  },
  mounted() {
    // 添加滚动监听，以高亮当前可见的标题
    window.addEventListener('scroll', this.highlightActiveHeading);
    // 添加滚动监听，处理优惠区域的固定效果
    window.addEventListener('scroll', this.handleScrollForSticky);
    // 添加窗口大小改变监听
    window.addEventListener('resize', this.handleWindowResize);
    
    // 等待页面完全加载后再初始化固定效果
    if (document.readyState === 'complete') {
      // 初始化宽度缓存
      if (this.$refs.stickyOffers) {
        this.stickyWidthCache = this.$refs.stickyOffers.getBoundingClientRect().width;
        this.lastWindowWidth = window.innerWidth;
      }
      this.handleScrollForSticky();
    } else {
      window.addEventListener('load', () => {
        // 初始化宽度缓存
        if (this.$refs.stickyOffers) {
          this.stickyWidthCache = this.$refs.stickyOffers.getBoundingClientRect().width;
          this.lastWindowWidth = window.innerWidth;
        }
        this.handleScrollForSticky();
      }, { once: true });
    }
  },
  beforeDestroy() {
    // 移除滚动监听
    window.removeEventListener('scroll', this.highlightActiveHeading);
    window.removeEventListener('scroll', this.handleScrollForSticky);
    window.removeEventListener('resize', this.handleWindowResize);
  },
  methods: {
    fetchArticleData() {
      this.loading = true;
      this.error = null;
      
      api.getBySlug(this.slug)
        .then(data => {
          this.article = data;
          console.log(this.article);
          
          // 解析文章中的一级标题
          this.parseArticleHeadings();
          
          // 获取相关文章
          return api.getAll({ page: 1, pageSize: 3, excludeSlug: this.slug });
        })
        .then(relatedData => {
          if (relatedData && relatedData.rows) {
            this.relatedPosts = relatedData.rows.map(post => ({
              id: post.id,
              title: post.title,
              image: post.featuredImage || post.image,
              link: `/article/${post.slug}`
            }));
            
            // 更多文章数据
            this.morePosts = relatedData.rows.slice(0, 3).map(post => ({
              id: post.id,
              title: post.title,
              image: post.featuredImage || post.image,
              excerpt: post.excerpt || post.description,
              date: post.publishDate || post.date,
              link: `/article/${post.slug}`
            }));
          }
          this.loading = false;
          
          // 确保在内容加载完成后，页面从顶部显示
          this.$nextTick(() => {
            window.scrollTo(0, 0);
          });
        })
        .catch(error => {
          console.error(`${this.$t('article.error.fetchFailed')}:`, error);
          this.error = this.$t('article.error.fetchFailed');
          this.loading = false;
        });
    },
    subscribe() {
      if (this.email) {
        this.subscribeLoading = true;
        
        commonApi.subscribe({ email: this.email })
          .then(() => {
            this.subscribeSuccess = true;
            this.email = '';
            setTimeout(() => {
              this.subscribeSuccess = false;
            }, 3000);
          })
          .catch(error => {
            console.error(`${this.$t('article.subscribe.error')}:`, error);
            this.subscribeError = this.$t('article.subscribe.error');
            setTimeout(() => {
              this.subscribeError = null;
            }, 3000);
          })
          .finally(() => {
            this.subscribeLoading = false;
          });
      }
    },
    scrollToHeading(id) {
      const element = document.getElementById(id);
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
        this.activeHeadingId = id;
      }
    },
    parseArticleHeadings() {
      this.articleToc = [];
      if (!this.article || !this.article.content) return;
      
      // 创建一个临时DOM元素来解析HTML内容
      const tempDiv = document.createElement('div');
      tempDiv.innerHTML = this.article.content;
      
      // 查找所有的h1, h2, h3标签
      const headings = tempDiv.querySelectorAll('h1, h2, h3');
      
      // 清空现有的目录
      this.articleToc = [];
      
      // 为每个标题生成一个唯一ID并添加到目录
      headings.forEach((heading, index) => {
        const headingId = `heading-${index}`;
        const tagName = heading.tagName.toLowerCase();
        const level = parseInt(tagName.replace('h', ''), 10); // 获取标题级别
        
        // 将ID添加到实际的标题元素上
        heading.id = headingId;
        
        // 添加到目录数组
        this.articleToc.push({
          id: headingId,
          text: heading.textContent.trim(),
          level: level
        });
      });
      
      // 处理表格元素，添加表格容器以支持水平滚动
      const tables = tempDiv.querySelectorAll('table');
      tables.forEach(table => {
        const tableContainer = document.createElement('div');
        tableContainer.className = 'table-container';
        
        // 获取表格的父元素
        const parent = table.parentNode;
        
        // 插入容器元素并将表格移动到容器内
        parent.insertBefore(tableContainer, table);
        tableContainer.appendChild(table);
      });
      
      // 处理iframe元素，添加响应式视频容器
      const iframes = tempDiv.querySelectorAll('iframe');
      iframes.forEach(iframe => {
        // 只处理视频相关的iframe
        if (iframe.src.includes('youtube.com') || 
            iframe.src.includes('vimeo.com') || 
            iframe.src.includes('youku.com') ||
            iframe.src.includes('bilibili.com')) {
          
          const videoContainer = document.createElement('div');
          videoContainer.className = 'video-container';
          
          // 获取iframe的父元素
          const parent = iframe.parentNode;
          
          // 插入容器元素并将iframe移动到容器内
          parent.insertBefore(videoContainer, iframe);
          videoContainer.appendChild(iframe);
        }
      });
      
      // 确保所有图片都有正确的样式
      const images = tempDiv.querySelectorAll('img');
      images.forEach(img => {
        // 不再移除style/width/height属性，保留编辑器设置的缩放
        img.classList.add('article-img');
        // 包装图片在响应式容器中
        if (img.parentNode.nodeName !== 'FIGURE' && 
            !img.parentNode.classList.contains('img-container')) {
          const imgContainer = document.createElement('div');
          imgContainer.className = 'img-container';
          // 获取图片的父元素
          const parent = img.parentNode;
          // 插入容器并移动图片到容器内
          parent.insertBefore(imgContainer, img);
          imgContainer.appendChild(img);
        }
      });
      
      // 更新文章内容，包含修改后的带ID的标题和调整后的媒体元素
      this.article.content = tempDiv.innerHTML;
    },
    // 高亮当前可见的标题
    highlightActiveHeading() {
      if (!this.articleToc.length) return;
      
      // 获取所有标题元素
      const headingElements = this.articleToc.map(heading => 
        document.getElementById(heading.id)
      ).filter(el => el !== null);
      
      if (!headingElements.length) return;
      
      // 找出当前在视口中的标题
      const scrollPosition = window.scrollY;
      
      // 找到第一个在视口顶部以下的标题，它的前一个就是当前标题
      for (let i = 0; i < headingElements.length; i++) {
        const headingTop = headingElements[i].getBoundingClientRect().top + scrollPosition;
        const nextHeadingTop = (i < headingElements.length - 1) 
          ? headingElements[i + 1].getBoundingClientRect().top + scrollPosition 
          : Infinity;
        
        if (scrollPosition >= headingTop - 100 && scrollPosition < nextHeadingTop - 100) {
          this.activeHeadingId = headingElements[i].id;
          return;
        }
      }
      
      // 如果没有找到合适的标题，默认选中第一个
      this.activeHeadingId = headingElements[0].id;
    },
    initOffers() {
      // 设置默认优惠数据
      this.offers = [
        {
          name: 'Helium 10',
          logo: 'https://www.helium10.com/app/themes/helium10/assets/img/logos/logo-blue-duotone.svg',
          link: 'https://www.helium10.com/offers/special?ref=revenuegeeks'
        },
        {
          name: 'Jungle Scout',
          logo: 'https://revenuegeeks.com/wp-content/uploads/2020/10/jungle-scout-logo-300x41.png',
          link: 'https://www.junglescout.com/offers/discount?ref=revenuegeeks'
        },
        {
          name: 'SmartScout',
          logo: 'https://revenuegeeks.com/wp-content/uploads/2022/05/smartscout-logo-300x75.png',
          link: 'https://smartscout.com/special-offer?ref=revenuegeeks'
        },
        {
          name: 'Quartile',
          logo: 'https://revenuegeeks.com/wp-content/uploads/2024/05/seller-assitant-new.png',
          link: 'https://quartile.com/promo?ref=revenuegeeks'
        }
      ];
    },
    handleScrollForSticky() {
      // 如果页面还在加载或者DOM元素不存在，则不处理
      if (this.loading || !this.$refs.stickyOffers) return;
      
      const sidebar = this.$el.querySelector('.article-sidebar');
      if (!sidebar) return; // 确保侧边栏元素存在
      
      const stickyElement = this.$refs.stickyOffers;
      const header = document.querySelector('header') || { offsetHeight: 0 };
      const headerHeight = header.offsetHeight || 0;
      
      const stickyOffset = sidebar.offsetTop;
      const scrollPosition = window.scrollY;
      
      // 如果页面刚加载且滚动位置不在顶部，则不应用sticky效果
      if (scrollPosition < 10 && document.readyState === 'complete') {
        this.isSticky = false;
        stickyElement.style.width = '';
        return;
      }
      
      // 考虑页面宽度，在移动视图中不应该有固定效果
      if (window.innerWidth <= 1024) {
        this.isSticky = false;
        stickyElement.style.width = '';
        stickyElement.style.top = '0px';
        return;
      }
      
      // 计算宽度并更新缓存
      if (!this.stickyWidthCache || window.innerWidth !== this.lastWindowWidth) {
        this.stickyWidthCache = stickyElement.getBoundingClientRect().width;
        this.lastWindowWidth = window.innerWidth;
      }
      
      // 判断是否应该固定
      if (scrollPosition > stickyOffset - headerHeight - 20) {
        if (!this.isSticky) {
          // 在切换到固定状态前设置精确宽度
          stickyElement.style.width = `${this.stickyWidthCache}px`;
          this.isSticky = true;
        }
        
        // 动态调整顶部距离，考虑header高度
        stickyElement.style.top = `${headerHeight + 20}px`;
      } else {
        if (this.isSticky) {
          this.isSticky = false;
          // 恢复默认宽度
          stickyElement.style.width = '100%';
        }
      }
      
      // 计算底部边界，防止与页脚重叠
      const footer = document.querySelector('footer');
      if (footer && this.isSticky) {
        const footerTop = footer.getBoundingClientRect().top;
        const stickyBottom = stickyElement.offsetHeight + headerHeight + 40;
        
        if (footerTop - stickyBottom < 0) {
          // 调整位置以避免与页脚重叠
          const newTop = footerTop - stickyBottom;
          stickyElement.style.top = `${newTop}px`;
        }
      }
    },
    handleWindowResize() {
      // 窗口大小改变时重置宽度缓存
      this.stickyWidthCache = null;
      // 重新计算悬浮位置
      this.handleScrollForSticky();
    }
  },
  computed: {
    tagList() {
      if (!this.article || !this.article.tags) return [];
      if (Array.isArray(this.article.tags)) return this.article.tags;
      return this.article.tags.split(',').map(tag => tag.trim()).filter(tag => tag);
    }
  },
  head() {
    return {
      title: this.article ? this.article.title : this.$t('article.loading'),
      meta: [
        { name: 'description', content: this.article ? this.article.excerpt : '' }
      ]
    }
  }
}
</script>

<style scoped>
.article-page {
  background-color: #fff;
}

.article-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

.article-header {
  margin-top: 48px;
  margin-bottom: 36px;
  text-align: center;
}

.article-title {
  font-size: 2.6rem;
  color: var(--e-global-color-primary, #222);
  margin-bottom: 18px;
  font-weight: 800;
  line-height: 1.2;
  letter-spacing: 1px;
}

.article-meta-new {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 18px;
  font-size: 1rem;
  color: #888;
  margin-bottom: 0;
}

.publish-date {
  font-weight: 500;
  color: #888;
}

.read-time {
  color: #aaa;
  font-size: 0.98em;
}

.article-content {
  display: flex;
  gap: 40px;
  max-width: 1200px;
  margin: 0 auto;
}

.article-main {
  flex: 3;
  min-width: 0; /* 防止flex项目溢出容器 */
}

.article-content-wrapper {
  max-width: 740px;
  margin: 0 auto;
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  padding: 0 15px;
  overflow-x: hidden; /* 防止内容溢出 */
}

.article-content-wrapper a {
  color: var(--e-global-color-accent);
  text-decoration: none;
  border-bottom: 1px solid transparent;
  transition: border-color 0.2s ease;
}

.article-content-wrapper a:hover {
  border-bottom-color: var(--e-global-color-accent);
}

.article-content-wrapper blockquote {
  border-left: 4px solid var(--e-global-color-accent);
  margin: 20px 0;
  padding: 10px 20px;
  background-color: rgba(0, 0, 0, 0.03);
  font-style: italic;
}

.article-content-wrapper pre {
  background-color: #f5f5f5;
  padding: 15px;
  border-radius: 5px;
  overflow-x: auto;
  margin: 20px 0;
}

.article-content-wrapper code {
  font-family: monospace;
  background-color: #f5f5f5;
  padding: 2px 5px;
  border-radius: 3px;
  font-size: 0.9em;
}

/* 确保表格不会突破容器宽度 */
.article-content-wrapper .table-container {
  width: 100%;
  overflow-x: auto;
  margin: 25px 0;
}

.article-content-wrapper table {
  width: 100%;
  border-collapse: collapse;
  margin: 0; /* 重置表格margin，因为.table-container已有margin */
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
}

.article-content-wrapper th,
.article-content-wrapper td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.article-content-wrapper th {
  background-color: var(--e-global-color-accent);
  color: white;
  font-weight: 600;
}

.article-content-wrapper tr:nth-child(even) {
  background-color: #f8f8f8;
}

.article-content-wrapper tr:hover {
  background-color: #f1f1f1;
}

.article-main h2 {
  font-size: 24px;
  color: var(--e-global-color-primary);
  margin: 30px 0 15px;
}

.article-main p {
  margin-bottom: 20px;
  line-height: 1.6;
}

.article-main ul, .article-main ol {
  margin: 20px 0;
  padding-left: 20px;
}

.article-main li {
  margin-bottom: 10px;
  line-height: 1.6;
}

.article-tags {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  border: 2px solid #ff3c3c;
  border-radius: 8px;
  padding: 18px 24px;
  margin: 40px 0 0 0;
  background: #fff;
  box-shadow: 0 4px 24px rgba(255,60,60,0.07);
  gap: 0 12px;
}

.tag-label {
  font-weight: bold;
  color: #222;
  margin-right: 18px;
  font-size: 1.08rem;
  letter-spacing: 1px;
}

.tag-item {
  display: inline-block;
  background: linear-gradient(90deg, #ff3c3c 0%, #ffb347 100%);
  color: #fff;
  padding: 6px 18px;
  border-radius: 20px;
  font-size: 1rem;
  font-weight: 500;
  margin-right: 10px;
  margin-bottom: 6px;
  box-shadow: 0 2px 8px rgba(255,60,60,0.10);
  transition: transform 0.18s cubic-bezier(.4,2,.6,1), box-shadow 0.18s;
  cursor: pointer;
  border: none;
  letter-spacing: 0.5px;
}

.tag-item:hover {
  transform: translateY(-3px) scale(1.08) rotate(-2deg);
  box-shadow: 0 6px 18px rgba(255,60,60,0.18);
  background: linear-gradient(90deg, #ffb347 0%, #ff3c3c 100%);
  color: #fff;
}

.article-author-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fff;
  padding: 40px 20px 30px 20px;
  border-radius: 10px;
  box-shadow: 0 2px 16px rgba(0,0,0,0.06);
  margin: 40px auto 0 auto;
  max-width: 700px;
  text-align: center;
}

.author-box-avatar {
  width: 110px;
  height: 110px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.author-box-name {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 18px;
  color: #222;
  text-align: center;
}

.author-box-bio {
  font-size: 1.08rem;
  color: #444;
  line-height: 1.7;
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
}

.article-sidebar {
  flex: 1;
  min-width: 250px;
}

.sidebar-widget {
  margin-bottom: 40px;
  background-color: #f8f8f8;
  padding: 20px;
  border-radius: 5px;
}

.widget-title {
  font-size: 20px;
  color: var(--e-global-color-primary);
  margin-bottom: 15px;
}

.widget-description {
  margin-bottom: 15px;
  font-size: 14px;
}

.related-posts-list {
  list-style: none;
  padding: 0;
}

.related-post-item {
  margin-bottom: 15px;
}

.related-post-link {
  display: flex;
  align-items: center;
  color: var(--e-global-color-primary);
  text-decoration: none;
}

.related-post-title {
  font-size: 14px;
  font-weight: 500;
  margin: 0;
}

.subscribe-form-container {
  display: flex;
  flex-direction: column;
}

.subscribe-form-container input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 3px;
  margin-bottom: 10px;
}

.subscribe-button {
  background-color: var(--e-global-color-accent);
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 3px;
  cursor: pointer;
  font-weight: 600;
}

.subscribe-button:hover {
  background-color: var(--e-global-color-8b9b721);
}

.subscribe-message {
  margin-top: 10px;
  padding: 8px;
  border-radius: 3px;
  font-size: 14px;
  text-align: center;
}

.subscribe-message.success {
  background-color: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
  border: 1px solid #4CAF50;
}

.subscribe-message.error {
  background-color: rgba(244, 67, 54, 0.1);
  color: #F44336;
  border: 1px solid #F44336;
}

.more-articles {
  background-color: #f8f8f8;
  padding: 60px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.section-title {
  font-size: 28px;
  color: var(--e-global-color-primary);
  margin-bottom: 30px;
  text-align: center;
}

.articles-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

@media (max-width: 1024px) {
  .article-content {
    flex-direction: column;
    padding: 0 20px;
  }
  
  .article-sidebar {
    margin-top: 40px;
  }
  
  .article-content-wrapper {
    max-width: 100%;
  }
  
  .articles-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .sidebar-widget.related-posts {
    position: relative; /* 确保在小屏设备上不是fixed定位 */
  }
  
  .sidebar-widget.related-posts.sticky {
    position: static;
    width: 100%;
    max-width: 100%;
    transform: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  }
}

@media (max-width: 767px) {
  .article-header {
    margin-top: 24px;
    margin-bottom: 22px;
  }
  .article-title {
    font-size: 1.4rem;
    margin-bottom: 12px;
  }
  .article-meta-new {
    font-size: 0.92rem;
    gap: 10px;
  }
  .articles-grid {
    grid-template-columns: 1fr;
  }
  
  .article-author-box {
    flex-direction: column;
    text-align: center;
  }
  
  .author-box-avatar {
    margin: 0 auto 15px;
  }
}

/* 加载和错误状态样式 */
.loading-container, .error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  padding: 40px 20px;
  text-align: center;
}

.loading-spinner {
  border: 4px solid rgba(0, 0, 0, 0.1);
  border-radius: 50%;
  border-top: 4px solid var(--e-global-color-accent);
  width: 50px;
  height: 50px;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-text {
  font-size: 18px;
  color: var(--e-global-color-primary);
}

.error-title {
  font-size: 24px;
  color: var(--e-global-color-accent);
  margin-bottom: 10px;
}

.error-message {
  font-size: 16px;
  color: var(--e-global-color-primary);
  margin-bottom: 20px;
}

.retry-button {
  background-color: var(--e-global-color-accent);
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 3px;
  cursor: pointer;
  font-weight: 600;
}

.retry-button:hover {
  background-color: var(--e-global-color-8b9b721);
}

.toc-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.toc-item {
  margin-bottom: 10px;
  line-height: 1.4;
}

.toc-item.level-2 {
  padding-left: 15px;
}

.toc-item.level-3 {
  padding-left: 30px;
  font-size: 0.9em;
}

.toc-item.active .toc-link {
  color: var(--e-global-color-accent);
  font-weight: 600;
  position: relative;
}

.toc-item.active .toc-link::before {
  content: '';
  position: absolute;
  left: -10px;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 16px;
  background-color: var(--e-global-color-accent);
  border-radius: 2px;
}

.toc-link {
  color: var(--e-global-color-primary);
  text-decoration: none;
  font-size: 14px;
  display: block;
  padding: 5px 0;
  transition: all 0.2s ease;
}

.toc-link:hover {
  color: var(--e-global-color-accent);
  text-decoration: underline;
}

.toc-empty {
  font-size: 14px;
  color: #666;
  font-style: italic;
}

/* 处理iframe和视频元素 */
.article-content-wrapper iframe,
.article-content-wrapper video,
.article-content-wrapper embed,
.article-content-wrapper object {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 25px auto;
}

/* 创建响应式视频容器 */
.article-content-wrapper .video-container {
  position: relative;
  padding-bottom: 56.25%; /* 16:9比例 */
  height: 0;
  overflow: hidden;
  margin: 25px 0;
}

.article-content-wrapper .video-container iframe,
.article-content-wrapper .video-container video,
.article-content-wrapper .video-container embed,
.article-content-wrapper .video-container object {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  margin: 0;
}

/* 移动设备适配 */
@media (max-width: 768px) {
  .article-content-wrapper {
    padding: 0 10px;
  }
  
  .article-content-wrapper img {
    margin: 15px 0;
  }
  
  .article-title {
    font-size: 28px;
  }
  
  .article-container {
    padding: 30px 15px;
  }
  
  /* 确保表格在小屏幕上正确显示 */
  .article-content-wrapper table {
    font-size: 14px;
  }
  
  .article-content-wrapper th,
  .article-content-wrapper td {
    padding: 8px 10px;
  }
  
  .article-content-wrapper .img-container {
    margin: 20px 0;
  }
  
  .article-content-wrapper img {
    margin: 0 auto;
  }
  
  .article-content-wrapper figure {
    margin: 20px 0;
  }
  
  /* 优化移动端优惠显示 */
  .offer-item {
    padding: 12px;
  }
  
  .offer-logo {
    max-width: 50%;
  }
  
  .show-offer-btn {
    padding: 6px 12px;
    font-size: 12px;
  }
}

/* 图片容器样式 */
.article-content-wrapper .img-container {
  margin: 25px 0;
  text-align: center;
  max-width: 100%;
}

.article-content-wrapper .img-container img {
  margin: 0 auto; /* 覆盖默认的img margin */
  max-height: none; /* 允许图片保持自然高度 */
}

/* 处理figure标签内的图片 */
.article-content-wrapper figure {
  margin: 25px 0;
  max-width: 100%;
  text-align: center;
}

.article-content-wrapper figure img {
  margin: 0 auto 10px;
}

.article-content-wrapper figcaption {
  font-size: 14px;
  color: #666;
  text-align: center;
  font-style: italic;
  margin-top: 5px;
}

/* 优惠列表样式 */
.offers-list {
  margin-top: 15px;
}

.offer-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  border-bottom: 1px solid #eee;
  background-color: #fff;
  margin-bottom: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.offer-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.offer-logo {
  flex: 1;
  padding-right: 15px;
  display: flex;
  align-items: center;
  max-width: 55%;
}

.offer-logo img {
  max-width: 100%;
  max-height: 30px;
  object-fit: contain;
}

.show-offer-btn {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
  text-decoration: none;
  white-space: nowrap;
}

.show-offer-btn:hover {
  background-color: #c0392b;
}

.sidebar-widget.related-posts h3.widget-title {
  position: relative;
  background-color: #2a9d8f;
  color: white;
  text-align: center;
  padding: 10px;
  margin: -20px -20px 15px -20px;
  border-radius: 5px 5px 0 0;
}

.sidebar-widget.related-posts h3.widget-title::before,
.sidebar-widget.related-posts h3.widget-title::after {
  content: '💥';
  margin: 0 5px;
}

/* 滚动跟随样式 */
.sidebar-widget.related-posts {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  width: 100%; /* 确保非固定状态下宽度与父容器一致 */
}

.sidebar-widget.related-posts.sticky {
  position: fixed;
  top: 20px;
  /* 移除width: inherit，改为使用固定的宽度值 */
  /* width: inherit; */
  z-index: 100;
  transition: top 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  transform: translateZ(0); /* 启用硬件加速 */
  will-change: position, top; /* 提示浏览器将要改变的属性 */
}

@media (max-width: 1024px) {
  .sidebar-widget.related-posts.sticky {
    position: static;
    width: 100%;
    max-width: 100%;
  }
}

.article-content-wrapper img,
.article-content-wrapper .article-img {
  max-width: 100% !important;
  height: auto !important;
  display: block;
  margin: 0 auto;
}

@media (max-width: 600px) {
  .article-author-box {
    padding: 25px 8px 20px 8px;
    max-width: 98%;
  }
  .author-box-avatar {
    width: 80px;
    height: 80px;
  }
  .author-box-name {
    font-size: 1.3rem;
  }
  .author-box-bio {
    font-size: 0.98rem;
    max-width: 98%;
  }
  .article-tags {
    padding: 12px 6px;
    gap: 0 6px;
  }
  .tag-label {
    margin-right: 8px;
    font-size: 0.98rem;
  }
  .tag-item {
    font-size: 0.92rem;
    padding: 5px 12px;
    margin-right: 6px;
  }
}
</style> 
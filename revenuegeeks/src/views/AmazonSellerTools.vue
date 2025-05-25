<template>
  <div class="amazon-seller-tools">
    <app-header />
    
    <div class="main-content-wrapper">
      <div class="hero-section">
        <h1>{{ $t('tools.amazon.rankingTitle') }}</h1>
        <p class="subtitle">{{ $t('tools.amazon.rankingSubtitle') }}</p>
      </div>

      <div class="tools-ranking-container">
        <!-- 排行榜工具列表 -->
        <div v-if="loading" class="loading-container">
          <el-skeleton :rows="10" animated />
        </div>

        <!-- 错误提示 -->
        <div v-else-if="error" class="error-container">
          <i class="el-icon-warning-outline"></i>
          <h3>{{ error }}</h3>
          <el-button type="primary" @click="fetchAmazonTools">重新加载</el-button>
        </div>

        <template v-else>
          <!-- 工具卡片列表 -->
          <div class="tools-list">
            <div v-for="(tool, index) in amazonTools" :key="tool.id" 
                 :class="{'tool-card': true, 'editors-choice-card': index === 0}"
                 :style="`--animation-delay: ${index * 0.15}s`">
              
              <!-- 背景图形装饰 -->
              <div class="card-background">
                <div class="bg-shape shape-1"></div>
                <div class="bg-shape shape-2"></div>
              </div>
              
              <!-- 奖章和排名 -->
              <div class="rank-badges">
                <!-- 编辑推荐标记 (仅第一个工具) -->
                <div v-if="index === 0" class="editors-choice">
                  <div class="badge-shine"></div>
                  <i class="el-icon-trophy"></i>
                  {{ $t('tools.amazon.editorsChoice') }}
                </div>
                
                <!-- 排名标记 -->
                <div class="rank-number" :class="`rank-${index + 1}`">
                  <span>{{ index + 1 }}</span>
                  <div class="rank-pulse"></div>
                </div>
              </div>
              
              <div class="tool-card-inner">
                <!-- 左侧: 工具logo和LOGO下方的小标签 -->
                <div class="tool-logo-section">
                  <div class="logo-container">
                    <img :src="tool.image" :alt="tool.name" class="tool-logo">
                    <div class="logo-glow"></div>
                  </div>
                  
                  <div v-if="index < 3" class="tool-badge" :class="`badge-${index+1}`">
                    <div class="badge-icon" :class="`icon-${index+1}`">
                      <i :class="getBadgeIcon(index)"></i>
                    </div>
                    <span>{{ $t('tools.amazon.badge' + (index + 1)) }}</span>
                  </div>
                </div>
                
                <!-- 中间: 工具描述和特点 -->
                <div class="tool-info-section">
                  <h3 class="tool-name">
                    {{ tool.name }}
                    <div class="rating-pill">
                      <i class="el-icon-star-on"></i>
                      <span>{{ tool.rating.toFixed(1) }}</span>
                    </div>
                  </h3>
                  
                  <p class="tool-description">{{ tool.shortDescription }}</p>
                  
                  <div class="tool-features">
                    <div v-for="(feature, featureIndex) in tool.highlightsList.slice(0, 5)" 
                         :key="featureIndex" 
                         class="feature-item"
                         :style="`--feature-delay: ${featureIndex * 0.1}s`">
                      <div class="feature-icon">
                        <i class="el-icon-check"></i>
                      </div>
                      <span>{{ feature }}</span>
                    </div>
                  </div>
                  
                  <a class="more-details-link" @click="toggleDetails(index)">
                    {{ $t(showDetails[index] ? 'tools.amazon.hideDetails' : 'tools.amazon.moreDetails') }}
                    <i :class="showDetails[index] ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
                  </a>
                </div>
                
                <!-- 右侧: 评分和按钮 -->
                <div class="tool-rating-section">
                  <div class="rating-metrics">
                    <!-- 评分圆环 -->
                    <div class="rating-circle">
                      <svg viewBox="0 0 36 36" class="rating-circle-svg">
                        <path class="rating-circle-bg"
                              d="M18 2.0845
                                a 15.9155 15.9155 0 0 1 0 31.831
                                a 15.9155 15.9155 0 0 1 0 -31.831" />
                        <path class="rating-circle-fill"
                              :stroke-dasharray="`${tool.rating * 10}, 100`"
                              d="M18 2.0845
                                a 15.9155 15.9155 0 0 1 0 31.831
                                a 15.9155 15.9155 0 0 1 0 -31.831" />
                        <text x="18" y="20.35" class="rating-circle-text">{{ tool.rating.toFixed(1) }}</text>
                      </svg>
                    </div>

                    <!-- 评分文本 -->
                    <div class="rating-text">
                      <div class="rating-label">{{ $t('tools.amazon.excellent') }}</div>
                    </div>
                    
                    <!-- 购买信息区域，移至评分下方 -->
                    <div class="purchase-info">
                      <div class="purchase-count">
                        <i class="el-icon-shopping-cart-2"></i>
                        <span>{{ tool.purchaseCount }} {{ $t('tools.amazon.purchases') }}</span>
                      </div>
                      
                      <!-- 价格标签 -->
                      <div class="price-tag">
                        <div class="price-icon"><i class="el-icon-price-tag"></i></div>
                        <div class="price-text">{{ tool.price }}</div>
                      </div>
                    </div>
                  </div>
                  
                  <div class="action-buttons">
                    <el-button type="danger" class="discount-btn" @click="visitWebsite(tool.website)">
                      {{ $t('tools.amazon.getDiscount') }}
                    </el-button>
                    <div class="free-trial-link">
                      {{ $t('tools.amazon.or') }} 
                      <a :href="tool.freeTrialLink" target="_blank">
                        {{ $t('tools.amazon.tryFree') }}
                        <span class="trial-days">{{ tool.freeTrialDays }}{{ $t('tools.daysTrial') }}</span>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- 详情展开面板 -->
              <div v-if="showDetails[index]" class="tool-details-panel">
                <div class="details-inner">
                  <div class="detail-tabs">
                    <div class="tab active">{{ $t('tools.amazon.keyFeatures') }}</div>
                    <!-- <div class="tab">{{ $t('tools.amazon.pricing') }}</div>
                    <div class="tab">{{ $t('tools.amazon.reviews') }}</div> -->
                  </div>
                  
                  <div class="detail-content">
                    <!-- 核心功能列表 -->
                    <div class="detail-section">
                      <ul class="detail-list">
                        <li v-for="(feature, fidx) in tool.detailedFeaturesList" :key="fidx">
                          <i class="el-icon-check"></i>
                          {{ feature }}
                        </li>
                      </ul>
                    </div>
                    
                    <!-- 价格信息 -->
                    <div class="pricing-panel">
                      <p class="pricing-info">{{ tool.detailedPricing }}</p>
                    </div>
                  </div>
                  
                  <div class="detail-actions">
                    <el-button type="primary" @click="readReview(tool.reviewLink)">
                      <i class="el-icon-reading"></i>
                      {{ $t('tools.readReview') }}
                    </el-button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>
      </div>

      
      <!-- 新增内容：Amazon卖家工具指南 -->
      <div class="amazon-tools-guide">
        <div class="guide-content">
          <div class="guide-intro">
            <h2 class="section-title">{{ $t('tools.amazon.guide.title') }}</h2>
            <div class="divider">
              <span class="divider-line"></span>
              <span class="divider-icon"><i class="el-icon-shopping-cart-full"></i></span>
              <span class="divider-line"></span>
            </div>
            <p class="guide-lead">
              {{ $t('tools.amazon.guide.intro') }}
            </p>
            <p class="guide-description">
              {{ $t('tools.amazon.guide.description') }}
            </p>
          </div>
          
          <div class="selection-methodology">
            <h3 class="methodology-title">{{ $t('tools.amazon.guide.selectionTitle') }}</h3>
            <p class="methodology-intro">
              {{ $t('tools.amazon.guide.selectionIntro') }}
            </p>
            
            <div class="criteria-grid">
              <div class="criteria-card" v-for="(type, key) in ['performance', 'features', 'usability', 'support', 'pricing', 'reviews']" :key="key">
                <div class="criteria-icon" :class="`icon-${key}`">
                  <i :class="getCriteriaIcon(key)"></i>
                </div>
                <h4>{{ $t(`tools.amazon.guide.criteriaCards.${type}.title`) }}</h4>
                <p>{{ $t(`tools.amazon.guide.criteriaCards.${type}.description`) }}</p>
              </div>
            </div>
            
            <div class="conclusion-note">
              <div class="note-icon"><i class="el-icon-info"></i></div>
              <div class="note-content">
                <p>{{ $t('tools.amazon.guide.conclusion') }}</p>
                <p class="source-link">
                  {{ $t('tools.amazon.guide.source') }}
                  <a :href="$t('tools.amazon.guide.sourceLink')" target="_blank">
                    {{ $t('tools.amazon.guide.sourceLink') }}
                  </a>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <app-footer />
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import api from '@/api/modules/sellerTools'

export default {
  name: 'AmazonSellerTools',
  components: {
    AppHeader,
    AppFooter
  },
  data() {
    return {
      loading: true,
      error: null,
      amazonTools: [],
      showDetails: {}
    }
  },
  mounted() {
    // 获取亚马逊卖家工具数据
    this.fetchAmazonTools();
  },
  watch: {
    // 监听路由参数变化，当slug变化时重新获取数据
    '$route.params.slug': {
      handler() {
        this.fetchAmazonTools();
      },
      immediate: false
    }
  },
  methods: {
    getCriteriaIcon(index) {
      // 为标准卡片提供图标
      const icons = [
        'el-icon-odometer',      // 性能
        'el-icon-s-grid',        // 功能
        'el-icon-user',          // 用户体验
        'el-icon-headset',       // 客户支持
        'el-icon-money',         // 定价
        'el-icon-chat-line-square' // 用户评价
      ];
      return icons[index] || 'el-icon-s-opportunity';
    },
    getBadgeIcon(index) {
      // 获取不同徽章的图标
      const icons = [
        'el-icon-trophy',    // 最佳选择
        'el-icon-medal',     // 最高价值
        'el-icon-star-on'    // 最易使用
      ];
      return icons[index] || 'el-icon-s-flag';
    },
    toggleDetails(index) {
      this.$set(this.showDetails, index, !this.showDetails[index]);
    },
    async fetchAmazonTools() {
      this.loading = true;
      this.error = null;
      this.amazonTools = []; // 重置工具列表
      this.showDetails = {}; // 重置详情展示状态
      
      try {
        //获取当前slug
        const slug = this.currentSlug;
        console.log(`slug:${slug}`);
        // 调用API获取亚马逊卖家工具数据
        const response = await api.getAmazonSellerTools(slug);
        
        if (response && Array.isArray(response.rows)) {
          console.log(response.rows);
          this.amazonTools = response.rows;
          
          // 初始化工具详情展示状态
          this.amazonTools.forEach((_, index) => {
            this.$set(this.showDetails, index, false);
          });
        } else {
          throw new Error('API返回数据格式不正确');
        }
      } catch (error) {
        console.error('获取亚马逊卖家工具数据失败:', error);
        this.error = error.message || '获取数据失败，请稍后重试';
        
        // 如果API请求失败，使用本地数据作为备选
        this.loadFallbackData();
      } finally {
        this.loading = false;
        // 滚动到页面顶部，提高用户体验
        window.scrollTo(0, 0);
      }
    },
    loadFallbackData() {
      // 本地备用数据
      this.amazonTools = [
        {
          id: 1,
          name: 'Jungle Scout',
          image: 'https://cdn.junglescout.com/wp-content/uploads/2018/05/26141648/js-logo-small.svg',
          rating: 9.9,
          price: '$49/月起',
          purchaseCount: 1528,
          freeTrialDays: 7,
          freeTrialLink: 'https://junglescout.com/free-trial',
          shortDescription: 'Jungle Scout 是开创性的产品研究平台，无数亚马逊卖家信任它来寻找获胜机会。其准确的数据、直观的功能和全面的资源简化了产品发现、列表优化和竞争分析，帮助您自信地发展业务。',
          highlights: [
            '全球超过50万卖家信任的工具',
            '行业领先的产品研究与验证工具',
            '精准、数据驱动的洞察力，助您自信选品',
            '全面的供应商数据库和库存管理',
            '强大的培训资源和活跃的卖家社区'
          ],
          detailedFeatures: [
            '产品数据库 - 搜索超过7000万亚马逊产品',
            '关键词工具 - 发现高价值、低竞争的关键词',
            '销售分析 - 跟踪您产品的性能和竞争对手',
            '供应商数据库 - 找到可靠的供应商',
            'PPC管理工具 - 优化您的广告活动',
            '库存管理 - 自动补货提醒和库存预测'
          ],
          detailedPricing: '基础套餐49美元/月，专业套餐69美元/月，套件套餐129美元/月。所有套餐均提供7天免费试用。',
          website: 'https://junglescout.com',
          reviewLink: '/article/jungle-scout-review'
        },
        {
          id: 2,
          name: 'Helium 10',
          image: 'https://www.helium10.com/app/themes/helium10/assets/img/logos/logo-blue-duotone.svg',
          rating: 9.8,
          price: '$39/月起',
          purchaseCount: 865,
          freeTrialDays: 14,
          freeTrialLink: 'https://helium10.com/free-trial',
          shortDescription: 'Helium 10是亚马逊卖家的一站式工具套件，从产品研究到列表优化和业务增长，提供了全面而强大的解决方案，让您的亚马逊业务更具竞争力。',
          highlights: [
            '全面的亚马逊卖家工具套装',
            '强大的关键词研究和优化工具',
            '高级产品列表优化功能',
            '竞争对手分析和追踪',
            '库存管理和补货提醒'
          ],
          detailedFeatures: [
            'Black Box - 产品研究和发现工具',
            'Cerebro - 反向ASIN关键词研究',
            'Magnet - 关键词研究工具',
            'Scribbles - 列表优化工具',
            'Alerts - 产品监控和保护',
            'Inventory Protector - 防止库存被恶意购买'
          ],
          detailedPricing: '入门套餐39美元/月，白金套餐99美元/月，钻石套餐199美元/月。提供14天免费试用和30天退款保证。',
          website: 'https://helium10.com',
          reviewLink: '/article/helium-10-review'
        },
        {
          id: 3,
          name: 'SmartScout',
          image: 'https://via.placeholder.com/120x50',
          rating: 9.8,
          price: '$59/月起',
          purchaseCount: 865,
          freeTrialDays: 7,
          freeTrialLink: 'https://smartscout.com/free-trial',
          shortDescription: 'SmartScout带来独特视角，通过品牌聚焦、分类级别分析和强大筛选功能提供亚马逊销售数据。它揭示利润品牌机会、发现类别趋势、定位顶级供应商，帮助卖家更智能地选品和扩张。',
          highlights: [
            '品牌中心的洞察力，针对有针对性的产品机会',
            '全面的类别映射和数据驱动的筛选工具',
            '深入的供应商数据库，助力战略性批发采购',
            '自动化品牌跟踪和竞争环境分析',
            '可操作的指标，助力自信扩张和规模化'
          ],
          detailedFeatures: [
            '品牌搜索 - 找到最有利可图的亚马逊品牌',
            '类别分析 - 深入了解亚马逊销售类别',
            '高级筛选器 - 定位特定产品和品牌机会',
            '供应商数据库 - 发现批发和收购目标',
            '竞争分析 - 了解品牌在亚马逊的表现',
            '销售预测 - 基于市场趋势的增长预测'
          ],
          detailedPricing: '个人套餐59美元/月，专业套餐99美元/月，企业套餐199美元/月。提供7天免费试用。',
          website: 'https://smartscout.com',
          reviewLink: '/article/smartscout-review'
        },
        {
          id: 4,
          name: 'AMZScout',
          image: 'https://via.placeholder.com/120x50',
          rating: 8.9,
          price: '$29/月起',
          purchaseCount: 410,
          freeTrialDays: 7,
          freeTrialLink: 'https://amzscout.net/free-trial',
          shortDescription: 'AMZScout为亚马逊卖家提供经济实惠的研究和分析工具，帮助新手和有经验的卖家找到盈利产品和优化列表。',
          highlights: [
            '适合预算有限的亚马逊卖家',
            '简单易用的产品研究工具',
            '准确的利润计算和分析',
            '有效的关键词研究工具',
            '亚马逊FBA费用计算器'
          ],
          detailedFeatures: [
            'Pro Extension - 浏览亚马逊时分析产品',
            'Product Database - 搜索数百万产品',
            'Keyword Tracker - 监控关键词排名',
            'Profit Calculator - 计算潜在利润',
            'Stock Stats - 库存管理工具'
          ],
          detailedPricing: '月度计划29美元/月，年度计划相当于14.9美元/月，终身计划一次性付费299美元。提供7天免费试用。',
          website: 'https://amzscout.net',
          reviewLink: '/article/amzscout-review'
        },
        {
          id: 5,
          name: 'Sellics',
          image: 'https://via.placeholder.com/120x50',
          rating: 8.7,
          price: '$57/月起',
          purchaseCount: 310,
          freeTrialDays: 14,
          freeTrialLink: 'https://sellics.com/free-trial',
          shortDescription: 'Sellics是一个全面的亚马逊卖家平台，提供PPC广告优化、关键词研究和销售分析，帮助卖家实现业务增长和提高ROI。',
          highlights: [
            '全方位的亚马逊业务管理平台',
            '高级PPC广告管理和优化',
            '详细的销售和绩效分析',
            '智能库存管理系统',
            '评论监控和管理'
          ],
          detailedFeatures: [
            'PPC管理器 - 自动化和优化广告',
            '利润分析 - 跟踪所有费用和利润',
            '竞争分析 - 监控竞争对手变化',
            '关键词排名 - 跟踪产品在重要关键词上的表现',
            '评论管理 - 及时响应客户反馈'
          ],
          detailedPricing: '根据年销售额定价，从57美元/月起。为增长中的卖家提供扩展选项。提供14天免费试用。',
          website: 'https://sellics.com',
          reviewLink: '/article/sellics-review'
        }
      ];
      
      // 初始化工具详情展示状态
      this.amazonTools.forEach((_, index) => {
        this.$set(this.showDetails, index, false);
      });
    },
    visitWebsite(url) {
      window.open(url, '_blank');
    },
    readReview(url) {
      if (url) {
        window.open(url, '_blank');
      }
    }
  },
  computed: {
    // 获取当前路径的slug参数
    currentSlug() {
      console.log(`this.$route.params.slug:${this.$route.params.slug}`);
      return this.$route.params.slug || undefined;
    },
  }
}
</script>

<style scoped>
.amazon-seller-tools {
  margin-top: 60px;
  --primary-blue-rgb: 30, 136, 229; /* RGB值用于透明度设置 */
}

.main-content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.hero-section {
  text-align: center;
  padding: 60px 20px;
  margin-bottom: 50px;
  background: linear-gradient(135deg, var(--primary-blue), #4a6fa5);
  color: white;
  border-radius: 8px;
}

.hero-section h1 {
  font-size: 2.8rem;
  margin-bottom: 20px;
  font-weight: 700;
}

.hero-section .subtitle {
  font-size: 1.2rem;
  max-width: 700px;
  margin: 0 auto;
  opacity: 0.9;
}

.tools-ranking-container {
  margin-bottom: 60px;
}

.loading-container {
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: var(--shadow-small);
}

.error-container {
  text-align: center;
  padding: 40px 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
}

.error-container i {
  font-size: 48px;
  color: #ff4d4f;
  margin-bottom: 16px;
}

.error-container h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 20px;
}

/* 工具卡片样式 */
.tools-list {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.tool-card {
  position: relative;
  background: linear-gradient(145deg, #ffffff, #f8f9fa);
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
  animation: fade-slide-up 0.8s forwards;
  opacity: 0;
  transform: translateY(30px);
  animation-delay: var(--animation-delay, 0s);
  z-index: 1;
}

.tool-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.12);
}

.tool-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, rgba(255,255,255,0.03) 0%, rgba(255,255,255,0.2) 50%, rgba(255,255,255,0.03) 100%);
  transform: translateX(-100%);
  transition: transform 0.6s ease-out;
  z-index: 2;
  pointer-events: none;
}

.tool-card:hover::before {
  transform: translateX(100%);
}

.editors-choice-card {
  border: none;
  box-shadow: 0 15px 40px rgba(255, 152, 0, 0.25);
  background: linear-gradient(145deg, #ffffff, #fff8e1);
}

.card-background {
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: -1;
}

.bg-shape {
  position: absolute;
  border-radius: 50%;
  opacity: 0.03;
}

.shape-1 {
  background: radial-gradient(circle, var(--primary-blue), transparent 70%);
  width: 500px;
  height: 500px;
  top: -250px;
  left: -150px;
}

.shape-2 {
  background: radial-gradient(circle, var(--primary-green), transparent 70%);
  width: 400px;
  height: 400px;
  bottom: -200px;
  right: -100px;
}

.editors-choice-card .shape-1 {
  background: radial-gradient(circle, #ff9800, transparent 70%);
}

.editors-choice-card .shape-2 {
  background: radial-gradient(circle, #ffc107, transparent 70%);
}

/* 排名和徽章样式 */
.rank-badges {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 5;
}

.editors-choice {
  position: absolute;
  top: 0;
  left: 0;
  background: linear-gradient(90deg, #FF9800, #FF5722);
  color: white;
  padding: 8px 16px 8px 25px;
  font-size: 0.85rem;
  font-weight: bold;
  border-radius: 0 0 12px 0;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 4px 10px rgba(255, 152, 0, 0.3);
  overflow: hidden;
}

.badge-shine {
  position: absolute;
  top: 0;
  left: 0;
  width: 50px;
  height: 100%;
  background: rgba(255, 255, 255, 0.4);
  transform: skewX(-20deg) translateX(-150%);
  animation: shine 3s infinite;
}

.editors-choice i {
  font-size: 1rem;
  color: #FFD700;
}

.rank-number {
  position: absolute;
  top: 15px;
  right: 15px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #f3f5f9, #e6ebf5);
  color: #333;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  font-size: 1.2rem;
  z-index: 1;
  overflow: hidden;
}

.rank-1 {
  background: linear-gradient(135deg, #FFD700, #FFC107);
  color: white;
}

.rank-2 {
  background: linear-gradient(135deg, #E0E0E0, #BDBDBD);
  color: #333;
}

.rank-3 {
  background: linear-gradient(135deg, #BF8970, #A1887F);
  color: white;
}

.rank-pulse {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: inherit;
  opacity: 0.6;
  animation: pulse 2s infinite;
  z-index: -1;
}

.tool-card-inner {
  display: flex;
  padding: 25px;
  gap: 25px;
  position: relative;
  z-index: 3;
}

/* Logo 区域优化 */
.tool-logo-section {
  flex: 0 0 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.logo-container {
  position: relative;
  width: 120px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 15px;
}

.tool-logo {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  position: relative;
  z-index: 2;
  filter: drop-shadow(0 4px 6px rgba(0, 0, 0, 0.1));
  transition: transform 0.3s ease;
}

.tool-card:hover .tool-logo {
  transform: scale(1.05);
}

.logo-glow {
  position: absolute;
  width: 100%;
  height: 50%;
  background: radial-gradient(ellipse at center, rgba(var(--primary-blue-rgb), 0.15), transparent 70%);
  bottom: -10px;
  border-radius: 50%;
  filter: blur(5px);
  opacity: 0.5;
  z-index: 1;
}

.editors-choice-card .logo-glow {
  background: radial-gradient(ellipse at center, rgba(255, 152, 0, 0.2), transparent 70%);
}

.tool-badge {
  display: flex;
  align-items: center;
  gap: 5px;
  background-color: #E6F7FF;
  color: var(--primary-blue);
  font-size: 0.7rem;
  font-weight: bold;
  padding: 5px 12px;
  border-radius: 50px;
  text-transform: uppercase;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border: 1px solid rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.badge-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: var(--primary-blue);
  color: white;
  font-size: 10px;
}

.icon-1 {
  background: #FF9800;
}

.icon-2 {
  background: #4CAF50;
}

.icon-3 {
  background: #2196F3;
}

.badge-1 {
  background-color: #FFF3E0;
  color: #FF9800;
  border-color: rgba(255, 152, 0, 0.2);
}

.badge-2 {
  background-color: #E8F5E9;
  color: #4CAF50;
  border-color: rgba(76, 175, 80, 0.2);
}

.badge-3 {
  background-color: #E3F2FD;
  color: #2196F3;
  border-color: rgba(33, 150, 243, 0.2);
}

/* 信息区域优化 */
.tool-info-section {
  flex: 1;
}

.tool-name {
  font-size: 1.5rem;
  color: #1a202c;
  margin: 0 0 15px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.rating-pill {
  display: inline-flex;
  align-items: center;
  background: #f1f8e9;
  color: #689f38;
  padding: 3px 8px;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: bold;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.rating-pill i {
  color: #ffc107;
  margin-right: 4px;
  font-size: 1rem;
}

.tool-description {
  color: #4a5568;
  line-height: 1.6;
  margin-bottom: 20px;
}

.tool-features {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 15px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  animation: slide-in-right 0.5s forwards;
  opacity: 0;
  animation-delay: calc(0.1s + var(--feature-delay, 0s));
}

.feature-icon {
  width: 22px;
  height: 22px;
  background: #e8f5e9;
  color: #4caf50;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  margin-top: 2px;
  flex-shrink: 0;
}

.more-details-link {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  color: var(--primary-blue);
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 500;
  margin-top: 5px;
  transition: all 0.2s ease;
  position: relative;
  padding-bottom: 2px;
}

.more-details-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 1px;
  background: var(--primary-blue);
  transition: width 0.3s ease;
}

.more-details-link:hover::after {
  width: 100%;
}

/* 评分区域优化 */
.tool-rating-section {
  flex: 0 0 220px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-left: 1px solid rgba(0, 0, 0, 0.05);
  padding-left: 25px;
}

.rating-metrics {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 12px;
  margin-bottom: 15px;
}

.rating-circle {
  position: relative;
  width: 90px;
  height: 90px;
  margin: 0 auto;
}

.rating-circle-svg {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
}

.rating-circle-bg {
  fill: none;
  stroke: #f0f0f0;
  stroke-width: 2.8;
}

.rating-circle-fill {
  fill: none;
  stroke: #4caf50;
  stroke-width: 2.8;
  stroke-linecap: round;
  animation: progress 1.5s ease-out forwards;
}

.rank-1 .rating-circle-fill {
  stroke: #ff9800;
}

.rating-circle-text {
  font-size: 10px;
  fill: #4a5568;
  font-weight: bold;
  text-anchor: middle;
  dominant-baseline: middle;
  transform: rotate(90deg);
}

.rating-text {
  font-weight: bold;
  color: #4caf50;
  font-size: 0.9rem;
  margin-top: 5px;
}

.purchase-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-top: 5px;
}

.purchase-count {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 0.9rem;
  color: #718096;
  white-space: nowrap;
  background: #f8f9fa;
  padding: 3px 10px;
  border-radius: 50px;
  width: fit-content;
}

.purchase-count i {
  color: #9e9e9e;
}

.price-tag {
  display: flex;
  align-items: center;
  gap: 8px;
  background: #f5f7fa;
  padding: 6px 12px;
  border-radius: 6px;
  margin-top: 5px;
}

.price-icon {
  color: #607d8b;
}

.price-text {
  font-weight: 600;
  color: #37474f;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.discount-btn {
  width: 100%;
  height: 45px;
  font-size: 0.95rem;
  font-weight: 600;
  border-radius: 25px;
  background: linear-gradient(135deg, #f44336, #ff7043);
  border: none;
  box-shadow: 0 4px 12px rgba(244, 67, 54, 0.3);
  transition: all 0.3s ease;
  overflow: hidden;
  position: relative;
}

.discount-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, rgba(255,255,255,0) 0%, rgba(255,255,255,0.2) 50%, rgba(255,255,255,0) 100%);
  transition: all 0.5s ease;
}

.discount-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(244, 67, 54, 0.4);
  background: linear-gradient(135deg, #e53935, #ff5722);
}

.discount-btn:hover::before {
  left: 100%;
}

.free-trial-link {
  text-align: center;
  font-size: 0.9rem;
  color: #718096;
}

.free-trial-link a {
  color: var(--primary-blue);
  text-decoration: none;
  font-weight: 600;
  position: relative;
  padding-bottom: 1px;
}

.free-trial-link a::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 1px;
  background: var(--primary-blue);
  transition: width 0.3s ease;
}

.free-trial-link a:hover::after {
  width: 100%;
}

.trial-days {
  display: inline-block;
  background: #ebf5ff;
  color: #1e88e5;
  font-size: 0.75rem;
  padding: 1px 4px;
  border-radius: 3px;
  margin-left: 3px;
  vertical-align: middle;
}

/* 详情面板优化 */
.tool-details-panel {
  background: #f8fafc;
  border-top: 1px solid #eaedf0;
  padding: 0;
  overflow: hidden;
  animation: expand 0.4s ease-out forwards;
}

.details-inner {
  max-width: 1000px;
  margin: 0 auto;
}

.detail-tabs {
  display: flex;
  border-bottom: 1px solid #e0e4e8;
  padding: 0 25px;
  background: linear-gradient(90deg, #f8fafc, #f1f5f9);
}

.tab {
  padding: 15px 25px;
  font-weight: 600;
  color: #64748b;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
}

.tab.active {
  color: var(--primary-blue);
}

.tab.active::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 2px;
  background: var(--primary-blue);
}

.detail-content {
  padding: 25px;
}

.detail-list {
  padding-left: 0;
  margin: 0;
  list-style: none;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 15px;
}

.detail-list li {
  color: #4a5568;
  display: flex;
  align-items: flex-start;
  gap: 10px;
  padding: 8px 12px;
  background: white;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.detail-list li:hover {
  transform: translateY(-2px);
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
}

.detail-list li i {
  color: #4caf50;
  margin-top: 2px;
}

.pricing-panel {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  margin-top: 20px;
}

.pricing-info {
  line-height: 1.6;
  color: #4a5568;
  margin: 0;
}

.detail-actions {
  padding: 20px 25px;
  display: flex;
  justify-content: flex-end;
  background: rgba(0, 0, 0, 0.02);
}

/* 动画效果 */
@keyframes fade-slide-up {
  0% {
    opacity: 0;
    transform: translateY(30px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slide-in-right {
  0% {
    opacity: 0;
    transform: translateX(-20px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes expand {
  0% {
    max-height: 0;
  }
  100% {
    max-height: 800px;
  }
}

@keyframes progress {
  0% {
    stroke-dasharray: 0, 100;
  }
}

@keyframes shine {
  0% {
    transform: skewX(-20deg) translateX(-150%);
  }
  25%, 100% {
    transform: skewX(-20deg) translateX(250%);
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.6;
  }
  50% {
    transform: scale(1.5);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 0;
  }
}

/* 响应式设计 */
@media (max-width: 992px) {
  .tool-card-inner {
    flex-direction: column;
  }
  
  .tool-logo-section {
    flex-direction: row;
    width: 100%;
    margin-bottom: 20px;
    gap: 20px;
  }
  
  .logo-container {
    margin-bottom: 0;
  }
  
  .tool-rating-section {
    flex-direction: row;
    width: 100%;
    margin-top: 20px;
    border-left: none;
    border-top: 1px solid rgba(0, 0, 0, 0.05);
    padding-left: 0;
    padding-top: 20px;
  }
  
  .rating-metrics {
    flex-direction: row;
    align-items: center;
    gap: 20px;
    flex: 1;
  }
  
  .purchase-info {
    flex-direction: row;
    gap: 15px;
  }
  
  .action-buttons {
    flex: 1;
  }
  
  .rating-circle {
    width: 80px;
    height: 80px;
  }
  
  .detail-list {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .tool-card-inner {
    padding: 20px 15px;
  }
  
  .hero-section h1 {
    font-size: 2rem;
  }
  
  .hero-section .subtitle {
    font-size: 1rem;
  }
  
  .tool-logo-section {
    flex-direction: column;
  }
  
  .tool-rating-section {
    flex-direction: column;
  }
  
  .rating-metrics {
    flex-direction: column;
    margin-bottom: 20px;
  }
  
  .purchase-info {
    flex-direction: column;
    margin-top: 10px;
  }
  
  .rank-number {
    top: 10px;
    right: 10px;
    width: 30px;
    height: 30px;
    font-size: 1rem;
  }
  
  .editors-choice {
    left: 0;
    right: auto;
    border-radius: 0 0 8px 0;
    font-size: 0.8rem;
    padding: 6px 12px;
  }
  
  .detail-tabs {
    padding: 0 15px;
  }
  
  .tab {
    padding: 12px 15px;
    font-size: 0.9rem;
  }
  
  .detail-content {
    padding: 15px;
  }
}

/* 新增部分：Amazon卖家工具指南样式 */
.amazon-tools-guide {
  padding: 60px 0;
  background-color: #f8fafc;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  margin-top: 60px;
}

.guide-content {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 20px;
}

.guide-intro {
  text-align: center;
  margin-bottom: 50px;
}

.section-title {
  font-size: 2.2rem;
  color: #1a202c;
  margin: 0 0 20px;
  font-weight: 700;
}

.divider {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px auto 30px;
  max-width: 400px;
}

.divider-line {
  height: 1px;
  background: linear-gradient(to right, transparent, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.1), transparent);
  flex: 1;
}

.divider-icon {
  margin: 0 15px;
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #4299e1, #667eea);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 1.2rem;
  box-shadow: 0 4px 10px rgba(66, 153, 225, 0.3);
}

.guide-lead {
  font-size: 1.25rem;
  line-height: 1.8;
  color: #4a5568;
  max-width: 800px;
  margin: 0 auto 20px;
  font-weight: 500;
}

.guide-description {
  font-size: 1.05rem;
  line-height: 1.7;
  color: #4a5568;
  max-width: 800px;
  margin: 0 auto;
}

/* 方法论部分 */
.selection-methodology {
  background-color: white;
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
  margin-top: 40px;
}

.methodology-title {
  font-size: 1.8rem;
  color: #1a202c;
  margin: 0 0 15px;
  font-weight: 600;
}

.methodology-intro {
  font-size: 1.05rem;
  line-height: 1.7;
  margin-bottom: 30px;
  color: #4a5568;
}

.criteria-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 25px;
  margin-bottom: 40px;
}

.criteria-card {
  background-color: #f7fafc;
  border-radius: 12px;
  padding: 30px;
  transition: all 0.3s ease;
  border: 1px solid rgba(0, 0, 0, 0.03);
  display: flex;
  flex-direction: column;
  height: 100%;
}

.criteria-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.05);
  background: linear-gradient(to bottom right, #ffffff, #f7fafc);
}

.criteria-card .criteria-icon {
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #4299e1, #667eea);
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: white;
  font-size: 1.5rem;
  box-shadow: 0 5px 15px rgba(66, 153, 225, 0.3);
}

.criteria-card h4 {
  font-size: 1.3rem;
  color: #1a202c;
  margin: 0 0 15px;
  font-weight: 600;
}

.criteria-card p {
  color: #4a5568;
  line-height: 1.6;
  font-size: 1rem;
  margin: 0;
  flex-grow: 1;
}

/* 图标颜色变化 */
.icon-0 {
  background: linear-gradient(135deg, #f6ad55, #ed8936);
  box-shadow: 0 5px 15px rgba(237, 137, 54, 0.3);
}

.icon-1 {
  background: linear-gradient(135deg, #68d391, #48bb78);
  box-shadow: 0 5px 15px rgba(72, 187, 120, 0.3);
}

.icon-2 {
  background: linear-gradient(135deg, #63b3ed, #4299e1);
  box-shadow: 0 5px 15px rgba(66, 153, 225, 0.3);
}

.icon-3 {
  background: linear-gradient(135deg, #fc8181, #f56565);
  box-shadow: 0 5px 15px rgba(245, 101, 101, 0.3);
}

.icon-4 {
  background: linear-gradient(135deg, #b794f4, #9f7aea);
  box-shadow: 0 5px 15px rgba(159, 122, 234, 0.3);
}

.icon-5 {
  background: linear-gradient(135deg, #fbd38d, #f6ad55);
  box-shadow: 0 5px 15px rgba(246, 173, 85, 0.3);
}

.conclusion-note {
  background-color: #ebf8ff;
  border-radius: 12px;
  padding: 30px;
  display: flex;
  gap: 25px;
  align-items: flex-start;
  border-left: 5px solid #4299e1;
}

.note-icon {
  font-size: 1.8rem;
  color: #4299e1;
  flex-shrink: 0;
  margin-top: 3px;
}

.note-content p {
  margin-bottom: 15px;
  line-height: 1.7;
  color: #4a5568;
  font-size: 1.05rem;
}

.note-content p:last-child {
  margin-bottom: 0;
}

.source-link {
  font-size: 0.95rem;
  color: #718096;
  font-style: italic;
}

.source-link a {
  color: #4299e1;
  text-decoration: none;
  transition: all 0.2s ease;
}

.source-link a:hover {
  color: #2b6cb0;
  text-decoration: underline;
}

/* 响应式调整 */
@media (max-width: 992px) {
  .criteria-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .section-title {
    font-size: 1.8rem;
  }
  
  .methodology-title {
    font-size: 1.6rem;
  }
  
  .selection-methodology {
    padding: 30px;
  }
}

@media (max-width: 768px) {
  .amazon-tools-guide {
    padding: 40px 0;
  }
  
  .section-title {
    font-size: 1.6rem;
  }
  
  .guide-lead {
    font-size: 1.1rem;
  }
  
  .selection-methodology {
    padding: 25px;
  }
  
  .criteria-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .criteria-card {
    padding: 25px;
  }
  
  .conclusion-note {
    flex-direction: column;
    padding: 25px;
    gap: 15px;
  }
  
  .note-icon {
    margin-bottom: 5px;
  }
}
</style> 
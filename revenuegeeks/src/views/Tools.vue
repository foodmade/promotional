<template>
  <div class="tools-page">
    <app-header />
    
    <div class="tools-content">
      <div class="hero-section">
        <h1>{{ $t('tools.title') }}</h1>
        <p>{{ $t('tools.subtitle') }}</p>
      </div>

      <div class="tools-container">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="8" v-for="tool in toolCategories" :key="tool.id">
            <router-link :to="tool.link" class="tool-card-link">
              <el-card shadow="hover" class="tool-card">
                <div class="tool-icon">
                  <i :class="tool.icon"></i>
                </div>
                <h3>{{ tool.title }}</h3>
                <p>{{ tool.description }}</p>
                <el-button type="primary">{{ $t('tools.viewTools') }}</el-button>
              </el-card>
            </router-link>
          </el-col>
        </el-row>
      </div>

      <div class="featured-tools">
        <h2>{{ $t('tools.featuredTitle') }}</h2>
        <el-row :gutter="20">
          <el-col :xs="24" :md="12" v-for="tool in featuredTools" :key="tool.id">
            <el-card shadow="hover" class="featured-tool-card">
              <div class="featured-tool-content">
                <div class="featured-tool-image">
                  <img :src="tool.image" :alt="tool.name">
                </div>
                <div class="featured-tool-info">
                  <h3>{{ tool.name }}</h3>
                  <div class="rating">
                    <el-rate
                      v-model="tool.rating"
                      disabled
                      show-score
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                  </div>
                  <p>{{ tool.description }}</p>
                  <div class="tool-buttons">
                    <el-button type="primary" @click="navigateTo(tool.reviewLink)">{{ $t('tools.readReview') }}</el-button>
                    <el-button type="success" @click="navigateToExternal(tool.website)">{{ $t('tools.visitWebsite') }}</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    
    <app-footer />
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'

export default {
  name: 'Tools',
  components: {
    AppHeader,
    AppFooter
  },
  data() {
    return {
      toolCategories: [
        {
          id: 1,
          title: this.$t('tools.categories.amazon'),
          description: this.$t('tools.descriptions.amazon'),
          icon: 'el-icon-shopping-cart-full',
          link: '/PlatformsTools/amazon-seller-tools'
        },
        {
          id: 2,
          title: this.$t('tools.categories.shopify'),
          description: this.$t('tools.descriptions.shopify'),
          icon: 'el-icon-shopping-bag',
          link: '/PlatformsTools/shopify-tools'
        },
        {
          id: 3,
          title: this.$t('tools.categories.marketing'),
          description: this.$t('tools.descriptions.marketing'),
          icon: 'el-icon-data-analysis',
          link: '/PlatformsTools/marketing-tools'
        },
        {
          id: 4,
          title: this.$t('tools.categories.analytics'),
          description: this.$t('tools.descriptions.analytics'),
          icon: 'el-icon-pie-chart',
          link: '/PlatformsTools/analytics-tools'
        },
        {
          id: 5,
          title: this.$t('tools.categories.email'),
          description: this.$t('tools.descriptions.email'),
          icon: 'el-icon-message',
          link: '/PlatformsTools/email-marketing-tools'
        },
        {
          id: 6,
          title: this.$t('tools.categories.social'),
          description: this.$t('tools.descriptions.social'),
          icon: 'el-icon-chat-dot-square',
          link: '/PlatformsTools/social-media-tools'
        }
      ],
      featuredTools: [
        {
          id: 1,
          name: 'Jungle Scout',
          image: 'https://via.placeholder.com/150x60',
          rating: 4.8,
          description: this.$t('tools.featuredDescriptions.jungleScout'),
          reviewLink: '/article/jungle-scout-review',
          website: 'https://www.junglescout.com'
        },
        {
          id: 2,
          name: 'Helium 10',
          image: 'https://via.placeholder.com/150x60',
          rating: 4.7,
          description: this.$t('tools.featuredDescriptions.helium10'),
          reviewLink: '/article/helium10-review',
          website: 'https://www.helium10.com'
        },
        {
          id: 3,
          name: 'Shopify',
          image: 'https://via.placeholder.com/150x60',
          rating: 4.9,
          description: this.$t('tools.featuredDescriptions.shopify'),
          reviewLink: '/article/shopify-review',
          website: 'https://www.shopify.com'
        },
        {
          id: 4,
          name: 'Klaviyo',
          image: 'https://via.placeholder.com/150x60',
          rating: 4.6,
          description: this.$t('tools.featuredDescriptions.klaviyo'),
          reviewLink: '/article/klaviyo-review',
          website: 'https://www.klaviyo.com'
        }
      ]
    }
  },
  methods: {
    navigateTo(link) {
      this.$router.push(link);
    },
    navigateToExternal(url) {
      window.open(url, '_blank');
    }
  }
}
</script>

<style scoped>
.tools-page {
  margin-top: 60px;
}

.tools-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px 50px;
}

.hero-section {
  text-align: center;
  padding: 50px 0;
}

.hero-section h1 {
  font-size: 2.4rem;
  color: var(--primary-blue);
  margin-bottom: 15px;
}

.hero-section p {
  font-size: 1.2rem;
  color: var(--gray-dark);
  max-width: 800px;
  margin: 0 auto;
}

.tools-container {
  margin-bottom: 60px;
}

.tool-card-link {
  text-decoration: none;
  color: inherit;
  display: block;
  margin-bottom: 30px;
}

.tool-card {
  height: 100%;
  text-align: center;
  padding: 20px;
  transition: var(--transition-default);
}

.tool-card:hover {
  transform: translateY(-5px);
}

.tool-icon {
  font-size: 40px;
  color: var(--primary-blue);
  margin-bottom: 20px;
}

.tool-card h3 {
  font-size: 1.4rem;
  margin-bottom: 10px;
  color: var(--gray-dark);
}

.tool-card p {
  margin-bottom: 20px;
  color: var(--gray-dark);
  min-height: 60px;
}

.featured-tools {
  margin-top: 50px;
}

.featured-tools h2 {
  text-align: center;
  font-size: 1.8rem;
  color: var(--primary-blue);
  margin-bottom: 30px;
}

.featured-tool-card {
  margin-bottom: 30px;
}

.featured-tool-content {
  display: flex;
  align-items: center;
}

.featured-tool-image {
  flex: 0 0 120px;
  padding: 15px;
}

.featured-tool-image img {
  max-width: 100%;
}

.featured-tool-info {
  flex: 1;
  padding: 15px;
}

.featured-tool-info h3 {
  margin-top: 0;
  margin-bottom: 10px;
  color: var(--primary-blue);
}

.rating {
  margin-bottom: 15px;
}

.tool-buttons {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}

@media (max-width: 768px) {
  .featured-tool-content {
    flex-direction: column;
  }
  
  .featured-tool-image {
    flex: 0 0 auto;
    margin-bottom: 15px;
  }
  
  .tool-buttons {
    flex-direction: column;
  }
}
</style>

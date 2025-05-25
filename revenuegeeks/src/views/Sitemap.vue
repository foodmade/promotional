<template>
  <div>
    <PageLayout :title="$t('pages.sitemap.title')" :subtitle="$t('pages.sitemap.subtitle')">
      <div class="sitemap-container">
        <div class="sitemap-section">
          <h2>{{ $t('pages.sitemap.mainSections') }}</h2>
          <ul class="sitemap-links">
            <li>
              <router-link to="/">{{ $t('nav.home') }}</router-link>
            </li>
            <li v-for="item in navigationConfig" :key="item.name">
              <router-link :to="item.path">{{ $t(item.name) }}</router-link>
            </li>
          </ul>
        </div>
        
        <div class="sitemap-section">
          <h2>{{ $t('pages.sitemap.tools') }}</h2>
          <ul class="sitemap-links">
            <li v-for="category in toolCategories" :key="category.path">
              <router-link :to="category.path">{{ $t(category.name) }}</router-link>
            </li>
          </ul>
        </div>
        
        <div class="sitemap-section">
          <h2>{{ $t('pages.sitemap.information') }}</h2>
          <ul class="sitemap-links">
            <li v-for="link in companyLinks" :key="link.path">
              <router-link :to="link.path">{{ $t(link.name) }}</router-link>
            </li>
          </ul>
        </div>
        
        <div class="sitemap-section">
          <h2>{{ $t('legal.title') }}</h2>
          <ul class="sitemap-links">
            <li v-for="link in legalLinks" :key="link.path">
              <router-link :to="link.path">{{ $t(link.name) }}</router-link>
            </li>
          </ul>
        </div>
      </div>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';
import navigationConfig from '@/config/navigation';
import { companyLinks, legalLinks } from '@/config/footerLinks';

export default {
  name: 'Sitemap',
  components: {
    PageLayout
  },
  data() {
    return {
      navigationConfig,
      companyLinks,
      legalLinks,
      toolCategories: [
        { name: 'tools.categories.amazon', path: '/PlatformsTools/amazon-seller-tools' },
        { name: 'tools.categories.shopify', path: '/PlatformsTools/shopify-tools' },
        { name: 'tools.categories.marketing', path: '/PlatformsTools/marketing-tools' },
        { name: 'tools.categories.analytics', path: '/PlatformsTools/analytics-tools' },
        { name: 'tools.categories.email', path: '/PlatformsTools/email-marketing-tools' },
        { name: 'tools.categories.social', path: '/PlatformsTools/social-media-tools' }
      ]
    }
  }
}
</script>

<style scoped>
.sitemap-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  margin-top: 20px;
}

.sitemap-section h2 {
  font-size: 20px;
  margin-bottom: 15px;
  color: var(--text-primary);
  padding-bottom: 8px;
  border-bottom: 1px solid #eee;
}

.sitemap-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sitemap-links li {
  margin-bottom: 10px;
}

.sitemap-links a {
  display: block;
  color: var(--text-primary);
  text-decoration: none;
  padding: 6px 0;
  transition: color 0.3s;
}

.sitemap-links a:hover {
  color: var(--primary-green);
}

@media (max-width: 768px) {
  .sitemap-container {
    grid-template-columns: 1fr;
  }
}
</style> 
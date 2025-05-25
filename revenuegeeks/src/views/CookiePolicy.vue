<template>
  <div>
    <PageLayout :title="$t('pages.cookiePolicy.title')" :subtitle="$t('pages.cookiePolicy.subtitle')">
      <div class="last-updated">{{ $t('pages.cookiePolicy.lastUpdated') }}</div>
      
      <p>{{ $t('pages.cookiePolicy.intro') }}</p>
      
      <h2>{{ $t('pages.cookiePolicy.whatAreCookiesTitle') }}</h2>
      <p>{{ $t('pages.cookiePolicy.whatAreCookiesContent') }}</p>
      
      <h2>{{ $t('pages.cookiePolicy.typesTitle') }}</h2>
      <ul>
        <li v-for="(type, index) in $t('pages.cookiePolicy.typesContent')" :key="index">
          {{ type }}
        </li>
      </ul>
      
      <h2>{{ $t('pages.cookiePolicy.controlTitle') }}</h2>
      <p>{{ $t('pages.cookiePolicy.controlContent') }}</p>
      
      <div class="browser-controls">
        <div class="browser-control">
          <h3>Chrome</h3>
          <p>设置 → 隐私和安全性 → Cookie和其他网站数据</p>
        </div>
        <div class="browser-control">
          <h3>Firefox</h3>
          <p>选项 → 隐私与安全 → Cookie和网站数据</p>
        </div>
        <div class="browser-control">
          <h3>Safari</h3>
          <p>偏好设置 → 隐私 → Cookie和网站数据</p>
        </div>
        <div class="browser-control">
          <h3>Edge</h3>
          <p>设置 → Cookie和网站权限 → Cookie</p>
        </div>
      </div>
      
      <h2>{{ $t('pages.cookiePolicy.thirdPartyTitle') }}</h2>
      <p>{{ $t('pages.cookiePolicy.thirdPartyContent') }}</p>
      
      <h2>{{ $t('pages.cookiePolicy.changesTitle') }}</h2>
      <p>{{ $t('pages.cookiePolicy.changesContent') }}</p>
      
      <h2>{{ $t('pages.cookiePolicy.contactTitle') }}</h2>
      <p>{{ $t('pages.cookiePolicy.contactContent') }}</p>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'CookiePolicy',
  components: {
    PageLayout
  }
}
</script>

<style scoped>
.last-updated {
  font-style: italic;
  color: var(--text-secondary);
  margin-bottom: 20px;
}

.browser-controls {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  margin: 20px 0;
}

.browser-control {
  background-color: #f5f5f5;
  padding: 15px;
  border-radius: 6px;
}

.browser-control h3 {
  margin-top: 0;
  margin-bottom: 10px;
  font-size: 16px;
  color: var(--text-primary);
}

.browser-control p {
  margin: 0;
  font-size: 14px;
  color: var(--text-secondary);
}
</style> 
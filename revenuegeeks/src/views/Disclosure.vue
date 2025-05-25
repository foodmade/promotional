<template>
  <div>
    <PageLayout :title="$t('pages.disclosure.title')" :subtitle="$t('pages.disclosure.subtitle')">
      <div class="last-updated">{{ $t('pages.disclosure.lastUpdated') }}</div>
      
      <p>{{ $t('pages.disclosure.intro') }}</p>
      
      <h2>{{ $t('pages.disclosure.affiliateTitle') }}</h2>
      <p>{{ $t('pages.disclosure.affiliateContent') }}</p>
      
      <div class="disclosure-box">
        <div class="disclosure-icon">
          <i class="el-icon-info"></i>
        </div>
        <div class="disclosure-content">
          <strong>{{ $t('pages.disclosure.title') }}:</strong> 本网站包含联盟链接，这意味着如果您通过我们的链接进行购买，我们可能会收到佣金，而不会向您收取额外费用。
        </div>
      </div>
      
      <h2>{{ $t('pages.disclosure.editorialTitle') }}</h2>
      <p>{{ $t('pages.disclosure.editorialContent') }}</p>
      
      <h2>{{ $t('pages.disclosure.advertisingTitle') }}</h2>
      <p>{{ $t('pages.disclosure.advertisingContent') }}</p>
      
      <h2>{{ $t('pages.disclosure.sponsoredTitle') }}</h2>
      <p>{{ $t('pages.disclosure.sponsoredContent') }}</p>
      
      <h2>{{ $t('pages.disclosure.compensationTitle') }}</h2>
      <p>{{ $t('pages.disclosure.compensationContent') }}</p>
      
      <h2>{{ $t('pages.disclosure.contactTitle') }}</h2>
      <p>{{ $t('pages.disclosure.contactContent') }}</p>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'Disclosure',
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

.disclosure-box {
  display: flex;
  background-color: #f8f8f8;
  border-left: 4px solid var(--primary-green);
  padding: 15px;
  margin: 20px 0;
  border-radius: 0 4px 4px 0;
}

.disclosure-icon {
  font-size: 20px;
  color: var(--primary-green);
  margin-right: 15px;
  display: flex;
  align-items: center;
}

.disclosure-content {
  flex: 1;
  font-size: 14px;
  line-height: 1.5;
}
</style> 
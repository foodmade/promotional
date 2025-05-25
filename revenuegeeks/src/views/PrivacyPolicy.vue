<template>
  <div>
    <PageLayout :title="$t('pages.privacyPolicy.title')" :subtitle="$t('pages.privacyPolicy.subtitle')">
      <div class="last-updated">{{ $t('pages.privacyPolicy.lastUpdated') }}</div>
      
      <p>{{ $t('pages.privacyPolicy.intro') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.informationTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.informationContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.useTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.useContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.sharingTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.sharingContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.cookiesTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.cookiesContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.securityTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.securityContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.rightsTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.rightsContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.changesTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.changesContent') }}</p>
      
      <h2>{{ $t('pages.privacyPolicy.contactTitle') }}</h2>
      <p>{{ $t('pages.privacyPolicy.contactContent') }}</p>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'PrivacyPolicy',
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
</style> 
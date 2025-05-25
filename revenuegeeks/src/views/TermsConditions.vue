<template>
  <div>
    <PageLayout :title="$t('pages.termsConditions.title')" :subtitle="$t('pages.termsConditions.subtitle')">
      <div class="last-updated">{{ $t('pages.termsConditions.lastUpdated') }}</div>
      
      <p>{{ $t('pages.termsConditions.intro') }}</p>
      
      <h2>{{ $t('pages.termsConditions.useTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.useContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.intellectualPropertyTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.intellectualPropertyContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.userContentTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.userContentContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.linksTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.linksContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.disclaimerTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.disclaimerContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.limitationTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.limitationContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.indemnificationTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.indemnificationContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.changesTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.changesContent') }}</p>
      
      <h2>{{ $t('pages.termsConditions.contactTitle') }}</h2>
      <p>{{ $t('pages.termsConditions.contactContent') }}</p>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'TermsConditions',
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
<template>
  <div class="home">
    <app-header />
    <hero-section />
    <brand-section />
    <article-section />
    <team-section />
    <contact-button />
    <app-footer />
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import HeroSection from '@/components/HeroSection.vue'
import BrandSection from '@/components/BrandSection.vue'
import ArticleSection from '@/components/ArticleSection.vue'
import TeamSection from '@/components/TeamSection.vue'
import ContactButton from '@/components/ContactButton.vue'

export default {
  name: 'Home',
  components: {
    AppHeader,
    AppFooter,
    HeroSection,
    BrandSection,
    ArticleSection,
    TeamSection,
    ContactButton
  }
}
</script> 
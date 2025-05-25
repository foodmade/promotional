<template>
  <div class="page-container">
    <div class="page-header">
      <h1 class="page-title">{{ title }}</h1>
      <div class="page-subtitle" v-if="subtitle">{{ subtitle }}</div>
    </div>
    <div class="page-content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageLayout',
  props: {
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>
.page-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px 80px;
}

.page-header {
  margin-bottom: 40px;
  text-align: center;
}

.page-title {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 16px;
  color: var(--text-primary);
}

.page-subtitle {
  font-size: 18px;
  color: var(--text-secondary);
  max-width: 800px;
  margin: 0 auto;
  line-height: 1.6;
}

.page-content {
  font-size: 16px;
  line-height: 1.8;
  color: var(--text-primary);
}

.page-content p {
  margin-bottom: 1.5em;
}

.page-content h2 {
  font-size: 24px;
  font-weight: 600;
  margin: 40px 0 20px;
  color: var(--text-primary);
}

.page-content h3 {
  font-size: 20px;
  font-weight: 600;
  margin: 30px 0 15px;
  color: var(--text-primary);
}

.page-content ul, .page-content ol {
  margin-bottom: 1.5em;
  padding-left: 24px;
}

.page-content li {
  margin-bottom: 0.5em;
}

@media (max-width: 768px) {
  .page-container {
    padding: 30px 15px 60px;
  }
  
  .page-title {
    font-size: 28px;
  }
  
  .page-subtitle {
    font-size: 16px;
  }
}
</style> 
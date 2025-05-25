<template>
  <div>
    <PageLayout :title="$t('pages.careers.title')" :subtitle="$t('pages.careers.subtitle')">
      <p>{{ $t('pages.careers.intro') }}</p>
      
      <h2>{{ $t('pages.careers.whyJoinTitle') }}</h2>
      <p>{{ $t('pages.careers.whyJoinContent') }}</p>
      
      <h2>{{ $t('pages.careers.benefitsTitle') }}</h2>
      <div class="benefits-grid">
        <div class="benefit-card" v-for="(benefit, index) in $t('pages.careers.benefitsList')" :key="index">
          <div class="benefit-icon">
            <i :class="benefitIcons[index]"></i>
          </div>
          <div class="benefit-text">{{ benefit }}</div>
        </div>
      </div>
      
      <h2>{{ $t('pages.careers.openPositionsTitle') }}</h2>
      <div v-if="openPositions.length === 0" class="no-positions">
        <i class="el-icon-info-circle"></i>
        <p>{{ $t('pages.careers.noOpenPositions') }}</p>
      </div>
      <div v-else class="positions-list">
        <div class="position-card" v-for="(position, index) in openPositions" :key="index">
          <h3 class="position-title">{{ position.title }}</h3>
          <div class="position-details">
            <span>
              <i class="el-icon-location"></i>
              {{ position.location }}
            </span>
            <span>
              <i class="el-icon-time"></i>
              {{ position.type }}
            </span>
          </div>
          <p class="position-description">{{ position.description }}</p>
          <el-button type="primary" size="small">申请职位</el-button>
        </div>
      </div>
      
      <h2>{{ $t('pages.careers.applicationProcess') }}</h2>
      <div class="process-steps">
        <div class="process-step" v-for="(step, index) in $t('pages.careers.applicationSteps')" :key="index">
          <div class="step-number">{{ index + 1 }}</div>
          <div class="step-content">{{ step }}</div>
        </div>
      </div>
      
      <div class="careers-cta">
        <h3>对加入我们团队感兴趣？</h3>
        <p>即使目前没有适合您的职位，我们也欢迎您发送简历，当有合适机会时我们会联系您。</p>
        <el-button type="success">发送简历</el-button>
      </div>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'Careers',
  components: {
    PageLayout
  },
  data() {
    return {
      benefitIcons: [
        'el-icon-money',
        'el-icon-house',
        'el-icon-date',
        'el-icon-reading',
        'el-icon-shopping-cart',
        'el-icon-coffee-cup'
      ],
      openPositions: []
    }
  }
}
</script>

<style scoped>
.benefits-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
  margin: 30px 0;
}

.benefit-card {
  display: flex;
  background-color: #f8f8f8;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.benefit-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.benefit-icon {
  color: var(--primary-green);
  font-size: 24px;
  margin-right: 15px;
  display: flex;
  align-items: center;
}

.benefit-text {
  flex: 1;
}

.no-positions {
  display: flex;
  align-items: center;
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  margin: 20px 0;
}

.no-positions i {
  font-size: 24px;
  color: var(--text-secondary);
  margin-right: 15px;
}

.no-positions p {
  margin: 0;
  color: var(--text-secondary);
}

.positions-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin: 20px 0;
}

.position-card {
  background-color: #f8f8f8;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.position-title {
  margin-top: 0;
  margin-bottom: 10px;
  color: var(--text-primary);
}

.position-details {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
  font-size: 14px;
  color: var(--text-secondary);
}

.position-details span {
  display: flex;
  align-items: center;
}

.position-details i {
  margin-right: 5px;
}

.position-description {
  margin-bottom: 15px;
  font-size: 14px;
  color: var(--text-secondary);
}

.process-steps {
  margin: 30px 0;
}

.process-step {
  display: flex;
  margin-bottom: 15px;
}

.step-number {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  background-color: var(--primary-green);
  color: white;
  border-radius: 50%;
  font-weight: 700;
  margin-right: 15px;
  flex-shrink: 0;
}

.step-content {
  padding-top: 5px;
}

.careers-cta {
  background-color: #f8f8f8;
  padding: 30px;
  border-radius: 8px;
  text-align: center;
  margin-top: 40px;
}

.careers-cta h3 {
  margin-top: 0;
  margin-bottom: 10px;
}

.careers-cta p {
  margin-bottom: 20px;
  color: var(--text-secondary);
}

@media (max-width: 768px) {
  .benefits-grid {
    grid-template-columns: 1fr;
  }
  
  .positions-list {
    grid-template-columns: 1fr;
  }
}
</style> 
<template>
  <div>
    <PageLayout :title="$t('pages.aboutUs.title')" :subtitle="$t('pages.aboutUs.subtitle')">
      <p>{{ $t('pages.aboutUs.intro') }}</p>
      
      <h2>{{ $t('pages.aboutUs.storyTitle') }}</h2>
      <p>{{ $t('pages.aboutUs.storyContent') }}</p>
      
      <h2>{{ $t('pages.aboutUs.missionTitle') }}</h2>
      <p>{{ $t('pages.aboutUs.missionContent') }}</p>
      
      <h2>{{ $t('pages.aboutUs.valuesTitle') }}</h2>
      <ul>
        <li v-for="(value, index) in $t('pages.aboutUs.valuesContent')" :key="index">
          {{ value }}
        </li>
      </ul>
      
      <h2>{{ $t('pages.aboutUs.teamTitle') }}</h2>
      <p>{{ $t('pages.aboutUs.teamContent') }}</p>
      
      <div class="team-cards">
        <div class="team-card" v-for="(member, index) in teamMembers" :key="index">
          <div class="team-image" :style="{ backgroundImage: `url(${member.image})` }"></div>
          <h3>{{ member.name }}</h3>
          <p class="team-title">{{ member.title }}</p>
          <p class="team-bio">{{ member.bio }}</p>
        </div>
      </div>
    </PageLayout>
  </div>
</template>

<script>
import PageLayout from '@/components/PageLayout.vue';

export default {
  name: 'AboutUs',
  components: {
    PageLayout
  },
  data() {
    return {
      teamMembers: [
        {
          name: 'John Smith',
          title: this.$t('team.roles.cofounder'),
          bio: '电子商务专家，拥有10年亚马逊销售经验，帮助数百位卖家成功拓展业务。',
          image: '/img/team/john.jpg'
        },
        {
          name: 'Sarah Johnson',
          title: this.$t('team.roles.editor'),
          bio: '曾任多家科技媒体编辑，专注于电子商务工具评测和比较，帮助卖家做出明智决策。',
          image: '/img/team/sarah.jpg'
        },
        {
          name: 'Michael Wong',
          title: this.$t('team.roles.writer'),
          bio: '资深内容创作者，专注于亚马逊卖家策略和工具评测，曾帮助众多初创企业优化其亚马逊业务。',
          image: '/img/team/michael.jpg'
        }
      ]
    }
  }
}
</script>

<style scoped>
.team-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 30px;
  margin-top: 30px;
}

.team-card {
  background-color: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.team-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.team-image {
  height: 200px;
  background-size: cover;
  background-position: center;
}

.team-card h3 {
  margin: 15px 15px 5px;
  font-size: 20px;
  color: var(--text-primary);
}

.team-title {
  margin: 0 15px 10px;
  font-size: 14px;
  color: var(--primary-green);
  font-weight: 600;
}

.team-bio {
  margin: 0 15px 15px;
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}

@media (max-width: 768px) {
  .team-cards {
    grid-template-columns: 1fr;
  }
}
</style> 
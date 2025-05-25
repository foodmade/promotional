<template>
  <section class="team">
    <h2>{{ $t('team.title') }}</h2>
    <p>{{ $t('team.subtitle') }}</p>
    
    <el-row v-loading="loading" :gutter="30" class="team-grid" type="flex" align="stretch">
      <el-col :xs="24" :sm="12" :md="8" v-for="member in teamMembers" :key="member.id">
        <el-card class="team-member" :body-style="{ padding: '30px 20px' }">
          <div class="member-avatar">
            <img :src="member.avatar" :alt="member.name">
          </div>
          <h3>{{ member.name }}</h3>
          <div class="member-title">{{ member.title }}</div>
          <p class="member-bio">{{ member.bio }}</p>
          <div class="social-links">
            <a v-for="(link, index) in member.socialLinks" :key="index" :href="link.url" target="_blank">
              {{ link.name }}
            </a>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </section>
</template>

<script>

import api from '@/api/modules/team';

export default {
  name: 'TeamSection',
  data() {
    return {
      loading: false,
      teamMembers: []
    }
  },
  async mounted() {
    try {
      this.loading = true
      const response = await api.getAll();
      this.teamMembers = response.rows;
    } catch (error) {
      console.error('获取团队数据失败:', error)
    } finally {
      this.loading = false
    }
  }
}
</script>

<style scoped>
.team {
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 20px;
  text-align: center;
}

.team h2 {
  font-size: 28px;
  margin-bottom: 10px;
  color: var(--gray-dark);
}

.team > p {
  max-width: 600px;
  margin: 0 auto 40px;
  opacity: 0.8;
}

.team-member {
  background-color: white;
  border-radius: var(--border-radius-md);
  margin-bottom: 30px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  transition: var(--transition-default);
  border: none;
}

.team-member:hover {
  transform: translateY(-5px);
  box-shadow: var(--shadow-medium) !important;
}

.member-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  overflow: hidden;
  margin: 0 auto 20px;
  border: 3px solid var(--primary-blue);
}

.member-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.team-member h3 {
  margin: 0 0 5px;
  font-size: 20px;
}

.member-title {
  color: var(--primary-green);
  font-size: 14px;
  margin-bottom: 15px;
  text-transform: uppercase;
  font-weight: 500;
}

.member-bio {
  font-size: 15px;
  line-height: 1.5;
  margin-bottom: 15px;
  opacity: 0.9;
  min-height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  flex: 1 1 auto;
}

.social-links {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.social-links a {
  color: var(--gray-dark);
  transition: var(--transition-default);
}

.social-links a:hover {
  color: var(--primary-blue);
}

@media (max-width: 768px) {
  .team {
    padding: 40px 20px;
  }
  
  .team h2 {
    font-size: 24px;
  }
}

@media (max-width: 480px) {
  .team {
    padding: 30px 15px;
  }
  
  .member-avatar {
    width: 100px;
    height: 100px;
  }
}
</style> 
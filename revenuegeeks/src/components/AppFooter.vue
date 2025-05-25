<template>
  <footer>
    <div class="footer-container">
      <div>
        <span class="footer-logo">Seller66<span>Tools</span></span>
        <p class="footer-about">{{ $t('footer.about') }}</p>
        <div class="social-links">
          <a href="#"><i class="el-icon-share"></i></a>
          <a href="#"><i class="el-icon-chat-dot-round"></i></a>
          <a href="#"><i class="el-icon-connection"></i></a>
          <a href="#"><i class="el-icon-video-camera"></i></a>
        </div>
      </div>
      
      <div>
        <h4 class="footer-heading">{{ $t('footer.navigation') }}</h4>
        <ul class="footer-links">
          <template v-for="(item, index) in navigationConfig">
            <li :key="index">
              <router-link :to="item.path">
                {{ $t(item.name) }}
              </router-link>
            </li>
          </template>
        </ul>
      </div>
      
      <div>
        <h4 class="footer-heading">{{ $t('nav.resources') }}</h4>
        <ul class="footer-links">
          <li v-for="(link, index) in companyLinks" :key="`company-${index}`">
            <router-link :to="link.path">
              {{ $t(link.name) }}
            </router-link>
          </li>
        </ul>
      </div>
      
      <div>
        <h4 class="footer-heading">{{ $t('footer.legal') }}</h4>
        <ul class="footer-links">
          <li v-for="(link, index) in legalLinks" :key="`legal-${index}`">
            <router-link :to="link.path">
              {{ $t(link.name) }}
            </router-link>
          </li>
          <li v-for="(link, index) in sitemapLinks" :key="`sitemap-${index}`">
            <router-link :to="link.path">
              {{ $t(link.name) }}
            </router-link>
          </li>
        </ul>
      </div>
      
      <div>
        <h4 class="footer-heading">{{ $t('footer.subscribe') }}</h4>
        <p style="opacity: 0.8; margin-bottom: 15px;">{{ $t('footer.subscribeText') }}</p>
        <el-form @submit.native.prevent="handleSubscribe">
          <el-input 
            v-model="subscribeName" 
            :placeholder="$t('footer.namePlaceholder')" 
            class="newsletter-input">
          </el-input>
          <el-input 
            v-model="subscribeEmail" 
            :placeholder="$t('footer.emailPlaceholder')" 
            class="newsletter-input">
          </el-input>
          <el-button type="success" class="newsletter-button" @click="handleSubscribe">
            {{ $t('footer.subscribeButton') }}
          </el-button>
        </el-form>
      </div>
    </div>
    
    <div class="footer-bottom">
      <p>{{ $t('footer.copyright') }}</p>
    </div>
  </footer>
</template>

<script>
import navigationConfig from '@/config/navigation';
import { companyLinks, legalLinks, sitemapLinks } from '@/config/footerLinks';

export default {
  name: 'AppFooter',
  data() {
    return {
      subscribeName: '',
      subscribeEmail: '',
      navigationConfig,
      companyLinks,
      legalLinks,
      sitemapLinks
    }
  },
  methods: {
    handleSubscribe() {
      // 处理订阅逻辑
      console.log('订阅信息:', {
        name: this.subscribeName,
        email: this.subscribeEmail
      })
      
      this.$message({
        message: this.$t('footer.subscribeButton') + '!',
        type: 'success'
      })
      
      this.subscribeName = ''
      this.subscribeEmail = ''
    }
  }
}
</script>

<style scoped>
footer {
  background-color: var(--gray-dark);
  color: white;
  padding: 60px 20px 30px;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
}

.footer-logo {
  font-size: 22px;
  font-weight: 700;
  color: white;
  margin-bottom: 20px;
  display: block;
}

.footer-logo span {
  color: var(--primary-green);
}

.footer-about {
  max-width: 300px;
  margin-bottom: 20px;
  opacity: 0.8;
  line-height: 1.6;
}

.footer-heading {
  font-size: 18px;
  margin-bottom: 20px;
  font-weight: 600;
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  margin-bottom: 12px;
}

.footer-links a {
  color: white;
  opacity: 0.8;
  text-decoration: none;
  transition: var(--transition-default);
}

.footer-links a:hover {
  opacity: 1;
  color: var(--primary-green);
}

.social-links {
  display: flex;
  gap: 15px;
}

.social-links a {
  color: white;
  opacity: 0.8;
  font-size: 18px;
  transition: var(--transition-default);
}

.social-links a:hover {
  opacity: 1;
  color: var(--primary-green);
}

.newsletter-input {
  margin-bottom: 10px;
}

.newsletter-input ::v-deep .el-input__inner {
  background-color: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
}

.newsletter-input ::v-deep .el-input__inner::placeholder {
  color: rgba(255, 255, 255, 0.6);
}

.newsletter-button {
  width: 100%;
}

.footer-bottom {
  max-width: 1200px;
  margin: 50px auto 0;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  text-align: center;
  font-size: 14px;
  opacity: 0.7;
}

@media (max-width: 768px) {
  .footer-container {
    grid-template-columns: 1fr;
    text-align: center;
  }

  .footer-about {
    max-width: 100%;
  }

  .social-links {
    justify-content: center;
  }
}
</style> 
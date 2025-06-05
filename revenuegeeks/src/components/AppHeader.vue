<template>
  <header>
    <div class="nav-container">
      <router-link to="/" class="logo">
        <img v-if="logoUrl" :src="logoUrl" alt="logo" style="height:40px;" />
        <template v-else>
          Seller66<span>Tools</span>
        </template>
      </router-link>
      
      <nav class="nav-menu">
        <!-- 渲染主导航菜单 -->
        <template v-for="(item, index) in menuList">
          <!-- 如果没有子菜单 -->
          <router-link 
            v-if="!item.children" 
            :key="'menu-'+index" 
            :to="item.path" 
            :class="{ 'active-link': isMenuActive(item) }">
            <i v-if="item.icon" :class="item.icon"></i>
            {{ $t(item.name) }}
          </router-link>
          
          <!-- 如果有子菜单 -->
          <el-dropdown 
            v-else 
            :key="'dropdown-'+index"
            @command="handleMenuClick" 
            trigger="hover" 
            class="nav-dropdown">
            <span class="el-dropdown-link" :class="{ 'active-link': isMenuActive(item) }">
              <router-link :to="item.path" class="parent-menu-link">
                <i v-if="item.icon" :class="item.icon"></i>
                {{ $t(item.name) }}
              </router-link>
              <i class="el-icon-arrow-down"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <!-- 子菜单项 -->
              <el-dropdown-item 
                v-for="(child, childIndex) in item.children" 
                :key="childIndex" 
                :command="child.path"
                :class="{ 'is-active': isMenuActive(child) }">
                <i v-if="child.icon" :class="child.icon"></i>
                {{ $t(child.name) }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
        
        <el-dropdown @command="handleLanguageChange">
          <span class="el-dropdown-link">
            {{ currentLanguage === 'zh-CN' ? '中文' : 'English' }} <i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="zh-CN">中文</el-dropdown-item>
            <el-dropdown-item command="en-US">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        
        <!-- 搜索按钮 -->
        <div class="search-icon" @click="openSearch">
          <i class="el-icon-search"></i>
        </div>
      </nav>
      
      <div class="mobile-menu" @click="toggleMobileMenu">☰</div>
      
      <el-drawer
        :visible.sync="mobileMenuVisible"
        direction="rtl"
        size="70%">
        <div class="mobile-nav-menu">
          <!-- 移动端导航菜单 -->
          <template v-for="(item, index) in menuList">
            <template v-if="!item.children">
              <router-link 
                :key="index" 
                :to="item.path" 
                :class="{ 'active-link': isMenuActive(item) }"
                @click="closeMobileMenu">
                <i v-if="item.icon" :class="item.icon"></i>
                {{ $t(item.name) }}
              </router-link>
            </template>
            
            <template v-else>
              <!-- 父菜单项作为标题 -->
              <div :key="`${index}-title`" class="mobile-submenu-title" @click="toggleSubMenu(index)">
                <router-link :to="item.path" class="mobile-parent-link" @click.stop="closeMobileMenu">
                  <i v-if="item.icon" :class="item.icon"></i>
                  {{ $t(item.name) }}
                </router-link>
                <i :class="[expandedMenus.includes(index) ? 'el-icon-arrow-up' : 'el-icon-arrow-down']" @click.stop="toggleSubMenu(index)"></i>
              </div>
              
              <!-- 子菜单列表 -->
              <div 
                :key="`${index}-submenu`" 
                class="mobile-submenu" 
                :class="{ 'expanded': expandedMenus.includes(index) }">
                <router-link 
                  v-for="(child, childIndex) in item.children" 
                  :key="childIndex" 
                  :to="child.path" 
                  :class="{ 'active-link': isMenuActive(child) }"
                  @click="closeMobileMenu">
                  <i v-if="child.icon" :class="child.icon"></i>
                  {{ $t(child.name) }}
                </router-link>
              </div>
            </template>
          </template>
          
          <!-- 移动端搜索按钮 -->
          <div class="mobile-search-button" @click="openSearch">
            <i class="el-icon-search"></i>
            {{ $t('nav.search') }}
          </div>
          
          <div class="language-selector">
            <span class="language-label">Language / 语言</span>
            <el-radio-group v-model="currentLanguage" @change="handleLanguageChange">
              <el-radio label="zh-CN">中文</el-radio>
              <el-radio label="en-US">English</el-radio>
            </el-radio-group>
          </div>
        </div>
      </el-drawer>
      
      <!-- 全屏搜索弹框 -->
      <transition name="search-fade">
        <div class="search-overlay" v-if="searchDialogVisible">
          <div class="search-modal">
            <div class="search-header">
              <h2>{{ $t('nav.search') }}</h2>
              <div class="close-search" @click="searchDialogVisible = false">
                <i class="el-icon-close"></i>
              </div>
            </div>
            
            <div class="search-content">
              <div class="search-input-container">
                <i class="el-icon-search search-prefix-icon"></i>
                <input 
                  ref="searchInput"
                  v-model="searchQuery" 
                  :placeholder="$t('hero.searchPlaceholder')"
                  @keyup.enter="handleSearch"
                  class="search-input"
                  autofocus
                />
                <i 
                  v-if="searchQuery" 
                  class="el-icon-close search-clear-icon" 
                  @click="searchQuery = ''"
                ></i>
              </div>
            </div>
            
            <div class="search-footer">
              <el-button @click="searchDialogVisible = false">{{ $t('nav.cancel') }}</el-button>
              <el-button type="primary" @click="handleSearch">{{ $t('hero.searchButton') }}</el-button>
            </div>
          </div>
        </div>
      </transition>
    </div>
  </header>
</template>

<script>
import api from '@/api/modules/menu';
import siteInfoApi from '@/api/modules/siteInfo';

export default {
  name: 'AppHeader',
  data() {
    return {
      mobileMenuVisible: false,
      searchDialogVisible: false,
      searchQuery: '',
      currentLanguage: this.$i18n.locale,
      menuList: [],
      expandedMenus: [],
      logoUrl: ''
    }
  },
  created() {
    api.getMenu().then(response => {
      this.menuList = response.rows;
    });
    this.fetchLogo();
  },
  watch: {
    searchDialogVisible(visible) {
      if (visible) {
        // 当搜索弹框显示时，禁止页面滚动
        document.body.style.overflow = 'hidden';
        // 自动聚焦搜索输入框
        this.$nextTick(() => {
          if (this.$refs.searchInput) {
            this.$refs.searchInput.focus();
          }
        });
      } else {
        // 关闭弹框时恢复页面滚动
        document.body.style.overflow = '';
      }
    }
  },
  computed: {
    currentPath() {
      return this.$route.path;
    }
  },
  methods: {
    async fetchLogo() {
      try {
        const res = await siteInfoApi.getSiteLogo();
        this.logoUrl = res;
      } catch (e) {
        this.logoUrl = '';
      }
    },
    isMenuActive(item) {
      if (item.path === this.currentPath) {
        return true;
      }
      
      // 检查子路径匹配
      if (item.children) {
        return item.children.some(child => this.isMenuActive(child));
      }
      
      // 检查路径前缀匹配
      if (item.path !== '/' && this.currentPath.startsWith(item.path)) {
        return true;
      }
      
      return false;
    },
    toggleMobileMenu() {
      this.mobileMenuVisible = !this.mobileMenuVisible;
    },
    closeMobileMenu() {
      this.mobileMenuVisible = false;
    },
    toggleSubMenu(index) {
      const position = this.expandedMenus.indexOf(index);
      if (position > -1) {
        this.expandedMenus.splice(position, 1);
      } else {
        this.expandedMenus.push(index);
      }
    },
    openSearch() {
      this.searchDialogVisible = true;
      this.searchQuery = '';
    },
    handleSearch() {
      if (!this.searchQuery.trim()) return;
      
      // 跳转到文章列表页面并传递搜索参数
      this.$router.push({
        name: 'ArticleSearch',
        query: { keyword: this.searchQuery }
      });
      
      // 关闭搜索弹框
      this.searchDialogVisible = false;
    },
    handleLanguageChange(lang) {
      this.currentLanguage = lang;
      this.$i18n.locale = lang;
      localStorage.setItem('language', lang);
    },
    handleMenuClick(path) {
      if (path) {
        this.$router.push(path);
      }
    }
  }
}
</script>

<style scoped>
header {
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  background-color: rgba(255, 255, 255, 0.95);
  box-shadow: var(--shadow-small);
  z-index: 1000;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
}

.logo {
  font-size: 22px;
  font-weight: 700;
  color: var(--primary-blue);
  text-decoration: none;
}

.logo span {
  color: var(--primary-green);
}

.nav-menu {
  display: flex;
  align-items: center;
}

.nav-menu a, .nav-menu .el-dropdown {
  color: var(--gray-dark);
  text-decoration: none;
  margin-left: 24px;
  font-weight: 500;
  transition: var(--transition-default);
}

.nav-menu a:hover, 
.nav-menu .el-dropdown:hover,
.nav-menu a.active-link,
.nav-menu .el-dropdown-link.active-link {
  color: var(--primary-blue);
}

.parent-menu-link {
  margin-left: 0 !important;
  padding-right: 5px;
}

.mobile-menu {
  display: none;
  font-size: 24px;
  cursor: pointer;
  color: var(--gray-dark);
}

/* 搜索图标按钮 */
.search-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--primary-blue);
  color: white;
  cursor: pointer;
  margin-left: 24px;
  transition: var(--transition-default);
}

.search-icon:hover {
  background-color: #1976D2;
  transform: scale(1.05);
}

.search-icon i {
  font-size: 18px;
}

/* 全屏搜索弹框 */
.search-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.8);
  z-index: 2000;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(5px);
}

.search-modal {
  width: 90%;
  max-width: 700px;
  background-color: white;
  border-radius: var(--border-radius-md);
  box-shadow: var(--shadow-medium);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.search-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 30px;
  border-bottom: 1px solid var(--gray-medium);
}

.search-header h2 {
  margin: 0;
  font-size: 22px;
  color: var(--gray-dark);
}

.close-search {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s;
}

.close-search:hover {
  background-color: var(--gray-light);
}

.close-search i {
  font-size: 24px;
  color: var(--gray-dark);
}

.search-content {
  padding: 40px 30px;
  flex-grow: 1;
}

.search-input-container {
  position: relative;
}

.search-prefix-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
  color: var(--primary-blue);
}

.search-clear-icon {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  color: #999;
  cursor: pointer;
  transition: color 0.3s;
}

.search-clear-icon:hover {
  color: var(--gray-dark);
}

.search-input {
  width: 100%;
  box-sizing: border-box;
  padding: 18px 50px;
  border: 2px solid var(--gray-medium);
  border-radius: var(--border-radius-sm);
  font-size: 18px;
  transition: all 0.3s;
  outline: none;
}

.search-input:focus {
  border-color: var(--primary-blue);
  box-shadow: 0 0 0 3px rgba(30, 136, 229, 0.2);
}

.search-footer {
  padding: 20px 30px;
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  border-top: 1px solid var(--gray-medium);
}

/* 移动端搜索按钮 */
.mobile-search-button {
  padding: 15px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
  background-color: var(--primary-blue);
  color: white;
  border-radius: var(--border-radius-sm);
  cursor: pointer;
}

.mobile-search-button i {
  margin-right: 10px;
}

/* 过渡动画 */
.search-fade-enter-active,
.search-fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}

.search-fade-enter,
.search-fade-leave-to {
  opacity: 0;
}

.search-fade-enter .search-modal {
  transform: scale(0.9);
}

.search-fade-enter-to .search-modal {
  transform: scale(1);
}

/* 移动端样式 */
@media (max-width: 768px) {
  .nav-menu {
    display: none;
  }
  
  .mobile-menu {
    display: block;
  }
  
  .mobile-nav-menu {
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
  
  .mobile-nav-menu a {
    display: block;
    padding: 15px 0;
    border-bottom: 1px solid var(--gray-medium);
    color: var(--gray-dark);
    text-decoration: none;
    font-weight: 500;
  }
  
  .mobile-submenu-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 0;
    border-bottom: 1px solid var(--gray-medium);
    cursor: pointer;
  }
  
  .mobile-parent-link {
    color: var(--gray-dark);
    text-decoration: none;
    font-weight: 500;
    flex-grow: 1;
  }
  
  .mobile-submenu {
    display: none;
    padding-left: 15px;
  }
  
  .mobile-submenu.expanded {
    display: block;
  }
  
  .language-selector {
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid var(--gray-medium);
  }
  
  .language-label {
    display: block;
    margin-bottom: 10px;
    font-weight: 500;
  }
  
  /* 移动端搜索弹框调整 */
  .search-modal {
    width: 95%;
    border-radius: 10px;
  }
  
  .search-content {
    padding: 30px 20px;
  }
  
  .search-header {
    padding: 15px 20px;
  }
  
  .search-footer {
    padding: 15px 20px;
  }
}
</style> 
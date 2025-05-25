<template>
  <div class="app-container">
    <el-card class="about-card">
      <h2 class="about-title">博客后台管理系统</h2>
      <div class="version">当前版本: v1.0.0</div>
      
      <div class="about-content">
        <p>这是一个使用Express + Vue3 + Element Plus搭建的博客后台管理系统。</p>
        <p>本系统主要用于管理博客网站的文章、分类、标签等内容，提供了简洁美观的用户界面和丰富的功能特性。</p>
      </div>
      
      <h3>主要特性</h3>
      <el-row :gutter="20" class="feature-list">
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="feature-header">
                <el-icon class="feature-icon"><document /></el-icon>
                <span>文章管理</span>
              </div>
            </template>
            <div class="feature-content">
              支持文章的创建、编辑、删除和查询，可以设置文章分类、标签、摘要等信息。
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="feature-header">
                <el-icon class="feature-icon"><collection /></el-icon>
                <span>分类管理</span>
              </div>
            </template>
            <div class="feature-content">
              支持文章分类的创建、编辑和删除，帮助更好地组织文章内容。
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="feature-header">
                <el-icon class="feature-icon"><price-tag /></el-icon>
                <span>标签管理</span>
              </div>
            </template>
            <div class="feature-content">
              支持文章标签的创建、编辑和删除，可以为标签设置不同的样式。
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <h3>技术栈</h3>
      <div class="tech-stack">
        <div class="tech-item">
          <img src="https://upload.wikimedia.org/wikipedia/commons/9/95/Vue.js_Logo_2.svg" alt="Vue.js" class="tech-logo">
          <div>Vue 3</div>
        </div>
        <div class="tech-item">
          <img src="https://element-plus.org/images/element-plus-logo.svg" alt="Element Plus" class="tech-logo">
          <div>Element Plus</div>
        </div>
        <div class="tech-item">
          <img src="https://upload.wikimedia.org/wikipedia/commons/d/d9/Node.js_logo.svg" alt="Node.js" class="tech-logo">
          <div>Node.js</div>
        </div>
        <div class="tech-item">
          <img src="https://upload.wikimedia.org/wikipedia/commons/6/64/Expressjs.png" alt="Express" class="tech-logo express-logo">
          <div>Express</div>
        </div>
      </div>
      
      <h3>联系我们</h3>
      <div class="contact-info">
        <p><el-icon><message /></el-icon> 邮箱: admin@example.com</p>
        <p><el-icon><phone /></el-icon> 电话: 10086-10010</p>
        <p><el-icon><location /></el-icon> 地址: 北京市朝阳区XX街XX号</p>
      </div>
      
      <div class="copyright">
        Copyright © 2025 博客后台管理系统. All Rights Reserved.
      </div>
    </el-card>
  </div>
</template>

<script>
import { Document, Collection, PriceTag, Message, Phone, Location } from '@element-plus/icons-vue'

export default {
  name: 'About',
  components: {
    Document,
    Collection,
    PriceTag,
    Message,
    Phone,
    Location
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.about-card {
  text-align: center;
  padding: 20px;
}

.about-title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 10px;
}

.version {
  color: #909399;
  font-size: 14px;
  margin-bottom: 30px;
}

.about-content {
  text-align: left;
  margin-bottom: 30px;
  line-height: 1.6;
  color: #606266;
  
  p {
    margin-bottom: 12px;
  }
}

h3 {
  margin: 30px 0 20px;
  font-weight: 500;
  color: #303133;
  position: relative;
  padding-bottom: 10px;
  
  &:after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 50px;
    height: 2px;
    background-color: #409EFF;
  }
}

.feature-list {
  margin-bottom: 30px;
  
  .feature-header {
    display: flex;
    align-items: center;
    
    .feature-icon {
      margin-right: 8px;
      font-size: 20px;
    }
  }
  
  .feature-content {
    text-align: left;
    color: #606266;
    font-size: 14px;
    line-height: 1.6;
  }
}

.tech-stack {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 30px;
  
  .tech-item {
    margin: 10px 20px;
    
    .tech-logo {
      width: 60px;
      height: 60px;
      margin-bottom: 10px;
      
      &.express-logo {
        background-color: #fff;
        padding: 5px;
        border-radius: 4px;
      }
    }
  }
}

.contact-info {
  margin-bottom: 30px;
  
  p {
    margin: 10px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    
    .el-icon {
      margin-right: 8px;
      color: #409EFF;
    }
  }
}

.copyright {
  color: #909399;
  font-size: 14px;
  margin-top: 50px;
}
</style> 
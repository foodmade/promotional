<template>
  <div class="article-card">
    <router-link :to="article.link" class="article-card-link">
      <div class="article-card-image">
        <img :src="article.image" :alt="article.title" />
      </div>
      <div class="article-card-content">
        <h3 class="article-card-title">{{ article.title }}</h3>
        <p class="article-card-excerpt">{{ article.excerpt }}</p>
        <div class="article-card-meta">
          <span class="article-card-date">{{ article.date }}</span>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'ArticleCard',
  props: {
    article: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.article-card {
  background-color: #fff;
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
}

.article-card-link {
  display: block;
  color: inherit;
  text-decoration: none;
}

.article-card-image {
  height: 200px;
  overflow: hidden;
}

.article-card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.article-card:hover .article-card-image img {
  transform: scale(1.05);
}

.article-card-content {
  padding: 20px;
}

.article-card-title {
  font-size: 18px;
  margin-bottom: 10px;
  color: var(--e-global-color-primary);
  line-height: 1.3;
}

.article-card-excerpt {
  font-size: 14px;
  color: #666;
  margin-bottom: 15px;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-card-meta {
  font-size: 12px;
  color: #999;
  display: flex;
  justify-content: space-between;
}

@media (max-width: 767px) {
  .article-card-image {
    height: 180px;
  }
  
  .article-card-content {
    padding: 15px;
  }
  
  .article-card-title {
    font-size: 16px;
  }
}
</style> 
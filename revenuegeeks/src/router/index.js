import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ArticleList from '../views/ArticleList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/tools',
    name: 'Tools',
    component: () => import('../views/Tools.vue')
  },
  {
    path: '/coupons',
    name: 'Coupons',
    component: () => import('../views/Coupons.vue')
  },
  {
    path: '/guides',
    name: 'Guides',
    component: () => import('../views/Guides.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/article/:slug',
    name: 'Article',
    component: () => import('../views/Article.vue'),
    props: true
  },
  {
    path: '/platformsTools/:slug',
    name: 'platformsTools',
    component: () => import('../views/AmazonSellerTools.vue')
  },
  {
    path: '/articles/:slug',
    name: 'ArticleList',
    component: ArticleList
  },
  {
    path: '/search',
    name: 'ArticleSearch',
    component: ArticleList
  },
  {
    path: '/about-us',
    name: 'AboutUs',
    component: () => import('../views/AboutUs.vue')
  },
  {
    path: '/contact-us',
    name: 'ContactUs',
    component: () => import('../views/ContactUs.vue')
  },
  {
    path: '/careers',
    name: 'Careers',
    component: () => import('../views/Careers.vue')
  },
  {
    path: '/privacy-policy',
    name: 'PrivacyPolicy',
    component: () => import('../views/PrivacyPolicy.vue')
  },
  {
    path: '/cookie-policy',
    name: 'CookiePolicy',
    component: () => import('../views/CookiePolicy.vue')
  },
  {
    path: '/terms-conditions',
    name: 'TermsConditions',
    component: () => import('../views/TermsConditions.vue')
  },
  {
    path: '/disclosure',
    name: 'Disclosure',
    component: () => import('../views/Disclosure.vue')
  },
  {
    path: '/sitemap',
    name: 'Sitemap',
    component: () => import('../views/Sitemap.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

export default router 
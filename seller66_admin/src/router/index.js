import { createRouter, createWebHistory } from 'vue-router'
import { getToken } from '../utils/auth'
import Layout from '../layout/index.vue'

// 公共路由
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('../views/login/index.vue'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('../views/error/404.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('../views/dashboard/index.vue'),
        name: 'Dashboard',
        meta: { title: '仪表盘', icon: 'Odometer', affix: true }
      }
    ]
  },
  // Banner管理
  {
    path: '/banner',
    component: Layout,
    redirect: '/banner/list',
    name: 'Banner',
    meta: { title: 'Banner管理', icon: 'Picture' },
    children: [
      {
        path: 'list',
        component: () => import('../views/banner/index.vue'),
        name: 'BannerList',
        meta: { title: 'Banner列表', icon: 'List' }
      }
    ]
  },
  // 文章管理
  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: 'Article',
    meta: { title: '文章管理', icon: 'Document' },
    children: [
      {
        path: 'list',
        component: () => import('../views/article/list.vue'),
        name: 'ArticleList',
        meta: { title: '文章列表', icon: 'List' }
      },
      {
        path: 'create',
        component: () => import('../views/article/edit.vue'),
        name: 'CreateArticle',
        meta: { title: '创建文章', icon: 'EditPen' }
      },
      {
        path: 'edit/:id',
        component: () => import('../views/article/edit.vue'),
        name: 'EditArticle',
        meta: { title: '编辑文章', noCache: true, activeMenu: '/article/list' },
        hidden: true
      }
    ]
  },
  // 品牌管理
  {
    path: '/brand',
    component: Layout,
    redirect: '/brand/list',
    name: 'Brand',
    meta: { title: '品牌管理', icon: 'Shop' },
    children: [
      {
        path: 'list',
        component: () => import('../views/brand/list.vue'),
        name: 'BrandList',
        meta: { title: '品牌列表', icon: 'List' }
      },
      {
        path: 'create',
        component: () => import('../views/brand/edit.vue'),
        name: 'CreateBrand',
        meta: { title: '创建品牌', icon: 'CirclePlus' }
      },
      {
        path: 'edit/:id',
        component: () => import('../views/brand/edit.vue'),
        name: 'EditBrand',
        meta: { title: '编辑品牌', noCache: true, activeMenu: '/brand/list' },
        hidden: true
      }
    ]
  },
  // 工具管理
  {
    path: '/tool',
    component: Layout,
    redirect: '/tool/list',
    name: 'Tool',
    meta: { title: '工具管理', icon: 'Tools' },
    children: [
      {
        path: 'list',
        component: () => import('../views/tool/list.vue'),
        name: 'ToolList',
        meta: { title: '工具列表', icon: 'List' }
      },
      {
        path: 'create',
        component: () => import('../views/tool/edit.vue'),
        name: 'CreateTool',
        meta: { title: '创建工具', icon: 'CirclePlus' }
      },
      {
        path: 'edit/:id',
        component: () => import('../views/tool/edit.vue'),
        name: 'EditTool',
        meta: { title: '编辑工具', noCache: true, activeMenu: '/tool/list' },
        hidden: true
      }
    ]
  },
  // 团队管理
  {
    path: '/author',
    component: Layout,
    redirect: '/author/list',
    name: 'Author',
    meta: { title: '作者管理', icon: 'User' },
    children: [
      {
        path: 'list',
        component: () => import('../views/team/list.vue'),
        name: 'TeamList',
        meta: { title: '作者列表', icon: 'UserFilled' }
      },
      {
        path: 'create',
        component: () => import('../views/team/edit.vue'),
        name: 'CreateTeamMember',
        meta: { title: '添加作者', icon: 'CirclePlus' }
      },
      {
        path: 'edit/:id',
        component: () => import('../views/team/edit.vue'),
        name: 'EditTeamMember',
        meta: { title: '编辑作者', noCache: true, activeMenu: '/team/list' },
        hidden: true
      }
    ]
  },
  // 分类管理
  // {
  //   path: '/category',
  //   component: Layout,
  //   redirect: '/category/list',
  //   name: 'Category',
  //   meta: { title: '分类管理', icon: 'Collection' },
  //   children: [
  //     {
  //       path: 'list',
  //       component: () => import('../views/category/index.vue'),
  //       name: 'CategoryList',
  //       meta: { title: '分类列表', icon: 'Menu' }
  //     }
  //   ]
  // },
  // 标签管理
  // {
  //   path: '/tag',
  //   component: Layout,
  //   redirect: '/tag/list',
  //   name: 'Tag',
  //   meta: { title: '标签管理', icon: 'PriceTag' },
  //   children: [
  //     {
  //       path: 'list',
  //       component: () => import('../views/tag/index.vue'),
  //       name: 'TagList',
  //       meta: { title: '标签列表', icon: 'CollectionTag' }
  //     }
  //   ]
  // },
  // 订阅管理
  // {
  //   path: '/subscriber',
  //   component: Layout,
  //   redirect: '/subscriber/list',
  //   name: 'Subscriber',
  //   meta: { title: '订阅管理', icon: 'Message' },
  //   children: [
  //     {
  //       path: 'list',
  //       component: () => import('../views/subscriber/list.vue'),
  //       name: 'SubscriberList',
  //       meta: { title: '订阅列表', icon: 'List' }
  //     }
  //   ]
  // },
  // 系统设置
  {
    path: '/system',
    component: Layout,
    redirect: '/system/setting',
    name: 'System',
    meta: { title: '系统设置', icon: 'Setting' },
    children: [
      {
        path: 'setting',
        component: () => import('../views/system/setting.vue'),
        name: 'SystemSetting',
        meta: { title: '网站设置', icon: 'Tools' }
      },
      {
        path: 'about',
        component: () => import('../views/system/about.vue'),
        name: 'About',
        meta: { title: '关于我们', icon: 'InfoFilled' }
      }
    ]
  },
  // 工具排行榜
  {
    path: '/sellerTools',
    component: Layout,
    redirect: '/sellerTools/list',
    name: 'SellerTools',
    meta: { title: '工具排行榜', icon: 'Star' },
    children: [
      {
        path: 'list',
        component: () => import('../views/sellerTools/list.vue'),
        name: 'SellerToolsList',
        meta: { title: '工具排行榜', icon: 'Star' }
      },
      {
        path: 'edit',
        component: () => import('../views/sellerTools/edit.vue'),
        name: 'CreateSellerTool',
        meta: { title: '新增工具', icon: 'CirclePlus' },
        hidden: true
      },
      {
        path: 'edit/:id',
        component: () => import('../views/sellerTools/edit.vue'),
        name: 'EditSellerTool',
        meta: { title: '编辑工具', noCache: true, activeMenu: '/sellerTools/list' },
        hidden: true
      }
    ]
  },
  // 客服管理
  {
    path: '/support',
    component: Layout,
    redirect: '/support/list',
    name: 'Support',
    meta: { title: '客服管理', icon: 'Headset' },
    children: [
      {
        path: 'list',
        component: () => import('../views/support/list.vue'),
        name: 'SupportList',
        meta: { title: '客服管理', icon: 'Headset' }
      }
    ]
  },
  // 404页面必须放在最后
  { path: '/:pathMatch(.*)*', redirect: '/404', hidden: true }
]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 判断该路由是否需要登录权限
  if (to.path !== '/login') {
    if (getToken()) {
      next()
    } else {
      next('/login')
    }
  } else {
    next()
  }
})

export default router 
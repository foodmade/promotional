/**
 * 导航菜单配置
 * 支持多级菜单，每个菜单项可以包含以下属性：
 * - name: 使用i18n key或直接字符串
 * - path: 路由路径
 * - icon: 图标类名
 * - children: 子菜单项数组
 * - external: 是否外部链接
 * - mobileDivider: 是否在移动端菜单中添加分隔线
 */

export default [
  {
    name: 'nav.tools',
    path: '/articles/tools',
    children: [
      {
        name: 'tools.categories.amazon',
        path: '/PlatformsTools/amazon-seller-tools',
      },
      {
        name: 'tools.categories.ebay',
        path: '/PlatformsTools/eaby-seller-tools',
      }
    ]
  },
  {
    name: 'nav.reviews',
    path: '/articles/reviews'
  },
  {
    name: 'nav.comparisons',
    path: '/articles/comparisons'
  },
  {
    name: 'nav.discounts',
    path: '/articles/discounts',
  },
  {
    name: 'nav.guides',
    path: '/articles/guides',
  },
  {
    name: 'nav.research',
    path: '/articles/research',
  },
  {
    name: 'nav.resources',
    path: '/articles/resources',
  }
]; 
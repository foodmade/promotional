/**
 * 页脚链接配置
 * 配置页脚中显示的链接，分为三组：公司信息、法律信息和站点地图
 * 每个链接项包含以下属性：
 * - name: 使用i18n key进行国际化
 * - path: 链接路径
 * - external: 是否为外部链接
 */

export const companyLinks = [
  {
    name: 'footer.aboutUs',
    path: '/about-us'
  },
  {
    name: 'footer.contactUs',
    path: '/contact-us'
  },
  {
    name: 'footer.careers',
    path: '/careers'
  }
];

export const legalLinks = [
  {
    name: 'footer.privacyPolicy',
    path: '/privacy-policy'
  },
  {
    name: 'footer.cookiePolicy',
    path: '/cookie-policy'
  },
  {
    name: 'footer.termsConditions',
    path: '/terms-conditions'
  },
  {
    name: 'footer.disclosure',
    path: '/disclosure'
  }
];

export const sitemapLinks = [
  {
    name: 'footer.sitemap',
    path: '/sitemap'
  }
];

export default {
  companyLinks,
  legalLinks,
  sitemapLinks
}; 
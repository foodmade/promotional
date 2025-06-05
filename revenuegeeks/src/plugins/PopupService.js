/**
 * 弹窗服务 - 用于管理特定页面的优惠弹窗，根据配置和条件自动显示
 */
import api from '@/api/modules/offers';

class PopupService {
  constructor() {
    // 弹窗基础配置
    this.config = {
      // 展示延迟时间 (毫秒)
      delay: 4000,
      
      // 针对特定页面路径的弹窗配置映射
      pageMapping: {
        '/PlatformsTools/amazon-seller-tools': 'amazon-seller-offer',
        '/PlatformsTools/eaby-seller-tools': 'ebay-seller-offer'
      }
    };
    
    // 存储键 - 仅用于记录关闭时间，不再用于判断是否显示
    this.storageKey = 'popup_last_shown';
  }
  
  /**
   * 检查当前页面是否应显示弹窗
   * @param {String} path - 当前路由路径
   * @param {Object} i18n - i18n实例，用于获取多语言文本
   * @returns {Promise<Object|null>} - 返回带有弹窗配置的Promise或null
   */
  async checkShouldShowPopup(path, i18n) {
    // 检查当前页面是否配置了弹窗
    const offerId = this.config.pageMapping[path];
    if (!offerId) return null;
    
    try {
      // 从API获取优惠数据
      const response = await api.getPageOffer(offerId);
      console.log("API返回数据:", JSON.stringify(response));
      
      if (!response || Object.keys(response).length === 0) return null;
      
      // 增加查看计数
      // this.updateViewCount(offerId);
      
      // 确保响应数据包含所有必要的字段
      const brandId = offerId.split('-')[0];
      const offerData = {
        logo: response.logo || '',
        title: response.title || i18n.t(`popup.offers.${brandId}.title`),
        buttonText: response.buttonText || i18n.t(`popup.offers.${brandId}.buttonText`),
        offerLink: response.offerLink || '',
        userCount: response.userCount || 0,
        discountAmount: response.discountAmount !== undefined ? response.discountAmount : i18n.t(`popup.offers.${brandId}.discountAmount`),
        originalPrice: response.originalPrice !== undefined ? response.originalPrice : i18n.t(`popup.offers.${brandId}.originalPrice`),
        specialPrice: response.specialPrice !== undefined ? response.specialPrice : i18n.t(`popup.offers.${brandId}.specialPrice`),
      };
      
      console.log("处理后的弹窗数据:", { popupId: offerId, offerData });
      
      return {
        popupId: offerId,
        offerData
      };
    } catch (error) {
      console.error('获取优惠数据失败:', error);
      return null;
    }
  }
  
  /**
   * 更新优惠查看次数
   * @param {String} offerId - 优惠ID
   */
  updateViewCount(offerId) {
    api.updateViewCount(offerId)
      .catch(error => console.error('更新查看次数失败:', error));
  }
}

export default new PopupService();
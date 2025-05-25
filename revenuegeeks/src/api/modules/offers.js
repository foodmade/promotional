import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 特殊优惠弹窗相关API
 */
export default {
  
    /**
   * 获取页面对应的特殊优惠信息
   * @param {string} pagePath - 页面路径
   * @returns {Promise<Object>} 处理后的优惠弹窗数据
   */
  getPageOffer(pagePath) {
    return apiClient.get(`/offers/page/${pagePath}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 更新优惠查看次数
   * @param {string} offerId - 优惠ID
   * @returns {Promise<Object>} 处理后的更新结果
   */
  updateViewCount(offerId) {
    // 由于API尚未实现，这里仅返回模拟成功结果
    return new Promise(resolve => {
      setTimeout(() => {
        resolve({ success: true });
      }, 300);
    });
  }
}; 
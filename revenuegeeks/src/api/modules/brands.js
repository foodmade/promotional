import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 品牌相关API
 */
export default {
  /**
   * 获取所有品牌
   * @returns {Promise<Object>} 处理后的品牌数据
   */
  getAll() {
    return apiClient.get('/brands/getAll')
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 根据ID获取品牌
   * @param {number|string} id - 品牌ID
   * @returns {Promise<Object>} 处理后的品牌数据
   */
  getById(id) {
    return apiClient.get(`/brands/${id}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 获取亚马逊卖家工具
   * @returns {Promise<Object>} 处理后的亚马逊卖家工具数据
   */
  getAmazonSellerTools() {
    return apiClient.get('/brands/amazon-seller-tools')
      .then(response => processResponse(response))
      .catch(handleApiError);
  }
}; 
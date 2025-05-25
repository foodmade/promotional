import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 通用API功能
 */
export default {
  /**
   * 搜索功能
   * @param {string} query - 搜索关键词
   * @returns {Promise<Object>} 处理后的搜索结果
   */
  search(query) {
    return apiClient.get('/search', { params: { q: query } })
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 订阅功能
   * @param {Object} data - 订阅数据
   * @returns {Promise<Object>} 处理后的订阅结果
   */
  subscribe(data) {
    return apiClient.post('/subscribe', data)
      .then(response => processResponse(response))
      .catch(handleApiError);
  }
}; 
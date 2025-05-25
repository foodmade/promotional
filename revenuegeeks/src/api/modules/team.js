import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 团队相关API
 */
export default {
  /**
   * 获取所有团队成员
   * @returns {Promise<Object>} 处理后的团队数据
   */
  getAll() {
    return apiClient.get('/author/getAll')
      .then(response => processResponse(response))
      .catch(handleApiError);
  }
}; 
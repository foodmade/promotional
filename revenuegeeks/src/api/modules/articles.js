import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 文章相关API
 */
export default {
  /**
   * 获取所有文章
   * @param {Object} params - 分页参数
   * @returns {Promise<Object>} 处理后的文章数据
   */
  getAll(params) {
    return apiClient.get('/articles/all', { params })
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 根据ID获取文章
   * @param {number|string} id - 文章ID
   * @returns {Promise<Object>} 处理后的文章数据
   */
  getById(id) {
    return apiClient.get(`/articles/${id}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  
  /**
   * 根据分类获取文章
   * @param {string} category - 分类名称
   * @returns {Promise<Object>} 处理后的文章数据
   */
  getByCategory(category) {
    return apiClient.get(`/articles/category/${category}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  }

  ,

  /**
   * 根据slug获取文章详情
   * @param {string} slug - 文章slug
   * @returns {Promise<Object>} 处理后的文章详情数据
   */
  getBySlug(slug) {
    return apiClient.get(`/articles/slug/${slug}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },

  getBySearch(search) {
    return apiClient.get(`/articles/search/${search}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  }
}; 
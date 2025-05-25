import request from '@/utils/request';
import { ApiResponse } from '@/utils/api-response';

/**
 * API基类，提供通用的HTTP方法
 */
export default class BaseApi {
  /**
   * 构造函数
   * @param {string} baseUrl - API基础路径
   */
  constructor(baseUrl = '') {
    this.baseUrl = baseUrl;
  }

  /**
   * 构建完整URL
   * @param {string} url - 相对URL
   * @returns {string} 完整URL
   */
  getFullUrl(url) {
    return `${this.baseUrl}${url}`;
  }

  /**
   * 处理响应数据
   * @param {Object} response - 原始响应数据
   * @returns {ApiResponse} 处理后的响应数据
   */
  handleResponse(response) {
    return ApiResponse.fromResponse(response);
  }

  /**
   * 发送GET请求
   * @param {string} url - 请求URL
   * @param {Object} params - 查询参数
   * @param {Object} config - 额外配置
   * @returns {Promise<ApiResponse>} 响应结果
   */
  async get(url, params = {}, config = {}) {
    try {
      const response = await request({
        url: this.getFullUrl(url),
        method: 'get',
        params,
        ...config
      });
      return this.handleResponse(response);
    } catch (error) {
      console.error('GET请求错误:', error);
      throw error;
    }
  }

  /**
   * 发送POST请求
   * @param {string} url - 请求URL
   * @param {Object} data - 请求体数据
   * @param {Object} config - 额外配置
   * @returns {Promise<ApiResponse>} 响应结果
   */
  async post(url, data = {}, config = {}) {
    try {
      const response = await request({
        url: this.getFullUrl(url),
        method: 'post',
        data,
        ...config
      });
      return this.handleResponse(response);
    } catch (error) {
      console.error('POST请求错误:', error);
      throw error;
    }
  }

  /**
   * 发送PUT请求
   * @param {string} url - 请求URL
   * @param {Object} data - 请求体数据
   * @param {Object} config - 额外配置
   * @returns {Promise<ApiResponse>} 响应结果
   */
  async put(url, data = {}, config = {}) {
    try {
      const response = await request({
        url: this.getFullUrl(url),
        method: 'put',
        data,
        ...config
      });
      return this.handleResponse(response);
    } catch (error) {
      console.error('PUT请求错误:', error);
      throw error;
    }
  }

  /**
   * 发送DELETE请求
   * @param {string} url - 请求URL
   * @param {Object} params - 查询参数
   * @param {Object} config - 额外配置
   * @returns {Promise<ApiResponse>} 响应结果
   */
  async delete(url, params = {}, config = {}) {
    try {
      const response = await request({
        url: this.getFullUrl(url),
        method: 'delete',
        params,
        ...config
      });
      return this.handleResponse(response);
    } catch (error) {
      console.error('DELETE请求错误:', error);
      throw error;
    }
  }
} 
import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

/**
 * 文章管理API
 */
export default class ArticleApi extends BaseApi {
  constructor() {
    super('/api/arcticle');
  }

  /**
   * 获取文章列表
   * @param {Object} params - 查询参数
   * @returns {Promise<ApiResponse>} 文章列表响应
   */
  async getArticles(params = {}) {
    try {
      return await this.get('/getArticleList', params);
    } catch (error) {
      console.error('获取文章列表失败:', error);
      throw error;
    }
  }

  /**
   * 获取文章详情
   * @param {number|string} id - 文章ID
   * @returns {Promise<ApiResponse>} 文章详情响应
   */
  async getArticle(id) {
    try {
      return await this.get(`/getArticleById`,{id:id});
    } catch (error) {
      console.error(`获取文章(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 创建文章
   * @param {Object} articleData - 文章数据
   * @returns {Promise<ApiResponse>} 创建结果响应
   */
  async createArticle(articleData) {
    console.log(`请求数据：${JSON.stringify(articleData)}`)
    try {
      return await this.post('/createArticle', articleData);
    } catch (error) {
      console.error('创建文章失败:', error);
      throw error;
    }
  }

  /**
   * 更新文章
   * @param {number|string} id - 文章ID
   * @param {Object} articleData - 更新的文章数据
   * @returns {Promise<ApiResponse>} 更新结果响应
   */
  async updateArticle(articleData) {
    try {
      return await this.put(`/updateArticle`, articleData);
    } catch (error) {
      console.error(`更新文章(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 删除文章
   * @param {number|string} id - 文章ID
   * @returns {Promise<ApiResponse>} 删除结果响应
   */
  async deleteArticle(id) {
    try {
      return await this.get(`/deleteArticle`,{id:id});
    } catch (error) {
      console.error(`删除文章(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 按分类获取文章
   * @param {string} tag - 分类标签
   * @returns {Promise<ApiResponse>} 文章列表响应
   */
  async getArticlesByCategory(tag) {
    try {
      return await this.get(`/category/${tag}`);
    } catch (error) {
      console.error(`获取分类(${tag})文章列表失败:`, error);
      throw error;
    }
  }
} 
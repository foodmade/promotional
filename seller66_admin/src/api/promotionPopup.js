import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

export default class PromotionPopupApi extends BaseApi {
  constructor() {
    super('/api/promotionPopup');
  }

  /**
   * 获取文章列表
   * @param {Object} params - 查询参数
   * @returns {Promise<ApiResponse>} 文章列表响应
   */
  async getList(params = {}) {
    try {
      return await this.get('/getList', params);
    } catch (error) {
      console.error('获取弹窗列表失败:', error);
      throw error;
    }
  }

  async update(params = {}) {
    try {
      return await this.post('/update', params);
    } catch (error) {
      console.error('更新弹窗失败:', error);
      throw error;
    }
  }

  async create(params = {}) {
    try {
      return await this.post('/create', params);
    } catch (error) {
      console.error('创建弹窗失败:', error);
      throw error;
    }
  }

  async delete(id) {
    try {
      return await this.get(`/delete`,{id:id});
    } catch (error) {
      console.error('删除弹窗失败:', error);
      throw error;
    }
  }
}

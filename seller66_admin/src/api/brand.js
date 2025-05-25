import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

/**
 * 品牌管理API
 */
export default class BrandApi extends BaseApi {
  constructor() {
    super('/api/brands');
  }

  /**
   * 获取品牌列表
   * @param {Object} params - 查询参数
   * @returns {Promise<ApiResponse>} 品牌列表响应
   */
  async getBrands() {
    try {
      return await this.get('/getAllBrands');
    } catch (error) {
      console.error('获取品牌列表失败:', error);
      throw error;
    }
  }

  /**
   * 获取品牌详情
   * @param {number|string} id - 品牌ID
   * @returns {Promise<ApiResponse>} 品牌详情响应
   */
  async getBrand(id) {
    try {
      return await this.get(`/getBrandsDetail`,{brandId:id});
    } catch (error) {
      console.error(`获取品牌(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 创建品牌
   * @param {Object} brandData - 品牌数据
   * @returns {Promise<ApiResponse>} 创建结果响应
   */
  async createBrand(brandData) {
    try {
      return await this.post('/createBrands', brandData);
    } catch (error) {
      console.error('创建品牌失败:', error);
      throw error;
    }
  }

  /**
   * 更新品牌
   * @param {number|string} id - 品牌ID
   * @param {Object} brandData - 更新的品牌数据
   * @returns {Promise<ApiResponse>} 更新结果响应
   */
  async updateBrand(brandData) {
    try {
      return await this.post(`/updateBrands`, brandData);
    } catch (error) {
      console.error(`更新品牌(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 删除品牌
   * @param {number|string} id - 品牌ID
   * @returns {Promise<ApiResponse>} 删除结果响应
   */
  async deleteBrand(id) {
    try {
      return await this.get(`/deleteBrands`,{brandId:id});
    } catch (error) {
      console.error(`删除品牌(ID: ${id})失败:`, error);
      throw error;
    }
  }
} 
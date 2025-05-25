import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

/**
 * 亚马逊卖家工具API
 */
export default class ToolApi extends BaseApi {
  constructor() {
    super('/api/amazon-seller-tools');
  }

  /**
   * 获取工具列表
   * @param {Object} params - 查询参数
   * @returns {Promise<ApiResponse>} 工具列表响应
   */
  async getTools(params = {}) {
    try {
      return await this.get('', params);
    } catch (error) {
      console.error('获取工具列表失败:', error);
      throw error;
    }
  }

  /**
   * 获取工具详情
   * @param {number|string} id - 工具ID
   * @returns {Promise<ApiResponse>} 工具详情响应
   */
  async getTool(id) {
    try {
      return await this.get(`/${id}`);
    } catch (error) {
      console.error(`获取工具(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 创建工具
   * @param {Object} toolData - 工具数据
   * @returns {Promise<ApiResponse>} 创建结果响应
   */
  async createTool(toolData) {
    try {
      return await this.post('', toolData);
    } catch (error) {
      console.error('创建工具失败:', error);
      throw error;
    }
  }

  /**
   * 更新工具
   * @param {number|string} id - 工具ID
   * @param {Object} toolData - 更新的工具数据
   * @returns {Promise<ApiResponse>} 更新结果响应
   */
  async updateTool(id, toolData) {
    try {
      return await this.put(`/${id}`, toolData);
    } catch (error) {
      console.error(`更新工具(ID: ${id})失败:`, error);
      throw error;
    }
  }

  /**
   * 删除工具
   * @param {number|string} id - 工具ID
   * @returns {Promise<ApiResponse>} 删除结果响应
   */
  async deleteTool(id) {
    try {
      return await this.delete(`/${id}`);
    } catch (error) {
      console.error(`删除工具(ID: ${id})失败:`, error);
      throw error;
    }
  }
} 
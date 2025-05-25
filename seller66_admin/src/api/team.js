import BaseApi from './base';
import ApiResponse from '@/utils/api-response';

/**
 * 团队成员API类
 */
class TeamApi extends BaseApi {
  /**
   * 构造函数
   */
  constructor() {
    super('/api/team');
  }

  /**
   * 获取团队成员列表
   * @param {Object} params - 查询参数
   * @param {number} params.page - 页码
   * @param {number} params.limit - 每页数量
   * @param {string} params.search - 搜索关键词
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async getTeamMembers(params = {}) {
    try {
      const response = await this.get('/members', params);
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '获取团队成员列表失败');
    }
  }

  /**
   * 获取团队成员详情
   * @param {string|number} id - 团队成员ID
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async getTeamMember(id) {
    try {
      const response = await this.get(`/members/${id}`);
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '获取团队成员详情失败');
    }
  }

  /**
   * 创建团队成员
   * @param {Object} data - 团队成员数据
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async createTeamMember(data) {
    try {
      const response = await this.post('/members', data);
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '创建团队成员失败');
    }
  }

  /**
   * 更新团队成员
   * @param {string|number} id - 团队成员ID
   * @param {Object} data - 团队成员更新数据
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async updateTeamMember(id, data) {
    try {
      const response = await this.put(`/members/${id}`, data);
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '更新团队成员失败');
    }
  }

  /**
   * 删除团队成员
   * @param {string|number} id - 团队成员ID
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async deleteTeamMember(id) {
    try {
      const response = await this.delete(`/members/${id}`);
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '删除团队成员失败');
    }
  }

  /**
   * 上传团队成员头像
   * @param {FormData} formData - 包含文件的表单数据
   * @returns {Promise<ApiResponse>} API响应对象
   */
  async uploadAvatar(formData) {
    try {
      const response = await this.post('/upload/avatar', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      return ApiResponse.fromResponse(response);
    } catch (error) {
      return this.handleError(error, '上传头像失败');
    }
  }
}

export default TeamApi; 
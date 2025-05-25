import BaseApi from './base';
import { LoginResponse } from '@/utils/api-response';

/**
 * 认证相关API
 */
export default class AuthApi extends BaseApi {
  constructor() {
    // 设置认证API的基础路径
    super('/api/admin');
  }

  /**
   * 处理登录响应
   * @param {Object} response - 原始响应数据
   * @returns {LoginResponse} 处理后的登录响应
   */
  handleLoginResponse(response) {
    return LoginResponse.fromResponse(response);
  }

  /**
   * 用户登录
   * @param {string} username - 用户名
   * @param {string} password - 密码
   * @returns {Promise<LoginResponse>} 登录响应结果
   */
  async login(username, password) {
    try {
      const response = await this.post('/login', { username, password });
      return this.handleLoginResponse(response);
    } catch (error) {
      console.error('登录失败:', error);
      throw error;
    }
  }

  /**
   * 用户登出
   * @returns {Promise<ApiResponse>} 登出响应结果
   */
  async logout() {
    try {
      const response = await this.post('/logout');
      
      // 无论是否成功，都清除本地token
      localStorage.removeItem('token');
      localStorage.removeItem('tokenHeader');
      localStorage.removeItem('tokenPrefix');
      
      return response;
    } catch (error) {
      console.error('登出失败:', error);
      
      // 出错也清除本地token
      localStorage.removeItem('token');
      localStorage.removeItem('tokenHeader');
      localStorage.removeItem('tokenPrefix');
      
      throw error;
    }
  }

  /**
   * 刷新token
   * @returns {Promise<LoginResponse>} 刷新token的响应结果
   */
  async refreshToken() {
    try {
      const response = await this.post('/refresh');
      return this.handleLoginResponse(response);
    } catch (error) {
      console.error('刷新token失败:', error);
      throw error;
    }
  }
} 
/**
 * API响应结果的通用模型
 */
export class ApiResponse {
  /**
   * 构造函数
   * @param {number} code - 状态码
   * @param {string} message - 消息
   * @param {any} data - 数据
   */
  constructor(code, message, data, total, pageNum, pageSize) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.total = total;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  /**
   * 检查响应是否成功
   * @returns {boolean} 是否成功
   */
  isSuccess() {
    return this.code === 200;
  }

  /**
   * 从原始响应数据创建ApiResponse实例
   * @param {Object} responseData - 原始响应数据
   * @returns {ApiResponse} ApiResponse实例
   */
  static fromResponse(responseData) {
    return new ApiResponse(
      responseData.code,
      responseData.message,
      responseData.data,
      responseData.total,
      responseData.pageNum,
      responseData.pageSize
    );
  }
}

/**
 * 登录响应数据的模型
 */
export class LoginResponse extends ApiResponse {
  /**
   * 从原始响应数据创建LoginResponse实例
   * @param {Object} responseData - 原始响应数据
   * @returns {LoginResponse} LoginResponse实例
   */
  static fromResponse(responseData) {
    const response = new LoginResponse(
      responseData.code,
      responseData.message,
      responseData.data
    );
    
    // 额外处理登录特有的数据
    if (response.isSuccess() && response.data) {
      // 导入auth.js中的setToken函数
      const { setToken } = require('./auth');
      
      // 保存token到cookie中(用于路由守卫)
      setToken(response.data);
      
      // 保存token相关信息到本地存储(用于请求头)
      localStorage.setItem('token', response.data);
      localStorage.setItem('tokenHeader', response.data.tokenHeader || 'Authorization');
      localStorage.setItem('tokenPrefix', response.data.tokenPrefix || 'Bearer ');
    }
    
    return response;
  }
} 
import axios from 'axios';

/**
 * 创建API客户端实例
 * @returns {Object} API客户端实例
 */
export const createApiClient = () => {
  const client = axios.create({
    baseURL: process.env.VUE_APP_API_URL || 'http://localhost:3358/api',
    timeout: 10000
  });

  // 请求拦截器
  client.interceptors.request.use(
    config => {
      // 可以在这里添加token等请求头
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );

  // 响应拦截器
  client.interceptors.response.use(
    response => {
      return response.data;
    },
    error => {
      console.error('API请求错误:', error);
      return Promise.reject(error);
    }
  );

  return client;
};

// 导出默认客户端实例供模块使用
export default createApiClient(); 
/**
 * 处理API响应数据
 * @param {Object} response - API响应对象
 * @returns {Object} 处理后的数据
 */
export const processResponse = (response) => {
  // 标准响应结构检查
  if (response && response.status === 200 && response.message === 'success') {
    // 如果有数据，返回处理后的数据
    if (response.data) {
      // 针对分页数据的处理
      if (response.data.rows) {
        return {
          ...response.data,
          rows: response.data.rows.map(item => ({
            ...item,
            // 对空值进行默认处理
            name: item.name || '',
            description: item.description || ''
          }))
        };
      }
      // 返回普通数据
      return response.data;
    }
    // 无数据时返回空对象
    return {};
  }
  
  // 处理错误情况
  console.error('API响应异常:', response);
  throw new Error(response?.message || '服务器响应异常');
};

/**
 * 统一处理API错误
 * @param {Error} error - 错误对象
 */
export const handleApiError = (error) => {
  console.error('API请求失败:', error);
  throw error;
}; 
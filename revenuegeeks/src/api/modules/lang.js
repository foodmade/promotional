import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

const langMap = {
  'en-US': '../../i18n/locales/en-US.js',
  'zh-CN': '../../i18n/locales/zh-CN.js'
};

export default {
  getLangPack(lang = 'zh-CN') {
    try {
      const filePath = langMap[lang] || langMap['zh-CN'];
      // 动态引入语言包
      // 注意：require 需要 .default
      const locale = require(filePath).default || require(filePath);
      // 模拟标准API响应结构
      const response = { status: 200, message: 'success', data: locale };
      return Promise.resolve(processResponse(response));
    } catch (error) {
      return Promise.reject(handleApiError(error));
    }
  },

  getAllLangPack() {
    return apiClient.get(`/lang/getAllLangPack`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  }

}; 
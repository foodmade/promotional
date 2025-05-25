import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

/**
 * 卖家工具相关API
 */
export default {
    /**
     * 获取亚马逊卖家工具
     * @returns {Promise<Object>} 处理后的亚马逊卖家工具数据
     */
    getAmazonSellerTools(slug) {
        return apiClient.get(`/sellerTools/getRankList/${slug}`)
            .then(response => processResponse(response))
            .catch(handleApiError);
    }
};


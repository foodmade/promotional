import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

export default {
    getAllBanners() {
        return apiClient.get('/banner/getBannerList')
            .then(response => processResponse(response))
            .catch(handleApiError);
    }
}
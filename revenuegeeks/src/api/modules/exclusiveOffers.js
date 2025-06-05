import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

export default {
    getExclusiveOffers() {
        return apiClient.get('exclusiveOffers/getAllExclusiveOffers')
            .then(response => processResponse(response))
            .catch(handleApiError);
    }
}
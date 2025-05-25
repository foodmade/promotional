import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

export default {

    getSupportList() {
        return apiClient.get('/support/getSupportList')
            .then(response => processResponse(response))
            .catch(handleApiError);
    }
}
import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

export default {
  getMenu() {
    return apiClient.get('/menu/getAll')
      .then(response => processResponse(response))
      .catch(handleApiError);
  },

  getMenuTitleAndSubtitleByPath(path){
    return apiClient.get(`/menu/getMenuTitleAndSubtitleByPath?path=${path}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  }
};

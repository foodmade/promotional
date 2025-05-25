import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

export default class SellerToolsApi extends BaseApi {
  constructor() {
    super('/api/sellerTools');
  }

  async getList(params = {}) {
    return this.get('/getAll', params);
  }

  async getDetail(id) {
    return this.get(`/getDetail`,{id:id});
  }

  async create(data) {
    return this.post('/create', data);
  }

  async update(data) {
    return this.post(`/update`, data);
  }

  async remove(id) {
    return this.get(`/delete`,{id:id});
  }
} 
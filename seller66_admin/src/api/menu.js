import BaseApi from './base';

export default class MenuApi extends BaseApi {
  constructor() {
    super('/api/menu');
  }
  async getMenuTree() {
    return await this.get('/tree');
  }
  async createMenu(data) {
    return await this.post('', data);
  }
  async updateMenu(id, data) {
    return await this.put(`/${id}`, data);
  }
  async deleteMenu(id) {
    return await this.delete(`/${id}`);
  }
} 
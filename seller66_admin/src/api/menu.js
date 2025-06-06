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
  async updateMenu( data) {
    return await this.post('/updateMenu',data);
  }
  async deleteMenu(id) {
    return await this.delete(`/${id}`);
  }

  async disableMenu(id) {
    return await this.get(`/disableMenu?id=${id}`);
  }
} 
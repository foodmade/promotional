import BaseApi from './base';

export default class BannerApi extends BaseApi {
  constructor() {
    super('/api/banner');
  }

  async getBannerList(params) {
    return await this.get('/getBannerList',params);
  }

  async createBanner(data) {
    return await this.post('/createBanner', data);
  }

  async updateBanner(data) {
    return await this.post('/updateBanner', data);
  }

  async deleteBanner(id) {
    return await this.get(`/deleteBanner`,{bannerId:id});
  }
}
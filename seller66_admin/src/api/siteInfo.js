import BaseApi from './base';

export default class SiteInfoApi extends BaseApi {
    constructor() {
        super('/api/siteConfig');
    }

    async getSiteInfo() {
        return await this.get('/getSiteConfig');
    }

    async updateSiteInfo(data) {
        return await this.post('/updateConfig', data);
    }
}
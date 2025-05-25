import BaseApi from './base';

/**
 * 团队成员API类
 */
export default class SupportApi extends BaseApi {
    constructor() {
        super('/api/support');
    }

    async getSupportList() {
        return await this.get('/list');
    }
    
    async createSupport(data) {
        return await this.post('/create', data);
    }

    async updateSupport(data) {
        return await this.post('/update', data);
    }

    async deleteSupport(id) {
        return await this.post('/delete', {id:id});
    }
    
}
import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

/**
 * 品牌管理API
 */
export default class ExclusiveOfferApi extends BaseApi {
    constructor() {
        super('/api/exclusiveOffers');
    }

    async getList(params = {}) {
        return await this.get('/getList', params);
    }

    async create(params = {}) {
        return await this.post('/create', params);
    }

    async update(params = {}) {
        return await this.post('/update', params);
    }

    async delete(id) {
        return await this.get('/delete', { id:id });
    }
}
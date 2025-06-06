import BaseApi from './base';

export default class LangApi extends BaseApi {
    constructor() {
        super('/api/lang');
    }
    async getLangList() {
        return await this.get('/getAllLangs');
    }

    async updateLang(lang) {
        return await this.post('/updateLang', lang);
    }
}

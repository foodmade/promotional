import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';

export default class AuthorApi extends BaseApi {

    constructor() {
        super('/api/author');
      }

    async getAuthorList() {
        return await this.get('/getAllAuthor');
    }

    async deleteAuthor(id) {
        return await this.get('/deleteAuthor',{id:id});
    }

    async getAuthor(id) {
        return await this.get('/getAuthorById',{id:id});
    }

    async createAuthor(author) {
        return await this.post('/createAuthor',author);
    }

    async updateAuthor(author) {
        return await this.post('/updateAuthor',author);
    }
    
    
}
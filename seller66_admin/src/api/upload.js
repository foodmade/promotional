import BaseApi from './base';
import { ApiResponse } from '@/utils/api-response';
import request from '@/utils/request';

export default class UploadApi extends BaseApi  {
    constructor(){
        super('/api/upload');
    }

    async uploadImage(file){
        try {
            const formData = new FormData();
            formData.append('file', file);
            
            const response = await request({
                url: '/api/upload/image',
                method: 'post',
                data: formData,
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            
            return ApiResponse.fromResponse(response);
        } catch (error) {
            console.error('上传图片失败:', error);
            throw error;
        }
    }
}
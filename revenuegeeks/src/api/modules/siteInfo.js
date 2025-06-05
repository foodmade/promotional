import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';


export default {
  getSiteInfo() {
    return apiClient.get(`/siteConfig/getSiteConfig`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  async getSiteLogo(){
     const res = await this.getSiteInfo()
     return res.logoUrl
  },
  async getSiteName(){
     const res = await this.getSiteInfo()
     return res.siteName
  },

  async getIcp(){
     const res = await this.getSiteInfo()
     return res.icp
  },

  async getDescription(){
    const res = await this.getSiteInfo()
    return res.siteDescription
  },
    
}; 
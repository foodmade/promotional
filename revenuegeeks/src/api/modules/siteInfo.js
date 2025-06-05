import apiClient from '../utils/client';
import { processResponse, handleApiError } from '../utils/response';

const siteInfoKey = {
  logo: 'logo',
  siteName: 'site_name',
  icp: 'icp',
  seoKeywords: 'seo_keywords',
  seoDescription: 'seo_description',
  contactEmail: 'contact_email',
  footer: 'footer',
  en_US: 'en_US',
  zh_CN: 'zh_CN',
}

export default {
  getSiteInfoByKey(key) {
    return apiClient.get(`/siteConfig/getSiteConfigByKey/${key}`)
      .then(response => processResponse(response))
      .catch(handleApiError);
  },
  getSiteLogo(){
    return this.getSiteInfoByKey(siteInfoKey.logo)
  },
  getSiteName(){
    return this.getSiteInfoByKey(siteInfoKey.siteName)
  },
  getIcp(){
    return this.getSiteInfoByKey(siteInfoKey.icp)
  },
  getLanguage(){
    return this.getSiteInfoByKey(siteInfoKey.language)
  }
}; 
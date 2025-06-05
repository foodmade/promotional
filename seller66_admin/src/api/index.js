import AuthApi from './auth';
import ArticleApi from './article';
import BrandApi from './brand';
import ToolApi from './tool';
import TeamApi from './team';
import UploadApi from './upload';
import AuthorApi from './author';
import SellerToolsApi from './sellerTools';
import BannerApi from './banner';
import SupportApi from './support';
import PromotionPopupApi from './promotionPopup';
import ExclusiveOfferApi from './exclusiveOffer';

/**
 * API工厂类，用于管理不同的API实例
 */
class ApiFactory {
  static apiInstances = new Map();

  /**
   * 获取认证API实例
   * @returns {AuthApi} 认证API实例
   */
  static getAuthApi() {
    if (!this.apiInstances.has('auth')) {
      this.apiInstances.set('auth', new AuthApi());
    }
    return this.apiInstances.get('auth');
  }

  /**
   * 获取文章API实例
   * @returns {ArticleApi} 文章API实例
   */
  static getArticleApi() {
    if (!this.apiInstances.has('article')) {
      this.apiInstances.set('article', new ArticleApi());
    }
    return this.apiInstances.get('article');
  }

  /**
   * 获取品牌API实例
   * @returns {BrandApi} 品牌API实例
   */
  static getBrandApi() {
    if (!this.apiInstances.has('brand')) {
      this.apiInstances.set('brand', new BrandApi());
    }
    return this.apiInstances.get('brand');
  }

  /**
   * 获取工具API实例
   * @returns {ToolApi} 工具API实例
   */
  static getToolApi() {
    if (!this.apiInstances.has('tool')) {
      this.apiInstances.set('tool', new ToolApi());
    }
    return this.apiInstances.get('tool');
  }

  /**
   * 获取团队API实例
   * @returns {TeamApi} 团队API实例
   */
  static getTeamApi() {
    if (!this.apiInstances.has('team')) {
      this.apiInstances.set('team', new TeamApi());
    }
    return this.apiInstances.get('team');
  }

  /**
   * 获取上传API实例
   * @returns {UploadApi} 上传API实例
   */
  static getUploadApi() {
    if(!this.apiInstances.has('upload')){
      this.apiInstances.set('upload', new UploadApi());
    }
    return this.apiInstances.get('upload');
  }

  static getAuthorApi() {
    if(!this.apiInstances.has('author')){
      this.apiInstances.set('author', new AuthorApi());
    }
    return this.apiInstances.get('author');
  }

  /**
   * 获取工具排行榜API实例
   * @returns {SellerToolsApi} 工具排行榜API实例
   */
  static getSellerToolsApi() {
    if (!this.apiInstances.has('sellerTools')) {
      this.apiInstances.set('sellerTools', new SellerToolsApi());
    }
    return this.apiInstances.get('sellerTools');
  }

  static getBannerApi() {
    if (!this.apiInstances.has('banner')) {
      this.apiInstances.set('banner', new BannerApi());
    }
    return this.apiInstances.get('banner');
  }

  static getSupportApi() {
    if (!this.apiInstances.has('support')) {
      this.apiInstances.set('support', new SupportApi());
    }
    return this.apiInstances.get('support');
  }

  static getPromotionPopupApi() {
    if (!this.apiInstances.has('promotionPopup')) {
      this.apiInstances.set('promotionPopup', new PromotionPopupApi());
    }
    return this.apiInstances.get('promotionPopup');
  }

  static getExclusiveOfferApi() {
    if (!this.apiInstances.has('exclusiveOffer')) {
      this.apiInstances.set('exclusiveOffer', new ExclusiveOfferApi());
    }
    return this.apiInstances.get('exclusiveOffer');
  }

  /**
   * 清除所有API实例
   */
  static clearAllInstances() {
    this.apiInstances.clear();
  }
}

export default ApiFactory; 
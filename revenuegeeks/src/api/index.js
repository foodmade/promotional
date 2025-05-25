import brands from './modules/brands';
import articles from './modules/articles';
import team from './modules/team';
import common from './modules/common';
import offers from './modules/offers';

/**
 * API模块集合
 */
export default {
  // 品牌相关API
  brands,
  
  // 文章相关API
  articles,
  
  // 团队相关API
  team,
  
  // 优惠弹窗相关API
  offers,
  
  // 通用API（搜索和订阅）
  ...common
}; 
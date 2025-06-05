import Vue from 'vue'
import VueI18n from 'vue-i18n'
import langApi from '@/api/modules/lang'

Vue.use(VueI18n)

let i18nInstance = null;
let allMessages = {};

export async function setupI18n() {
  // 通过API获取所有语言包
  const response = await langApi.getAllLangPack();
  const langArr = response.rows;
  allMessages = {};
  langArr.forEach(item => {
    try {
      allMessages[item.type] = JSON.parse(item.context);
    } catch (e) {
      console.log(e);
      allMessages[item.type] = '出现异常';
    }
  });
  console.log(allMessages);
  i18nInstance = new VueI18n({
    locale: localStorage.getItem('language') || 'zh-CN',
    fallbackLocale: 'zh-CN',
    messages: allMessages
  });
  return i18nInstance;
}

export function changeLanguage(lang) {
  if (i18nInstance && allMessages[lang]) {
    i18nInstance.locale = lang;
    localStorage.setItem('language', lang);
  }
}

export default i18nInstance; 
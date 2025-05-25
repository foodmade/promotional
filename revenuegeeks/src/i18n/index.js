import Vue from 'vue'
import VueI18n from 'vue-i18n'
import enUS from './locales/en-US'
import zhCN from './locales/zh-CN'

Vue.use(VueI18n)

const messages = {
  'en-US': enUS,
  'zh-CN': zhCN
}

const i18n = new VueI18n({
  locale: localStorage.getItem('language') || 'zh-CN',
  fallbackLocale: 'zh-CN',
  messages
})

export default i18n 
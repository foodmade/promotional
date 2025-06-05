import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { setupI18n } from './i18n'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import { createApp } from 'vue'
import siteInfo from '@/api/modules/siteInfo'

// 导入全局服务
import './plugins/PopupService'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.prototype.$http = axios

siteInfo.getSiteName().then(name => {
  if (name) document.title = name;
});
siteInfo.getDescription().then(desc => {
  let meta = document.querySelector('meta[name="description"]');
  if (meta && desc) meta.setAttribute('content', desc);
});

setupI18n().then(i18n => {
  new Vue({
    router,
    store,
    i18n,
    render: h => h(App)
  }).$mount('#app')
}); 
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import i18n from './i18n'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

// 导入全局服务
import './plugins/PopupService'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.prototype.$http = axios

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app') 
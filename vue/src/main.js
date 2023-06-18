import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import request from '@/utils/request';
import VueI18n from 'vue-i18n'
import i18n from './utils/i18n'


Vue.config.productionTip = false

//{size:"big"}
Vue.use(ElementUI);
Vue.use(VueI18n)

Vue.prototype.request=request

new Vue({
  router,
  i18n,
  render: h => h(App)
}).$mount('#app')

// const i18n = new VueI18n({
//   locale: 'zh',
//   messages: {
//     zh,
//     en
//   }
// })
//
// Vue.prototype.$i18n = i18n

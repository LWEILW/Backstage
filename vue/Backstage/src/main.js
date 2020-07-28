// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

// element框架
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// element 主题颜色
import '../theme/index.css'

// 导入组件
import navigation from './layout/components/navigation'
import header from './layout/components/header'
import Tags from './components/tags/index'
import breadBox from './components/breadBox/index'
import editor from './components/wangEnduit/index'
import wavePlay from './components/canvas/index'

// 导入全局样式
import './assets/css/global.css'

// 引入公共方法
import store from './utils/store'
Vue.prototype.store = store

Vue.config.productionTip = false
Vue.use(ElementUI)

// 引用组件
Vue.component('Navigation', navigation)
Vue.component('Header', header)
Vue.component('Tags', Tags)
Vue.component('BreadBox', breadBox)
Vue.component('wavePlay', wavePlay)
Vue.component('editor', editor)


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})

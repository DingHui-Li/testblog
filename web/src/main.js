import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import vuetify from 'vuetify'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(vuetify,{
  iconfont: 'md'
})

Vue.config.productionTip = false

import aboutPage from './view/aboutPage.vue'
import homePage from './view/homePage.vue'
import blogPage from './view/blogPage.vue'
import msgPage from './view/msgPage.vue'
import contentPage from './view/contentPage.vue'

const routes=[
  {path:'/',component:homePage},
  {path:'/home',component:homePage},
  {path:'/about',component:aboutPage},
  {path:'/blog',component:blogPage},
  {path:'/blog/:id',component:contentPage},
  {path:'/msg',component:msgPage}
]
const router=new VueRouter({
  routes
})
Vue.use(VueRouter);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

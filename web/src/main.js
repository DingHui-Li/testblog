import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import vuetify from 'vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(vuetify,{
  iconfont: 'md'
})
Vue.use(VueAxios,axios);
Vue.config.productionTip = false
export const apiHost="http://localhost:8888";
//--------------------------路由配置----------------
import navbar from './components/navbar'
import navbar2 from './components/navbar2'
import aboutPage from './view/aboutPage.vue'
import homePage from './view/homePage.vue'
import blogPage from './view/blogPage.vue'
import msgPage from './view/msgPage.vue'
import contentPage from './view/contentPage.vue'

import overview from './view/admin/overview'
import write from './view/admin/write'

import empty from './components/empty'

const routes=[
    {path:'/',component:navbar,children:[
        {path:'',redirect:'home'},
        {path:'home',component:homePage},
        {path:'about',component:aboutPage},
        {path:'blog',component:blogPage},
        {path:'/blog/:id',component:contentPage},
        {path:'msg',component:msgPage}
    ]},
    {path:'/admin',component:navbar2,children:[
        {path:'',redirect:'overview'},
        {path:'overview',component:overview},
        {path:'write',component:write},
    ]},
    {path:"/refresh",component:empty}
]
const router=new VueRouter({
  routes
})
//---------------------------------------------------------
Vue.use(VueRouter);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

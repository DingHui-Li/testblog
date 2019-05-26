import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import vuetify from 'vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'
// import CKEditor  from '@ckeditor/ckeditor5-vue'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(vuetify,{
  iconfont: 'md'
})
Vue.use(VueAxios,axios);
// Vue.use(CKEditor);
Vue.config.productionTip = false
// export const apiHost="http://localhost:8888";
export const apiHost="http://lidh.top:8084/blog";
//--------------------------路由配置----------------
const navbar=() =>import('./components/navbar')
const navbar2 =()=>import('./components/navbar2')
const aboutPage=() =>import('./view/aboutPage.vue')
const homePage =() =>import( './view/homePage.vue')
const blogPage =() =>import( './view/blogPage.vue')
const tagPage =() =>import( './view/tagPage.vue')
const contentPage =() =>import( './view/contentPage.vue')

const overview =() =>import( './view/admin/overview')
const write =() =>import( './view/admin/write')
const manage =() =>import( './view/admin/blogManage')
const tag =() =>import( './view/admin/tagManage')
const comment =() =>import( './view/admin/commentManage')

import empty from './components/empty'

const routes=[
    {path:'/',component:navbar,children:[
        {path:'',redirect:'home'},
        {path:'home',component:homePage},
        {path:'about',component:aboutPage},
        {path:'blog',component:blogPage},
        {path:'/blog/:id',component:contentPage},
        {path:'tag',component:tagPage}
    ]},
    {path:'/admin',component:navbar2,children:[
        {path:'',redirect:'overview'},
        {path:'overview',component:overview},
        {path:'write',component:write},
        {path:'write/:id',component:write},
        {path:'manage',component:manage},
        {path:'tag',component:tag},
        {path:'comment',component:comment},
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

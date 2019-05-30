import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import vuetify from 'vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'
import animated from 'animate.css'

import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(vuetify,{
  iconfont: 'md'
})
Vue.use(animated);
Vue.use(VueAxios,axios);
axios.interceptors.request.use(
  config=>{
    config.headers['token']=localStorage['token'];
    return config;
  }
);
axios.interceptors.response.use(res=>{
  if(res.data.code==200){
    return res;
  }else if(res.data.code==-1){
    return res;
  }else{
    alert("登录过期");
    return res;
  }
})
Vue.config.productionTip = false
//export const apiHost="http://localhost:8888";
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
const setting=()=>import('./view/admin/setting')

import empty from './components/empty'

const routes=[
    {path:'/',component:navbar,children:[
        {path:'',redirect:'home'},
        {path:'home',component:homePage},
        {path:'about',component:aboutPage},
        {path:'blog',component:blogPage},
        {path:'blog/:id',component:contentPage},
        {path:'tag',component:tagPage},
        {path:'tag/:name',component:tagPage}
    ]},
    {path:'/admin',component:navbar2,children:[
        {path:'',redirect:'overview'},
        {path:'overview',component:overview},
        {path:'write',component:write},
        {path:'write/:id',component:write},
        {path:'manage',component:manage},
        {path:'tag',component:tag},
        {path:'comment',component:comment},
        {path:'setting',component:setting},
    ]},
    {path:"/refresh",component:empty}
]
const router=new VueRouter({
  routes
})
router.beforeEach((to,from,next)=>{
  if(to.fullPath.indexOf('admin')!=-1){//判断是否需要验证登录
    if(localStorage['token']!=undefined&&localStorage['token']!='undefined'){//判断是否登录过
        axios({
          method:'get',
          url:apiHost+'/admin/islogin?token='+localStorage['token'],
        }).then(res=>{
          if(res.data.code==-1){//判断登录是否过期
            alert("登录过期，请重新登录")
            router.replace('/')
          }else if(res.data.code=200){
            next();
          }
        })
    }else{
      alert("请登录");
      router.replace('/')
    }
  }else next();
})
//---------------------------------------------------------
Vue.use(VueRouter);
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

<template>
	<div>
		<v-toolbar class="animated fadeInDown" id="nav" style="animation-duration:.5s;" fixed >
			<v-layout align-center justify-center row >
				<v-flex xs4 md1>
					<v-toolbar-side-icon @click="drawer=!drawer" class="hidden-md-and-up"></v-toolbar-side-icon>
				</v-flex>
				<v-flex xs6 md4 lg2>
					<v-toolbar-title style="cursor:pointer">{{websiteData.name}}</v-toolbar-title>
				</v-flex>
				<v-flex md4 class="hidden-sm-and-down" >
					<v-tabs grow color=rgba(0,0,0,0)>
					<v-tab v-for="item in catalog" :key="'s'+item.name" :to="item.to"><v-icon>{{item.icon}}</v-icon>{{item.name}} </v-tab>
					</v-tabs>
				</v-flex>
				<v-flex xs2 class="text-xs-center"><v-icon @click="islogin">person</v-icon></v-flex>
				<v-flex xs2 class="text-xs-right text-md-center text-lg-left">
					<v-icon @click="changeTheme">brightness_4</v-icon>
				</v-flex>
			</v-layout>
		</v-toolbar>
		<v-navigation-drawer  temporary  v-model="drawer" fixed height="100vh">
			<v-img :src="require('../assets/bg.jpg')" style="width:100%;height:auto"></v-img>
			<v-list>
				<v-list-tile v-for="item in catalog" :key="item.name" @click="to(item.to)" v-ripple>
					<v-list-tile-avatar>
						<v-icon large>{{item.icon}}</v-icon>
					</v-list-tile-avatar>
					<v-list-tile-content class="subheading font-weight-black text-xs-center">
						{{item.name}}
					</v-list-tile-content>
				</v-list-tile>
			</v-list>
		</v-navigation-drawer>
		<v-content>
			<v-layout>
				<v-flex xs12 style="margin-top:65px;margin-bottom:50px">
					<keep-alive exclude="contentPage">
						<router-view class="animated fadeIn" :websiteTitle="websiteData.title" :homeCover="websiteData.cover"> </router-view>
					</keep-alive>
				</v-flex>
			</v-layout>
        </v-content>
		<v-dialog v-model="dialog" persistent max-width="600px">
			<v-card style="padding:40px 40px 20px 40px;border-radius:5px">
				<v-text-field label="账号" single-line v-model="name"></v-text-field>
				<v-text-field label="密码" single-line v-model="pw" type="password"></v-text-field>
				<v-layout justify-center>
					<v-btn depressed @click="dialog=false">取消</v-btn>
						<v-btn depressed @click="login">登录</v-btn>
				</v-layout>
			</v-card>
		</v-dialog>
		<v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
			{{snackText}}
			<v-btn flat @click="snack=!snack">Close</v-btn>
		</v-snackbar>
	</div>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'navbar',
	beforeRouteEnter (to,from,next){
    	next(vm=>{
			if(to.path=="/home"){
				vm.axios({//记录访问者
					method:'post',
					url:apiHost+"/visit/add",
					data:JSON.stringify({'ip':returnCitySN.cip,'adress':returnCitySN.cname}),
					headers:{
								'content-type':'application/json;charset=utf-8'
							}
					}).then(function(res){
						//console.log(res.data.code);
					})
			}
		});
	},
	data(){
		return{
			drawer:false,
			dialog:false,
			catalog:[
					{'name':'主页','to':'/home','icon':'home'},
					{'name':'关于','to':'/about','icon':'info'},
					{'name':'blog','to':'/blog','icon':'view_list'},
					{'name':'标签','to':'/tag','icon':'loyalty'}
			],
			name:localStorage["adminName"],
			pw:'',
			snack:false,
			snackColor:'',
			snackText:'',
			top:0,//距离顶部距离
			websiteData:{
				name:'',
				title:'',
				cover:''
			}
		}
	},
	methods:{
		changeTheme:function(){
			this.$emit('changeTheme');
		},
		to:function(to){
			this.$router.replace(to);
		},
		getData:function(){
			this.axios({
				method:'get',
				url:apiHost+'/admin/get'
			}).then(res=>{
				if(res.data.code==200){
					this.websiteData=res.data.data;
				}
			})
		},
		islogin:function(){
			if(localStorage['token']!=undefined&&localStorage['token']!='undefined'){
				this.axios({
				method:'get',
				url:apiHost+'/admin/islogin?token='+localStorage['token'],
				}).then(res=>{
					if(res.data.code==-1){//判断登录是否过期
						this.dialog=true;
					}else if(res.data.code=200){
						this.$router.replace('/admin');
					}
				})
			}else{
				this.dialog=true;
			}
		},
		login:function(){
			localStorage["adminName"]=this.name;
			this.axios({
				method:'post',
				url:apiHost+'/admin/login',
				data:JSON.stringify({'name':this.name,'pw':this.pw}),
				headers:{
					'Content-type':'application/json;charset=utf-8'
				}
			}).then(res=>{
				if(res.data.code==200){
					this.snackColor="success";
					this.snackText="登录成功";
					localStorage.setItem("token",res.data.token);
					this.$router.replace("/admin")
				}
				else{
					this.snackColor="error";
					this.snackText="登录失败";
				}
				this.snack=true;
			})
		},
		scrollFunc:function() {  
			let newTop = document.scrollingElement.scrollTop;
			if(nav!=undefined){
				if (this.top-newTop>0) {              
					nav.classList.add("slideInDown")
					nav.classList.remove("slideOutUp")
				}  
				else { //当滑轮向下滚动时  
					nav.classList.add("slideOutUp")
					nav.classList.remove("slideDownUp")
				}  
				this.top=newTop;
			}
		} 
	},
	created:function(){
		this.getData();
	},
	mounted:function(){
		window.addEventListener('scroll',this.scrollFunc,true);
	}
}
</script>

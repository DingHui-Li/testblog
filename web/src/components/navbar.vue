<template>
	<div>
		<v-toolbar>
			<v-layout align-center justify-center row fill-height>
				<v-flex xs6 md1>
					<v-toolbar-side-icon @click="drawer=!drawer" class="hidden-md-and-up"></v-toolbar-side-icon>
					<v-icon @click="dialog=true">person</v-icon>
				</v-flex>
				<v-flex xs6 md4 lg2>
					<v-toolbar-title style="cursor:pointer">个人博客</v-toolbar-title>
				</v-flex>
				<v-flex md4 class="hidden-sm-and-down" >
					<v-tabs grow color=rgba(0,0,0,0)>
					<v-tab v-for="(val,key) in catalog" :key="'s'+key" :to="val">{{key}} </v-tab>
					</v-tabs>
				</v-flex>
			</v-layout>
		</v-toolbar>
		<v-navigation-drawer  temporary  v-model="drawer" absolute>
			<v-layout wrap  align-center justify-center >
			<v-flex xs12 v-for="(val,key) in catalog" :key="'m'+val">
				<v-card dark color="dark"  v-ripple style="border-radius:0;">
				<router-link :to="val" tag="div">
					<v-card-text><div class="text-xs-center" style="cursor:pointer" :to="val">{{key}}</div></v-card-text >
				</router-link>
				</v-card>
			</v-flex>
			</v-layout>
		</v-navigation-drawer>
		<v-content>
			<v-layout>
				<v-flex xs12 style="height:100vh">
				<keep-alive exclude="contentPage">
					<router-view> </router-view>
				</keep-alive>
				</v-flex>
			</v-layout>
        </v-content>
		<v-dialog v-model="dialog" persistent max-width="600px">
			<v-card style="padding:40px 40px 20px 40px;border-radius:5px">
				<v-text-field label="账号" single-line></v-text-field>
				<v-text-field label="密码" single-line></v-text-field>
				<v-layout justify-center>
					<v-btn depressed @click="dialog=false">取消</v-btn>
					<router-link to="/admin" style="text-decoration:none">
						<v-btn depressed>登录</v-btn>
					</router-link>
				</v-layout>
			</v-card>
		</v-dialog>
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
			catalog:{ 
					'主页':'/home',
					'关于':'/about',
					'blog':'/blog',
					'留言':'/msg'
			},
		}
	}
}
</script>

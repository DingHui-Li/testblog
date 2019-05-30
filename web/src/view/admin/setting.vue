<template>
	<v-layout wrap justify-center>
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-layout align-center justify-center row fill-height>
					<v-toolbar-side-icon @click="drawerChange" class="hidden-md-and-up"></v-toolbar-side-icon>
					<v-flex xs3 class="text-xs-center"></v-flex>
					<v-flex xs6 class="text-xs-center">
						<v-toolbar-title style="cursor:pointer">网站首页设置</v-toolbar-title>
					</v-flex>
					<v-flex xs3 class="text-xs-right text-lg-left"></v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12 lg12 xl12 style="padding:10px;border-radius:5px;" class="elevation-1">
			<v-layout wrap align-center justify-center>
				<v-flex xs12>
					<v-layout align-center justify-center row >
						<v-flex xs4 md1>
						</v-flex>
						<v-flex xs6 md4 lg2>
								<v-text-field v-model="websiteData.name"  solo placeholder=""></v-text-field>
						</v-flex>
						<v-flex md4 class="hidden-sm-and-down" >
							<v-tabs grow color=rgba(0,0,0,0) align-with-title>
								<v-tab v-for="(item,index) in catalog" :key="'sx'+index" disable>{{item}} </v-tab>
							</v-tabs>
						</v-flex>
						<v-flex xs2 class="text-xs-center"><v-icon>person</v-icon></v-flex>
						<v-flex xs2 class="text-xs-right text-md-center text-lg-left">
							<v-icon>brightness_4</v-icon>
						</v-flex>
					</v-layout>
				</v-flex>
				<v-flex xs12>
					<v-parallax  :src="getCover()" class="hidden-sm-and-down animated fadeIn" style="animation-duration:.5s" @click='clickCover'>
						<input type="file" accept="image/png, image/jpeg" id='uploadCover' style="display:none"/>
						<v-layout align-center justify-center>
							<v-text-field v-model="websiteData.title" class="display-1 font-weight-thin text-uppercase" solo @click.stop></v-text-field>
						</v-layout>
					</v-parallax>
				</v-flex>
				<v-flex xs12 class="text-xs-center" style="margin-top:50px">
					<v-btn @click="submit">提交</v-btn>
				</v-flex>
			</v-layout>
			<v-dialog v-model="uploadLoading" hide-overlay persistent width="300">
				<v-card	color="primary"	dark>
					<v-card-text>
						上传中
						<v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
					</v-card-text>
				</v-card>
			</v-dialog>
			<v-snackbar v-model='snack' :timeout="4000" :color="snackColor">
				{{snackText}}
				<v-btn flat @click="snack = false">Close</v-btn>
			</v-snackbar>
		</v-flex>
	</v-layout>
</template>
<script>
import {apiHost} from '../../main'
import qs from 'qs'
export default {
	name:'manage',
	data(){
		return{
			catalog:[
				"xx",'xx','xx','xx'
			],
			uploadLoading:false,
			websiteData:{
				name:'',
				title:'',
				cover:''
			},

			snack:false,
			snackColor:'',
			snackText:''
		}
	},
	computed:{
	},
	methods:{
		drawerChange:function(){
			this.$emit('drawerChange');
		},
		clickCover:function(){//触发input的点击事件
			uploadCover.click();
		},
		getCover:function(){
			if(this.websiteData.cover=='') return apiHost+'/default.jpg';
			return apiHost+this.websiteData.cover;
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
		submit:function(){
			this.uploadLoading=true;
			this.axios({
				method:'post',
				url:apiHost+'/admin/set',
				data:JSON.stringify({'name':this.websiteData.name,'title':this.websiteData.title,'cover':this.websiteData.cover}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(res=>{
				if(res.data.code==200){
					this.snackColor='success';
					this.snackText="修改成功";
				}else{
					this.snackColor='error';
					this.snackText="修改失败";
				}
				this.uploadLoading=false;
				this.snack=true;
			})
		},
		uploadCover:function(){
			let _this=this;
			uploadCover.addEventListener('change',function(){
				let formData =new FormData();
				if(this.files[0]!=null){
					formData.append("file",this.files[0]);
					formData.append("type","cover");
					if(this.files[0].size>( 2 * 1024 * 1024)){
						alert("图片大小不要超过2M")
					}
					else{
						_this.uploadLoading=true;
						_this.axios({
								method:'post',
								url: apiHost+'/blog/upload',
								data:formData,
							}).then(function(res){
								if(res.data.code==200){
									_this.websiteData.cover=res.data.path;
									_this.uploadLoading=false;
								}
							})
					}
				}
				this.value="";
			})
		}
	},
	created:function(){
		this.getData();
	},
	mounted:function(){
		this.uploadCover();
	},
	watch:{

	}
}
</script>

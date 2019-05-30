<template>
	<div>
		<v-parallax  :src="getHomeCover()" class="hidden-sm-and-down animated fadeIn" style="animation-duration:.5s">
			<v-layout align-center justify-center>
				<div class="display-3 font-weight-thin text-uppercase">{{websiteTitle}}</div>
			</v-layout>
		</v-parallax>
		<v-layout align-center justify-center>
			<v-flex xs12 md10 lg8 xl6>
						<v-layout wrap align-center>
							<v-flex xs12 class="body-2 font-weight-bold animated fadeIn" style="color:#757575;padding:0 15px;margin-top:20px;animation-duration:3s">最近更新</v-flex>
							<v-flex xs12 class="display-2 font-weight-black animated fadeIn" style="margin:10px 0;padding:0 10px;animation-duration:3s">blog</v-flex>
							<v-flex xs12 sm6 md5 lg4 style="margin:10px 0;padding:0 5px;animation-duration:0.5s;" v-for="blog in blogData" :key="'blog'+blog.id" class="animated fadeInDown">
								<v-card style="border-radius:5px;color:#fff" hover v-ripple @click="click(blog.id)" :color="color[Math.round(Math.random()*5)]">
									<v-card-title class=" text-no-wrap text-truncate">{{blog.title}}</v-card-title>
									<v-card-text class="text-xs-right caption text-no-wrap text-truncate">{{dateFormat(blog.time)}}</v-card-text>
								</v-card>
							</v-flex>
							<v-flex xs12 sm6 md5 lg4 style="animation-duration:0.5s;" class="animated fadeInDown">
								<v-layout align-center justify-left>
									<router-link to="/blog" style="text-decoration:none;" >
										<v-btn fab dark color="indigo">
											<v-icon dark>keyboard_arrow_right</v-icon>
										</v-btn>
									</router-link>
								</v-layout>
							</v-flex>
							<v-flex xs12 style="margin:20px 0">
								<v-divider></v-divider>
							</v-flex>
							<v-flex xs12 class="body-2 font-weight-bold animated fadeIn" style="color:#757575;padding:0 15px;margin-top:20px;animation-duration:3s" >最新评论</v-flex>
							<v-flex xs12 class="display-2 font-weight-black animated fadeIn" style="margin:10px 0;padding:0 10px;animation-duration:3s">评论</v-flex>
							<v-flex xs12 sm6 md5 lg4 style="margin:10px 0;padding:0 5px;animation-duration:.5s" v-for="comment in commentData" :key="'comment'+comment.id" class="animated fadeInDown">
								<v-card style="border-radius:5px;color:#fff" hover v-ripple @click="click(comment.blogid)" :color="color[Math.round(Math.random()*5)]">
									<v-card-title class=" text-no-wrap text-truncate subheading font-weight-bold" >{{comment.name}}</v-card-title>
									<v-card-text class="text-no-wrap text-truncate" v-html="comment.comment" style="padding:0 15px;"></v-card-text>
									<v-divider></v-divider>
									<v-layout wrap >
										<v-flex xs6>
											<v-card-text class="text-xs-left caption text-no-wrap text-truncate" >来自：{{comment.title}}</v-card-text>
										</v-flex>
										<v-flex xs6>
											<v-card-text class="text-xs-right caption text-no-wrap text-truncate" >{{dateFormat(comment.time)}}</v-card-text>
										</v-flex>
									</v-layout>
								</v-card>
							</v-flex>
						</v-layout>
			</v-flex>
		</v-layout>
		<!-- <v-footer color="primary lighten-1" style="margin-top:100px">
			<v-layout justify-center>
				<v-flex primary  lighten-2 py-3 text-xs-center  white--text xs12>
				&copy;2019 — <strong style="cursor:pointer" >粤ICP备18114096号</strong>
				</v-flex>
			</v-layout>
		</v-footer> -->
	</div>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'homePage',
	props:['websiteTitle','homeCover'],
	data(){
		return{
			blogData:[],
			commentData:[],
			color:['green', 'light-green','orange','deep-orange','blue-grey','primary'],
		}
	},
	methods:{
		beian:function(){
			window.open('http://www.beian.miit.gov.cn');
		},
		getHomeCover:function(){
			if(this.homeCover=='') return apiHost+'/default.jpg';
			return apiHost+this.homeCover;
		},
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
		click:function(id){
			this.$router.push({path:`/blog/${id}`});
		},
		getBlog:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getall?offset=0&limit=3"
			}).then(function(res){
				if(res.data.code==200)
					_this.blogData=res.data.data;
			})
		},
		getComment:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/comment/getall?offset=0&limit=6"
			}).then(function(res){
				if(res.data.code==200){
					_this.commentData=res.data.data;
				}
			})
		},
	},
	mounted:function(){
		this.getBlog();
		this.getComment();
	}
}
</script>
<style>

</style>

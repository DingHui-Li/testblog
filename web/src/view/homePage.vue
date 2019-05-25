<template>
	<div>
		<v-parallax  :src="require('../assets/bg.jpg')">
			<v-layout align-center justify-center>
				<div class="display-3 font-weight-thin text-uppercase">welcome</div>
			</v-layout>
		</v-parallax>
		<v-layout align-center >
			<v-flex xs12 lg8 offset-lg2 style="">
				<v-container>
					<v-layout wrap>
						<v-flex xs12 class="body-2 font-weight-bold" style="color:#757575">最近更新</v-flex>
						<v-flex xs12 class="display-2 font-weight-black" style="margin:10px 0">blog</v-flex>
						<v-flex xs12 sm6 md5 lg4 style="padding:10px;margin:0 0 0 -10px" v-for="blog in blogData" :key="'blog'+blog.id">
							<v-hover>
								<v-card style="border-radius:5px"  slot-scope="{ hover }" :class="`elevation-${hover ? 12 : 2}`" v-ripple @click="click(blog.id)">
									<v-card-title class=" text-no-wrap text-truncate">{{blog.title}}</v-card-title>
									<v-card-text class="text-xs-right caption text-no-wrap text-truncate" style="color:#757575">{{dateFormat(blog.time)}}</v-card-text>
								</v-card>
							</v-hover>
						</v-flex>
						<v-flex xs12 sm6 md5 lg4 style="padding-top:30px;margin:0 0 0 -10px">
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
						
						<!-- <v-flex xs12 class="body-2 font-weight-bold" style="color:#757575">最新评论</v-flex> -->
						<v-flex xs12 class="display-2 font-weight-black" style="margin:10px 0">评论</v-flex>
						<v-flex xs12 sm6 md5 lg4 style="padding:10px;margin:0 0 0 -10px" v-for="comment in commentData" :key="'comment'+comment.id">
							<v-hover>
								<v-card style="border-radius:5px"  slot-scope="{ hover }" :class="`elevation-${hover ? 12 : 2}`" v-ripple @click="click(comment.blogid)">
									<v-card-title class=" text-no-wrap text-truncate subheading font-weight-bold" >{{comment.name}}</v-card-title>
									<v-card-text class="text-no-wrap text-truncate" v-html="comment.comment"></v-card-text>
									<v-layout wrap>
										<v-flex xs6>
											<v-card-text class="text-xs-right caption text-no-wrap text-truncate" style="color:#757575">来自：{{comment.title}}</v-card-text>
										</v-flex>
										<v-flex xs6>
											<v-card-text class="text-xs-right caption text-no-wrap text-truncate" style="color:#757575">{{dateFormat(comment.time)}}</v-card-text>
										</v-flex>
									</v-layout>
								</v-card>
							</v-hover>
						</v-flex>
					</v-layout>
				</v-container>
			</v-flex>
		</v-layout>
		<!-- <v-footer color="primary lighten-1">
			<v-layout justify-center>
				<v-btn v-for="link in links" :key="link" color="white" flat round> {{ link }} </v-btn>
				<v-flex primary  lighten-2 py-3 text-xs-center  white--text xs12>
				&copy;2019 — <strong @click='click' style="cursor:pointer">粤ICP备18114096号</strong>
				</v-flex>
			</v-layout>
		</v-footer> -->
	</div>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'homePage',
	data(){
		return{
			blogData:[],
			commentData:[]
		}
	},
	methods:{
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
				url:apiHost+"/comment/getLately"
			}).then(function(res){
				if(res.data.code==200){
					_this.commentData=res.data.data;
				}
				console.log(res.data);
			})
		}
	},
	mounted:function(){
		this.getBlog();
		this.getComment();
	}
}
</script>
<style>

</style>

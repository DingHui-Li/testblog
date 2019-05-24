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
					</v-layout>
				</v-container>
			</v-flex>
		</v-layout>
	</div>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'homePage',
	data(){
		return{
			blogData:[]
		}
	},
	methods:{
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
		click:function(id){
			this.$router.push({path:`/blog/${id}`});
		}
	},
	mounted:function(){
		let _this=this;
		this.axios({
			method:'get',
			url:apiHost+"/blog/getall?offset=0&limit=3"
		}).then(function(res){
			_this.blogData=res.data.data;
		})
	}
}
</script>
<style>

</style>

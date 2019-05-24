<template>
	<v-layout align-center >
			<v-flex xs12 lg6 offset-lg3 style="">
				<v-container>
					<v-layout wrap justify-center>
						<v-flex xs12>
							<v-card style="border-radius:5px;margin-bottom:20px" v-for="blog in filter" :key="'blog'+blog.id" v-ripple @click="click(blog.id)">
								<v-layout>
									<v-flex xs4 lg3>
										<v-img :src="coverPath(blog.cover)" style="border-radius:5px 0 0 5px;height:100%;width:100%"></v-img>
									</v-flex>
									<v-flex xs8 lg9 class="text-no-wrap text-truncate">
										<v-card-title class="title font-weight-black">{{blog.title}}</v-card-title>
										<v-card-text class="subheading hidden-xs-only">{{blog.desc}}</v-card-text>
										<div v-if="blog.tag.length!=0">
											<v-chip color="primary" text-color="white" 
												style="margin-left:10px;z-index:1" v-for="tag in blog.tag.split(',')" :key="tag+blog.id" @click='tagClick(tag,$event)'>
												{{tag}}
											</v-chip>
										</div>
										<v-card-text class="text-xs-right caption" style="color:#757575;">{{dateFormat(blog.time)}}</v-card-text>
									</v-flex>
								</v-layout>
							</v-card>
						</v-flex>
						<v-flex xs2>
							<v-btn @click="getBlogData">加载</v-btn>
						</v-flex>
					</v-layout>
				</v-container>
			</v-flex>
			<v-flex lg2 :style="{position:'fixed',right:selectTagPanel?0:'-230px',top:'20%','z-index':9,width:'250px',transition:'right 0.5s'}" ref='selectTagPanel'>
				<v-card color=primary style="border-radius:5px 0 0 5px;color:#fff;">
					<v-layout wrap>
						<v-icon color="white" style="cursor:pointer" @click="selectTagPanel=!selectTagPanel">{{selectTagPanel?'keyboard_arrow_right':'keyboard_arrow_left'}}</v-icon>
							<v-card-title>已选标签</v-card-title>
							<div style="padding:10px">
								<v-chip color=green text-color="white" v-for="(tag,index) in selectTag" :key="'selectTag'+tag" @click="selectTagClick(index)">{{tag}}<v-icon right size="20" color='#CFD8DC'>cancel</v-icon></v-chip>
							</div>
					</v-layout>
				</v-card>
			</v-flex>
	</v-layout>
</template>
<script>
import { apiHost } from '../main';
export default {
	name:'blogPage',
	data(){
		return{
			blogData:[],
			selectTag:[],
			selectTagPanel:false,
			mini:true,
			drawer: true,
		}
	},
	methods:{
		coverPath:function(path){
			if(path==null)path="/default.jpg"
			return apiHost+path;
		},
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
		tagClick:function(tag,e){
			e.stopPropagation();
			if(!this.exist(this.selectTag,tag)){
				this.selectTag.push(tag);
			}
		},
		click:function(id){
			this.$router.push({path:`/blog/${id}`});
		},
		exist:function(arr,item){
			let length=arr.length;
			for(let i=0;i<length;i++){
				if(arr[i]==item) return true;
			}
			return false;
		},
		selectTagClick:function(index){
			this.selectTag.splice(index,1);
		},
		getBlogData:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getall?offset="+this.nowBlogNum+"&limit=10"
			}).then(function(res){
				for(let i=0;i<res.data.data.length;i++){
					_this.blogData.push(res.data.data[i]);
				}
				
			})
		},
	},
	computed:{
		filter:function(){
			if(this.selectTag.length!=0){
				return this.blogData.filter(blog=>{
					let length=this.selectTag.length;
					for(let i=0;i<length;i++){
						if(this.exist(blog.tag.split(','),this.selectTag[i])) return true;
					}
					return false;
				})
			}
			else return this.blogData;
		},
		nowBlogNum:function(){
			return this.blogData.length;
		}
	},
	mounted:function(){
		this.getBlogData();
	}
}
</script>
<style>

</style>
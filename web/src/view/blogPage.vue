<template>
	<v-layout align-center >
			<v-flex xs12 md8 lg6 offset-md2 offset-lg3 style="margin-top:20px">
					<v-layout wrap justify-center>
						<v-flex xs12 v-if="selectTag.length!=0">
							<v-card color=primary style="border-radius:5px;color:#fff;margin-bottom:20px">
								<v-layout wrap>
									<v-card-title>已选标签</v-card-title>
									<div style="padding:10px">
										<v-chip color=green text-color="white" v-for="(tag,index) in selectTag" :key="'selectTag'+tag" @click="selectTagClick(index)">
											{{tag}}
											<v-icon right size="20" color='#CFD8DC'>cancel</v-icon>
										</v-chip>
									</div>
								</v-layout>
							</v-card>
						</v-flex>
						<v-flex xs12 style="padding:5px">
							<v-card style="border-radius:5px;margin-bottom:20px;animation-duration:.5s;" :height='imgHeight()'
									v-for="blog in filter" :key="'blog'+blog.id" v-ripple @click="click(blog.id)" class="animated fadeInDown">
								<v-layout style="height:100%">
									<v-flex xs4 lg3 style="height:100%;">
										<v-img :src="coverPath(blog.cover)" style="border-radius:5px 0 0 5px;width:100%;height:100%"></v-img>
									</v-flex>
									<v-flex xs8 lg9 class="text-no-wrap text-truncate">
										<v-card-title class="title font-weight-black">{{blog.title}}</v-card-title>
										<v-card-text class="subheading hidden-xs-only" v-html="blog.desc"></v-card-text>
										<div v-if="blog.tag.length!=0">
											<v-chip color="primary" text-color="white" class="caption"
												style="margin-left:10px;z-index:1;" v-for="tag in blog.tag.split(',')" :key="tag+blog.id" @click='tagClick(tag,$event)'>
												{{tag}}
											</v-chip>
										</div>
										<v-divider></v-divider>
										<v-card-text class="text-xs-right caption" style="color:#757575;">{{dateFormat(blog.time)}}</v-card-text>
									</v-flex>
								</v-layout>
							</v-card>
						</v-flex>
						<v-flex xs12 class="text-xs-center">
							<v-btn @click="getBlogData" depressed class=" caption font-weight-bold">{{loadText}}</v-btn>
						</v-flex>
					</v-layout>
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
			loadText:'加载更多'
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
		tagClick:function(tag,e){//选中标签
			e.stopPropagation();
			if(!this.exist(this.selectTag,tag)){
				this.selectTag.push(tag);
			}
		},
		selectTagClick:function(index){//取消选中某个标签
			this.selectTag.splice(index,1);
		},
		click:function(id){//blog item点击事件
			this.$router.push({path:`/blog/${id}`});
		},
		exist:function(arr,item){//用于判断item是否存在于数组中
			let length=arr.length;
			for(let i=0;i<length;i++){
				if(arr[i]==item) return true;
			}
			return false;
		},
		getBlogData:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getall?offset="+this.nowBlogNum+"&limit=10"
			}).then(function(res){
				let length=res.data.data.length;
				if(length<10) _this.loadText="没有更多了";
				for(let i=0;i<length;i++){
					if(res.data.data[i].tag==null)res.data.data[i].tag="";
					_this.blogData.push(res.data.data[i]);
				}
			})
		},
		imgHeight:function(){
			if(document.body.clientWidth<500) return 150;
			else return 200;
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
	},
	updated:function(){
		//this.imgHeight();
	}
}
</script>
<style>

</style>
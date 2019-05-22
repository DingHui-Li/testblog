<template>
	<v-layout align-center >
			<v-flex xs12 lg6 offset-lg3 style="">
				<v-container>
					<v-layout wrap>
						<v-flex xs12>
							<v-card style="border-radius:5px;margin-bottom:20px" v-for="blog in filter" :key="'blog'+blog.id" v-ripple @click="click(blog.id)">
								<v-layout>
									<v-flex xs4 lg3>
										<v-img :src="require('../assets/bg.jpg')" style="border-radius:5px 0 0 5px;height:100%;width:100%"></v-img>
									</v-flex>
									<v-flex xs8 lg9 class="text-no-wrap text-truncate">
										<v-card-title class="title font-weight-black">{{blog.title}}</v-card-title>
										<v-card-text class="subheading hidden-xs-only">{{blog.desc}}</v-card-text>
										<v-chip color="primary" text-color="white" 
											style="margin-left:10px;z-index:1" v-for="tag in blog.tag" :key="tag+blog.id" @click='tagClick(tag,$event)' >
											{{tag}}
										</v-chip>
										<v-card-text class="text-xs-right caption" style="color:#757575;">{{blog.time}}</v-card-text>
									</v-flex>
								</v-layout>
							</v-card>
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
			<!-- <routr-view></routr-view> -->
	</v-layout>
</template>
<script>
export default {
	name:'blogPage',
	data(){
		return{
			blogData:[
				{'id':'1','title':'blog1','desc':'描述：内容的前50个字','tag':['tag1','tag2'],'time':'2019/5/22'},
				{'id':'2','title':'blog2','desc':'描述：内容的前50个字','tag':['tag1'],'time':'2019/5/23'},
				{'id':'3','title':'blog3','desc':'描述：内容的前50个字','tag':['tag1','tag2'],'time':'2019/5/24'},
				{'id':'4','title':'blog4','desc':'描述：内容的前50个字','tag':['tag2','tag3','tag4','tag23423523khnklhk'],'time':'2019/5/25'}
			],
			selectTag:[],
			selectTagPanel:false,
			mini:true,
			drawer: true,
		}
	},
	methods:{
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
	},
	computed:{
		filter:function(){
			if(this.selectTag.length!=0){
				return this.blogData.filter(blog=>{
					let length=this.selectTag.length;
					for(let i=0;i<length;i++){
						if(this.exist(blog.tag,this.selectTag[i])) return true;
					}
					return false;
				})
			}
			else return this.blogData;
		},
	},
}
</script>
<style>

</style>
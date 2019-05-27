<template>
	<v-layout wrap align-center justify-center>
		<v-container>
			<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="padding:0;margin-top:100px">
				<v-chip v-for="(tag,index) in tagData" :key="'tag'+tag.id" :color="chipColor[Math.round(Math.random() * 5)]" text-color="white" @click="selectTagClick(index)">
					<span class="subheading">{{tag.name}}</span>: <span class="subheading" style="margin-left:5px">{{tag.num}}</span>
				</v-chip>
			</v-flex>
			<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="margin-top:20px" >
				<v-card color=primary style="border-radius:5px;color:#fff;margin-bottom:20px">
					<v-layout wrap>
						<v-card-title>已选标签</v-card-title>
						<div style="padding:10px">
							<v-chip class="font-weight-bold" color=green text-color="white" v-for="(tag,index) in selectTag" :key="'selectTag'+tag.id" @click="selectedTagClick(index)">
								{{tag.name}}
								<v-icon right size="20" color='#CFD8DC'>cancel</v-icon>
							</v-chip>
						</div>
					</v-layout>
				</v-card>
			</v-flex>
			<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="padding:0;margin-top:100px">
				<v-card v-for="blog in blogData" :key="'blog'+blog.id" v-ripple hover @click="toBlog(blog.id)">
					<v-card-title class="subheading font-weight-black">{{blog.title}}</v-card-title>
					<v-card-text>{{blog.desc}}</v-card-text>
					<v-card-text class="text-xs-right caption">
						{{dateFormat(blog.time)}}
					</v-card-text>
					<v-divider></v-divider>
				</v-card>
				<v-card>
					<v-alert :value="blogData.length==0" color="error" icon="warning">
						Sorry, 没有找到符合的结果 :(
					</v-alert>
				</v-card>
			</v-flex>
		</v-container>
		<v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
			{{ snackText }}
			<v-btn flat @click="snack = false">Close</v-btn>
		</v-snackbar>
		<v-dialog v-model="loading" hide-overlay persistent width="300">
			<v-card	color="primary"	dark>
				<v-card-text>
					查询中
					<v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
				</v-card-text>
			</v-card>
		</v-dialog>
	</v-layout>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'tagPage',
	beforeRouteEnter:function(to,from,next){
		next(vm=>{
			vm.tagName=to.params.name;
		})
	},
	data(){
		return{
			selectTag:[],
			tagData:[],
			chipColor:['green', 'light-green','orange','deep-orange','blue-grey','primary'],
			loading:false,
			snack:false,
			snackColor:'error',
			snackText:'加载出错啦',
			blogData:[],
			tagName:-1
		}
	},
	computed:{
		
	},
	methods:{
		toBlog:function(id){
			this.$router.push({path:`/blog/${id}`});
		},
		getData:function(){//获取所有标签数据
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/tag/get_all_tag_num'
			}).then(function(res){
				if(res.data.code==200){
					_this.tagData=res.data.data;
				}
				console.log("获取数据");
			})
		},
		selectTagClick:function(index){//标签点击事件==>添加
			let exist=false;
			for(let i=0;i<this.selectTag.length;i++){//防止重复
				exist=false;
				if(this.selectTag[i].id==this.tagData[index].id){
					exist=true;break
				}
			}
			if(!exist) this.selectTag.push(this.tagData[index]);
			this.getBlogData();
		},
		selectedTagClick:function(index){//已选择标签的点击事件==>移除
			this.selectTag.splice(index,1);
			if(this.selectTag.length!=0){
				this.getBlogData();
			}
			else{
				this.blogData=[];
			}
		},
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
		getBlogData:function(){//获取匹配标签的blog
			let ids=this.selectTag.map(item=>item.id);
			this.loading=true;
			let _this=this;
			this.axios({
				method:'post',
				url:apiHost+'/blog/getbytag',
				data:JSON.stringify({'ids':ids}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(function(res){
				if(res.data.code==200){
					_this.blogData=res.data.data;
				}
				else{
					_this.snack=true;
				}
				console.log(res.data);
				_this.loading=false;
			})
		},
		async get(name){
			this.axios.get(apiHost+'/tag/get_all_tag_num').then(res=>{
				this.tagData=res.data.data;
				for(let i=0;i<this.tagData.length;i++){
					if(this.tagData[i].name==name){
						this.selectTagClick(i);
					}
				}
			});
		}
	},
	created:function(){
		
	},
	mounted:function(){
		this.getData();
	},
	watch:{
		tagName:function(newVal,oldVal){
			if(newVal!=''&&newVal!=undefined&&newVal!=-1){
				if(this.tagData.length==0){
					this.get(newVal);
				}else{
					for(let i=0;i<this.tagData.length;i++){
						if(this.tagData[i].name==name){
							this.selectTagClick(i);
						}
					}
				}
			}
		}
	}
}
</script>

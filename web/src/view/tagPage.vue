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
							<v-chip color=green text-color="white" v-for="(tag,index) in selectTag" :key="'selectTag'+tag.id" @click="selectedTagClick(index)">
								{{tag.name}}
								<v-icon right size="20" color='#CFD8DC'>cancel</v-icon>
							</v-chip>
						</div>
					</v-layout>
				</v-card>
			</v-flex>
		</v-container>
	</v-layout>
</template>
<script>
import {apiHost} from '../main'
export default {
	name:'tagPage',
	data(){
		return{
			selectTag:[],
			tagData:[],
			chipColor:['green', 'light-green','orange','deep-orange','blue-grey','primary'],
		}
	},
	computed:{
		
	},
	methods:{
		getData:function(){
			this.loading=true;
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/tag/get_all_tag_num'
			}).then(function(res){
				if(res.data.code==200){
					_this.tagData=res.data.data;
				}
			})
		},
		selectTagClick:function(index){//标签点击事件==>添加
			let exist=false;
			for(let i=0;i<this.selectTag.length;i++){
				exist=false;
				if(this.selectTag[i].id==this.tagData[index].id){
					exist=true;break
				}
			}
			if(!exist) this.selectTag.push(this.tagData[index]);
			let ids=this.selectTag.map(item=>item.id);
			this.axios({
				method:'post',
				url:apiHost+'/blog/getbytag',
				data:JSON.stringify({'ids':ids}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(function(res){
				console.log(res.data);
			})
		},
		selectedTagClick:function(index){//已选择标签的点击事件==>移除
			this.selectTag.splice(index,1);
		}
	},
	created:function(){
		
	},
	mounted:function(){
		this.getData();
	}
}
</script>

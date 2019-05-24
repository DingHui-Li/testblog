<template>
	<v-layout wrap >
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-layout align-center justify-center row fill-height>
					<v-flex xs4>
						<v-toolbar-side-icon @click="drawer=!drawer" class="hidden-md-and-up"></v-toolbar-side-icon>
						<router-link to="/">
							<v-icon>keyboard_arrow_left</v-icon>
						</router-link>
					</v-flex>
					<v-flex xs4>
						<v-toolbar-title style="cursor:pointer">写博客</v-toolbar-title>
					</v-flex>
					<v-flex xs4>
						<v-btn depressed color="primary" @click="submit">发布</v-btn>
					</v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12 lg10 xl9 style="padding:10px;height:90vh;border-radius:5px;" class="elevation-1">
			<v-text-field label="标题" v-model="title" style="height:10%"></v-text-field>
			<v-combobox chips v-model='chips' multiple label="添加标签" style="height:10%"></v-combobox>
			<div ref='toolbar'></div>
			<div ref='text' style="height:70%"></div>
		</v-flex>
	</v-layout>

</template>
<script>
import wangeditor from 'wangeditor'
import {apiHost} from '../../main'
export default {
	name:'write',
	data(){
		return{
			editor:null,
			title:'',
			chips:[],
			items:[],
		}
	},
	methods:{
		init:function(){
			const editor=new wangeditor(this.$refs.toolbar,this.$refs.text);
			editor.customConfig.uploadImgServer = apiHost+'/blog/upload';
			editor.customConfig.uploadFileName = 'file';
			const _this=this;
			editor.customConfig.customUploadImg=function(file,insert){
				let formData =new FormData();
				formData.append("file",file[0]);
				_this.axios({
					method:'post',
					url: apiHost+'/blog/upload',
					data:formData,
				}).then(function(res){
					if(res.data.code==200){
						insert(apiHost+res.data.path)
					}
				})
			}
			editor.create();
			this.editor=editor;
		},
		submit:function(){
			let desc=this.editor.txt.text().substring(0,50);
			let content=this.editor.txt.html();
			let tag='';
			for(let i=0;i<this.chips.length;i++){
				tag+=this.chips[i]+',';
			}
			tag=tag.slice(0,tag.length-1);
			let _this=this;
			if(this.title!=''){
				this.axios({
					method:'post',
					url:apiHost+"/blog/add",
					data:JSON.stringify({'title':this.title,'tag':tag,'desc':desc,'content':content}),
					headers:{
						'content-type':'application/json;charset=utf-8'
					}
				}).then(function(res){
					if(res.data.code==200){
						alert("发布成功");
						_this.$router.replace("/refresh");
					}
					else{
						alert("失败！");
					}
				})
			}else alert("标题不能为空")
		}
	},
	mounted:function(){
		this.init();
	}
}
</script>

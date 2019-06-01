<template>
	<v-layout wrap justify-center>
		<v-flex xs12>
			<v-card style="margin-bottom:20px;border-radius:5px">
				<v-card-title class="headline">我要发表看法</v-card-title>
				<v-card-text>
					<v-text-field outline label="您的大名" v-model="nickName"></v-text-field>
				</v-card-text>
				<v-card-text>
						<div ref='toolbar'></div>
						<div ref='text' style="height:100px;background-color:#e0e0e0;border-radius:5px">
							
						</div>
				</v-card-text>
				<v-layout wrap justify-center >
					<v-flex xs12 style="margin-bottom:10px" class="text-xs-center">
						<v-btn color="primary caption font-weight-bold" depressed @click="submit">提交</v-btn>
					</v-flex>
				</v-layout>
			</v-card>
		</v-flex>
		<v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
			{{ snackText }}
			<v-btn flat @click="snack = false">Close</v-btn>
		</v-snackbar>
	</v-layout>
</template>
<script>
import wangeditor from 'wangeditor'
import {apiHost} from '../main'
export default {
	name:'comment',
	props:['id'],
	data(){
		return{
			snack:false,
			snackColor:'',
			snackText:'',

			nickName:localStorage['nickName'],
			editor:null
		}
	},
	methods:{
		initEditor:function(){
			const editor=new wangeditor(this.$refs.toolbar,this.$refs.text);
			editor.customConfig.menus = [
				'foreColor', 
				'backColor',
				'emoticon',
			]
			editor.create();
			this.editor=editor;
		},
		submit:function(){
			localStorage['nickName']=this.nickName;
			let text=this.editor.txt.html().trim();
			let name=this.nickName.trim();
			let _this=this;
			if(this.nickName.length!=0){
				if(this.editor.txt.text().trim().length!=0){
					this.axios({
						method:'post',
						url:apiHost+'/comment/add',
						data:JSON.stringify({'blogid':this.id,'name':name,'comment':text}),
						headers:{
							'content-type':'application/json;charset=utf-8'
						}
					}).then(function(res){
						if(res.data.code==200){
							_this.snackColor="success";
							_this.snackText="成功";
							_this.$router.replace("/refresh");
						}else{
							_this.snackColor="error";
							_this.snackText="失败";
						}
						_this.snack=true;
					})
				}else{
					alert("内容不能为空");
				}
			}else{
				alert("名称不能为空")
			}
		}
	},
	mounted:function(){
		this.initEditor();
	}
}
</script>

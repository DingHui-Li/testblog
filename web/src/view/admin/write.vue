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
		<v-flex xs12 lg10 xl9 style="padding:10px;height:100%;border-radius:5px;margin-bottom:50px" class="elevation-1">
			<v-img style="width:100%;height:300px;" :src="apiHost+coverPath" @click="inputBtn">
				<v-layout align-center justify-center wrap style="height:100%">
					<v-flex xs2 ref="mydropzone"  >
						<input style="display:none" type="file" id="inputBtn" ref="inputBtn" accept="image/png, image/jpeg"/>
						<span class="text-xs-center title" style="cursor:pointer;color:#fff" ref="uploadTip">上传封面</span> 
					</v-flex>
				</v-layout>
			</v-img>
			<v-text-field label="标题" v-model="title" style="height:10%"></v-text-field>
			<v-combobox chips v-model='chips' multiple label="添加标签" style="height:10%" :items="allTag"></v-combobox>
			<div ref='toolbar'></div>
			<div ref='text' style="height:70%;"></div>
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
			title:'',//blog.title
			editor:null,
			chips:[],
			id:-1,//blogID:由blogManage页面传递而来
			coverPath:'/default.jpg',
			allTag:[]//所有标签
		}
	},
	computed:{
		apiHost:function(){
			return apiHost;
		}
	},
	methods:{
		inputBtn:function(){//上传封面的按钮
			this.$refs.inputBtn.click();
		},
		init:function(){//初始化编辑区域
			const editor=new wangeditor(this.$refs.toolbar,this.$refs.text);
			editor.customConfig.uploadImgServer = apiHost+'/blog/upload';
			editor.customConfig.uploadFileName = 'file';
			editor.customConfig.zIndex = 1;
			editor.customConfig.menus = [
				 'head',  // 标题
				'bold',  // 粗体
				'fontSize',  // 字号
				'fontName',  // 字体
				'italic',  // 斜体
				'underline',  // 下划线
				'strikeThrough',  // 删除线
				'foreColor',  // 文字颜色
				'backColor',  // 背景颜色
				'link',  // 插入链接
				'list',  // 列表
				'justify',  // 对齐方式
				'quote',  // 引用
				'emoticon',  // 表情
				'image',  // 插入图片
				'code',  // 插入代码
				'undo',  // 撤销
				'redo'  // 重复
			]
			const _this=this;
			editor.customConfig.customUploadImg=function(file,insert){
				let formData =new FormData();
				formData.append("file",file[0]);
				formData.append("type","img")
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
		submit:function(){//提交全部内容
			let desc=this.editor.txt.text().substring(0,50);
			let content=this.editor.txt.html();
			let tag='';
			for(let i=0;i<this.chips.length;i++){
				tag+=this.chips[i]+',';
			}
			tag=tag.slice(0,tag.length-1);
			let type;//new or update
			if(this.id!=undefined&&this.id!=-1) type="update";
			else type="new";
			let _this=this;
			if(this.title!=''){
				this.axios({
					method:'post',
					url:apiHost+"/blog/add",
					data:JSON.stringify({'title':this.title,'tag':tag,'desc':desc,'content':content,'cover':_this.coverPath,'type':type,'id':this.id}),
					headers:{
						'Content-type':'application/json;charset=UTF-8'
					}
				}).then(function(res){
					if(res.data.code==200){
						alert("发布成功");
						_this.$router.replace("/refresh");
						_this.$router.replace("/admin/write");
						_this.id=-1;
					}
					else{
						alert("失败！");
					}
				})
			}else alert("标题不能为空")
		},
		uploadCover:function(){//上传封面
			let _this=this;
			document.getElementById("inputBtn").addEventListener("change",function(){
				let formData =new FormData();
				if(this.files[0]!=null){
					formData.append("file",this.files[0]);
					formData.append("type","cover")
					this.files=null;
					_this.axios({
							method:'post',
							url: apiHost+'/blog/upload',
							data:formData,
						}).then(function(res){
							if(res.data.code==200){
								_this.coverPath=res.data.path;
								_this.$refs.uploadTip.style.display='none';
							}
						})
				}
			})
		},
		getAllTag:function(){//获取所有标签以供选择
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getalltag"
			}).then(function(res){
				if(res.data.code==200){
					_this.allTag=res.data.data.map((item)=>item.name);
				}
			})
		},
		getById:function(){//若是由blogManage跳转而来则获取相应数据填充
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getbyid?id="+this.id
			}).then(function(res){
				if(res.data.code==200){
					_this.title=res.data.data.title;
					_this.editor.txt.html(res.data.data.content);
					let t=res.data.data.tag.split(',');
					for(let i=0;i<t.length;i++){
						_this.chips.push(t[i])
					}
				}
			})
		}

	},
	// created:function(){
	// 	this.id=this.$route.params.id;
	// },
	mounted:function(){
		this.init();
		this.uploadCover();//input按钮添加监听事件
		this.getAllTag();
	},
	beforeRouteEnter: (to, from, next)=>{
		next(vm=>{
			vm.id=to.params.id;
		});
	},
	watch:{
		id:function(newVal,oldVal){
			if(newVal!=undefined&&newVal!=-1){
				this.getById();
				console.log("get")
			}else{
				this.title="";
				this.chips=[];
				this.editor.txt.clear();
				this.coverPath='/default.jpg';
			}
		}
	}
}
</script>


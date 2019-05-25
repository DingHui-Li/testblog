<template>
	<v-layout wrap>
		<v-flex xs12>
			<v-parallax  :src="coverPath">
				<v-layout align-center justify-center>
					<div class="display-2 font-weight-thin text-uppercase font-weight-regular">{{contentData.title}}</div>
				</v-layout>
			</v-parallax>
		</v-flex>
		<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="padding:0;">
			<v-card style="border-radius:5px;padding:0">
				<!-- <v-card-text class="text-xs-center title">{{contentData.title}}</v-card-text> -->
				<!-- <v-card-text id="textArea">
					<ckeditor :editor="editor" v-model="contentData.content" :config="editorConfig" disabled></ckeditor>
				</v-card-text> -->
				<v-card-text v-html="contentData.content" style="border-radius:5px;margin-top:10px"></v-card-text>
				<v-card-text class="text-xs-right caption">更新于：{{dateFormat(contentData.time)}}</v-card-text>
			</v-card>
		</v-flex>
		<!-- -------------------------标签---------------- -->
		<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 v-if="contentData.tag.length!=0" style="padding:10px">
			<v-chip color="primary" text-color="white" v-for="(chip,index) in contentData.tag.split(',')" :key="index">{{chip}}</v-chip>
		</v-flex>
		<!-- ----------------------------评论区-------------------- -->
		<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="padding:15px;">
			<comment :id="id"/> 
		</v-flex>
		<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="padding:15px;">
			<v-card v-for="comment in comments" :key="'comment'+comment.id">
				<v-card-title class="subheading font-weight-bold"><v-icon style="margin-right:5px">face</v-icon> {{comment.name}}</v-card-title>
				<v-card-text v-html="comment.comment" style=" word-wrap:break-word; word-break:break-all;">
				</v-card-text>
				<v-card-text>
					<div class="text-xs-right caption">{{dateFormat(comment.time)}}</div>
				</v-card-text>
				<v-divider></v-divider>
			</v-card>
		</v-flex>
		<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3>
			<v-layout justify-center>
				<v-flex xs2><v-btn @click="getComment" depressed>{{loadText}}</v-btn></v-flex>
			</v-layout>
		</v-flex>
	</v-layout>
</template>
<script>
import {apiHost} from '../main'
// import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import comment from '../components/comment'
export default {
	name:'contentPage',
	components:{
		comment
	},
	data(){
		return{
			// editor:ClassicEditor,
			id:this.$route.params.id,
			contentData:{'tag':''},
			// editorConfig:{
			// 	toolbar: []
			// },
			comments:[],
			loadText:'加载更多'
		}
	},
	computed:{
		coverPath:function(){
			return apiHost+this.contentData.cover;
		},
	},
	methods:{
		getContent:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/blog/getbyid?id='+this.id
			}).then(function(res){
				if(res.data.data.tag==null) res.data.data.tag="";
				_this.contentData=res.data.data;
			})
		},
		getComment:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/comment/get?id='+this.id+"&offset="+_this.comments.length+"&limit=20"
			}).then(function(res){
				if(res.data.code==200){
					let length=res.data.data.length;
					if(length<20) _this.loadText="没有更多了"
					for(let i=0;i<length;i++){
						_this.comments.push(res.data.data[i]);
					}
				}
				console.log(res.data);
			})
		},
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
	},
	created:function(){
		this.getContent();
		this.getComment();
	},
	mounted:function(){
		
	}
}
</script>
<style scoped type="text/css"  lang="css">
	/* blockquote 样式 */
	blockquote {
		display: block;
		border-left: 8px solid #d0e5f2;
		padding: 5px 10px;
		margin: 10px 0;
		line-height: 1.4;
		font-size: 100%;
		background-color: #f1f1f1;
	}

	/* code 样式 */
	code {
		display: inline-block;
		*display: inline;
		*zoom: 1;
		background-color: #f1f1f1;
		border-radius: 3px;
		padding: 3px 5px;
		margin: 0 3px;
	}
	pre code {
		display: block;
	}

	/* ul ol 样式 */
	ul, ol {
		margin: 10px 0 10px 20px;
	}
</style>

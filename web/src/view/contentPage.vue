<template>
	<v-layout wrap>
		<v-container>
			<v-flex xs12 md10 lg8 xl6 offset-md1 offset-lg2 offset-xl3 style="height:auto">
				<v-card style="border-radius:5px;box-shadow:none;">
					<v-card-text class="text-xs-center title">{{contentData.title}}</v-card-text>
					<v-card-text class="text-xs-right">{{dateFormat(contentData.time)}}</v-card-text>
					<v-card-text style="height:100vh" id="textArea">
						<ckeditor :editor="editor" v-model="contentData.content" :config="editorConfig" disabled></ckeditor>
					</v-card-text>
				</v-card>
			</v-flex>
		</v-container>
	</v-layout>
</template>
<script>
import {apiHost} from '../main'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
	name:'contentPage',
	data(){
		return{
			editor:ClassicEditor,
			id:this.$route.params.id,
			contentData:{},
			editorConfig:{
				toolbar: []
			}
		}
	},
	methods:{
		getContent:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/blog/getbyid?id='+this.id
			}).then(function(res){
				_this.contentData=res.data.data;
			})
		},
		dateFormat:function(date){
			let d=new Date(date);
			return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate()+" "+d.getUTCHours()+":"+d.getMinutes()+":"+d.getSeconds();
		},
	},
	created:function(){
		this.getContent();
	},
	mounted:function(){
		
	}
}
</script>
<style>

</style>

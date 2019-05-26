<template>
	<v-layout wrap justify-center>
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-toolbar-side-icon @click="drawerChange" class="hidden-md-and-up"></v-toolbar-side-icon>
				<v-layout align-center justify-center row fill-height>
					<v-flex xs3 class="text-xs-center">
						<v-icon style="cursor:pointer" @click="getData">refresh</v-icon>
					</v-flex>
					<v-flex xs6 class="text-xs-center">
						<v-toolbar-title style="cursor:pointer">评论管理</v-toolbar-title>
					</v-flex>
					<v-flex xs3 class="text-xs-right text-lg-left">
						<div ref="delBtn" style="display:none">
							<v-btn depressed color="primary" @click="dialog=true" >删除</v-btn>
						</div>
					</v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12 lg12 xl12 style="padding:10px;border-radius:5px;" class="elevation-1">
			<v-data-table :items="comments" :headers="tableHead" sort-icon="none" hide-actions  select-all v-model="selected" :loading="loading">
				<v-progress-linear v-slot:progress color="blue" indeterminate></v-progress-linear>
				<template v-slot:items="props">
					<td><v-checkbox  v-model="props.selected" hide-details></v-checkbox></td>
					<td>{{props.item.id}}</td>
					<td>{{props.item.name}}</td>
					<td class="text-no-wrap text-truncate" v-html="props.item.comment" style="max-width:200px"></td>
					<td>{{props.item.title}}</td>
					<td>{{props.item.time}}</td>
				</template>
			</v-data-table>
		</v-flex>
		<v-flex xs12>
			<div class="text-xs-center">
				<v-pagination v-model="nowPage" :length="allPageNum" total-visible="5"></v-pagination>
			</div>
		</v-flex>
		<v-snackbar v-model="snack" :timeout="3000" :color="snackColor">
			{{ snackText }}
			<v-btn flat @click="snack = false">Close</v-btn>
		</v-snackbar>
		<v-dialog v-model="dialog" max-width="300">
			<v-card >
				<v-layout wrap justify-center align-center>
					<v-flex xs12>
						<div class="title text-xs-center" style="margin:20px 10px">确定要删除吗？</div>
					</v-flex>
						<v-btn depressed color="error" style="margin-bottom:20px" @click="deleteComment">确定</v-btn>
						<v-btn depressed color="success" style="margin-bottom:20px" @click="dialog=false">取消</v-btn>
				</v-layout>
			</v-card>
		</v-dialog>
		<v-dialog v-model="deleteDialog" hide-overlay persistent width="300">
			<v-card	color="primary"	dark>
				<v-card-text>
					删除中
					<v-progress-linear indeterminate color="white" class="mb-0"></v-progress-linear>
				</v-card-text>
			</v-card>
		</v-dialog>
	</v-layout>
</template>
<script>
import {apiHost} from '../../main'
import qs from 'qs'
export default {
	name:'manage',
	data(){
		return{
			drawer:false,
			selected:[],
			comments:[],
			tableHead:[
				{text:'ID'},
				{text:'Name'},
				{text:'内容'},
				{text:'来自'},
				{text:'Time'},
			],
			nowPage:1,
			allPageNum:1,

			loading:false,
			snack:false,
			snackColor:'',
			snackText:'',
			dialog:false,
			deleteDialog:false
		}
	},
	computed:{
	},
	methods:{
		drawerChange:function(){
			this.$emit('drawerChange');
		},
		getData:function(){
			this.loading=true;
			let offset=(this.nowPage-1)*20;
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/comment/getall?offset="+offset+"&limit=20"
			}).then(function(res){
				if(res.data.code==200){
					_this.comments=res.data.data;
				}
				_this.loading=false;
			})
		},
		deleteComment:function(){
			let ids=this.selected.map(item=>item.id);
			this.deleteDialog=true;
			this.dialog=false;
			let _this=this;
			this.axios({
				method:'delete',
				url:apiHost+"/comment/delete",
				data:JSON.stringify({'ids':ids}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(function(res){
				if(res.data.code==200){
					_this.snackColor='success';
					_this.snackText="删除成功";
					_this.getData();
					_this.getCommentNum();
				}else{
					_this.snackColor='serror';
					_this.snackText="删除失败";
				}
				_this.snack=true;
				_this.deleteDialog=false;
			})
		},
		getCommentNum:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/comment/getnum"
			}).then(function(res){
				if(res.data.code==200){
					_this.allPageNum=Math.ceil(res.data.data/20);
				}
			})
		},
	},
	created:function(){

	},
	mounted:function(){
		this.getData();
	},
	watch:{
		nowPage:function(newVal,oldVal){//若nowpage变化，则重新获取数据
			this.getData();
		},
		selected:function(newVal){
			if(newVal.length==0) this.$refs.delBtn.style.display='none';
			else this.$refs.delBtn.style.display='block';
		}
	}
}
</script>

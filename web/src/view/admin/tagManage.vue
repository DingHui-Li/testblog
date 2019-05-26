<template>
	<v-layout wrap justify-center>
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-layout align-center justify-center row fill-height>
					<v-toolbar-side-icon @click="drawerChange" class="hidden-md-and-up"></v-toolbar-side-icon>
					<v-flex xs3 class="text-xs-center">
						<v-icon style="cursor:pointer" @click="getData">refresh</v-icon>
					</v-flex>
					<v-flex xs6 class="text-xs-center">
						<v-toolbar-title style="cursor:pointer">标签管理</v-toolbar-title>
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
			<v-text-field v-model="search" label="搜索" single-line hide-details append-icon="search" style="width:50%;float:right"></v-text-field>
			<v-data-table :items="tagData" :headers="tableHead" sort-icon="none" hide-actions  select-all v-model="selected" :loading="loading" :search="search">
				<v-progress-linear v-slot:progress color="blue" indeterminate></v-progress-linear>
				<template v-slot:items="props">
					<td><v-checkbox  v-model="props.selected" hide-details></v-checkbox></td>
					<td>{{props.item.id}}</td>
					<td> 
						<v-edit-dialog :return-value.sync="props.item.name" large lazy persistent  @save="updateTag(props.item.id)">
							<v-chip label>
								{{props.item.name}}
							</v-chip>
							<template v-slot:input>
								<div class="mt-3 title">Update name</div>
							</template>
							<template v-slot:input>
								<v-text-field
									v-model="props.item.name"
									label="Edit"
									single-line
									counter
									autofocus
								></v-text-field>
							</template>
						</v-edit-dialog>
					</td>
					<td>{{props.item.num}}</td>
				</template>
			</v-data-table>
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
						<v-btn depressed color="error" style="margin-bottom:20px" @click="deleteTag">确定</v-btn>
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
			search:'',
			snack:false,
			snackText:'',
			snackColor:'',
			dialog:false,
			deleteDialog:false,

			selected:[],
			tagData:[],
			tableHead:[
				{text:'ID',value:'id'},
				{text:'标签名',value:'name'},
				{text:'blog数',value:'num'},
			],
			loading:true,

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
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+'/tag/get_all_tag_num'
			}).then(function(res){
				if(res.data.code==200){
					_this.tagData=res.data.data;
				}
				_this.loading=false;
				console.log(res.data);
			})
		},
		updateTag:function(id){
			let length=this.tagData.length;
			let tag;
			for(let i=0;i<length;i++)//根据id获取tag数据
			{
				if(this.tagData[i].id==id){
					tag=this.tagData[i];break;
				}
			}
			let _this=this;
			this.axios({
				method:'post',
				url:apiHost+'/tag/update',
				data:JSON.stringify({'id':tag.id,'name':tag.name}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(function(res){
				if(res.data.code==200){
					_this.snackText="修改成功";
					_this.snackColor="success";
				}else{
					_this.snackText="修改失败";
					_this.snackColor="error";
				}
				_this.snack=true;
			})
		},
		deleteTag:function(){
			this.deleteDialog=true;
			this.dialog=false;
			let ids=this.selected.map(item=>item.id);
			let _this=this;
			this.axios({
				method:'delete',
				url:apiHost+'/tag/delete',
				data:JSON.stringify({'ids':ids}),
				headers:{
					'content-type':'application/json;charset=utf-8'
				}
			}).then(function(res){
				if(res.data.code==200){
					_this.snackText="删除成功";
					_this.snackColor="success";
					_this.getData();
				}else{
					_this.snackText="修改失败";
					_this.snackColor="error";
				}
				_this.deleteDialog=false;
				_this.snack=true;
			})
		},
	},
	created:function(){

	},
	mounted:function(){
		this.getData();
	},
	watch:{
		selected:function(newVal){
			if(newVal.length==0) this.$refs.delBtn.style.display='none';
			else this.$refs.delBtn.style.display='block';
		}
	}
}
</script>

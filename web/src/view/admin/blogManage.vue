<template>
	<v-layout wrap justify-center>
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
						<v-toolbar-title style="cursor:pointer">Blog管理</v-toolbar-title>
					</v-flex>
					<v-flex xs4>
						<div ref="delBtn" style="display:none">
							<v-btn depressed color="primary" @click="deleteBlog" >删除</v-btn>
						</div>
					</v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12 lg12 xl12 style="padding:10px;border-radius:5px;" class="elevation-1">
			<v-data-table :items="blogData" :headers="tableHead" sort-icon="none" hide-actions  select-all v-model="selected">
				<template v-slot:items="props">
					<td><v-checkbox  v-model="props.selected" hide-details></v-checkbox></td>
					<td @click="click(props.item.id)">{{props.item.id}}</td>
					<td @click="click(props.item.id)">{{props.item.title}}</td>
					<td @click="click(props.item.id)">{{props.item.desc}}</td>
					<td @click="click(props.item.id)">
						<div v-if="props.item.tag.length!=0">
							<v-chip v-for="tag in props.item.tag.split(',')" :key="tag" color="green" text-color="#fff">{{tag}}</v-chip>
						</div>
					</td>
					<td @click="click(props.item.id)">{{props.item.time}}</td>
				</template>
			</v-data-table>
		</v-flex>
		<v-flex xs12>
			<div class="text-xs-center">
				<v-pagination v-model="nowPage" :length="allPageNum" total-visible="5"></v-pagination>
			</div>
		</v-flex>
	</v-layout>

</template>
<script>
import {apiHost} from '../../main'
import qs from 'qs'
export default {
	name:'manage',
	data(){
		return{
			selected: [],
			blogData:[],
			allPageNum:0,//总页数
			nowPage:1,//当前页数,
			tableHead:[
				{text:'ID'},
				{text:'标题'},
				{text:'desc'},
				{text:'标签'},
				{text:'time'},
			]
		}
	},
	computed:{
	},
	methods:{
		click:function(id){//表格一行点击事件
			this.$router.push({path:`/admin/write/${id}`});
		},
		deleteBlog:function(){
			let ids=this.selected.map(item=>item.id);
			ids=JSON.stringify(ids);
			let _this=this;
			this.axios({
				method:'post',
				url:apiHost+'/blog/delete',
				data:qs.stringify({'ids':ids}),
			}).then(function(res){
				console.log(res.data.code)
				if(res.data.code==200){
					_this.getData();
				}
			})
		},
		getData:function(){
			let _this=this;
			let offset=(this.nowPage-1)*20;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getall?offset="+offset+"&limit=20"
			}).then(function(res){
				_this.checkBox=[];
				let data=res.data.data;
				if(res.data.code==200){
					_this.blogData=data;
				}
			})
		},
		getBlogNum:function(){//and tagNum
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getnum"
			}).then(function(res){
				if(res.data.code==200)
				_this.allPageNum=Math.ceil(res.data.blognum/20);
			})
		},
	},
	created:function(){
		this.getBlogNum();
	},
	mounted:function(){
		this.getData();
	},
	watch:{
		nowPage:function(newVal,oldVal){
			this.getData();
		},
		selected:function(newVal){
			if(newVal.length==0) this.$refs.delBtn.style.display='none';
			else this.$refs.delBtn.style.display='block';
		}
	}
}
</script>

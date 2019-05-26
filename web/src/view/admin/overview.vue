<template>
	<v-layout wrap >
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-toolbar-side-icon @click="drawerChange" class="hidden-lg-and-up"></v-toolbar-side-icon>
				<v-layout align-center justify-center wrap>
					<v-flex xs12 class="text-xs-left text-md-center">
						<v-toolbar-title style="cursor:pointer" class="title font-weight-bold">网站概览</v-toolbar-title>
					</v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12>
			<v-layout wrap>
				<v-flex xs12 md6 xl4 style="padding:20px;" v-for="data in statisticsData" :key="data.name" >
					<v-card color="green" style="padding:20px;border-radius:5px" v-ripple @click="to(data.to)">
						<v-layout align-center>
							<v-flex lg2>
								<v-icon size="60" color="white">{{data.icon}}</v-icon>
							</v-flex>
							<v-flex lg10 class="text-xs-right">
								<div class="title" style="color:white">{{data.name}}</div>
								<div class="title" style="color:white;margin-top:20px">{{data.num}}</div>
							</v-flex>
						</v-layout>	
					</v-card>
				</v-flex>
				<v-flex xs12 style="padding:20px">
					<v-card color="blue">
						<v-card color="blue" style="padding:20px;border-radius:5px">
						<v-layout align-center>
							<v-flex lg1>
								<v-icon size="60" color="white">group_add</v-icon>
							</v-flex>
							<v-flex lg11 class="text-xs-right text-lg-left">
								<div class="title" style="color:white">访问人数</div>
								<div class="title" style="color:white;margin-top:20px">{{visitNum}}</div>
							</v-flex>
						</v-layout>	
					</v-card>
						<v-card-text>
							<div caption class="text-xs-center" style="color:white;margin-bottom:10px">近30天访问趋势</div>
							<div ref='chart' style="height:400px;width:100%;"></div>
						</v-card-text>
					</v-card>
				</v-flex>
			</v-layout>
		</v-flex>
	</v-layout>
</template>
<script>
import {apiHost} from '../../main'
import echarts from 'echarts'
export default {
	name:'overview',
	data(){
		return{
			value:[1,2,3,4,33,6,7,80,9],
			statisticsData:[
				{	'name':'博客总数',
					'icon':'insert_drive_file',
					'num':'0',
					'to':'manage'
				},
				{	'name':'标签总数',
					'icon':'local_offer',
					'num':'0',
					'to':'tag'
				},
				{	'name':'评论总数',
					'icon':'forum',
					'num':'0',
					'to':'comment'
				},
			],
			visitNum:0
		}
	},
	methods:{
		drawerChange:function(){
			this.$emit('drawerChange');
		},
		getVisitNum:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/visit/getnum"
			}).then(function(res){
				_this.visitNum=res.data.num;
			})
		},
		getBlogNum:function(){//and tagNum
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/blog/getnum"
			}).then(function(res){
				_this.statisticsData[0].num=res.data.blognum;
				_this.statisticsData[1].num=res.data.tagnum;
			})
		},
		getCommentNum:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/comment/getnum"
			}).then(function(res){
				_this.statisticsData[2].num=res.data.data;
			})
		},
		getHistory:function(){
			let _this=this;
			this.axios({
				method:'get',
				url:apiHost+"/visit/getdata"
			}).then(function(res){
				_this.initCharts(res.data.data);
			})
		},
		initCharts:function(data){
			let days=data.map((item)=>{
				let d=new Date(item.time);
				return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getUTCDate();
			});
			let num=data.map((item)=>item.num);
			let charts=echarts.init(this.$refs.chart);
			let options={
					color: ['#fff'],
					tooltip : {
					trigger: 'axis',
					axisPointer : {
						type : 'shadow'
					},
					},
					xAxis : [
					{
						data : days,
						axisLabel:{
							color:'#fff'
						},
						splitLine:{
							show:false
						},
						axisLine:{
							lineStyle:{
								color:'#fff',
								width:3
							}
						}
					}
					],
					yAxis : [
					{
						type : 'value',
						axisLabel:{
							color:'#fff'
						},
						splitLine:{
							show:false
						},
						axisLine:{
							lineStyle:{
								color:'#fff',
								width:3
							}
						}
					}
					],
					series : [
					{
						name:'访问人数',
						type:'line',
						barWidth: '60%',
						data:num,
						smooth: true
					}
					]
			};
			charts.setOption(options);
			window.addEventListener('resize',function() {charts.resize()});
		},
		to:function(to){
			this.$router.push("/admin/"+to);
		}
	},
	mounted:function(){
		this.getVisitNum();
		this.getBlogNum();
		this.getCommentNum();
		this.getHistory();
	}
}
</script>

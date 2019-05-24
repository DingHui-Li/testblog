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
						<v-toolbar-title style="cursor:pointer">网站概览</v-toolbar-title>
					</v-flex>
				</v-layout>
			</v-toolbar>
		</v-flex>
		<v-flex xs12>
			<v-layout wrap>
				<v-flex xs12 md6 xl4 style="padding:20px;" v-for="data in statisticsData" :key="data.name">
					<v-card color="green" style="padding:20px;border-radius:5px">
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
							<v-sheet color="rgba(0, 0, 0, .12)">
								<v-sparkline
								:value="value"
								color="rgba(255, 255, 255, .7)"
								height="100"
								padding="24"
								line-width="1"
								stroke-linecap="round"
								smooth
								>
								<template v-slot:label="item">
									{{ item.value }}
								</template>
								</v-sparkline>
							</v-sheet>
						</v-card-text>
					</v-card>
				</v-flex>
			</v-layout>
		</v-flex>
	</v-layout>
</template>
<script>
import {apiHost} from '../../main'
export default {
	name:'overview',
	data(){
		return{
			value:[1,2,3,4,33,6,7,80,9],
			statisticsData:[
				{	'name':'博客总数',
					'icon':'insert_drive_file',
					'num':'0'
				},
				{	'name':'标签总数',
					'icon':'local_offer',
					'num':'0'
				},
				{	'name':'留言总数',
					'icon':'forum',
					'num':'0'
				},
			],
			visitNum:0
		}
	},
	methods:{
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
		}
	},
	mounted:function(){
		this.getVisitNum();
		this.getBlogNum();
	}
}
</script>

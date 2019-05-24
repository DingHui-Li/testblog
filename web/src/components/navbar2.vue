<template>
	<v-layout wrap>
		<v-flex xs0 md0 lg3 xl2 class="hidden-md-and-down">
			<v-navigation-drawer style="height:100vh;">
				<v-list>
					<v-list-tile style="height:100px;">
						<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0">
							<v-list-tile-action><v-icon large>album</v-icon></v-list-tile-action>
							<v-list-tile-content class="subheading font-weight-black">网站后台管理</v-list-tile-content>
						</v-toolbar>
					</v-list-tile>
					<v-list-tile v-for="item in catalog" :key="'catalog:'+item.name">
						<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;cursor:pointer" v-ripple @click="to(item.to,item.name)">
							<v-list-tile-action><v-icon large>{{item.icon}}</v-icon></v-list-tile-action>
							<v-list-tile-content class="subheading font-weight-black">{{item.name}}</v-list-tile-content>
						</v-toolbar>
					</v-list-tile>
				</v-list>
			</v-navigation-drawer>
		</v-flex>
		<v-flex xs12 md12 lg9 xl10>
			<v-layout wrap>
				<v-flex xs12 >
					<keep-alive>
						<router-view ></router-view>
					</keep-alive>
				</v-flex>
			</v-layout>
		</v-flex>
	</v-layout>
</template>
<script>
export default {
	name:'navbar2',
	data(){
		return{
			drawer:false,
			title:'网站后台管理',
			catalog:[
				{'name':'网站概览','icon':'equalizer','to':'/admin/overview'},
				{'name':'写博客','icon':'font_download','to':'/admin/write'},
				{'name':'博客管理','icon':'vertical_split','to':'/admin/manage'},
				{'name':'标签管理','icon':'turned_in','to':'./admin/tag'}
			]
		}
	},
	methods:{
		to:function(to,name){
			this.title=name;
			this.$router.push(to);
		}
	}
}
</script>

<template>
	<v-layout wrap>
		<v-flex xs0 md0 lg3 xl2 class="hidden-md-and-down">
			<v-navigation-drawer style="height:100vh;">
				<v-list>
					<v-list-tile style="height:100px;">
							<v-list-tile-avatar><v-icon large>album</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">网站后台管理</v-list-tile-content>
					</v-list-tile>
					<v-list-tile v-for="item in catalog" :key="'catalog:'+item.name"  v-ripple @click="to(item.to,item.name)">
							<v-list-tile-avatar><v-icon large>{{item.icon}}</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">{{item.name}}</v-list-tile-content>
					</v-list-tile>
					<v-divider style="margin:10px 0"></v-divider>
					<v-list-tile @click="changeTheme">
							<v-list-tile-avatar><v-icon large>brightness_4</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">主题</v-list-tile-content>
					</v-list-tile>
					<v-list-tile @click="back"> 
							<v-list-tile-avatar><v-icon large>keyboard_return</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">返回</v-list-tile-content>
					</v-list-tile>
					<v-list-tile @click="dialog=true"> 
							<v-list-tile-avatar><v-icon large>close</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">退出</v-list-tile-content>
					</v-list-tile>
				</v-list>
			</v-navigation-drawer>
		</v-flex>
		<v-navigation-drawer style="height:100vh;" absolute v-model="drawer">
				<v-list>
					<v-list-tile style="height:100px;">
							<v-list-tile-avatar><v-icon large>album</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">网站后台管理</v-list-tile-content>
					</v-list-tile>
					<v-list-tile v-for="item in catalog" :key="'catalog:'+item.name"  v-ripple @click="to(item.to,item.name)">
							<v-list-tile-avatar><v-icon large>{{item.icon}}</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">{{item.name}}</v-list-tile-content>
					</v-list-tile>
					<v-divider style="margin:10px 0"></v-divider>
					<v-list-tile @click="changeTheme">
							<v-list-tile-avatar><v-icon large>brightness_4</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">主题</v-list-tile-content>
					</v-list-tile>
					<v-list-tile @click="back"> 
							<v-list-tile-avatar><v-icon large>keyboard_return</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">返回</v-list-tile-content>
					</v-list-tile>
					<v-list-tile @click="dialog=true"> 
							<v-list-tile-avatar><v-icon large>close</v-icon></v-list-tile-avatar>
							<v-list-tile-content class="subheading font-weight-black">退出</v-list-tile-content>
					</v-list-tile>
				</v-list>
			</v-navigation-drawer>
		<v-flex xs12 md12 lg9 xl10>
			<v-layout wrap>
				<v-flex xs12 >
					<keep-alive>
						<router-view @drawerChange="drawerChange"></router-view>
					</keep-alive>
				</v-flex>
			</v-layout>
		</v-flex>
		<v-dialog v-model="dialog" max-width="300">
			<v-card >
				<v-layout wrap justify-center align-center>
					<v-flex xs12>
						<div class="title text-xs-center" style="margin:20px 10px">确定要退出吗？</div>
					</v-flex>
						<v-btn depressed color="error" style="margin-bottom:20px" @click="signout">确定</v-btn>
						<v-btn depressed color="success" style="margin-bottom:20px" @click="dialog=false">取消</v-btn>
				</v-layout>
			</v-card>
		</v-dialog>
	</v-layout>
</template>
<script>
export default {
	name:'navbar2',
	data(){
		return{
			dialog:false,
			drawer:true,
			title:'网站后台管理',
			catalog:[
				{'name':'网站概览','icon':'equalizer','to':'/admin/overview'},
				{'name':'写博客','icon':'font_download','to':'/admin/write'},
				{'name':'博客管理','icon':'vertical_split','to':'/admin/manage'},
				{'name':'标签管理','icon':'turned_in','to':'/admin/tag'},
				{'name':'评论管理','icon':'forum','to':'/admin/comment'}
			]
		}
	},
	methods:{
		back:function(){
			this.$router.replace('/');
		},
		to:function(to,name){
			this.title=name;
			this.$router.push(to);
		},
		drawerChange:function(){
			this.drawer=!this.drawer;
		},
		signout:function(){
			localStorage['token']=undefined;
			this.$router.replace('/')
		},
		changeTheme:function(){
			this.$emit('changeTheme')
		}
	}
}
</script>

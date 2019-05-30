<template>
	<v-container>
		<v-layout  align-center justify-center wrap>
			<v-flex xs12 lg10 xl7>
				<v-card style="border-radius:5px;">
					<v-card-title class="title font-weight-black">关于本站</v-card-title>
					<v-card-text class="subheading font-weight-regular">本站前端是由Vue开发的单页面应用(SPA)，Api接口由SpringBoot编写。</v-card-text>
					<v-divider></v-divider>
					<v-card-title class="subheading font-weight-black">相关技术和插件</v-card-title>
					<v-card-text class="subheading font-weight-regular">
						前端框架：<a href="https://cn.vuejs.org/" target="blank">Vue</a>；
						UI库：<a href="https://vuetifyjs.com/zh-Hans/" target="blank">Vuetify</a>； 
						路由：<a href="https://router.vuejs.org/zh/" target="blank">VueRouter</a> ；
						Ajax库：<a href="http://www.axios-js.com/" target="blank">Axios</a> ；
						富文本编辑器：<a href="https://www.kancloud.cn/wangfupeng/wangeditor3/332599" target="blank">WangEditor</a> ；
						动画库：<a href="https://github.com/daneden/animate.css" target="blank">Animate.Css</a> 
						</v-card-text>
					<v-card-text class="subheading font-weight-regular">
						后端框架：<a href="https://spring.io/projects/spring-boot" target="blank">SpringBoot</a> ；
						DAO：<a href="http://www.mybatis.org/mybatis-3/zh/getting-started.html" target="blank">Mybatis</a> ;
						数据库：<a href="https://www.mysql.com/" target="blank">Mysql</a> 
					</v-card-text>
					<v-divider></v-divider>
					<v-card-text class="subheading font-weight-regular"> 源码地址：<a href="https://github.com/DingHui-Li/blog" target="blank" style="color:#43A047">GitHub</a> </v-card-text>
				</v-card>
			</v-flex>
			<v-flex xs12 class="text-xs-center subheading font-weight-regular" style="margin-top:100px">2019.5.30</v-flex>
		</v-layout>
	</v-container>
</template>
<script>
export default {
	name:'aboutPage',
	data(){
		return{
			aboutMe:{

			}
		}
	}
}
</script>
<style>
	a{
		text-decoration:none;
		target:blank
	}
</style>
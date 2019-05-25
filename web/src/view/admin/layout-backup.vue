<template>
	<v-layout wrap justify-center>
		<v-flex xs12>
			<v-toolbar style="box-shadow:none;border-bottom:1px solid #e0e0e0;">
				<v-layout align-center justify-center row fill-height>
					<v-flex xs2>
						<v-toolbar-side-icon @click="drawer=!drawer" class="hidden-md-and-up"></v-toolbar-side-icon>
						<router-link to="/">
							<v-icon>keyboard_arrow_left</v-icon>
						</router-link>
					</v-flex>
					<v-flex xs2>
						<v-icon style="cursor:pointer" @click="getData">refresh</v-icon>
					</v-flex>
					<v-flex xs4>
						<v-toolbar-title style="cursor:pointer">标签管理</v-toolbar-title>
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
			
		}
	},
	computed:{
	},
	methods:{
	
	},
	created:function(){

	},
	mounted:function(){

	},
	watch:{

	}
}
</script>

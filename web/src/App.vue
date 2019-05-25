<template>
  <v-app :dark="dark" :light="light">
      <router-view @changeTheme="changeTheme"></router-view>
  </v-app>
</template>

<script>
export default {
  name: 'app',
  data(){
      return{
        dark:false,
        light:true
      }
  },
  methods:{
    click:function(){
      window.open('http://www.beian.miit.gov.cn');
    },
    changeTheme:function(){
      this.dark=!this.dark;
      this.light=!this.light;
    }
  }
}
</script>
<style>
</style>

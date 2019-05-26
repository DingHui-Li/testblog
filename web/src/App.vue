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
    changeTheme:function(){
      this.dark=!this.dark;
      this.light=!this.light;
    }
  }
}
</script>

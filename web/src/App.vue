<template>
  <v-app>
      <v-toolbar>
        <v-layout align-center justify-center row fill-height>
          <v-flex xs6 md1>
              <v-toolbar-side-icon @click="drawer=!drawer" class="hidden-md-and-up"></v-toolbar-side-icon>
          </v-flex>
          <v-flex xs6 md4 lg2>
              <v-toolbar-title style="cursor:pointer">个人博客</v-toolbar-title>
          </v-flex>
          <v-flex md4 class="hidden-sm-and-down" >
            <v-tabs grow color=rgba(0,0,0,0)>
              <v-tab v-for="(val,key) in catalog" :key="'s'+key" :to="val">{{key}} </v-tab>
            </v-tabs>
          </v-flex>
        </v-layout>
      </v-toolbar>
      <v-navigation-drawer  temporary  v-model="drawer" absolute>
        <v-layout wrap  align-center justify-center >
          <v-flex xs12 v-for="(val,key) in catalog" :key="'m'+val">
            <v-card dark color="dark"  v-ripple style="border-radius:0;">
              <router-link :to="val" tag="div">
                <v-card-text><div class="text-xs-center" style="cursor:pointer" :to="val">{{key}}</div></v-card-text >
              </router-link>
            </v-card>
          </v-flex>
        </v-layout>
      </v-navigation-drawer>
        <v-content>
          <v-layout>
            <v-flex xs12 style="height:100vh">
              <keep-alive>
                <router-view> </router-view>
              </keep-alive>
            </v-flex>
          </v-layout>
        </v-content>
  </v-app>
</template>

<script>
export default {
  name: 'app',
  components: {
    
  },
  data(){
    return{
      drawer:false,
      theme:true,
      catalog:{ 
              '主页':'/home',
              '关于':'/about',
              'blog':'/blog',
              '留言':'/msg'
      },
    }
  },
}
</script>
<style>
</style>

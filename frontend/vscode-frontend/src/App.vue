<template>
  <div id="app">
    <Header></Header>
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <Contents v-bind:vscodeList="vscodeList"></Contents>
    <MyFooter></MyFooter>
    <Sidebar></Sidebar>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Contents from './components/Contents.vue'
import MyFooter from './components/MyFooter.vue'
import Sidebar from './components/Sidebar.vue'

import axios from 'axios'

var backendUrl = "http://localhost:8100"

export default {
  name: 'App',
  components: {
    Header,
    Contents, 
    MyFooter,
    Sidebar,
  },
  data() {
    return {
      vscodeList: []
    }
  },
  created() {
    var path = "/vscode/"
    axios.get(backendUrl+path).then(
      res=>{
        this.vscodeList=res.data
      }
    )   
  },
  methods: {
    getVSCodeList(){
      var path = "/vscode/"
      axios.get(backendUrl+path).then(
        res=>{
          this.vscodeList=res.data
        }
      )
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

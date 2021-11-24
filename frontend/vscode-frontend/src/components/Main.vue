<template>
  <div id="Main">
    <Header></Header>
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <Contents v-bind:vscodeList="vscodeList"></Contents>
    <MyFooter></MyFooter>
    <Sidebar></Sidebar>
  </div>
</template>

<script>
import Header from './Header.vue'
import Contents from './Contents.vue'
import MyFooter from './MyFooter.vue'
import Sidebar from './Sidebar.vue'

import axios from 'axios'

var backendUrl = "http://localhost:8100"

export default {
  name: 'Main',
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

  },
}
</script>

<style>
#Main {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

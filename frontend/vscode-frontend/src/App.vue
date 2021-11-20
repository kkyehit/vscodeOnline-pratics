<template>
  <div id="app">
    <Header></Header>
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <Contents></Contents>
    <MyFooter></MyFooter>
    <Sidebar></Sidebar>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Contents from './components/Contents.vue'
import MyFooter from './components/Footer.vue'
import Sidebar from './components/Sidebar.vue'

import axios from 'axios'

var backendUrl = "localhost:8100"

var getVSCodeList = function(){
  var path = "/vscode/"
  axios.get(backendUrl+path).then(
    res=>console.log("res : "+res.data)
  )
}

var vscodeList = getVSCodeList()

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
      vscodeList: vscodeList
    }
  },
  methods: {
    getVSCodeList : getVSCodeList
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

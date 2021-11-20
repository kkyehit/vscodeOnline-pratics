<template>
  <div id="app">
    <Header></Header>
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <Contents></Contents>
    <Footer></Footer>
    <Sidebar></Sidebar>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Contents from './components/Contents.vue'
import Footer from './components/Footer.vue'
import Sidebar from './components/Sidebar.vue'

export default {
  name: 'App',
  components: {
    Header,
    Contents, 
    Footer,
    Sidebar,
  }
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

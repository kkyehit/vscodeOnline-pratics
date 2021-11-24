import Vue from "vue"
import VueRouter from 'vue-router'
import Login from './components/Login.vue'
import Main from './components/Main.vue'

Vue.use(VueRouter)

const route = [
    { path: '/', redirect: '/login'  },
    { path: '/login', component: Login },
    { path: '/main', component: Main }
]

const router = new VueRouter({
    mode: "history",
    routes: route 
})
export default router;
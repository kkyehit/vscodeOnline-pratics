<template>
    <div id="app">
        <h2>Login</h2>
        <input placeholder="Username" type="text" v-model="userId">
        <br>
        <input placeholder="Password" type="password" v-model="userPassword">
        <br>
        <button v-on:click="doLogin()">Sign in</button>
    </div>
</template>

<script>
import axios from 'axios'

var backendUrl = "http://localhost:8100"

export default {
    name: "app",
    data() {
        return {
            userId: null,
            userPassword: null,
        }
    },
    methods: {
        doLogin: function() {
            alert(this.userId)
            alert(this.userPassword)

            var path = "/login/login.do"
            axios.post(backendUrl+path).then(
                res=>{
                    if(res.status === 200) {
                        alert("로그인 성공");
                    }
                }
            )   
        }
    }
}
</script>

<style>

</style>
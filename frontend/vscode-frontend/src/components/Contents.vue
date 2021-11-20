<template>
    <div class="contents">
        <h2>{{ msg }}</h2>
        <li v-for="(vscode, index) in vscodeList" v-bind:key="index">
            <a>{{index}} - url : {{vscode.url}}</a>
        </li>
    </div>
</template>

<script>
export default {
  name: 'contents',
  props: ['vscodeList'],
  data() {
      return {
          msg: "This is contents"
      }
  },
}
</script>

<style>

</style>
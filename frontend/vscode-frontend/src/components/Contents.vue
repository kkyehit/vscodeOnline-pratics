<template>
    <div class="contents">
        <h2>{{ msg }}</h2>
    </div>
</template>

<script>
export default {
  name: 'contents',
//   props: {
//     msg: "This is Header"
//   }
  data() {
      return {
          msg: "This is contents"
      }
  },
}
</script>

<style>

</style>
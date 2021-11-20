<template>
    <div class="header">
        <h2>{{ msg }}</h2>
    </div>
</template>

<script>
export default {
  name: 'Header',
//   props: {
//     msg: "This is Header"
//   }
  data() {
      return {
          msg: "This is Header"
      }
  },
}
</script>

<style>

</style>
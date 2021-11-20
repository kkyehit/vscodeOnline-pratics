<template>
    <div class="sidebar">
        <h2>{{ msg }}</h2>
    </div>
</template>

<script>
export default {
  name: 'sidebar',
//   props: {
//     msg: "This is Header"
//   }
  data() {
      return {
          msg: "This is sidebar"
      }
  },
}
</script>

<style>

</style>
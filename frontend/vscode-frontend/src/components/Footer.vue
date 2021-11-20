<template>
    <div class="footer">
        <h2>{{ msg }}</h2>
    </div>
</template>

<script>
export default {
  name: 'footer',
//   props: {
//     msg: "This is Header"
//   }
  data() {
      return {
          msg: "This is footer"
      }
  },
}
</script>

<style>

</style>
<template id="view-dev">
  <layout>
    {{ $data.test }}
  </layout>
</template>
<script>
  app.vue.component("view-dev", {
    template: "#view-dev",
    data() {
      return {
        test: "test"
      }
    },
    mounted() {
      console.log(this.$javalin);
      app.events.listen(e => this.$data.test = JSON.stringify(e));
    }
  });
</script>
<template id="view-device-index">
  <layout>
    <h1> Device </h1>
  </layout>
</template>
<script>
  app.vue.component("view-device-index", {
    template: "#view-device-index",
    created() {
      console.log(this.$javalin.state);
    }
  });
</script>
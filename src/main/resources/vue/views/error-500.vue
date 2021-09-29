<template id="error-500">
  <growbox-main>
    <h1>
      500 Internal Server Error
    </h1>
    <p>An internal error occurred.</p>
  </growbox-main>
</template>
<script>
  app.component("error-500", {
    template: "#error-500",
    data: () => ({
      device_id: null
    }),
    created() {
      this.device_id = this.$javalin.pathParams["device_id"];
    }
  });
</script>
<style>

</style>
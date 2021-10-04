<template id="view-device-not-found">
  <layout>
    <h1>404 - Device #{{ id }} not found</h1>
    <p>No device with the id #{{ id }} exists.</p>
  </layout>
</template>
<script>
  app.vue.component("view-device-not-found", {
    template: "#view-device-not-found",
    data() {
      return {
        id: this.$javalin.pathParams["id"]
      }
    }
  });
</script>
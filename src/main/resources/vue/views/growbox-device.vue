<template id="growbox-device">
  <app-frame>
    <p>Test</p>
  </app-frame>
</template>
<script>
  app.component("growbox-device", {
    template: "#growbox-device",
    data: () => ({
      device: null
    }),
    created() {
      const deviceId = this.$javalin.pathParams["device_id"];
      fetch(`/api/device?device_id=${deviceId}`)
          .then(res => res.json())
          .then(device => this.device = device)
          .catch(error => console.log(error));
    }
  });
</script>
<style>

</style>
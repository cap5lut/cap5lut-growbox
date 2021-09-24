<template id="growbox-device">
  <growbox-main>
    <h1>
      <growbox-device-name :device="device"></growbox-device-name>
    </h1>
    <growbox-device-chart :id="device.device_id"></growbox-device-chart>
  </growbox-main>
</template>
<script>
  app.component("growbox-device", {
    template: "#growbox-device",
    data: () => ({
      device: null
    }),
    created() {
      this.device = this.$javalin.state["device"];
    }
    /*created() {
      const deviceId = this.$javalin.pathParams["device_id"];
      fetch(`/api/device?device_id=${deviceId}`)
          .then(res => res.json())
          .then(device => this.device = device)
          .catch(error => console.log(error));
    }*/
  });
</script>
<style>

</style>
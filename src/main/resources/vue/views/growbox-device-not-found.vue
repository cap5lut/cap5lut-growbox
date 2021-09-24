<template id="growbox-device-not-found">
  <growbox-main>
    <h1>
      <growbox-device-name :device="{device_id: device_id, name: null}"></growbox-device-name> not found!
    </h1>
    <p>The device #{{device_id}} does not exist.</p>
  </growbox-main>
</template>
<script>
  app.component("growbox-device-not-found", {
    template: "#growbox-device-not-found",
    data: () => ({
      device_id: null
    }),
    created() {
      this.device_id = this.$javalin.pathParams["device_id"];
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
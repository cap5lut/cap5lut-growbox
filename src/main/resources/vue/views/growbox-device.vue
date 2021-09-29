<template id="growbox-device">
  <growbox-main>
    <h1>
      <growbox-device-name :device="device"></growbox-device-name>
    </h1>
    <!--<growbox-device-data-chart :id="device.device_id"></growbox-device-data-chart>-->
    <growbox-device-data-chart2 :id="'data-chart-' + device.device_id" :device="device.device_id" :data="device_data"></growbox-device-data-chart2>
    <growbox-device-data-table :data="device_data"></growbox-device-data-table>
  </growbox-main>
</template>
<script>
  app.component("growbox-device", {
    template: "#growbox-device",
    data: () => ({
      device: null,
      device_data: null
    }),
    created() {
      this.device = this.$javalin.state["device"];
      const adcToPercentage = v => (1 - (v / 4096)) * 100;
      this.device_data = this.$javalin.state["device_data"].map(e => {
        e.timestamp = new Date(e.timestamp * 1000);
        e.light_level = adcToPercentage(e.light_level);
        e.moisture = adcToPercentage(e.moisture);
        return e;
      });
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
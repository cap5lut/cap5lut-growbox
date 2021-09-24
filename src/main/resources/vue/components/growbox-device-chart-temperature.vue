<template id="growbox-device-chart-temperature">
  <div v-bind:id="'growbox-device-chart-temperature-' + id"></div>
  <table>
    <thead>
      <tr>
        <th>Timestamp</th>
        <th>Light (%)</th>
        <th>Temp. (°C)</th>
        <th>Humidity (%)</th>
        <th>Moisture (%)</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="data in device_data">
        <td>{{new Date(data.timestamp * 1000).toISOString()}}</td>
        <td>{{data.light_level}}</td>
        <td>{{data.temperature}}</td>
        <td>{{data.humidity}}</td>
        <td>{{data.moisture}}</td>
      </tr>
    </tbody>
  </table>
</template>
<script>
  app.component("growbox-device-chart-temperature", {
    template: "#growbox-device-chart-temperature",
    props: ["id"],
    data: () => ({
      device_data: null,
      chartOptions: null
    }),
    created() {
      console.log("created");
      fetch("/api/device/data?since=0&device_id=" + this.$props.id)
        .then(res => res.json())
        .then(data => this.device_data = data)
        .then(data => {
          this.chart = new CanvasJS.Chart("growbox-device-chart-temperature-" + this.id, {
            title: {
              text: "Temperature"
            },
            data: [
              {
                type: "spline",
                dataPoints: data.map(e => ({x: new Date(e.timestamp * 1000), y: e.temperature}))
              }
            ]
          });
          this.chart.render();
        })
        .catch(error => console.log(error));
    }
  });
</script>
<template id="growbox-device-data-table">
  <table class="growbox-device-data-table">
    <thead>
      <th>Timestamp</th>
      <th>Light Level (%)</th>
      <th>Temperature (°C)</th>
      <th>Humidity (%)</th>
      <th>Moisture (%)</th>
    </thead>
    <tbody>
      <tr v-for="d in data">
        <td>a{{d.timestamp.toISOString()}}</td>
        <td>b{{d.light_level.toFixed(2)}}</td>
        <td>c{{d.temperature.toFixed(2)}}</td>
        <td>d{{d.humidity.toFixed(2)}}</td>
        <td>e{{d.moisture.toFixed(2)}}</td>
      </tr>
    </tbody>
  </table>
</template>
<script>
  app.component("growbox-device-data-table", {
    template: "#growbox-device-data-table",
    props: ["data"]
  });
</script>
<style>
.growbox-device-data-table td,.growbox-device-data-table th {
  border: 1px solid black;
}
</style>
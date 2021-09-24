<template id="growbox-device-chart">
  <div v-bind:id="'growbox-device-chart-' + id"></div>
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
  app.component("growbox-device-chart", {
    template: "#growbox-device-chart",
    props: ["id"],
    data: () => ({
      device_data: null,
      chartOptions: null
    }),
    created() {
      fetch("/api/device/data?since=0&device_id=" + this.$props.id)
        .then(res => res.json())
        .then(data => this.device_data = data)
        .then(data => {
          data = data.map(e => {
            e.timestamp = new Date(e.timestamp * 1000);
            return e;
          });
          this.chart = new CanvasJS.Chart("growbox-device-chart-" + this.id, {
            zoomEnabled: true,
            title: {
              text: "Device #" + this.$props.id,
            },
            axisY: [
              {
                title: "Light Level"
              },
              {
                title: "Temperature"
              },
            ],
            axisY2: [
              {
                title: "Humidity"
              },
              {
                title: "Moisture"
              }
            ],
            data: [
              {
                type: "spline",
                axisYIndex: 0,
                name: "Light Level",
                color: 'rgba(255, 255, 0, 0.75)',
                showInLegend: true,
                dataPoints: data.map(e => ({x: e.timestamp, y: e.light_level}))
              },
              {
                type: "spline",
                axisYIndex: 1,
                name: "Temperature (°C)",
                color: 'rgba(0, 0, 255, 0.75)',
                showInLegend: true,
                dataPoints: data.map(e => ({x: e.timestamp, y: e.temperature}))
              },
              {
                type: "spline",
                axisYIndex: 2,
                axisYType: "secondary",
                name: "Humidity (%)",
                color: 'rgba(10, 255, 189, 0.75)',
                showInLegend: true,
                dataPoints: data.map(e => ({x: e.timestamp, y: e.humidity}))
              },
              {
                type: "spline",
                axisYIndex: 3,
                axisYType: "secondary",
                name: "Moisture (%)",
                color: 'rgba(139, 69, 19, 0.75)',
                showInLegend: true,
                dataPoints: data.map(e => ({x: e.timestamp, y: e.moisture}))
              }
            ]
          });
          this.chart.render();
        })
        .catch(error => console.log(error));
    }
  });
</script>
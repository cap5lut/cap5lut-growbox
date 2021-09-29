<template id="growbox-device-data-chart">
  <div v-bind:id="'growbox-device-data-chart-' + id" style="height: 400px"></div>
</template>
<script>
  app.component("growbox-device-data-chart", {
    template: "#growbox-device-data-chart",
    props: ["id"],
    data: () => ({
      data: null,
      light_level: null,
      temperature: null,
      humidity: null,
      moisture: null
    }),
    mounted() {
      const adcToPercentage = v => (1 - (v / 4096)) * 100;
      this.data = this.$javalin.state["device_data"].map(e => {
        e.timestamp = new Date(e.timestamp * 1000);
        e.light_level = adcToPercentage(e.light_level);
        e.moisture = adcToPercentage(e.moisture);
        return e;
      });
      this.light_level = this.data.map(e => ({x: e.timestamp, y: e.light_level}));
      this.temperature = this.data.map(e => ({x: e.timestamp, y: e.temperature}));
      this.humidity = this.data.map(e => ({x: e.timestamp, y: e.humidity}));
      this.moisture = this.data.map(e => ({x: e.timestamp, y: e.moisture}));

      this.chart = new CanvasJS.Chart("growbox-device-data-chart-" + this.id, {
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
          }
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
            dataPoints: this.light_level
          },
          {
            type: "spline",
            axisYIndex: 1,
            name: "Temperature (°C)",
            color: 'rgba(0, 0, 255, 0.75)',
            showInLegend: true,
            dataPoints: this.temperature
          },
          {
            type: "spline",
            axisYIndex: 2,
            axisYType: "secondary",
            name: "Humidity (%)",
            color: 'rgba(10, 255, 189, 0.75)',
            showInLegend: true,
            dataPoints: this.humidity
          },
          {
            type: "spline",
            axisYIndex: 3,
            axisYType: "secondary",
            name: "Moisture (%)",
            color: 'rgba(139, 69, 19, 0.75)',
            showInLegend: true,
            dataPoints: this.moisture
          }
        ]
      });
      this.chart.render();
      /*const onResize = () => this.chart.container.style.height = this.chart.canvas.height + "px";
      window.addEventListener("resize", onResize);
      onResize();*/
    }
  });
</script>
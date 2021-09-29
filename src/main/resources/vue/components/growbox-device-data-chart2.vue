<template id="growbox-device-data-chart2">
  <canvas v-bind:id="id"></canvas>
</template>
<script>
  app.component("growbox-device-data-chart2", {
    template: "#growbox-device-data-chart2",
    props: ["id", "device", "data"],
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

      const f = (str) => {
        str = str + "";
        return "0".repeat(2 - str.length) + str;
      }

      let chart = new Chart(this.$props.id, {
        type: 'line',
        scales: {
          x: {
            type: 'time',
            time: {
              unit: 'minute'
            }
          },
          y: {
            min: 0,
            max: 100
          }
        },
        data: {
          labels: this.data
              .map(e => e.timestamp)
              .map(ts => `${f(ts.getMonth() + 1)}-${f(ts.getDate())} ${f(ts.getHours())}:${f(ts.getMinutes())}`),
          datasets: [
            {
              label: "Light",
              data: this.data.map(e => e.light_level),
              fill: false,
              borderColor: 'yellow',
              tension: 0.1
            },
            {
              label: "Temperature",
              data: this.data.map(e => e.temperature),
              fill: false,
              borderColor: 'aqua',
              tension: 0.1
            },
            {
              label: "Humidity",
              data: this.data.map(e => e.humidity).map(h => h !== 50 ? h : null),
              fill: false,
              borderColor: 'aquamarine',
              tension: 0.1
            },
            {
              label: "Moisture",
              data: this.data.map(e => e.moisture),
              fill: false,
              borderColor: 'blue',
              tension: 0.1
            }
          ]
        }
      });
    }
  });
</script>
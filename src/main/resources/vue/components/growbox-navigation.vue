<template id="growbox-navigation">
  <nav>
    <a href="/">Overview</a>
    <growbox-device-link v-for="device in devices" :device="device" />
  </nav>
</template>
<script>
  app.component("growbox-navigation", {
    template: "#growbox-navigation",
    data: () => ({
      devices: null
    }),
    created() {
      fetch("/api/device/list")
        .then(res => res.json())
        .then(devices => this.devices = devices)
        .catch(error => console.log(error));
    }
  });
</script>
<style>
nav {
  background-color: rgba(0,0,0,0.1);
}
</style>
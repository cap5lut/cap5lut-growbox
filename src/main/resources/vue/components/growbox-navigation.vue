<template id="growbox-navigation">
  <nav>
    <a href="/">Overview</a>
    <growbox-device-name v-for="device in devices" :id="{{device.id}}" :name="{{device.name}}" />
  </nav>
</template>
<script>
  app.component("growbox-navigation", {
    template: "#growbox-navigation",
    data: () => ({
      devices: [{device_id: "083AF2AD1A64", name: null}]
    }),
  });
</script>
<style>
nav {
  background-color: rgba(0,0,0,0.1);
}
</style>
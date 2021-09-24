<template id="growbox-device-name">
  <span class="device-title">Device</span>
  #<span class="device-id">{{device.device_id}}</span>
  <span class="device-name">{{device.name}}</span>
</template>
<script>
  app.component("growbox-device-name", {
    template: "#growbox-device-name",
    props: ["device"]
  });
</script>
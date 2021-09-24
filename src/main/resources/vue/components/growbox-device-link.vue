<template id="growbox-device-link">
  <a v-bind:href="'/device/' + device.device_id">
    <growbox-device-name :device="device" />
  </a>
</template>
<script>
  app.component("growbox-device-link", {
    template: "#growbox-device-link",
    props: ["device"]
  });
</script>
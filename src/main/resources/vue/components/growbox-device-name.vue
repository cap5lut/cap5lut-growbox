<template id="growbox-device-name">
  <span class="device-title">Device</span>
  <span v-if="id" class="device-id">{{id}}</span>
  <span v-if="name" class="device-name">{{name}}</span>
</template>
<script>
  app.component("growbox-device-name", {
    template: "#growbox-device-name",
    data: () => ({
      id: null,
      name: null
    })
  });
</script>
<style>
  span.device-id::before {
    content: '#';
  }
</style>
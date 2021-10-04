<template id="layout">
  <debug-panel></debug-panel>
  <nav>
    <a href="/">Overview</a>
    <a href="/dev">Development</a>
    <a v-bind:href="'/device/' + device.id" v-for="device in $data.devices">Device #{{device.id}}</a>
  </nav>
  <main>
    <slot></slot>
  </main>
</template>
<script>
  app.vue.component("layout", {
    template: "#layout",
    data: ()=>({
      devices: null
    }),
    created() {
      this.$data.devices = this.$javalin.state.devices;
    }
  });
</script>
<template id="debug-logs">
  <h1>Logs</h1>
  <ul class="console">
    <li v-if="$data.entries.length === 0">No logs available</li>
    <li v-for="entry in $data.entries">{{entry}}</li>
  </ul>
</template>
<script>
  app.vue.component("debug-logs", {
    template: "#debug-logs",
    data: () => ({
      entries: []
    }),
    created() {
      app.events.listen(e => this.$data.entries.unshift(JSON.stringify(e)));
    }
  });
</script>
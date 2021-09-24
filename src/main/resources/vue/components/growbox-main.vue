<template id="growbox-main">
  <growbox-navigation></growbox-navigation>
  <main>
    <slot></slot>
  </main>
  <footer>
    footer
  </footer>
</template>
<script>
  app.component("growbox-main", {template: "#growbox-main"});
</script>
<style>
</style>
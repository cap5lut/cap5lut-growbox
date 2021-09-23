<template id="app-frame">
    <growbox-navigation />
    <main>
      <h1>title</h1>
      <slot></slot>
    </main>
    <footer>
      footer
    </footer>
</template>
<script>
  app.component("app-frame", {template: "#app-frame"});
</script>
<style>
</style>
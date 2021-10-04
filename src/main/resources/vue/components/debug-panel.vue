<template id="debug-panel">
  <aside v-bind:class="'debug-panel' + ($data.show ? ' show' : '')">
    <button class="toggle" v-on:click="$data.show = !$data.show">
      <img src="/debug.svg" width="16" height="16" />
    </button>
    <article class="content">
      <h1>Debug Panel</h1>
      <device-form :device="$data.device"></device-form>
      <debug-logs></debug-logs>
    </article>
  </aside>
</template>
<script>
  app.vue.component("debug-panel", {
    template: "#debug-panel",
    data() {
      return state(this, "debug-panel", {
        show: false,
        device: {
          id: "device-id",
          name: "device-name"
        }
      })
    }
  });
</script>
<style>
  .debug-panel {
    position: absolute;
    top: 0;
    right: 0;
    background-color: white;
    border: none;
  }

  .debug-panel > button.toggle {
    float: right;
  }

  .debug-panel > .content {
    display: none;
  }

  .debug-panel.show {
    border: black 1px solid;
  }

  .debug-panel.show > .content {
    display: block;
  }
</style>
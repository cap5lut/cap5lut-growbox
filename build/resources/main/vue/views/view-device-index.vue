<template id="view-device-index">
  <layout>
    <h1> Device #{{$javalin.state.model.device.id}} {{$javalin.state.model.device.name}}</h1>
  </layout>
</template>
<script>
  app.vue.component("view-device-index", {
    template: "#view-device-index"
    created() {
      console.log(this.$javalin.state);
    }
  });
</script>
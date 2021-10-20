<template id="v-dev">
  {{ model }}
</template>
<script>
 app.vue.component("v-dev", {
   template: "#v-dev",
   data() {
     return {
       model: this.$javalin.state.model
     }
   }
 });
</script>
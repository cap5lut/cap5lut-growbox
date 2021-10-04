<template id="form-async">
  <form v-on:submit.prevent="submit">
    <slot></slot>
    <button type="submit">{{$props.button}}</button>
  </form>
</template>
<script>
  app.vue.component("form-async", {
    template: "#form-async",
    props: ["button"],
    methods: {
      submit: function(event) {
        console.log(event);
        const data = {}
        new FormData(event.target).forEach((value, key) => data[key] = value);
        console.log(data);
      }
    }
  });
</script>
<template id="device-form">
  <form v-on:submit.prevent="submit" action="/api/v1/device" method="put" :button="'create device'">
    <dl>
      <dt>Device ID:</dt>
      <dd>
        <input type="text" v-model.lazy="id" />
      </dd>
      <dt>Name:</dt>
      <dd>
        <input type="text" v-model.lazy="name" />
      </dd>
    </dl>
    <button type="submit">create device</button>
  </form>
</template>
<script>
  app.vue.component("device-form", {
    template: "#device-form",
    props: ["device"],
    data() {
      return {
        id: this.$props.device ? this.$props.device.id : '',
        name: this.$props.device ? this.$props.device.name : ''
      }
    },
    methods: {
      submit() {
        fetch(this.$el.action, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(this.$data)
        })
            .then(_ => console.log("done"))
            .catch(err => console.log(err));
      }
    }
  });
</script>
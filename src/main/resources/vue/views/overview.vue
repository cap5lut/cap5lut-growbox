<template id="overview">
  <article class="overview">
    <h1>Hello World</h1>
    <p>all devices</p>
  </article>
</template>
<script>
  Vue.component("overview", {template: "#overview"});
</script>
<style>
  article.overview {
    background-color: rgba(0, 0, 0, 0.1);
  }
</style>
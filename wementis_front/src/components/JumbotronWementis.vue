<template>
  <div class="jumbotron-wementis rounded-3 d-flex align-items-start m-auto">
    <img src="../assets/logo.png" alt="logo">
    <div>
        <h1 class="display-5 fw-bold text-primary">WeMentis</h1>
        <p class="fs-4">
          Tu herramienta de gestión de citas favorita para psicología.
        </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "JumbotronWementis"
}  
</script>

<style scoped>
.jumbotron-wementis {
  position: relative;
}
img {
  position: absolute;
  left: -4.5rem;
}
</style>
<template>
  <form class="browser-tarifas d-flex flex-wrap">
    <div class="input-group">
      <input type="text" class="form-control form-control-browser m-1" name="id" v-model="idTarifa" placeholder="ID">
      <input type="text" class="form-control form-control-browser m-1" name="nombre" v-model="nombre" placeholder="Nombre">
      <input type="text" class="form-control form-control-browser m-1" name="precioHora" v-model="precioHora" placeholder="Precio/h">

       <span class="input-group-text form-control-browser m-1">Fecha crea.</span>
      <input type="date" class="form-control form-control-browser m-1" name="fechaCrea" v-model="fechaCreacion">
    </div>

    <div class="input-group mt-3 d-flex justify-content-center gap-2">
      <button type="button" class="btn btn-browser" @click="resetFields">
        <i class="bi bi-arrow-clockwise me-1 text-muted"></i> <span class="fw-bold text-muted">Limpiar</span>
      </button>

      <button type="button" class="btn btn-browser" @click="buscarTarifas">
        <i class="bi bi-search me-1"></i> <span class="fw-bold">Buscar</span>
      </button>
    </div>
  </form>
</template>

<script>
export default {
  name: "BrowserTarifas",
  events: ["buscarTarifas"],
  data() {
    return {
      idTarifa: "",
      nombre: "",
      precioHora: "",
      fechaCreacion: ""
    }
  },
  methods: {
    buscarTarifas() {
      this.$emit("buscarTarifas", this.idTarifa, this.nombre, this.precioHora, this.fechaCreacion);
    },
    resetFields() {
      this.idTarifa = "",
      this.nombre = "";
      this.precioHora = "",
      this.fechaCreacion = "";
    }
  }
}
</script>

<style scoped>
.form-control:focus {
  transform: scale(1.2, 1.2);
  transition: 0.1s;
}

.btn-browser {
  background:#e7f1ff;
  color: rgba(0, 0, 0, 0.74);
}

.btn-browser:hover {
  color: #0d6efd;
}

.btn-browser:hover .text-muted {
  color: #0d6efd !important;
}

.form-control-browser {
  border: 2px solid rgba(0, 0, 0, 0.103);
  text-align: center;
  border-radius: 9px !important;
}
</style>
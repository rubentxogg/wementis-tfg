<template>
   <form class="form-new-paciente d-flex flex-wrap">
    <div class="input-group mb-3">
     <span class="input-group-text">Nombre</span>
     <input type="text" class="form-control" name="nombre" maxlength="25" placeholder="Ej. Rubén" v-model="nombre">
     <i v-if="isCorrectNombre" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Apellidos</span>
     <input type="text" class="form-control" name="apellidos" maxlength="25" placeholder="Ej. García" v-model="apellidos">
     <i v-if="isCorrectApellidos" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Teléfono</span>
     <input type="tel" class="form-control" name="telefono" maxlength="9" placeholder="9 Dígitos" v-model="telefono">
     <i v-if="isCorrectTelefono" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Email</span>
     <input type="email" class="form-control" name="email" v-model="email" placeholder="example@hotmail.com">
     <i v-if="isCorrectEmail" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Fecha nac.</span>
     <input type="date" class="form-control" name="fechaNac" v-model="fechaNacimiento">
     <i v-if="isCorrectFechaNacimiento" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3 m-1">
      <span class="input-group-text me-4">Género</span>
     
      <div class="form-check form-check-inline align-self-center">
        <input class="form-check-input" type="radio" name="inlineRadioOptions" id="masculino" value="M" v-model="genero">
        <label class="form-check-label" for="masculino">Masculino</label>
      </div>

      <div class="form-check form-check-inline align-self-center">
        <input class="form-check-input" type="radio" name="inlineRadioOptions" id="femenino" value="F" v-model="genero">
        <label class="form-check-label" for="femenino">Femenino</label>
      </div>

      <i v-if="isCorrectGenero" class="bi bi-check fs-3 text-success"></i>
      <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

     <div class="modal-footer w-100">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="resetForm">Cancelar</button>
        <button type="button" :class="isFormValidationCorrect" data-bs-dismiss="modal" @click="anadirPaciente">Guardar</button>
     </div>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  name: "FormNewPaciente",
  events: ["updateTabla"],
  data() {
    return {
      nombre: "",
      apellidos: "",
      telefono: "",
      email: "",
      fechaNacimiento: "",
      genero: "",
      regNombre: /^[\w|á|é|í|ó|ú]+[\s]?[\w|á|é|í|ó|ú]*[\s]?[\w|á|é|í|ó|ú]*$/, // Juan José
      regNumeros: /\d+/,
      regTelefono: /^[\d]{9}$/,
      regEmail: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
      regFechaNacimiento: /^[\d]{2}-[\d]{2}-[\d]{4}$/
    };
  },
  methods: {
    anadirPaciente() {
      const paciente = {
        nombre: this.nombre,
        apellidos: this.apellidos,
        telefono: this.telefono,
        email: this.email,
        fechaNacimiento: this.fechaNacimiento,
        genero: this.genero,
        activo: 1
      }
      
      axios
        .post("wementis/v1/pacientes/", paciente)
        .then(() => this.nombre = "", this.apellidos = "", this.telefono = "", this.email = "", this.fechaNacimiento = "", this.genero = "")
        .then(() => {
          this.$emit("updateTabla");
          this.successAlert();
        })
        .catch(() => this.errorAlert());
    },
    resetForm() {
      this.nombre = "",
      this.apellidos = "",
      this.email = "",
      this.telefono = ""
      this.fechaNacimiento = "",
      this.genero = ""
    },
    successAlert() {
      this.$toast.open({
      message: "Paciente añadido con éxito",
      type: "success",
      duration: 5000,
      dismissible: true,
      });
    },
    errorAlert() {
      this.$toast.open({
      message: "No se ha podido añadir el paciente",
      type: "error",
      duration: 5000,
      dismissible: true,
      });
    },
  },
  computed: {
    isCorrectNombre() {
      if(!this.regNombre.test(this.nombre) || this.regNumeros.test(this.nombre)) return false;
      return true;
    },
    isCorrectApellidos() {
      if(!this.regNombre.test(this.apellidos) || this.regNumeros.test(this.apellidos)) return false;
      return true;
    },
    isCorrectTelefono() {
      if(!this.regTelefono.test(this.telefono)) return false;
      return true;
    },
    isCorrectEmail() {
      if(!this.regEmail.test(this.email)) return false;
      return true;
    },
    isCorrectFechaNacimiento() {
      if(this.regFechaNacimiento.test(this.fechaNacimiento) || this.fechaNacimiento === "") return false;
      return true;
    },
    isCorrectGenero() {
      if(this.genero === "") return false;
      return true;
    },
    isFormValidationCorrect() {
      if(this.isCorrectNombre && this.isCorrectApellidos && this.isCorrectTelefono && this.isCorrectEmail
        && this.isCorrectFechaNacimiento && this.isCorrectGenero) return "btn btn-success";
      return "btn btn-outline-success disabled";
    }
  }
}
</script>

<style scoped>
div i{
  position: absolute;
  right: 0.5rem;
}

input[type="date"] + i {
  position: absolute;
  right: 2rem;
}
</style>
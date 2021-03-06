<template>
  <form class="form-update-cita d-flex flex-wrap">
    <div class="input-group mb-3">
     <dropdown-pacientes @seleccionPaciente="pacienteSeleccionado"/>
     <input type="text" class="form-control" name="paciente" :value="idYNombrePaciente" readonly :placeholder="pacientePlaceholder">
     <i v-if="isCorrectPaciente" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <dropdown-psicologos @seleccionPsicologo="psicologoSeleccionado"/>
     <input type="text" class="form-control" name="psicologo" :value="idYNombrePsicologo" readonly :placeholder="psicologoPlaceholder">
     <i v-if="isCorrectPsicologo" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <dropdown-tarifas @seleccionTarifa="tarifaSeleccionada"/>
     <input type="text" class="form-control" name="tarifa" :value="idYNombreTarifa" readonly :placeholder="tarifaPlaceholder">
     <i v-if="isCorrectTarifa" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Hora</span>
     <input :type="checkIsTimeOnFocus" class="form-control date" name="hora" :placeholder="getHourAndMins" v-model="hora" 
      @focus="isTimeOnFocus = true" @blur="isTimeOnFocus = false">
     <i v-if="isCorrectHora" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Fecha</span>
     <input :type="checkIsDateOnFocus" class="form-control date" name="fecha" :placeholder="cita.fecha"
      v-model="fecha" @focus="isDateOnFocus = true" @blur="isDateOnFocus = false">
     <i v-if="isCorrectFecha" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

    <div class="input-group mb-3">
     <span class="input-group-text">Cantidad/h</span>
     <input type="text" class="form-control" name="email" v-model="cantidadHoras" :placeholder="cita.cantidadHoras">
     <i v-if="isCorrectCantidadHoras" class="bi bi-check fs-3 text-success"></i>
     <i v-else class="bi bi-x fs-3 text-danger"></i>
    </div>

     <div class="modal-footer w-100">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="resetForm">Cancelar</button>
        <button type="button" :class="isFormValidationCorrect" data-bs-dismiss="modal" @click="actualizarCita">Guardar</button>
     </div>
  </form>
</template>

<script>
import DropdownPacientes from '@/components/citas/DropdownPacientes.vue';
import DropdownPsicologos from '@/components/citas/DropdownPsicologos.vue';
import DropdownTarifas from '@/components/citas/DropdownTarifas.vue';
import axios from 'axios';

export default {
  name: "FormUpdateCita",
  props: ["cita"],
  events: ["updateTabla"],
  components: {
      DropdownPacientes,
      DropdownPsicologos,
      DropdownTarifas
  },
  data() {
      return {
        paciente: "",
        psicologo: "",
        tarifa: "",
        fecha: "",
        hora: "",
        cantidadHoras: "",
        regFecha: /^[\d]{2,4}-[\d]{2}-[\d]{2,4}$/,
        regHora: /^[\d]{2}:[\d]{2}/,
        regNum: /^[\d]+$/,
        pacientePlaceholder: "",
        psicologoPlaceholder: "",
        tarifaPlaceholder: "",
        isDateOnFocus: false,
        isTimeOnFocus: false
      }
  },
  methods: {
    actualizarCita() {
      axios
        .put("wementis/v1/citas/", { 
          id: this.cita.id,
          fecha: this.fecha || this.cita.fecha,
          estado: this.cita.estado,
          paciente: this.paciente || this.cita.paciente,
          psicologo: this.psicologo || this.cita.psicologo,
          tarifa: this.tarifa || this.cita.tarifa,
          hora: this.hora || this.cita.hora,
          cantidadHoras: this.cantidadHoras || this.cita.cantidadHoras,
          activo: 1})
        .then(() => {
          this.$emit("updateTabla");
          this.successAlert();
        })
        .catch(() => this.errorAlert());
    },
    pacienteSeleccionado(paciente) {
      this.paciente = paciente;
    },
    psicologoSeleccionado(psicologo) {
      this.psicologo = psicologo;
    },
    tarifaSeleccionada(tarifa) {
      this.tarifa = tarifa;
    },
    resetForm() {
      this.paciente = "",
      this.psicologo = "",
      this.tarifa = "",
      this.fecha = "",
      this.hora = "",
      this.cantidadHoras = ""
    },
    successAlert() {
      this.$toast.open({
      message: "Cita actualizada con éxito",
      type: "success",
      duration: 5000,
      dismissible: true,
      });
    },
    errorAlert() {
      this.$toast.open({
      message: "No se ha podido actualizar la cita",
      type: "error",
      duration: 5000,
      dismissible: true,
      });
    },
  },
  computed: {
    idYNombrePaciente() {
      if(this.paciente !== "") return `${this.paciente.id || this.paciente.idPaciente} - ${this.paciente.nombre}`;
      return "";
    },
    idYNombrePsicologo() {
      if(this.psicologo !== "") return `${this.psicologo.id || this.psicologo.idPsicologo} - ${this.psicologo.nombre}`;
      return ""; 
    },
    idYNombreTarifa() {
      if(this.tarifa !== "") return `${this.tarifa.id || this.tarifa.idTarifa} - ${this.tarifa.nombre}`;
      return ""; 
    },
    isCorrectPaciente() {
      return true;
    },
    isCorrectPsicologo() {
      return true;
    },
    isCorrectTarifa() {
      return true;
    },
    isCorrectHora() {
      if(this.regHora.test(this.hora) || this.hora === "") return true;
      return false;
    },
    isCorrectFecha() {
      if(this.regFecha.test(this.fecha) || this.fecha === "") return true;
      return false;
    },
    isCorrectCantidadHoras() {
      if(this.regNum.test(this.cantidadHoras) || this.cantidadHoras === "") return true;
      return false;
    },
    isFormValidationCorrect() {
      if(this.isCorrectPaciente && this.isCorrectPsicologo && this.isCorrectTarifa
        && this.isCorrectHora && this.isCorrectFecha && this.isCorrectCantidadHoras) return "btn btn-success";
      return "btn btn-outline-success disabled";
    },
    checkIsDateOnFocus() {
      if(this.isDateOnFocus) return "date";
      return "text";
    },
    checkIsTimeOnFocus() {
      if(this.isTimeOnFocus) return "time";
      return "text";
    },
    getHourAndMins() {
      if(this.cita.hora) return this.cita.hora.substring(0,5);
      return this.cita.hora;
    }
  },
  updated() {
    this.pacientePlaceholder = `${this.cita.paciente.idPaciente || this.cita.paciente.id} - ${this.cita.paciente.nombre}`;
    this.psicologoPlaceholder = `${this.cita.psicologo.idPsicologo || this.cita.psicologo.id} - ${this.cita.psicologo.nombre}`;
    this.tarifaPlaceholder = `${this.cita.tarifa.idTarifa || this.cita.tarifa.id} - ${this.cita.tarifa.nombre}`;
  }
}
</script>

<style scoped>
div i{
  position: absolute;
  right: 0.5rem;
}

input[type="date"] + i,
input[type="time"] + i {
  position: absolute;
  right: 2rem;
}

.date {
  cursor: pointer;
}
</style>
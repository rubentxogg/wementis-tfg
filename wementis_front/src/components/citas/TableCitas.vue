<template>
  <table class="table table-citas bg-primary align-middle">
    <thead>
      <tr class="text-light">
        <th>ID</th>
        <th>Paciente</th>
        <th>Psicólogo</th>
        <th>Tarifa</th>
        <th>Fecha</th>
        <th>Hora</th>
        <th>Duración/h</th>
        <th>Estado</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="cita in citas" :key="cita.id" class="table-light">
        <th>{{ cita.id }}</th>
        <td>{{ cita.paciente.idPaciente || cita.paciente.id }}-{{ cita.paciente.nombre }}</td>
        <td>{{ cita.psicologo.idPsicologo || cita.psicologo.id}}-{{ cita.psicologo.nombre }}</td>
        <td>{{ cita.tarifa.nombre }}</td>
        <td>{{ cita.fecha }}</td>
        <td>{{ cita.hora.substring(0,5) }}</td>
        <td>{{ cita.cantidadHoras }}</td>
        <td class="text-start">
          <span :class="circuloEstado(cita.estado.idEstado)"></span> {{ capitalizeFirstLetter(cita.estado.nombre) }}
        </td>
        <td v-if="(cita.estado.idEstado || cita.estado.id) === 1">
          <modal-completar-cita @completacionCita="obtenerIdCita(cita.id)" @updateTabla="updateTabla" :id="id"/>
        </td>

        <td v-if="(cita.estado.idEstado || cita.estado.id) === 1">
          <modal-update-cita @updateCita="obtenerCita(cita)" :cita="citaToUpdate" @updateTabla="updateTabla"/>
        </td>

        <td v-if="(cita.estado.idEstado || cita.estado.id) === 1">
          <modal-cancelar-cita @cancelacionCita="obtenerIdCita(cita.id)" @updateTabla="updateTabla" :id="id" />
        </td>

        <td colspan="3" class="text-end" v-if="(cita.estado.idEstado || cita.estado.id) === 3">
          <modal-delete-cita @borradoCita="obtenerIdCita(cita.id)" @updateTabla="updateTabla" :id="id"/>
        </td>

        <td colspan="3" class="text-end" v-if="(cita.estado.idEstado || cita.estado.id) === 2">
          <modal-delete-cita @borradoCita="obtenerIdCita(cita.id)" @updateTabla="updateTabla" :id="id"/>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import ModalCompletarCita from '@/components/citas/ModalCompletarCita.vue';
import ModalCancelarCita from '@/components/citas/ModalCancelarCita.vue';
import ModalUpdateCita from '@/components/citas/ModalUpdateCita.vue';
import ModalDeleteCita from '@/components/citas/ModalDeleteCita.vue';

export default {
  name: "TableCitas",
  props: ["citas"],
  events: ["updateTabla"],
  components: {
    ModalCompletarCita,
    ModalCancelarCita,
    ModalUpdateCita,
    ModalDeleteCita
  },
  data() {
    return {
      id: "",
      citaToUpdate: ""
    }
  },
  methods: {
    obtenerIdCita(id) {
      this.id = id;
    },
    obtenerCita(cita) {
      this.citaToUpdate = cita;
    },
    updateTabla() {
      this.$emit("updateTabla");
    },
    circuloEstado(estadoId) {
      if(estadoId === 1) return "d-inline-block bg-warning rounded-circle me-1";
      if(estadoId === 2) return "d-inline-block bg-success rounded-circle me-1";
      if(estadoId === 3) return "d-inline-block bg-danger rounded-circle me-1";
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    }
  },
};
</script>

<style scoped>
span {
  width: .5em; 
  height: .5em;
}
</style>

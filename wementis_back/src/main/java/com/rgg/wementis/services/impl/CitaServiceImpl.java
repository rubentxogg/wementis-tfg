package com.rgg.wementis.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rgg.wementis.entities.CitaEntity;
import com.rgg.wementis.entities.EstadoEntity;
import com.rgg.wementis.entities.GananciaEntity;
import com.rgg.wementis.repositories.CitaRepository;
import com.rgg.wementis.repositories.EstadoRepository;
import com.rgg.wementis.repositories.GananciaRepository;
import com.rgg.wementis.services.interfaces.ICitaService;
import com.rgg.wementis.utils.Utils;

@Service
public class CitaServiceImpl implements ICitaService{

	@Autowired
	private CitaRepository citaRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private GananciaRepository gananciaRepository;
	
	@Override
	public Iterable<CitaEntity> getCitas() {
		return citaRepository.findByActivo(1);
	}

	@Override
	public Optional<CitaEntity> getCitaPorId(Integer id) {
		return citaRepository.findById(id);
	}

	@Override
	public List<CitaEntity> getCitasPorCampos(String id, String nombrePaciente, String nombrePsicologo, String nombreTarifa,
			String idEstado, String fecha, String hora, Integer cantidadHoras) {
		return citaRepository.getCitasPorCampos(id, nombrePaciente, nombrePsicologo, nombreTarifa, idEstado, fecha, hora, cantidadHoras);
	}

	@Override
	public void actualizarCita(CitaEntity cita) {
		citaRepository.save(cita);
	}

	@Override
	public void insertarCita(CitaEntity cita) {
		EstadoEntity estadoActiva;
		
		if (estadoRepository.findById(1).isPresent()) {
			estadoActiva = estadoRepository.findById(1).get();
			cita.setEstado(estadoActiva);
			citaRepository.save(cita);
		}
	}

	@Override
	public void cancelarCita(Integer id) {
		EstadoEntity estadoCancelada;
		CitaEntity cita = citaRepository.findById(id).get();
		
		if (estadoRepository.findById(3).isPresent()) {
			estadoCancelada = estadoRepository.findById(3).get();
			cita.setEstado(estadoCancelada);
			citaRepository.save(cita);
		}
	}

	@Override
	public void completarCita(Integer id) {
		EstadoEntity estadoCompletada;
		GananciaEntity ganancia;
		CitaEntity cita = citaRepository.findById(id).get();
		Double cantidad;
		
		if (estadoRepository.findById(2).isPresent()) {
			estadoCompletada = estadoRepository.findById(2).get();
			cita.setEstado(estadoCompletada);
			cantidad = cita.getCantidadHoras() * cita.getTarifa().getPrecioHora();
			ganancia = new GananciaEntity(cantidad, Utils.currentDate(), cita);	
			gananciaRepository.save(ganancia);
			citaRepository.save(cita);
		}
	}

	@Override
	public void borrarCita(Integer id) {
		CitaEntity cita = citaRepository.findById(id).get();
		
		if(cita.getEstado().getIdEstado() == 2 || cita.getEstado().getIdEstado() == 3) {
			cita.setActivo(0);
			citaRepository.save(cita);
		}
	}

	@Override
	public List<CitaEntity> getCitasPorFechaYEstado(String fecha, String idEstado) {
		return citaRepository.getCitasPorFechaYEstado(fecha, idEstado);
	}
}
package com.rgg.wementis.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.rgg.wementis.beans.Paciente;
import com.rgg.wementis.entities.PacienteEntity;

public interface IPacienteDAO {

	public Iterable<PacienteEntity> getPacientes();

	public Optional<PacienteEntity> getPacientePorId(Integer id);

	public List<Paciente> getPacientesPorCampos(String id, String nombre, String apellidos, String email,
			String telefono, String fechaNacimiento, String genero, String fechaCreacion);

}

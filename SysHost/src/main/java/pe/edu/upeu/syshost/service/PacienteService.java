package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Paciente;

import java.util.List;

public interface PacienteService {
    Paciente registrarPaciente(Paciente paciente);
    List<Paciente> buscarPacientesPorNombre(String nombre);
}
package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> getAllPacientes();
    Optional<Paciente> getPacienteById(Long id);
    Paciente savePaciente(Paciente paciente);
    Paciente updatePaciente(Paciente paciente);
    void deletePaciente(Long id);
    List<Paciente> buscarPacientesPorNombre(String nombre);
    List<Paciente> getPacientesActivos();
    long contarPacientes();
}
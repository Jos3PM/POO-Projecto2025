package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Cita;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    List<Cita> getAllCitas();
    Optional<Cita> getCitaById(Long id);
    Cita saveCita(Cita cita);
    Cita updateCita(Cita cita);
    void deleteCita(Long id);
    List<Cita> getCitasByPaciente(Long pacienteId);
    List<Cita> getCitasByMedico(Long medicoId);
    List<Cita> getCitasByEstado(String estado);
    List<Cita> getCitasProgramadas();
    List<Cita> getCitasEnRango(LocalDateTime inicio, LocalDateTime fin);
    long contarCitasProgramadas();
    long contarCitasHoy();
}
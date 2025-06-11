package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.HistorialMedico;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistorialMedicoService {
    List<HistorialMedico> getAllHistoriales();
    Optional<HistorialMedico> getHistorialById(Long id);
    HistorialMedico saveHistorial(HistorialMedico historial);
    HistorialMedico updateHistorial(HistorialMedico historial);
    void deleteHistorial(Long id);
    List<HistorialMedico> getHistorialesByPaciente(Long pacienteId);
    List<HistorialMedico> getHistorialesByMedico(Long medicoId);
    List<HistorialMedico> getHistorialesByCita(Long citaId);
    List<HistorialMedico> getHistorialesEnRango(LocalDateTime inicio, LocalDateTime fin);
    List<HistorialMedico> getHistorialesPacienteEnRango(Long pacienteId, LocalDateTime inicio, LocalDateTime fin);
    long contarConsultasHoy();
    long contarConsultasMedicoHoy(Long medicoId);
}
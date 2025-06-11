package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.HistorialMedico;
import pe.edu.upeu.syshost.repository.HistorialMedicoRepository;
import pe.edu.upeu.syshost.service.HistorialMedicoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HistorialMedicoServiceImpl implements HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getAllHistoriales() {
        return historialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HistorialMedico> getHistorialById(Long id) {
        return historialRepository.findById(id);
    }

    @Override
    public HistorialMedico saveHistorial(HistorialMedico historial) {
        return historialRepository.save(historial);
    }

    @Override
    public HistorialMedico updateHistorial(HistorialMedico historial) {
        Optional<HistorialMedico> existingHistorial = historialRepository.findById(historial.getId());
        if (existingHistorial.isEmpty()) {
            throw new RuntimeException("Historial médico no encontrado con ID: " + historial.getId());
        }
        return historialRepository.save(historial);
    }

    @Override
    public void deleteHistorial(Long id) {
        if (!historialRepository.existsById(id)) {
            throw new RuntimeException("Historial médico no encontrado con ID: " + id);
        }
        historialRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getHistorialesByPaciente(Long pacienteId) {
        return historialRepository.findByPacienteIdOrderByFechaConsultaDesc(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getHistorialesByMedico(Long medicoId) {
        return historialRepository.findByMedicoIdOrderByFechaConsultaDesc(medicoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getHistorialesByCita(Long citaId) {
        return historialRepository.findByCitaId(citaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getHistorialesEnRango(LocalDateTime inicio, LocalDateTime fin) {
        return historialRepository.findByFechaConsultaBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialMedico> getHistorialesPacienteEnRango(Long pacienteId, LocalDateTime inicio, LocalDateTime fin) {
        return historialRepository.findByPacienteAndFechaBetween(pacienteId, inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarConsultasHoy() {
        return historialRepository.countConsultasHoy();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarConsultasMedicoHoy(Long medicoId) {
        return historialRepository.countConsultasMedicoHoy(medicoId);
    }
}
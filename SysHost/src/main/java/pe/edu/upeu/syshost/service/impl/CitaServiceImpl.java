package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.Cita;
import pe.edu.upeu.syshost.repository.CitaRepository;
import pe.edu.upeu.syshost.service.CitaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> getCitaById(Long id) {
        return citaRepository.findById(id);
    }

    @Override
    public Cita saveCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public Cita updateCita(Cita cita) {
        Optional<Cita> existingCita = citaRepository.findById(cita.getId());
        if (existingCita.isEmpty()) {
            throw new RuntimeException("Cita no encontrada con ID: " + cita.getId());
        }
        return citaRepository.save(cita);
    }

    @Override
    public void deleteCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasByPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasByMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasByEstado(String estado) {
        return citaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasProgramadas() {
        return citaRepository.findCitasProgramadas(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasEnRango(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaHoraBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarCitasProgramadas() {
        return citaRepository.countCitasProgramadas();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarCitasHoy() {
        return citaRepository.countCitasHoy();
    }
}
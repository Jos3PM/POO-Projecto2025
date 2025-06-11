package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.Horario;
import pe.edu.upeu.syshost.repository.HorarioRepository;
import pe.edu.upeu.syshost.service.HorarioService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getAllHorarios() {
        return horarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> getHorarioById(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    public Horario saveHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Override
    public Horario updateHorario(Horario horario) {
        Optional<Horario> existingHorario = horarioRepository.findById(horario.getId());
        if (existingHorario.isEmpty()) {
            throw new RuntimeException("Horario no encontrado con ID: " + horario.getId());
        }
        return horarioRepository.save(horario);
    }

    @Override
    public void deleteHorario(Long id) {
        if (!horarioRepository.existsById(id)) {
            throw new RuntimeException("Horario no encontrado con ID: " + id);
        }
        horarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getHorariosByMedico(Long medicoId) {
        return horarioRepository.findByMedicoIdAndActivoTrue(medicoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getHorariosByDia(String diaSemana) {
        return horarioRepository.findByDiaSemanaAndActivoTrue(diaSemana);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getHorariosMedicoEnDia(Long medicoId, String diaSemana) {
        return horarioRepository.findByMedicoAndDia(medicoId, diaSemana);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeHorarioMedicoEnDia(Long medicoId, String diaSemana) {
        return horarioRepository.existsByMedicoIdAndDiaSemanaAndActivoTrue(medicoId, diaSemana);
    }
}
package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.Medico;
import pe.edu.upeu.syshost.repository.MedicoRepository;
import pe.edu.upeu.syshost.service.MedicoService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> getMedicoById(Long id) {
        return medicoRepository.findById(id);
    }

    @Override
    public Medico saveMedico(Medico medico) {
        // Validar DNI único
        if (medico.getId() == null && medicoRepository.existsByDni(medico.getDni())) {
            throw new RuntimeException("Ya existe un médico con el DNI: " + medico.getDni());
        }
        
        // Validar CMP único
        if (medico.getId() == null && medicoRepository.existsByCmp(medico.getCmp())) {
            throw new RuntimeException("Ya existe un médico con el CMP: " + medico.getCmp());
        }
        
        return medicoRepository.save(medico);
    }

    @Override
    public Medico updateMedico(Medico medico) {
        Optional<Medico> existingMedico = medicoRepository.findById(medico.getId());
        if (existingMedico.isEmpty()) {
            throw new RuntimeException("Médico no encontrado con ID: " + medico.getId());
        }
        
        // Validar DNI único (excluyendo el médico actual)
        Optional<Medico> medicoConMismoDni = medicoRepository.findByDni(medico.getDni());
        if (medicoConMismoDni.isPresent() && !medicoConMismoDni.get().getId().equals(medico.getId())) {
            throw new RuntimeException("Ya existe otro médico con el DNI: " + medico.getDni());
        }
        
        // Validar CMP único (excluyendo el médico actual)
        Optional<Medico> medicoConMismoCmp = medicoRepository.findByCmp(medico.getCmp());
        if (medicoConMismoCmp.isPresent() && !medicoConMismoCmp.get().getId().equals(medico.getId())) {
            throw new RuntimeException("Ya existe otro médico con el CMP: " + medico.getCmp());
        }
        
        return medicoRepository.save(medico);
    }

    @Override
    public void deleteMedico(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new RuntimeException("Médico no encontrado con ID: " + id);
        }
        medicoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> getMedicosActivos() {
        return medicoRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarMedicosPorEspecialidad(String especialidad) {
        return medicoRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getEspecialidades() {
        return medicoRepository.findDistinctEspecialidades();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarMedicos() {
        return medicoRepository.count();
    }
}
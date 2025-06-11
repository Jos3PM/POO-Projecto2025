package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.Especialidad;
import pe.edu.upeu.syshost.repository.EspecialidadRepository;
import pe.edu.upeu.syshost.service.EspecialidadService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EspecialidadServiceImpl implements EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Especialidad> getEspecialidadById(Long id) {
        return especialidadRepository.findById(id);
    }

    @Override
    public Especialidad saveEspecialidad(Especialidad especialidad) {
        if (especialidad.getId() == null && especialidadRepository.existsByNombre(especialidad.getNombre())) {
            throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidad.getNombre());
        }
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad updateEspecialidad(Especialidad especialidad) {
        Optional<Especialidad> existingEspecialidad = especialidadRepository.findById(especialidad.getId());
        if (existingEspecialidad.isEmpty()) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + especialidad.getId());
        }
        
        Optional<Especialidad> especialidadConMismoNombre = especialidadRepository.findByNombre(especialidad.getNombre());
        if (especialidadConMismoNombre.isPresent() && !especialidadConMismoNombre.get().getId().equals(especialidad.getId())) {
            throw new RuntimeException("Ya existe otra especialidad con el nombre: " + especialidad.getNombre());
        }
        
        return especialidadRepository.save(especialidad);
    }

    @Override
    public void deleteEspecialidad(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especialidad> getEspecialidadesActivas() {
        return especialidadRepository.findByActivaTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especialidad> buscarEspecialidadesPorNombre(String nombre) {
        return especialidadRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarEspecialidades() {
        return especialidadRepository.count();
    }
}
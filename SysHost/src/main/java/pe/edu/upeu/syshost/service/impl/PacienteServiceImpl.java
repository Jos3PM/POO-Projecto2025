package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.syshost.entity.Paciente;
import pe.edu.upeu.syshost.repository.PacienteRepository;
import pe.edu.upeu.syshost.service.PacienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> getPacienteById(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente savePaciente(Paciente paciente) {
        // Calcular edad automáticamente
        if (paciente.getFechaNacimiento() != null) {
            int edad = LocalDate.now().getYear() - paciente.getFechaNacimiento().getYear();
            paciente.setEdad(edad);
        }
        
        // Validar DNI único
        if (paciente.getId() == null && pacienteRepository.existsByDni(paciente.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + paciente.getDni());
        }
        
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente updatePaciente(Paciente paciente) {
        Optional<Paciente> existingPaciente = pacienteRepository.findById(paciente.getId());
        if (existingPaciente.isEmpty()) {
            throw new RuntimeException("Paciente no encontrado con ID: " + paciente.getId());
        }
        
        // Validar DNI único (excluyendo el paciente actual)
        Optional<Paciente> pacienteConMismoDni = pacienteRepository.findByDni(paciente.getDni());
        if (pacienteConMismoDni.isPresent() && !pacienteConMismoDni.get().getId().equals(paciente.getId())) {
            throw new RuntimeException("Ya existe otro paciente con el DNI: " + paciente.getDni());
        }
        
        // Calcular edad automáticamente
        if (paciente.getFechaNacimiento() != null) {
            int edad = LocalDate.now().getYear() - paciente.getFechaNacimiento().getYear();
            paciente.setEdad(edad);
        }
        
        return pacienteRepository.save(paciente);
    }

    @Override
    public void deletePaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPacientesPorNombre(String nombre) {
        return pacienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> getPacientesActivos() {
        return pacienteRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPacientes() {
        return pacienteRepository.count();
    }
}
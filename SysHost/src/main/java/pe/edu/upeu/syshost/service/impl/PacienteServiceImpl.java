package pe.edu.upeu.syshost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.syshost.entity.Paciente;
import pe.edu.upeu.syshost.repository.PacienteRepository;
import pe.edu.upeu.syshost.service.PacienteService;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente registrarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> buscarPacientesPorNombre(String nombre) {
        return pacienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
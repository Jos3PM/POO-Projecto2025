package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Paciente;
import pe.edu.upeu.syshost.repository.PacienteRepository;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<Paciente> savePaciente(@RequestBody Paciente paciente) {
        Paciente savedPaciente = pacienteRepository.save(paciente);
        return ResponseEntity.ok(savedPaciente);
    }
}
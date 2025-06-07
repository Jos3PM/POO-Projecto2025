package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Paciente;
import pe.edu.upeu.syshost.service.PacienteService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Paciente>>> getAllPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.getAllPacientes();
            return ResponseEntity.ok(new ApiResponse<>(true, "Pacientes obtenidos exitosamente", pacientes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener pacientes: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Paciente>> getPacienteById(@PathVariable Long id) {
        try {
            Optional<Paciente> paciente = pacienteService.getPacienteById(id);
            if (paciente.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Paciente encontrado", paciente.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Paciente no encontrado", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar paciente: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Paciente>> savePaciente(@Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            Paciente savedPaciente = pacienteService.savePaciente(paciente);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Paciente guardado exitosamente", savedPaciente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar paciente: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Paciente>> updatePaciente(@PathVariable Long id, @Valid @RequestBody Paciente paciente, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            paciente.setId(id);
            Paciente updatedPaciente = pacienteService.updatePaciente(paciente);
            return ResponseEntity.ok(new ApiResponse<>(true, "Paciente actualizado exitosamente", updatedPaciente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar paciente: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePaciente(@PathVariable Long id) {
        try {
            pacienteService.deletePaciente(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Paciente eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar paciente: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Paciente>>> buscarPacientesPorNombre(@RequestParam String nombre) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPacientesPorNombre(nombre);
            return ResponseEntity.ok(new ApiResponse<>(true, "Búsqueda completada", pacientes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error en la búsqueda: " + e.getMessage(), null));
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Paciente>>> getPacientesActivos() {
        try {
            List<Paciente> pacientes = pacienteService.getPacientesActivos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Pacientes activos obtenidos", pacientes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener pacientes activos: " + e.getMessage(), null));
        }
    }
}
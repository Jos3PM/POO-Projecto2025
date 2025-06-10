package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Medico;
import pe.edu.upeu.syshost.service.MedicoService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Medico>>> getAllMedicos() {
        try {
            List<Medico> medicos = medicoService.getAllMedicos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Médicos obtenidos exitosamente", medicos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener médicos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Medico>> getMedicoById(@PathVariable Long id) {
        try {
            Optional<Medico> medico = medicoService.getMedicoById(id);
            if (medico.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Médico encontrado", medico.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Médico no encontrado", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar médico: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Medico>> saveMedico(@Valid @RequestBody Medico medico, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            Medico savedMedico = medicoService.saveMedico(medico);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Médico guardado exitosamente", savedMedico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar médico: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Medico>> updateMedico(@PathVariable Long id, @Valid @RequestBody Medico medico, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            medico.setId(id);
            Medico updatedMedico = medicoService.updateMedico(medico);
            return ResponseEntity.ok(new ApiResponse<>(true, "Médico actualizado exitosamente", updatedMedico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar médico: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMedico(@PathVariable Long id) {
        try {
            medicoService.deleteMedico(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Médico eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar médico: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Medico>>> buscarMedicosPorNombre(@RequestParam String nombre) {
        try {
            List<Medico> medicos = medicoService.buscarMedicosPorNombre(nombre);
            return ResponseEntity.ok(new ApiResponse<>(true, "Búsqueda completada", medicos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error en la búsqueda: " + e.getMessage(), null));
        }
    }

    @GetMapping("/especialidad")
    public ResponseEntity<ApiResponse<List<Medico>>> buscarMedicosPorEspecialidad(@RequestParam String especialidad) {
        try {
            List<Medico> medicos = medicoService.buscarMedicosPorEspecialidad(especialidad);
            return ResponseEntity.ok(new ApiResponse<>(true, "Médicos por especialidad obtenidos", medicos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar por especialidad: " + e.getMessage(), null));
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Medico>>> getMedicosActivos() {
        try {
            List<Medico> medicos = medicoService.getMedicosActivos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Médicos activos obtenidos", medicos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener médicos activos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/especialidades")
    public ResponseEntity<ApiResponse<List<String>>> getEspecialidades() {
        try {
            List<String> especialidades = medicoService.getEspecialidades();
            return ResponseEntity.ok(new ApiResponse<>(true, "Especialidades obtenidas", especialidades));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener especialidades: " + e.getMessage(), null));
        }
    }
}
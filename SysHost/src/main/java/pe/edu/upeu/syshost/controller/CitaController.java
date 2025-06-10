package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Cita;
import pe.edu.upeu.syshost.service.CitaService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cita>>> getAllCitas() {
        try {
            List<Cita> citas = citaService.getAllCitas();
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas obtenidas exitosamente", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener citas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cita>> getCitaById(@PathVariable Long id) {
        try {
            Optional<Cita> cita = citaService.getCitaById(id);
            if (cita.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Cita encontrada", cita.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Cita no encontrada", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar cita: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cita>> saveCita(@Valid @RequestBody Cita cita, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            Cita savedCita = citaService.saveCita(cita);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cita guardada exitosamente", savedCita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar cita: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cita>> updateCita(@PathVariable Long id, @Valid @RequestBody Cita cita, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            cita.setId(id);
            Cita updatedCita = citaService.updateCita(cita);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita actualizada exitosamente", updatedCita));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar cita: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCita(@PathVariable Long id) {
        try {
            citaService.deleteCita(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cita eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar cita: " + e.getMessage(), null));
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ApiResponse<List<Cita>>> getCitasByPaciente(@PathVariable Long pacienteId) {
        try {
            List<Cita> citas = citaService.getCitasByPaciente(pacienteId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas del paciente obtenidas", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener citas del paciente: " + e.getMessage(), null));
        }
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<ApiResponse<List<Cita>>> getCitasByMedico(@PathVariable Long medicoId) {
        try {
            List<Cita> citas = citaService.getCitasByMedico(medicoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas del médico obtenidas", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener citas del médico: " + e.getMessage(), null));
        }
    }

    @GetMapping("/programadas")
    public ResponseEntity<ApiResponse<List<Cita>>> getCitasProgramadas() {
        try {
            List<Cita> citas = citaService.getCitasProgramadas();
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas programadas obtenidas", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener citas programadas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<Cita>>> getCitasByEstado(@PathVariable String estado) {
        try {
            List<Cita> citas = citaService.getCitasByEstado(estado);
            return ResponseEntity.ok(new ApiResponse<>(true, "Citas por estado obtenidas", citas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener citas por estado: " + e.getMessage(), null));
        }
    }
}
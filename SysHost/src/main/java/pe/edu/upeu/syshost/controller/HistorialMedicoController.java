package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.HistorialMedico;
import pe.edu.upeu.syshost.service.HistorialMedicoService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historiales")
@CrossOrigin(origins = "*")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HistorialMedico>>> getAllHistoriales() {
        try {
            List<HistorialMedico> historiales = historialService.getAllHistoriales();
            return ResponseEntity.ok(new ApiResponse<>(true, "Historiales obtenidos exitosamente", historiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener historiales: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HistorialMedico>> getHistorialById(@PathVariable Long id) {
        try {
            Optional<HistorialMedico> historial = historialService.getHistorialById(id);
            if (historial.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Historial encontrado", historial.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Historial no encontrado", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar historial: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<HistorialMedico>> saveHistorial(@Valid @RequestBody HistorialMedico historial, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            HistorialMedico savedHistorial = historialService.saveHistorial(historial);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Historial guardado exitosamente", savedHistorial));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar historial: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HistorialMedico>> updateHistorial(@PathVariable Long id, @Valid @RequestBody HistorialMedico historial, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            historial.setId(id);
            HistorialMedico updatedHistorial = historialService.updateHistorial(historial);
            return ResponseEntity.ok(new ApiResponse<>(true, "Historial actualizado exitosamente", updatedHistorial));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar historial: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHistorial(@PathVariable Long id) {
        try {
            historialService.deleteHistorial(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Historial eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar historial: " + e.getMessage(), null));
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ApiResponse<List<HistorialMedico>>> getHistorialesByPaciente(@PathVariable Long pacienteId) {
        try {
            List<HistorialMedico> historiales = historialService.getHistorialesByPaciente(pacienteId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Historiales del paciente obtenidos", historiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener historiales del paciente: " + e.getMessage(), null));
        }
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<ApiResponse<List<HistorialMedico>>> getHistorialesByMedico(@PathVariable Long medicoId) {
        try {
            List<HistorialMedico> historiales = historialService.getHistorialesByMedico(medicoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Historiales del médico obtenidos", historiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener historiales del médico: " + e.getMessage(), null));
        }
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<ApiResponse<List<HistorialMedico>>> getHistorialesByCita(@PathVariable Long citaId) {
        try {
            List<HistorialMedico> historiales = historialService.getHistorialesByCita(citaId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Historiales de la cita obtenidos", historiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener historiales de la cita: " + e.getMessage(), null));
        }
    }

    @GetMapping("/consultas-hoy")
    public ResponseEntity<ApiResponse<Long>> getConsultasHoy() {
        try {
            long consultas = historialService.contarConsultasHoy();
            return ResponseEntity.ok(new ApiResponse<>(true, "Consultas de hoy obtenidas", consultas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener consultas de hoy: " + e.getMessage(), null));
        }
    }
}
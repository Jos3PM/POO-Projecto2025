package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Horario;
import pe.edu.upeu.syshost.service.HorarioService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Horario>>> getAllHorarios() {
        try {
            List<Horario> horarios = horarioService.getAllHorarios();
            return ResponseEntity.ok(new ApiResponse<>(true, "Horarios obtenidos exitosamente", horarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener horarios: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Horario>> getHorarioById(@PathVariable Long id) {
        try {
            Optional<Horario> horario = horarioService.getHorarioById(id);
            if (horario.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Horario encontrado", horario.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Horario no encontrado", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar horario: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Horario>> saveHorario(@Valid @RequestBody Horario horario, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            Horario savedHorario = horarioService.saveHorario(horario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Horario guardado exitosamente", savedHorario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar horario: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Horario>> updateHorario(@PathVariable Long id, @Valid @RequestBody Horario horario, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            horario.setId(id);
            Horario updatedHorario = horarioService.updateHorario(horario);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horario actualizado exitosamente", updatedHorario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar horario: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHorario(@PathVariable Long id) {
        try {
            horarioService.deleteHorario(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horario eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar horario: " + e.getMessage(), null));
        }
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<ApiResponse<List<Horario>>> getHorariosByMedico(@PathVariable Long medicoId) {
        try {
            List<Horario> horarios = horarioService.getHorariosByMedico(medicoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horarios del médico obtenidos", horarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener horarios del médico: " + e.getMessage(), null));
        }
    }

    @GetMapping("/dia/{diaSemana}")
    public ResponseEntity<ApiResponse<List<Horario>>> getHorariosByDia(@PathVariable String diaSemana) {
        try {
            List<Horario> horarios = horarioService.getHorariosByDia(diaSemana);
            return ResponseEntity.ok(new ApiResponse<>(true, "Horarios del día obtenidos", horarios));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener horarios del día: " + e.getMessage(), null));
        }
    }
}
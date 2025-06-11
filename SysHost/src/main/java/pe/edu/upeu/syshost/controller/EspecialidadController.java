package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.syshost.entity.Especialidad;
import pe.edu.upeu.syshost.service.EspecialidadService;
import pe.edu.upeu.syshost.dto.ApiResponse;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Especialidad>>> getAllEspecialidades() {
        try {
            List<Especialidad> especialidades = especialidadService.getAllEspecialidades();
            return ResponseEntity.ok(new ApiResponse<>(true, "Especialidades obtenidas exitosamente", especialidades));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener especialidades: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Especialidad>> getEspecialidadById(@PathVariable Long id) {
        try {
            Optional<Especialidad> especialidad = especialidadService.getEspecialidadById(id);
            if (especialidad.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad encontrada", especialidad.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Especialidad no encontrada", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al buscar especialidad: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Especialidad>> saveEspecialidad(@Valid @RequestBody Especialidad especialidad, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            Especialidad savedEspecialidad = especialidadService.saveEspecialidad(especialidad);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Especialidad guardada exitosamente", savedEspecialidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al guardar especialidad: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Especialidad>> updateEspecialidad(@PathVariable Long id, @Valid @RequestBody Especialidad especialidad, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Errores de validación: " + errors.toString(), null));
        }

        try {
            especialidad.setId(id);
            Especialidad updatedEspecialidad = especialidadService.updateEspecialidad(especialidad);
            return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad actualizada exitosamente", updatedEspecialidad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al actualizar especialidad: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEspecialidad(@PathVariable Long id) {
        try {
            especialidadService.deleteEspecialidad(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al eliminar especialidad: " + e.getMessage(), null));
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<ApiResponse<List<Especialidad>>> getEspecialidadesActivas() {
        try {
            List<Especialidad> especialidades = especialidadService.getEspecialidadesActivas();
            return ResponseEntity.ok(new ApiResponse<>(true, "Especialidades activas obtenidas", especialidades));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error al obtener especialidades activas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Especialidad>>> buscarEspecialidadesPorNombre(@RequestParam String nombre) {
        try {
            List<Especialidad> especialidades = especialidadService.buscarEspecialidadesPorNombre(nombre);
            return ResponseEntity.ok(new ApiResponse<>(true, "Búsqueda completada", especialidades));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error en la búsqueda: " + e.getMessage(), null));
        }
    }
}
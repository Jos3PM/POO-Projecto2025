package pe.edu.upeu.syshost.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)$", 
             message = "Día de la semana inválido")
    @Column(name = "dia_semana", nullable = false, length = 10)
    private String diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Min(value = 15, message = "La duración mínima de consulta es 15 minutos")
    @Max(value = 120, message = "La duración máxima de consulta es 120 minutos")
    @Column(name = "duracion_consulta", nullable = false)
    private Integer duracionConsulta = 30;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
        if (duracionConsulta == null) {
            duracionConsulta = 30;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        // Validar que hora fin sea después de hora inicio
        if (horaInicio != null && horaFin != null && !horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }
}
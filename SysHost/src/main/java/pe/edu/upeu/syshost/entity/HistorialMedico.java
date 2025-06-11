package pe.edu.upeu.syshost.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historiales_medicos")
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(min = 10, max = 1000, message = "El diagnóstico debe tener entre 10 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String diagnostico;

    @Size(max = 1000, message = "El tratamiento no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String tratamiento;

    @Size(max = 500, message = "Los medicamentos no pueden exceder 500 caracteres")
    @Column(length = 500)
    private String medicamentos;

    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    @Column(length = 1000)
    private String observaciones;

    @Size(max = 500, message = "Los exámenes no pueden exceder 500 caracteres")
    @Column(length = 500)
    private String examenes;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;

    @Column(name = "proxima_cita")
    private LocalDateTime proximaCita;

    @DecimalMin(value = "0.0", message = "El peso debe ser positivo")
    @DecimalMax(value = "500.0", message = "El peso no puede ser mayor a 500 kg")
    @Column(precision = 5, scale = 2)
    private Double peso;

    @DecimalMin(value = "0.0", message = "La estatura debe ser positiva")
    @DecimalMax(value = "3.0", message = "La estatura no puede ser mayor a 3 metros")
    @Column(precision = 3, scale = 2)
    private Double estatura;

    @Min(value = 30, message = "La presión sistólica mínima es 30")
    @Max(value = 300, message = "La presión sistólica máxima es 300")
    @Column(name = "presion_sistolica")
    private Integer presionSistolica;

    @Min(value = 20, message = "La presión diastólica mínima es 20")
    @Max(value = 200, message = "La presión diastólica máxima es 200")
    @Column(name = "presion_diastolica")
    private Integer presionDiastolica;

    @DecimalMin(value = "30.0", message = "La temperatura mínima es 30°C")
    @DecimalMax(value = "45.0", message = "La temperatura máxima es 45°C")
    @Column(precision = 3, scale = 1)
    private Double temperatura;

    @Min(value = 30, message = "El pulso mínimo es 30 bpm")
    @Max(value = 200, message = "El pulso máximo es 200 bpm")
    private Integer pulso;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (fechaConsulta == null) {
            fechaConsulta = LocalDateTime.now();
        }
    }
}
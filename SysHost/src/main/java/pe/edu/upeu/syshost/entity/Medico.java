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
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @NotBlank(message = "El CMP es obligatorio")
    @Size(min = 5, max = 20, message = "El CMP debe tener entre 5 y 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String cmp;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String especialidad;

    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    @Column(length = 9)
    private String telefono;

    @Email(message = "El formato del correo no es válido")
    @Column(length = 100)
    private String correo;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(length = 200)
    private String direccion;

    @Min(value = 25, message = "La edad mínima es 25 años")
    @Max(value = 80, message = "La edad máxima es 80 años")
    @Column(nullable = false)
    private Integer edad;

    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El sexo debe ser Masculino, Femenino u Otro")
    @Column(length = 10)
    private String sexo;

    @Min(value = 1, message = "Los años de experiencia deben ser al menos 1")
    @Max(value = 50, message = "Los años de experiencia no pueden ser más de 50")
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
    }
}
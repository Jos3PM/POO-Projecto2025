package pe.edu.upeu.syshost.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

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

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    @Column(length = 9)
    private String telefono;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(length = 200)
    private String direccion;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150")
    @Column(nullable = false)
    private Integer edad;

    @Pattern(regexp = "^(Masculino|Femenino|Otro)$", message = "El sexo debe ser Masculino, Femenino u Otro")
    @Column(length = 10)
    private String sexo;

    @Email(message = "El formato del correo no es válido")
    @Column(length = 100)
    private String correo;

    @Size(max = 500, message = "El caso no puede exceder 500 caracteres")
    @Column(length = 500)
    private String caso;

    @DecimalMin(value = "0.0", message = "La estatura debe ser positiva")
    @DecimalMax(value = "3.0", message = "La estatura no puede ser mayor a 3 metros")
    @Column(precision = 3, scale = 2)
    private Double estatura;

    @DecimalMin(value = "0.0", message = "El peso debe ser positivo")
    @DecimalMax(value = "500.0", message = "El peso no puede ser mayor a 500 kg")
    @Column(precision = 5, scale = 2)
    private Double peso;

    @Pattern(regexp = "^(A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-)$", message = "Tipo de sangre inválido")
    @Column(name = "tipo_sangre", length = 3)
    private String tipoSangre;

    @Size(max = 300, message = "La enfermedad genética no puede exceder 300 caracteres")
    @Column(name = "enfermedad_genetica", length = 300)
    private String enfermedadGenetica;

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

    @PreUpdate
    protected void onUpdate() {
        // Calcular edad automáticamente si se actualiza la fecha de nacimiento
        if (fechaNacimiento != null) {
            this.edad = LocalDate.now().getYear() - fechaNacimiento.getYear();
        }
    }
}
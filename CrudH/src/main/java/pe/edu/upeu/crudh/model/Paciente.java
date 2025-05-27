package pe.edu.upeu.crudh.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String dni;
    private Integer edad;
    private String sexo;
    private String correo;
    private String telefono;
    private String direccion;
    private Double estatura;
    private Double peso;
    private String tipoSangre;
    private String enfermedadGenetica;
    private String caso;
    private LocalDate fechaNacimiento;
}
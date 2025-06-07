package pe.edu.upeu.syshost.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;
    private String fechaNacimiento;
    private String telefono;
    private String direccion;
    private int edad;
    private String sexo;
    private String correo;
    private String caso;
    private String estatura;
    private String peso;
    private String tipoSangre;
    private String enfermedadGenetica;
}
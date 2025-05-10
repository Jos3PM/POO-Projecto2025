package pe.edu.upeu.crudf.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Patient patient;
    
    private String previousConditions;
    private String allergies;
    private String surgeries;
    private String familyHistory;
    private String medications;
    private LocalDate recordDate;
}
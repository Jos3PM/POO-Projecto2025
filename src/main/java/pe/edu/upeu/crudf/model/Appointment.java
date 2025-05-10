package pe.edu.upeu.crudf.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Patient patient;
    
    private LocalDateTime appointmentDateTime;
    private String doctorName;
    private String specialty;
    private String reason;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
}
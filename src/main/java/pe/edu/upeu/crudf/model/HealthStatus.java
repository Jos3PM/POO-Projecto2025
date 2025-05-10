package pe.edu.upeu.crudf.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class HealthStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Patient patient;
    
    private LocalDateTime checkupDate;
    private String bloodPressure;
    private Double temperature;
    private Double weight;
    private Double height;
    private String symptoms;
    private String diagnosis;
    private String treatment;
}
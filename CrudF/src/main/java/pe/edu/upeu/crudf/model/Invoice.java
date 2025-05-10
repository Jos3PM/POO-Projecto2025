package pe.edu.upeu.crudf.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Patient patient;
    
    private LocalDateTime dateTime;
    private String serviceDescription;
    private Double amount;
    private String invoiceNumber;
}
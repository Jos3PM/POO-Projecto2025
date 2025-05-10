package pe.edu.upeu.crudf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudf.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
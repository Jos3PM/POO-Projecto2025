package pe.edu.upeu.crudf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudf.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
}
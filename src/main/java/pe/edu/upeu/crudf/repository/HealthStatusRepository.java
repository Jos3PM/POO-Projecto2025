package pe.edu.upeu.crudf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudf.model.HealthStatus;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {
}
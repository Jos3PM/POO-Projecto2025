package pe.edu.upeu.crudf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudf.model.Appointment;
import pe.edu.upeu.crudf.model.Patient;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
}
package pe.edu.upeu.crudh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudh.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
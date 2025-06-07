package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Paciente;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
}
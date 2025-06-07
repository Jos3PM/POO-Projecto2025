package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Paciente;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Paciente> findByActivoTrue();
    
    Optional<Paciente> findByDni(String dni);
    
    boolean existsByDni(String dni);
    
    @Query("SELECT p FROM Paciente p WHERE p.edad BETWEEN :edadMin AND :edadMax")
    List<Paciente> findByEdadBetween(@Param("edadMin") Integer edadMin, @Param("edadMax") Integer edadMax);
    
    @Query("SELECT p FROM Paciente p WHERE p.sexo = :sexo")
    List<Paciente> findBySexo(@Param("sexo") String sexo);
    
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.activo = true")
    long countActivePacientes();
}
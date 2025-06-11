package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Especialidad;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    
    List<Especialidad> findByActivaTrue();
    
    Optional<Especialidad> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
    
    List<Especialidad> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT COUNT(e) FROM Especialidad e WHERE e.activa = true")
    long countActiveEspecialidades();
}
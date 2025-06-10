package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Medico;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    List<Medico> findByNombreContainingIgnoreCase(String nombre);
    
    List<Medico> findByActivoTrue();
    
    List<Medico> findByEspecialidadContainingIgnoreCase(String especialidad);
    
    Optional<Medico> findByDni(String dni);
    
    Optional<Medico> findByCmp(String cmp);
    
    boolean existsByDni(String dni);
    
    boolean existsByCmp(String cmp);
    
    @Query("SELECT COUNT(m) FROM Medico m WHERE m.activo = true")
    long countActiveMedicos();
    
    @Query("SELECT DISTINCT m.especialidad FROM Medico m WHERE m.activo = true ORDER BY m.especialidad")
    List<String> findDistinctEspecialidades();
}
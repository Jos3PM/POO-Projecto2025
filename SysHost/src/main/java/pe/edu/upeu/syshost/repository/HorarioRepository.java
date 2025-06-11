package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Horario;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    
    List<Horario> findByMedicoIdAndActivoTrue(Long medicoId);
    
    List<Horario> findByDiaSemanaAndActivoTrue(String diaSemana);
    
    @Query("SELECT h FROM Horario h WHERE h.medico.id = :medicoId AND h.diaSemana = :diaSemana AND h.activo = true")
    List<Horario> findByMedicoAndDia(@Param("medicoId") Long medicoId, @Param("diaSemana") String diaSemana);
    
    boolean existsByMedicoIdAndDiaSemanaAndActivoTrue(Long medicoId, String diaSemana);
}
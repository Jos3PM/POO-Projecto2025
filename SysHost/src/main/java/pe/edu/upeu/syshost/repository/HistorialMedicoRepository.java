package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.HistorialMedico;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    
    List<HistorialMedico> findByPacienteIdOrderByFechaConsultaDesc(Long pacienteId);
    
    List<HistorialMedico> findByMedicoIdOrderByFechaConsultaDesc(Long medicoId);
    
    List<HistorialMedico> findByCitaId(Long citaId);
    
    @Query("SELECT h FROM HistorialMedico h WHERE h.fechaConsulta BETWEEN :inicio AND :fin ORDER BY h.fechaConsulta DESC")
    List<HistorialMedico> findByFechaConsultaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    @Query("SELECT h FROM HistorialMedico h WHERE h.paciente.id = :pacienteId AND h.fechaConsulta BETWEEN :inicio AND :fin ORDER BY h.fechaConsulta DESC")
    List<HistorialMedico> findByPacienteAndFechaBetween(@Param("pacienteId") Long pacienteId, @Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    @Query("SELECT COUNT(h) FROM HistorialMedico h WHERE DATE(h.fechaConsulta) = CURRENT_DATE")
    long countConsultasHoy();
    
    @Query("SELECT COUNT(h) FROM HistorialMedico h WHERE h.medico.id = :medicoId AND DATE(h.fechaConsulta) = CURRENT_DATE")
    long countConsultasMedicoHoy(@Param("medicoId") Long medicoId);
}
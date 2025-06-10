package pe.edu.upeu.syshost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.syshost.entity.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    List<Cita> findByPacienteId(Long pacienteId);
    
    List<Cita> findByMedicoId(Long medicoId);
    
    List<Cita> findByEstado(String estado);
    
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT c FROM Cita c WHERE c.fechaHora >= :fecha AND c.estado = 'Programada'")
    List<Cita> findCitasProgramadas(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.estado = 'Programada'")
    long countCitasProgramadas();
    
    @Query("SELECT COUNT(c) FROM Cita c WHERE DATE(c.fechaHora) = CURRENT_DATE")
    long countCitasHoy();
}
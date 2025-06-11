package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Horario;
import java.util.List;
import java.util.Optional;

public interface HorarioService {
    List<Horario> getAllHorarios();
    Optional<Horario> getHorarioById(Long id);
    Horario saveHorario(Horario horario);
    Horario updateHorario(Horario horario);
    void deleteHorario(Long id);
    List<Horario> getHorariosByMedico(Long medicoId);
    List<Horario> getHorariosByDia(String diaSemana);
    List<Horario> getHorariosMedicoEnDia(Long medicoId, String diaSemana);
    boolean existeHorarioMedicoEnDia(Long medicoId, String diaSemana);
}
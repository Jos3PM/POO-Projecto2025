package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoService {
    List<Medico> getAllMedicos();
    Optional<Medico> getMedicoById(Long id);
    Medico saveMedico(Medico medico);
    Medico updateMedico(Medico medico);
    void deleteMedico(Long id);
    List<Medico> buscarMedicosPorNombre(String nombre);
    List<Medico> getMedicosActivos();
    List<Medico> buscarMedicosPorEspecialidad(String especialidad);
    List<String> getEspecialidades();
    long contarMedicos();
}
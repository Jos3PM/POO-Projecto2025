package pe.edu.upeu.syshost.service;

import pe.edu.upeu.syshost.entity.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadService {
    List<Especialidad> getAllEspecialidades();
    Optional<Especialidad> getEspecialidadById(Long id);
    Especialidad saveEspecialidad(Especialidad especialidad);
    Especialidad updateEspecialidad(Especialidad especialidad);
    void deleteEspecialidad(Long id);
    List<Especialidad> getEspecialidadesActivas();
    List<Especialidad> buscarEspecialidadesPorNombre(String nombre);
    long contarEspecialidades();
}
package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.upeu.syshost.service.PacienteService;
import pe.edu.upeu.syshost.service.MedicoService;
import pe.edu.upeu.syshost.service.CitaService;
import pe.edu.upeu.syshost.service.HistorialMedicoService;
import pe.edu.upeu.syshost.service.EspecialidadService;

@Controller
public class HomeController {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private MedicoService medicoService;
    
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private HistorialMedicoService historialService;
    
    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalPacientes", pacienteService.contarPacientes());
        model.addAttribute("pacientesActivos", pacienteService.getPacientesActivos().size());
        model.addAttribute("totalMedicos", medicoService.contarMedicos());
        model.addAttribute("medicosActivos", medicoService.getMedicosActivos().size());
        model.addAttribute("citasProgramadas", citaService.contarCitasProgramadas());
        model.addAttribute("citasHoy", citaService.contarCitasHoy());
        model.addAttribute("consultasHoy", historialService.contarConsultasHoy());
        model.addAttribute("totalEspecialidades", especialidadService.contarEspecialidades());
        return "index";
    }

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.getAllPacientes());
        return "pacientes";
    }
    
    @GetMapping("/medicos")
    public String medicos(Model model) {
        model.addAttribute("medicos", medicoService.getAllMedicos());
        model.addAttribute("especialidades", medicoService.getEspecialidades());
        return "medicos";
    }
    
    @GetMapping("/citas")
    public String citas(Model model) {
        model.addAttribute("citas", citaService.getAllCitas());
        model.addAttribute("pacientes", pacienteService.getAllPacientes());
        model.addAttribute("medicos", medicoService.getAllMedicos());
        return "citas";
    }
    
    @GetMapping("/historiales")
    public String historiales(Model model) {
        model.addAttribute("historiales", historialService.getAllHistoriales());
        model.addAttribute("pacientes", pacienteService.getAllPacientes());
        model.addAttribute("medicos", medicoService.getAllMedicos());
        return "historiales";
    }
    
    @GetMapping("/reportes")
    public String reportes(Model model) {
        model.addAttribute("totalPacientes", pacienteService.contarPacientes());
        model.addAttribute("totalMedicos", medicoService.contarMedicos());
        model.addAttribute("citasProgramadas", citaService.contarCitasProgramadas());
        model.addAttribute("consultasHoy", historialService.contarConsultasHoy());
        model.addAttribute("totalEspecialidades", especialidadService.contarEspecialidades());
        return "reportes";
    }
}
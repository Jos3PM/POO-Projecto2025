package pe.edu.upeu.syshost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.upeu.syshost.service.PacienteService;

@Controller
public class HomeController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalPacientes", pacienteService.contarPacientes());
        model.addAttribute("pacientesActivos", pacienteService.getPacientesActivos().size());
        return "index";
    }

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.getAllPacientes());
        return "pacientes";
    }
}
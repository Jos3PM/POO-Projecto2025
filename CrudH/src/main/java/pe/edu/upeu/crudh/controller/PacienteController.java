package pe.edu.upeu.crudh.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.crudh.model.Paciente;
import pe.edu.upeu.crudh.service.PacienteService;

@Component
public class PacienteController {
    @FXML private TextField nombreField;
    @FXML private TextField dniField;
    @FXML private TextField edadField;
    @FXML private TextField sexoField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private TextField direccionField;
    @FXML private TextField estaturaField;
    @FXML private TextField pesoField;
    @FXML private TextField tipoSangreField;
    @FXML private TextField enfermedadGeneticaField;
    @FXML private TextField casoField;
    @FXML private DatePicker fechaNacimientoPicker;
    
    @FXML private TableView<Paciente> pacienteTable;
    @FXML private TableColumn<Paciente, Long> idColumn;
    @FXML private TableColumn<Paciente, String> nombreColumn;
    @FXML private TableColumn<Paciente, Integer> edadColumn;
    @FXML private TableColumn<Paciente, String> casoColumn;

    @Autowired
    private PacienteService pacienteService;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
        casoColumn.setCellValueFactory(new PropertyValueFactory<>("caso"));
        loadPacientes();
    }

    private void loadPacientes() {
        pacienteTable.setItems(FXCollections.observableArrayList(pacienteService.getAllPacientes()));
    }

    @FXML
    private void handleGuardar() {
        Paciente paciente = new Paciente();
        paciente.setNombre(nombreField.getText());
        paciente.setDni(dniField.getText());
        paciente.setEdad(Integer.parseInt(edadField.getText()));
        paciente.setSexo(sexoField.getText());
        paciente.setCorreo(correoField.getText());
        paciente.setTelefono(telefonoField.getText());
        paciente.setDireccion(direccionField.getText());
        paciente.setEstatura(Double.parseDouble(estaturaField.getText()));
        paciente.setPeso(Double.parseDouble(pesoField.getText()));
        paciente.setTipoSangre(tipoSangreField.getText());
        paciente.setEnfermedadGenetica(enfermedadGeneticaField.getText());
        paciente.setCaso(casoField.getText());
        paciente.setFechaNacimiento(fechaNacimientoPicker.getValue());
        
        pacienteService.savePaciente(paciente);
        limpiarCampos();
        loadPacientes();
    }

    @FXML
    private void handleEliminar() {
        Paciente selectedPaciente = pacienteTable.getSelectionModel().getSelectedItem();
        if (selectedPaciente != null) {
            pacienteService.deletePaciente(selectedPaciente.getId());
            loadPacientes();
        }
    }

    private void limpiarCampos() {
        nombreField.clear();
        dniField.clear();
        edadField.clear();
        sexoField.clear();
        correoField.clear();
        telefonoField.clear();
        direccionField.clear();
        estaturaField.clear();
        pesoField.clear();
        tipoSangreField.clear();
        enfermedadGeneticaField.clear();
        casoField.clear();
        fechaNacimientoPicker.setValue(null);
    }
}
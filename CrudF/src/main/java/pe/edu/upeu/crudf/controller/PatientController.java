package pe.edu.upeu.crudf.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.crudf.model.Patient;
import pe.edu.upeu.crudf.service.PatientService;

import java.time.LocalDate;

@Component
public class PatientController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Long> idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> dniColumn;
    @FXML private TableColumn<Patient, LocalDate> dobColumn;
    @FXML private TextField nameField;
    @FXML private TextField dniField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField bloodTypeField;

    @Autowired
    private PatientService patientService;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        loadPatients();
    }

    private void loadPatients() {
        patientTable.setItems(FXCollections.observableArrayList(patientService.getAllPatients()));
    }

    @FXML
    private void handleSave() {
        Patient patient = new Patient();
        patient.setName(nameField.getText());
        patient.setDni(dniField.getText());
        patient.setDateOfBirth(dobPicker.getValue());
        patient.setAddress(addressField.getText());
        patient.setPhoneNumber(phoneField.getText());
        patient.setBloodType(bloodTypeField.getText());
        
        patientService.savePatient(patient);
        clearFields();
        loadPatients();
    }

    @FXML
    private void handleDelete() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            patientService.deletePatient(selectedPatient.getId());
            loadPatients();
        }
    }

    private void clearFields() {
        nameField.clear();
        dniField.clear();
        dobPicker.setValue(null);
        addressField.clear();
        phoneField.clear();
        bloodTypeField.clear();
    }
}
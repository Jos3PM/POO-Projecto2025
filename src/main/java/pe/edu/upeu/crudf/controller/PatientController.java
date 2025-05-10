package pe.edu.upeu.crudf.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.print.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.crudf.model.*;
import pe.edu.upeu.crudf.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

@Component
public class PatientController {
    // Patient Information fields
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

    // Health Status fields
    @FXML private TextField bpField;
    @FXML private TextField tempField;
    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private TextArea symptomsArea;
    @FXML private TextArea diagnosisArea;
    @FXML private TextArea treatmentArea;

    // Medical History fields
    @FXML private TextArea conditionsArea;
    @FXML private TextArea allergiesArea;
    @FXML private TextArea surgeriesArea;
    @FXML private TextArea familyHistoryArea;
    @FXML private TextArea medicationsArea;

    // Appointment fields
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<String> appointmentTime;
    @FXML private TextField doctorField;
    @FXML private TextField specialtyField;
    @FXML private TextArea reasonArea;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentDateColumn;
    @FXML private TableColumn<Appointment, String> doctorColumn;
    @FXML private TableColumn<Appointment, String> specialtyColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    @Autowired private PatientService patientService;
    @Autowired private HealthStatusService healthStatusService;
    @Autowired private MedicalHistoryService medicalHistoryService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private InvoiceService invoiceService;

    @FXML
    public void initialize() {
        setupPatientTable();
        setupAppointmentTable();
        setupTimeComboBox();
    }

    private void setupPatientTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        loadPatients();
    }

    private void setupAppointmentTable() {
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDateTime"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setupTimeComboBox() {
        appointmentTime.setItems(FXCollections.observableArrayList(
            IntStream.rangeClosed(9, 17)
                    .mapToObj(hour -> String.format("%02d:00", hour))
                    .toList()
        ));
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
    private void handleSaveHealthStatus() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            HealthStatus status = new HealthStatus();
            status.setPatient(selectedPatient);
            status.setCheckupDate(LocalDateTime.now());
            status.setBloodPressure(bpField.getText());
            status.setTemperature(Double.parseDouble(tempField.getText()));
            status.setWeight(Double.parseDouble(weightField.getText()));
            status.setHeight(Double.parseDouble(heightField.getText()));
            status.setSymptoms(symptomsArea.getText());
            status.setDiagnosis(diagnosisArea.getText());
            status.setTreatment(treatmentArea.getText());
            
            healthStatusService.saveHealthStatus(status);
            clearHealthStatusFields();
        }
    }

    @FXML
    private void handleSaveMedicalHistory() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            MedicalHistory history = new MedicalHistory();
            history.setPatient(selectedPatient);
            history.setPreviousConditions(conditionsArea.getText());
            history.setAllergies(allergiesArea.getText());
            history.setSurgeries(surgeriesArea.getText());
            history.setFamilyHistory(familyHistoryArea.getText());
            history.setMedications(medicationsArea.getText());
            history.setRecordDate(LocalDate.now());
            
            medicalHistoryService.saveMedicalHistory(history);
            clearMedicalHistoryFields();
        }
    }

    @FXML
    private void handleScheduleAppointment() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null && appointmentDate.getValue() != null && appointmentTime.getValue() != null) {
            Appointment appointment = new Appointment();
            appointment.setPatient(selectedPatient);
            appointment.setAppointmentDateTime(
                LocalDateTime.of(
                    appointmentDate.getValue(),
                    LocalTime.parse(appointmentTime.getValue())
                )
            );
            appointment.setDoctorName(doctorField.getText());
            appointment.setSpecialty(specialtyField.getText());
            appointment.setReason(reasonArea.getText());
            appointment.setStatus("SCHEDULED");
            
            appointmentService.saveAppointment(appointment);
            clearAppointmentFields();
            loadAppointments(selectedPatient);
        }
    }

    private void loadAppointments(Patient patient) {
        appointmentTable.setItems(FXCollections.observableArrayList(
            appointmentService.getAppointmentsByPatient(patient)
        ));
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

    private void clearHealthStatusFields() {
        bpField.clear();
        tempField.clear();
        weightField.clear();
        heightField.clear();
        symptomsArea.clear();
        diagnosisArea.clear();
        treatmentArea.clear();
    }

    private void clearMedicalHistoryFields() {
        conditionsArea.clear();
        allergiesArea.clear();
        surgeriesArea.clear();
        familyHistoryArea.clear();
        medicationsArea.clear();
    }

    private void clearAppointmentFields() {
        appointmentDate.setValue(null);
        appointmentTime.setValue(null);
        doctorField.clear();
        specialtyField.clear();
        reasonArea.clear();
    }

    @FXML
    private void handleGenerateInvoice() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            Invoice invoice = new Invoice();
            invoice.setPatient(selectedPatient);
            invoice.setDateTime(LocalDateTime.now());
            invoice.setServiceDescription("Medical Consultation");
            invoice.setAmount(100.0); // Example amount
            
            Invoice savedInvoice = invoiceService.generateInvoice(invoice);
            printInvoice(savedInvoice);
        }
    }

    private void printInvoice(Invoice invoice) {
        VBox content = new VBox(10);
        content.getChildren().addAll(
            new Text("HOSPITAL INVOICE"),
            new Text("Invoice #: " + invoice.getInvoiceNumber()),
            new Text("Date: " + invoice.getDateTime()),
            new Text("Patient: " + invoice.getPatient().getName()),
            new Text("DNI: " + invoice.getPatient().getDni()),
            new Text("Service: " + invoice.getServiceDescription()),
            new Text("Amount: $" + invoice.getAmount())
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean printed = job.printPage(content);
            if (printed) {
                job.endJob();
            }
        }
    }
}
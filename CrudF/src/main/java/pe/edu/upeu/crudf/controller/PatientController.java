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
import pe.edu.upeu.crudf.model.Patient;
import pe.edu.upeu.crudf.model.Invoice;
import pe.edu.upeu.crudf.service.PatientService;
import pe.edu.upeu.crudf.service.InvoiceService;

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
    @FXML private TextField serviceField;
    @FXML private TextField amountField;

    @Autowired
    private PatientService patientService;
    
    @Autowired
    private InvoiceService invoiceService;

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

    @FXML
    private void handleGenerateInvoice() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null && !serviceField.getText().isEmpty() && !amountField.getText().isEmpty()) {
            Invoice invoice = new Invoice();
            invoice.setPatient(selectedPatient);
            invoice.setServiceDescription(serviceField.getText());
            invoice.setAmount(Double.parseDouble(amountField.getText()));
            
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

    private void clearFields() {
        nameField.clear();
        dniField.clear();
        dobPicker.setValue(null);
        addressField.clear();
        phoneField.clear();
        bloodTypeField.clear();
        serviceField.clear();
        amountField.clear();
    }
}
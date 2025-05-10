package pe.edu.upeu.crudf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.crudf.model.Appointment;
import pe.edu.upeu.crudf.model.Patient;
import pe.edu.upeu.crudf.repository.AppointmentRepository;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }
}
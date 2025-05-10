package pe.edu.upeu.crudf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.crudf.model.MedicalHistory;
import pe.edu.upeu.crudf.repository.MedicalHistoryRepository;

@Service
public class MedicalHistoryService {
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistory saveMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }
}
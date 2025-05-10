package pe.edu.upeu.crudf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.crudf.model.HealthStatus;
import pe.edu.upeu.crudf.repository.HealthStatusRepository;

@Service
public class HealthStatusService {
    @Autowired
    private HealthStatusRepository healthStatusRepository;

    public HealthStatus saveHealthStatus(HealthStatus healthStatus) {
        return healthStatusRepository.save(healthStatus);
    }
}
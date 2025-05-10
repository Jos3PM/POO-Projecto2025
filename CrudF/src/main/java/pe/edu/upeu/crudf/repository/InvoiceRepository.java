package pe.edu.upeu.crudf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.crudf.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
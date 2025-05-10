package pe.edu.upeu.crudf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.crudf.model.Invoice;
import pe.edu.upeu.crudf.repository.InvoiceRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice generateInvoice(Invoice invoice) {
        invoice.setDateTime(LocalDateTime.now());
        invoice.setInvoiceNumber(generateInvoiceNumber());
        return invoiceRepository.save(invoice);
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }
}
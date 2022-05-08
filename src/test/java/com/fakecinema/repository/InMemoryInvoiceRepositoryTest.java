package com.fakecinema.repository;

import com.fakecinema.domain.Invoice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryInvoiceRepositoryTest {

  @Nested
  class SaveOrUpdate {
    @Test
    void shouldSaveTheInvoiceWhenInvoiceIsNew() {
      InMemoryInvoiceRepository repository = new InMemoryInvoiceRepository();
      Invoice invoice = new Invoice(1, "Titanic", LocalDateTime.now(), 100.0);
      repository.saveOrUpdate(invoice);

      Optional<Invoice> invoiceFromDatabase = repository.findById(invoice.getInvoiceNumber());
      assertTrue(invoiceFromDatabase.isPresent());
      assertEquals(invoice, invoiceFromDatabase.get());
    }

    @Test
    void shouldUpdateExistingInvoiceWhenInvoiceAlreadyExists() {
      InMemoryInvoiceRepository repository = new InMemoryInvoiceRepository();
      Invoice originalInvoice = new Invoice(1, "Titanic", LocalDateTime.now(), 100.0);
      repository.saveOrUpdate(originalInvoice);

      Invoice updatedInvoice = originalInvoice.markAsPaid();
      repository.saveOrUpdate(updatedInvoice);

      Optional<Invoice> invoiceFromDatabase =
          repository.findById(originalInvoice.getInvoiceNumber());
      assertTrue(invoiceFromDatabase.isPresent());
      assertEquals(updatedInvoice, invoiceFromDatabase.get());
    }
  }
}

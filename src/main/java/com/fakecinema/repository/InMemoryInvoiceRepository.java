package com.fakecinema.repository;

import com.fakecinema.domain.Invoice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryInvoiceRepository {
  private final Map<Integer, Invoice> invoices;

  public InMemoryInvoiceRepository() {
    this.invoices = new HashMap<>();
  }

  public void saveOrUpdate(Invoice invoice) {
    this.invoices.put(invoice.getInvoiceNumber(), invoice);
  }

  public Optional<Invoice> findById(Integer invoiceNumber) {
    return Optional.ofNullable(this.invoices.get(invoiceNumber));
  }
}

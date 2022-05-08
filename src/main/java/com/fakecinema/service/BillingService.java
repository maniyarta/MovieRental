package com.fakecinema.service;

import com.fakecinema.domain.Invoice;
import com.fakecinema.domain.Rental;
import com.fakecinema.repository.InMemoryInvoiceRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class BillingService {
  private final InMemoryInvoiceRepository invoiceRepository;
  private final AtomicInteger invoiceNumber;

  public BillingService(InMemoryInvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
    this.invoiceNumber = new AtomicInteger(0);
  }

  public Invoice createInvoiceFor(Rental rental) {
    Invoice invoice =
        new Invoice(
            invoiceNumber.incrementAndGet(),
            rental.movieTitle(),
            LocalDateTime.now(),
            rental.charges());
    invoiceRepository.saveOrUpdate(invoice);
    return invoice;
  }

  public Invoice markAsPaid(Integer invoiceNumber) {
    Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceNumber);
    Invoice newInvoice =
        optionalInvoice
            .map(Invoice::markAsPaid)
            .orElseThrow(
                () ->
                    new RuntimeException("No invoice found for invoice number : " + invoiceNumber));
    invoiceRepository.saveOrUpdate(newInvoice);
    return newInvoice;
  }

  public Integer lastInvoiceNumber() {
    return invoiceNumber.get();
  }
}

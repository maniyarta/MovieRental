package com.fakecinema.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Invoice {
  private final Integer invoiceNumber;
  private final String movieTitle;
  private final LocalDateTime invoiceDateTime;
  private final Double charges;
  private final PaymentStatus paymentStatus;
  private final Optional<LocalDateTime> paymentDateTime;

  public Invoice(
      Integer invoiceNumber, String movieTitle, LocalDateTime invoiceDateTime, Double charges) {
    this(
        invoiceNumber,
        movieTitle,
        invoiceDateTime,
        charges,
        PaymentStatus.UNPAID,
        Optional.empty());
  }

  public Invoice(
      Integer invoiceNumber,
      String movieTitle,
      LocalDateTime invoiceDateTime,
      Double charges,
      PaymentStatus paymentStatus,
      Optional<LocalDateTime> paymentDateTime) {
    this.invoiceNumber = invoiceNumber;
    this.movieTitle = movieTitle;
    this.invoiceDateTime = invoiceDateTime;
    this.charges = charges;
    this.paymentStatus = paymentStatus;
    this.paymentDateTime = paymentDateTime;
  }

  public Integer getInvoiceNumber() {
    return invoiceNumber;
  }

  public Invoice markAsPaid() {
    return new Invoice(
        this.invoiceNumber,
        this.movieTitle,
        this.invoiceDateTime,
        charges,
        PaymentStatus.PAID,
        Optional.of(LocalDateTime.now()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Invoice invoice = (Invoice) o;
    return invoiceNumber.equals(invoice.invoiceNumber)
        && movieTitle.equals(invoice.movieTitle)
        && invoiceDateTime.equals(invoice.invoiceDateTime)
        && charges.equals(invoice.charges)
        && paymentStatus == invoice.paymentStatus
        && paymentDateTime.equals(invoice.paymentDateTime);
  }
}

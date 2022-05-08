package com.fakecinema.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class InvoiceTest {
  @Nested
  class MarkAsPaid {
    @Test
    void shouldMarkGivenInvoiceAsPaidWithPaymentDate() {
      LocalDateTime invoiceDateTime = LocalDateTime.now();
      Integer invoiceNumber = 1;
      String movieTitle = "Titanic";
      Double charges = 123.00;
      Invoice unpaidInvoice = new Invoice(invoiceNumber, movieTitle, invoiceDateTime, charges);

      LocalDateTime frozenPaymentDateTime = LocalDateTime.now();
      try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
        mockedLocalDateTime.when(LocalDateTime::now).thenReturn(frozenPaymentDateTime);

        Invoice paidInvoice = unpaidInvoice.markAsPaid();

        Invoice expectedNewInvoice =
            new Invoice(
                invoiceNumber,
                movieTitle,
                invoiceDateTime,
                charges,
                PaymentStatus.PAID,
                Optional.of(frozenPaymentDateTime));

        assertEquals(expectedNewInvoice, paidInvoice);
      }
    }
  }
}

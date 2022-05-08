package com.fakecinema.service;

import com.fakecinema.domain.Genre;
import com.fakecinema.domain.Invoice;
import com.fakecinema.domain.Movie;
import com.fakecinema.domain.Rental;
import com.fakecinema.repository.InMemoryInvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class BillingServiceTest {
  private final InMemoryInvoiceRepository invoiceRepository = mock(InMemoryInvoiceRepository.class);
  private final BillingService service = new BillingService(invoiceRepository);

  @BeforeEach
  void setUp() {
    Mockito.reset(invoiceRepository);
  }

  @Nested
  class createInvoiceFor {
    @Test
    void shouldCreateNewInvoiceInDatabase() {
      Movie movie = new Movie("Movie Title", Genre.DRAMA, 2022);
      int rentedDays = 5;
      Rental rental = new Rental(movie, rentedDays);

      doNothing().when(invoiceRepository).saveOrUpdate(any(Invoice.class));

      LocalDateTime frozenDateTime = LocalDateTime.now();
      try (MockedStatic<LocalDateTime> mockedDateTime = mockStatic(LocalDateTime.class)) {
        mockedDateTime.when(LocalDateTime::now).thenReturn(frozenDateTime);

        Invoice invoice = service.createInvoiceFor(rental);

        Invoice expectedInvoice =
            new Invoice(
                service.lastInvoiceNumber(), movie.title(), frozenDateTime, rental.charges());

        assertEquals(expectedInvoice, invoice);
        verify(invoiceRepository).saveOrUpdate(expectedInvoice);
      }
    }
  }

  @Nested
  class MarkAsPaid {
    @Test
    void shouldUpdateExistingInvoiceInDatabase() {
      Invoice unpaidInvoice = new Invoice(1, "Movie Title", LocalDateTime.now(), 11.00);

      doReturn(Optional.of(unpaidInvoice)).when(invoiceRepository).findById(any(Integer.class));
      doNothing().when(invoiceRepository).saveOrUpdate(any(Invoice.class));

      LocalDateTime frozenDateTime = LocalDateTime.now();
      try (MockedStatic<LocalDateTime> mockedDateTime = mockStatic(LocalDateTime.class)) {
        mockedDateTime.when(LocalDateTime::now).thenReturn(frozenDateTime);

        Invoice paidInvoice = service.markAsPaid(unpaidInvoice.getInvoiceNumber());

        Invoice expectedPaidInvoice = unpaidInvoice.markAsPaid();
        verify(invoiceRepository).findById(unpaidInvoice.getInvoiceNumber());
        verify(invoiceRepository).saveOrUpdate(paidInvoice);
        assertEquals(expectedPaidInvoice, paidInvoice);
      }
    }

    @Test
    void shouldThrowExceptionWhenInvoiceDoesNotExist() {
      doReturn(Optional.empty()).when(invoiceRepository).findById(any(Integer.class));

      int invalidInvoiceNumber = 1234;
      RuntimeException invalidInvoiceNumberException =
          assertThrows(RuntimeException.class, () -> service.markAsPaid(invalidInvoiceNumber));

      assertEquals(
          "No invoice found for invoice number : " + invalidInvoiceNumber,
          invalidInvoiceNumberException.getMessage());
      verify(invoiceRepository).findById(invalidInvoiceNumber);
      verifyNoMoreInteractions(invoiceRepository);
    }
  }
}

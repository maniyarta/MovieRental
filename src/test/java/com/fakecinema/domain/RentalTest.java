package com.fakecinema.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentalTest {
  @Nested
  class Charges {
    @Test
    void shouldComputeChargesWhenDiscountIsNotApplicable() {
      Movie fastAndFurious = new Movie("Fast and Furious", Genre.ACTION, 2003);
      Rental fastAndFuriousMovieRental = new Rental(fastAndFurious, 3);
      assertEquals(6.0, fastAndFuriousMovieRental.charges());
    }

    @Test
    void shouldComputeChargesWhenDiscountIsApplicable() {
      Movie spiderMan = new Movie("Spider Man", Genre.CHILDREN, 2001);
      Rental fastAndFuriousMovieRental = new Rental(spiderMan, 5);
      assertEquals(9.0, fastAndFuriousMovieRental.charges());
    }
  }
}

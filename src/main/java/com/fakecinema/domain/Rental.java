package com.fakecinema.domain;

public class Rental {
  private final Double RENT_PER_DAY = 2.0;
  private final Double DISCOUNT = 0.10;
  private final Movie movie;
  private final Integer rentedDays;

  public Rental(Movie movie, Integer rentedDays) {
    this.movie = movie;
    this.rentedDays = rentedDays;
  }

  public String movieTitle() {
    return movie.title();
  }

  public Double charges() {
    double charges = rentedDays * RENT_PER_DAY;
    return charges - discount(charges);
  }

  private Double discount(Double charges) {
    if (movie.isForChildren()) {
      return charges * DISCOUNT;
    } else {
      return 0D;
    }
  }
}

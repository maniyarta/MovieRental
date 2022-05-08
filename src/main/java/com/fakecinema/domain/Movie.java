package com.fakecinema.domain;

public class Movie {
  private final String title;
  private final Genre genre;
  private final Integer releaseYear;

  public Movie(String title, Genre genre, Integer releaseYear) {
    this.title = title;
    this.genre = genre;
    this.releaseYear = releaseYear;
  }

  public String title() {
    return title;
  }

  public boolean isForChildren() {
    return this.genre == Genre.CHILDREN;
  }
}

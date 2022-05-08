package com.fakecinema.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieTest {
  @Nested
  class IsForChildren {
    @Test
    void shouldReturnTrueWhenMovieGenreIsChildren() {
      Movie movie = new Movie("Harry Potter", Genre.CHILDREN, 1999);
      assertTrue(movie.isForChildren());
    }

    @ParameterizedTest
    @EnumSource(
        mode = EnumSource.Mode.EXCLUDE,
        names = {"CHILDREN"})
    void shouldReturnFalseWhenMovieGenreIsNotChildren(Genre otherThanChildrenGenre) {
      Movie movie = new Movie("Harry Potter", otherThanChildrenGenre, 1999);
      assertFalse(movie.isForChildren());
    }
  }
}

package watchlist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
  private Movie movie1;
  private static Movie movie2;
  private static Movie movie3;
  private static Movie movie4;

  /**
   * Setup tests.
   *
   */
  @BeforeAll
  public static void setup() {
    movie2 = new Movie("Unforgiven", 1992,
        "Retired Old West gunslinger William Munny reluctantly takes on one last job,"
            + "with the help of his old partner Ned Logan and a young man",
        List.of("revenge", "revisionist western", "one last job", "sadist", "gun control"),
        5.25, 42, List.of("Clint Eastwood", "Gene Hackman", "Morgan Freeman"),
        List.of("Clint Eastwood"), List.of("Drama", "Western"),
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
    movie3 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
            + "of greatness are mentored by an instructor who will stop at nothing to realize a"
            + "student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        8.5, 100, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
        List.of("Damien Chazelle"), List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        5);
    movie4 = new Movie("Whiplash", 1948,
        "A struggling artist becomes a New York City prizefighter in an attempt to win"
            + "the affection of the ring promoter's night club singing sister.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        6.4, 100, List.of("Dane Clark", "Alexis Smith", "Zachary Scott"),
        List.of("Lewis Seiler"), List.of("Drama", "Film-Noir", "Sport"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
  }

  @Test
  @DisplayName("Testing the Movie constructor")
  public void testConstructor() {
    // Testing that the Movie object is constructed as expected
    movie1 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams of "
            + "greatness are mentored by an instructor who will stop at nothing to realize a "
            + "student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        8.5, 100, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
        List.of("Damien Chazelle"), List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        5);
    assertEquals("Whiplash", movie1.getName());
    assertEquals(2014, movie1.getYear());
    assertEquals(
        "A promising young drummer enrolls at a cut-throat music conservatory where his "
            + "dreams of greatness are mentored by an instructor who will stop at nothing to "
            + "realize a student’s potential.",
        movie1.getDescription());
    assertEquals(8.5, movie1.getRating());
    assertEquals(List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"), movie1.getActors());
    assertEquals(List.of("Damien Chazelle"), movie1.getDirectors());
    assertEquals(List.of("Drama", "Music"), movie1.getGenre());
    assertEquals(
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        movie1.getImageUrl());
    assertEquals(
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        movie1.getThumbUrl());

    movie1 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams "
            + "of greatness are mentored by an instructor who will stop at nothing to realize a "
            + "student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        10, 100, List.of("Miles Teller",
            "J.K. Simmons",
            "Melissa Benoist"),
        List.of("Damien Chazelle"),
        List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        -1);
    assertEquals(0, movie1.getUserRating(), "The userRating should be 0 if it is lower than 1");
    movie1 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams "
            + "of greatness are mentored by an instructor who will stop at nothing to realize a "
            + "student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        10, 100, List.of("Miles Teller",
            "J.K. Simmons",
            "Melissa Benoist"),
        List.of("Damien Chazelle"),
        List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        11);
    assertEquals(0, movie1.getUserRating(), "The userRating should be 0 if it is higher than 10");

    // Testing that the constructor throws IllegalArgumentExceptions
    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("", 2014,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
              + "of greatness are mentored by an instructor who will stop at nothing to realize a"
              + " student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          8.5, 100, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
          List.of("Damien Chazelle"), List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the name is empty");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash", 1337,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
              + " of greatness are mentored by an instructor who will stop at nothing to realize a "
              + "student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          8.5, 100, List.of("Miles Teller",
              "J.K. Simmons",
              "Melissa Benoist"),
          List.of("Damien Chazelle"),
          List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the year is before 1888");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash", 2030,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
              + " of greatness are mentored by an instructor who will stop at nothing to realize a "
              + "student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          8.5, 100, List.of("Miles Teller",
              "J.K. Simmons",
              "Melissa Benoist"),
          List.of("Damien Chazelle"),
          List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the year is after 2022");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash",
          2014, "",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          8.5, 100,
          List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
          List.of("Damien Chazelle"), List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the description is empty");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash", 2014,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams "
              + "of greatness are mentored by an instructor who will stop at nothing to realize a "
              + "student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          0.5, 100, List.of("Miles Teller",
              "J.K. Simmons",
              "Melissa Benoist"),
          List.of("Damien Chazelle"),
          List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the rating is lower than 1");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash", 2014,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams "
              + "of greatness are mentored by an instructor who will stop at nothing to realize a "
              + "student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          10.5, 100, List.of("Miles Teller",
              "J.K. Simmons",
              "Melissa Benoist"),
          List.of("Damien Chazelle"),
          List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the rating is higher than 10");

    assertThrows(IllegalArgumentException.class, () -> {
      movie1 = new Movie("Whiplash", 2014,
          "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
              + "of greatness are mentored by an instructor who will stop at nothing to realize a"
              + " student’s potential.",
          List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
              "new york city"),
          9, 7, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
          List.of("Damien Chazelle"), List.of("Drama", "Music"),
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
          "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
          0);
    }, "An exception should be thrown when the ratingCount is lower than the rating");
  }

  @Test
  @DisplayName("Testing the equals method")
  public void testEquals() {
    // String and Movie
    assertFalse(movie2.equals("Unforgiven"));

    // Completely different movies
    assertFalse(movie2.equals(movie3));

    // Movies with same name
    assertFalse(movie3.equals(movie4));

    // Same movie
    movie1 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams of"
            + " greatness are mentored by an instructor who will stop at nothing to realize a "
            + "student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        8.5, 100, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
        List.of("Damien Chazelle"), List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
    assertTrue(movie1.equals(movie3));
  }

  @Test
  @DisplayName("Testing the rating method")
  public void testRating() {
    // Movie2 : 5.25 (42 / 8)
    movie2.rate(3);
    assertEquals(3, movie2.getUserRating());
    assertEquals(5.0, movie2.getRating());

    // Movie2 : 5.0 (45 / 9)
    movie2.rate(9);
    assertEquals(9, movie2.getUserRating());
    assertEquals(5.4, movie2.getRating());

    // Movie2 : 5.4 (54 / 10)
    movie2.updateRating(9, 10);
    assertEquals(10, movie2.getUserRating());
    assertEquals(5.5, movie2.getRating());
  }
}

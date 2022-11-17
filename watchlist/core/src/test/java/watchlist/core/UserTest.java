package watchlist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
  private User user;
  private List<Movie> movies = new ArrayList<Movie>();
  private Movie movie1;
  private Movie movie2;
  private Movie movie3;
  private ArrayList<String> movieNames = new ArrayList<String>();

  /**
   * Setup method for UserTest.
   */
  @BeforeEach
  public void setup() {
    user = new User("TestUser");
    movie1 = new Movie("Unforgiven", 1992,
        "Retired Old West gunslinger William Munny reluctantly"
            + "takes on one last job, with the help of his old partner Ned Logan and a young man",
        List.of("revenge", "revisionist western", "one last job", "sadist", "gun control"),
        8.2, 100, List.of("Clint Eastwood", "Gene Hackman", "Morgan Freeman"),
        List.of("Clint Eastwood"),
        List.of("Drama", "Western"),
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
    movie2 = new Movie("Whiplash", 2014,
        "A promising young drummer enrolls at a cut-throat music conservatory where his dreams"
            + "of greatness are mentored by an instructor who will stop at"
            + "nothing to realize a student’s potential.",
        List.of("emotional abuse", "drummer", "music school", "teacher student relationship",
            "new york city"),
        8.5, 100, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"),
        List.of("Damien Chazelle"),
        List.of("Drama", "Music"),
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
    movie3 = new Movie("Spotlight", 2015,
        "The true story of how the Boston Globe uncovered the massive scandal"
            + "of child molestation and cover-up within the local Catholic Archdiocese,"
            + "shaking the entire Catholic Church to its core.",
        List.of("investigation", "child molestation", "sexual abuse",
            "catholic church", "cover up"),
        8.1, 467884, List.of("Mark Ruffalo", "Michael Keaton", "Rachel McAdams"),
        List.of("Tom McCarthy"),
        List.of("Biography", "Crime", "Drama"),
        "https://m.media-amazon.com/images/M/MV5BMjIyOTM5OTIzNV5BMl5BanBnXkFtZTgwMDkzODE2NjE@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BMTc2ODAxMzQzOV5BMl5BanBnXkFtZTgwNjA4NjkyNzE@._V1_.jpg",
        0);
    movies.add(movie1);
    movies.add(movie2);
    movieNames.add(movie1.getName());
    movieNames.add(movie2.getName());
  }

  @Test
  @DisplayName("Testing getName")
  public void testGetName() {
    // Testing that getName works
    assertEquals("TestUser", user.getName());
  }

  @Test
  @DisplayName("Testing setName")
  public void testSetName() {
    // Testing that user's name is TestUser
    assertEquals("TestUser", user.getName());

    // Testing that user's name is changed to TestUser2
    user.setName("TestUser2");
    assertEquals("TestUser2", user.getName());
  }

  @Test
  @DisplayName("Testing getMovieNames")
  public void testGetMovieNames() {
    // Setting user's list of movies
    user.setMovies(movies);

    // Testing that getMovieNames returns the correct movie names
    assertEquals(movieNames, user.getMovieNames());
  }

  @Test
  @DisplayName("Testing watchMovie")
  public void testWatchMovie() {
    // Testing that user has no watched movies
    assertEquals(new ArrayList<>(), user.getMovies());

    // Testing that user can watch first movie
    user.watchMovie(movie1);
    assertEquals(movie1, user.getMovies().get(0));
    ;

    // Testing that user can watch second movie
    // and that user's watched movies is equal to movies List
    user.watchMovie(movie2);
    assertEquals(movie2, user.getMovies().get(1));
    assertEquals(movies, user.getMovies());

    // Should not be able to watch same movie twice
    user.watchMovie(movie1);
    assertEquals(movies, user.getMovies());
  }

  @Test
  @DisplayName("Testing unwatchMovie")
  public void testUnwatchMovie() {
    // Setting user's list of movies
    user.setMovies(movies);

    // Testing that user has watched both movies
    assertEquals(movies, user.getMovies());

    // Testing that first movie gets unwatched
    assertTrue(user.unwatchMovie(movie1.toString()));
    assertFalse(user.getMovies().contains(movie1));

    // Testing that first movie cannot be unwatched twice
    assertFalse(user.unwatchMovie(movie1.toString()));

    // Testing that second movie gets unwatched
    assertTrue(user.unwatchMovie(movie2.toString()));
    assertFalse(user.getMovies().contains(movie2));

    // Testing that user's list of movies is empty after unwatcing both movies
    assertTrue(user.getMovies().isEmpty());
  }

  @Test
  @DisplayName("Testing sortUserlistByName")
  public void testSortUserlistByName() {
    // Setting user's list of movies
    user.setMovies(movies);
    user.watchMovie(movie3);

    // Testing that user's list of movies is sorted by name
    user.sortUserlistByName();
    assertEquals(user.getMovies(), new ArrayList<>(Arrays.asList(movie3, movie1, movie2)));
  }

  @Test
  @DisplayName("Testing sortUserlistByRating")
  public void testSortUserlistByRating() {
    // Setting user's list of movies
    user.setMovies(movies);
    user.watchMovie(movie3);

    // Testing that user's list of movies is sorted by rating
    user.sortUserlistByRating();
    assertEquals(user.getMovies(), new ArrayList<>(Arrays.asList(movie2, movie1, movie3)));
  }

  @Test
  @DisplayName("Testing sortUserlistByYear")
  public void testSortUserlistByYear() {
    // Setting user's list of movies
    user.setMovies(movies);
    user.watchMovie(movie3);

    // Testing that user's list of movies is sorted by year
    user.sortUserlistByYear();
    assertEquals(user.getMovies(), new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));
  }
}

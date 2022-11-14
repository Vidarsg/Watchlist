package watchlist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
  private User user;
  private List<Movie> movies = new ArrayList<Movie>();
  private Movie movie1;
  private Movie movie2;
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
}

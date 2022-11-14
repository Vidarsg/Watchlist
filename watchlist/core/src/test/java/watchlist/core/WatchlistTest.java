package watchlist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WatchlistTest {
  private Movie movie1;
  private Movie movie2;
  private Movie movie3;
  private Watchlist watchlist;


  /**
   * Setup method for WatchlistTest.
   */
  @BeforeEach
  public void setup() {
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
  }

  @Test
  @DisplayName("Testing the Wathclist constructor")
  public void testConstructor() {
    watchlist = new Watchlist(List.of(movie1, movie2, movie3));
    assertEquals(List.of(movie1, movie2, movie3), watchlist.getList());
  }

  @Test
  @DisplayName("Testing addMovie")
  public void testAddMovie() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1)));
    assertEquals(watchlist.getList().size(), 1);
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1)));

    watchlist.addMovie(movie2);
    assertEquals(watchlist.getList().size(), 2);
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2)));
  }

  @Test
  @DisplayName("Testing removeMovie")
  public void testRemoveMovie() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2)));
    assertEquals(watchlist.getList().size(), 2);
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2)));

    watchlist.removeMovie(movie2);
    assertEquals(watchlist.getList().size(), 1);
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1)));
  }

  @Test
  @DisplayName("Testing sortWatchlistByRating")
  public void testSortWatchlistByRating() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.sortWatchlistByRating();
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie2, movie1, movie3)));
  }

  @Test
  @DisplayName("Testing sortWatchlistByYear")
  public void testSortWatchlistByYear() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.sortWatchlistByYear();
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));
  }

  @Test
  @DisplayName("Testing sortWatchlistByName")
  public void testSortWatchlistByName() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.sortWatchlistByName();
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie3, movie1, movie2)));
  }

  @Test
  @DisplayName("Testing setList")
  public void testSetList() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.setList(new ArrayList<>(Arrays.asList(movie1, movie2)));
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2)));
  }

  /* @Test
  @DisplayName("Testing filterWatchlist (by genre)")
  public void testFilterWatchlistByGenre() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.filterWatchlist("Western");
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1)));
  }

  @Test
  @DisplayName("Testing filterWatchlist (by keyword)")
  public void testFilterWatchlistByKeyword() {
    watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2, movie3)));

    watchlist.filterWatchlist("abuse");
    assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie2, movie3)));
  } */
}

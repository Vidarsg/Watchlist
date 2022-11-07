package watchlist.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import watchlist.core.Movie;
import watchlist.core.Watchlist;

public class AppTest extends ApplicationTest {
  private ObjectMapper objectMapper = new ObjectMapper();
  private WatchlistController controller;
  private Watchlist watchlist;
  private Movie movie1;
  private Movie movie2;
  private Movie movie3;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("Test-Watchlist.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    this.watchlist = controller.getWatchlist();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Performs a setup before each test.
   * 
   * @throws IOException If the test json file does not exist
   */
  @BeforeEach
  public void setup() throws IOException {
    List<Movie> fileContent = objectMapper.readValue(AppTest.class.getResourceAsStream(
        "test-movies.json"), new TypeReference<>() {
        });

    movie1 = fileContent.get(0);
    movie2 = fileContent.get(1);
    movie3 = new Movie("Unforgiven", 1992,
        "Retired Old West gunslinger William Munny reluctantly takes"
            + "on one last job, with the help of his old partner Ned Logan and a young man",
        List.of(),
        8.2, 100,
        List.of("Clint Eastwood", "Gene Hackman", "Morgan Freeman"), List.of("Clint Eastwood"),
        List.of("Drama", "Western"),
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
        "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        0);
  }

  // TODO: Add a test for testing the possibility to add a movie to Watchlist?

  @Test
  @DisplayName("Testing the app setup")
  public void testAppSetup() {
    assertNotNull(this.controller);
    assertNotNull(this.watchlist);

    checkMovieList(this.watchlist, movie1, movie2);
  }

  @Test
  @DisplayName("Testing tabs")
  public void testTabs() {
    clickOn("#browserTab");
    clickOn("#profileTab");
  }

  @Test
  @DisplayName("Testing user watching a movie")
  public void testWatchMovie() {
    final ListView<Movie> listView = lookup("#moviebrowser").query();
    final ListView<Movie> watchedMovies = lookup("#watchedMovies").query();
    final Iterator<Node> listItems = listView.lookupAll(".list-cell").iterator();

    clickOn("#browserTab");

    clickOn(listItems.next());
    clickOn("#watchMovieButton");
    checkListView(watchedMovies, movie1);

    clickOn(listItems.next());
    clickOn("#watchMovieButton");
    checkListView(watchedMovies, movie1, movie2);
  }

  @Test
  @DisplayName("Testing user watching a movie")
  public void testUnwatchMovie() {
    final ListView<Movie> listView = lookup("#watchedMovies").query();
    // TODO: Add movies (movie1 and movie2) to watchedMovies without using the
    // watchMovie methods...

    // Temporary solution
    testWatchMovie();
    // ! Temporary solution

    clickOn("#profileTab");

    clickOn(listView.lookup(".list-cell"));
    clickOn("#unwatchMovieButton");
    checkListView(listView, movie2);

    clickOn(listView.lookup(".list-cell"));
    clickOn("#unwatchMovieButton");
    assertNull(listView.getItems());
  }

  @Test
  @DisplayName("Testing user rating a movie")
  public void testRateMovie() {
    testWatchMovie();

    clickOn("#profileTab");

    final ListView<Movie> listView = lookup("#watchedMovies").query();

    clickOn(listView.lookup(".list-cell"));
    clickOn("#ratingSlider");
    assertEquals(6, listView.getItems().get(0).getUserRating());

    moveBy(-50, 0);
    drag().dropBy(150, 0);
    assertEquals(10, listView.getItems().get(0).getUserRating());
  }

  /**
   * Checks whether a listView consists of a list of Movies.
   * 
   * @param listView The listview to compare
   * @param movie    The list of movies to compare to
   */
  private void checkListView(ListView<Movie> listView, Movie... movies) {
    ObservableList<Movie> items = listView.getItems();
    assertEquals(movies.length, items.size());
    for (Movie m : movies) {
      if (!items.contains(m)) {
        fail("At least one movie is not included in the ListView.");
      }
    }
  }

  /**
   * Checks whether a watchlist consists of a list of Movies.
   * 
   * @param watchlist The watchlist to compare
   * @param movie     The list of movies to compare to
   */
  private void checkMovieList(Watchlist watchlist, Movie... movies) {
    List<Movie> items = watchlist.getList();
    assertEquals(movies.length, items.size());
    for (Movie m : movies) {
      if (!items.contains(m)) {
        fail("At least one movie is not included in the WatchList.");
      }
    }
  }

  /**
   * Closes the opened files after each test to prevent complications between
   * tests.
   */
  @AfterEach
  public void tearDown() {
    try {
      Files.deleteIfExists(Paths.get(System.getProperty("user.home"), "TestUser.json"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

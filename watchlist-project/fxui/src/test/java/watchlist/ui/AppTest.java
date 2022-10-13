package watchlist.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import watchlist.core.Movie;
import watchlist.core.Watchlist;

public class AppTest extends ApplicationTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private WatchlistController controller;
    private Watchlist watchlist;
    private Movie movie1, movie2, movie3;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Watchlist.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        this.watchlist = controller.getWatchlist();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @BeforeEach
    public void setup() throws IOException {
        //controller.setUsername("TestUser");
        List<Movie> fileContent = objectMapper.readValue(AppTest.class.getResourceAsStream("test-movies.json"), new TypeReference<>(){});

        movie1 = fileContent.get(0);
        movie2 = fileContent.get(1);
        movie3 = new Movie("Unforgiven", 1992, "Retired Old West gunslinger William Munny reluctantly takes on one last job, with the help of his old partner Ned Logan and a young man", 8.2, List.of("Clint Eastwood", "Gene Hackman", "Morgan Freeman"), List.of("Clint Eastwood"), List.of("Drama", "Western"), "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL__QL50.jpg");
    }

    // TODO: Add a test for testing the possibility to add a movie to Watchlist?
        /* Something like this?
            this.watchlist.addMovie(movie3);
            this.controller.updateMoviebrowser();
        */

    // TODO: None of the tests are able to run because of "java.lang.IllegalAccessError: class org.testfx.toolkit.impl.ToolkitServiceImpl (in unnamed module @0x22635ba0) cannot access class com.sun.javafx.application.ParametersImpl (in module javafx.graphics) because module javafx.graphics does not export com.sun.javafx.application to unnamed module @0x22635ba0"

    @Test
    @DisplayName("Testing the app setup")
    public void testAppSetup() {
        assertNotNull(this.controller);
        assertNotNull(this.watchlist);

        //checkMovieList(this.watchlist, movie1, movie2);
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
        final ListView<String> listView = lookup("#moviebrowser").query();
        clickOn("#browserTab");

        clickOn(listView.getItems().get(0));
        clickOn("#watchMovieButton");
        //checkListView(listView, movie1);

        clickOn(listView.getItems().get(1));
        clickOn("#watchMovieButton");
        //checkListView(listView, movie1, movie2);
    }

    @Test
    @DisplayName("Testing user watching a movie")
    public void testUnwatchMovie() {
        final ListView<String> listView = lookup("#watchedMovies").query();
        // TODO: Add movies (movie1 and movie2) to watchedMovies without using the watchMovie methods...
        
            // Temporary solution
            testWatchMovie();
            // ! Temporary solution

        clickOn("#profileTab");

        clickOn(listView.getItems().get(0));
        clickOn("#unwatchMovieButton");
        //checkListView(listView, movie2);

        clickOn(listView.getItems().get(0));
        clickOn("#unwatchMovieButton");
        assertNull(listView.getItems());
    }

    // Help methods

    private void checkListView(ListView<String> listView, Movie... movie) {
        // TODO: Add testing to compare a ListViews (param listView) items to expected item (param movie)
    }

    private void checkMovieList(Watchlist watchlist, Movie... movies) {
        // TODO: Add testing for whether watchlist consists of movie1 and movie2
    }
}

package watchlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WatchlistController {
    @FXML
    private ListView<String> moviebrowser;

    public void initialize() {
        updateMovies();
    }

    public void updateMovies() {
        ArrayList<Movie> list = new ArrayList<Movie>(Arrays.asList(new Movie("Movie A", 2020), new Movie("Movie B", 2021), new Movie("Movie C", 2022)));
        moviebrowser.setItems(FXCollections.observableArrayList(list.stream().map(x -> x.toString()).collect(Collectors.toList())));
    }
}

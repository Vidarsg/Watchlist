package watchlist;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WatchlistController {
    @FXML
    private ListView<String> moviebrowser;

    public void initialize() {
        List<String> list = List.of("a, 2022","b, 2019");
        moviebrowser.setItems(FXCollections.observableList(list));
    }
}

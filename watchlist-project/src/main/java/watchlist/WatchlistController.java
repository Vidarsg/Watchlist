package watchlist;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WatchlistController {
    @FXML
    private ListView<String> moviebrowser;
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

    public void initialize() {
        updateMovies();
    }

    public void updateMovies() {
        handleLoad();
        moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
    }

    public void handleLoad() {
        try {
            list = saveLoadHandler.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

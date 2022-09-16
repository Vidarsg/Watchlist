package watchlist;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class WatchlistController {
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

    
    @FXML
    private ListView<String> moviebrowser;
    @FXML
    private TextField addMovieTitle;
    @FXML
    private TextField addMovieYear;
    @FXML
    private Text feedbackBox;

   
    void initialize() {
        updateMovies();
    }

    private void updateMovies() {
        handleLoad();
        moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
    }

    private void handleLoad() {
        try {
            list = saveLoadHandler.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddMovie() {
        redBorder(false, addMovieTitle, addMovieYear);
        feedbackBox.setText("");

        String title = addMovieTitle.getText();
        String strYear = addMovieYear.getText();
        int year = 0;
        
        try {
            year = Integer.parseInt(strYear);
            list.addMovie(new Movie(title, year));
            
            addMovieTitle.setText("");
            addMovieYear.setText("");
        } catch (NumberFormatException e) {
            redBorder(true, addMovieYear);
            feedbackBox.setText("Vennligst fyll inn et gyldig årstall");
        } catch (Exception e) {
            if (title.isEmpty()) {redBorder(true, addMovieTitle);}
            if (year<1888) {redBorder(true, addMovieYear);}
            
            feedbackBox.setText(e.getMessage());
        }

        updateMovies();
    }

    private void redBorder(boolean set, TextField... tf) {
        if (set) {for (TextField t : tf) {t.setStyle("-fx-border-color:red");}}
        else {for (TextField t : tf) {t.setStyle("-fx-border-color:initial");}}
    }


}

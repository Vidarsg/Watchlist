package watchlist.ui;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import watchlist.core.*;
import watchlist.json.SaveLoadHandler;

public class WatchlistController {
    private User user;
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

    
    @FXML
    private TextField watchMovieTitle;
    @FXML
    private ListView<String> moviebrowser;
    @FXML
    private TextField addMovieTitle;
    @FXML
    private TextField addMovieYear;
    @FXML
    private Text feedbackBoxBrowsing;


    @FXML
    private TextField unwatchMovieTitle;
    @FXML
    private Text feedbackBoxProfile;

   
    public void initialize() {
        user = new User("Username", 21);
        list = new Watchlist();
        handleLoadResourceList("movies");
        updateMovies();
    }

    private void updateMovies() {
        moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
    }

    // Methods for file handling

    public void handleLoadResourceList(String filename) {
        try {
            list.setList(saveLoadHandler.loadResourceList(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLoadUserList() {
        if (saveLoadHandler.getSaveFilePath() == null) {
            saveLoadHandler.setSaveFile("test");
        }
        try {
            list.setList(saveLoadHandler.loadUserList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSaveUserList() {
        if (saveLoadHandler.getSaveFilePath() == null) {
            saveLoadHandler.setSaveFile("test");
        }
        try {
            saveLoadHandler.saveUserList(list.getList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ! Methods for file handling


    // Handle methods for browsing

    @FXML
    void handleAddMovie() {
        redBorder(false, addMovieTitle, addMovieYear);
        feedbackBoxBrowsing.setText("");

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
            feedbackBoxBrowsing.setText("Please fill in a valid year");
        } catch (Exception e) {
            if (title.isEmpty()) {redBorder(true, addMovieTitle);}
            if (year<1888) {redBorder(true, addMovieYear);}
            
            feedbackBoxBrowsing.setText(e.getMessage());
        }

        updateMovies();
    }

    @FXML
    void handleWatchMovie() {
        String title = watchMovieTitle.getText();
        if (title.isEmpty()) {feedbackBoxProfile.setText("Please choose a movie from the list");}
        else {
            for (Movie m : list.getList()) {
                if (m.toString().equals(title)) {
                    user.watchMovie(m);
                    return;
                }
            }
        }
    }

    // ! Handle methods for browsing


    // Handle methods for profile

    @FXML
    void handleUnwatchMovie() {
        String title = unwatchMovieTitle.getText();
        if (title.isEmpty()) {feedbackBoxProfile.setText("Please choose a movie from the list");}
        else {
            if (! user.unwatchMovie(title)) {
                feedbackBoxProfile.setText("You have not watched this movie...");
            }
        }
    }

    // ! Handle methods for profile


    // Help methods for GUI

    private void redBorder(boolean set, TextField... tf) {
        if (set) {for (TextField t : tf) {t.setStyle("-fx-border-color:red");}}
        else {for (TextField t : tf) {t.setStyle("-fx-border-color:initial");}}
    }

    // ! Help methods for GUI

}

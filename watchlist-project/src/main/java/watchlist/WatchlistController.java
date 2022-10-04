package watchlist;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class WatchlistController {
    private User user;
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
    private Movie activeBrowserMovie;
    private Movie activeProfileMovie;
    

    @FXML
    private ListView<String> moviebrowser;
    @FXML
    private TextField watchMovieTitle;
    @FXML
    private Button watchMovieButton;
    @FXML
    private TextField addMovieTitle;
    @FXML
    private TextField addMovieYear;
    @FXML
    private Text feedbackBoxBrowsing;


    @FXML
    private ListView<String> watchedMovies;
    @FXML
    private TextField unwatchMovieTitle;
    @FXML
    private Button unwatchMovieButton;
    @FXML
    private Text feedbackBoxProfile;

   
    public void initialize() {
        user = new User("Username", 21);
        list = new Watchlist();
        handleLoad("watchlist");
        updateMoviebrowser();
        updateGUI();
    }

    private void updateMoviebrowser() {
        moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
        setListeners(moviebrowser, watchMovieTitle, watchMovieButton);
    }

    private void updateWatchedMovies() {
        watchedMovies.setItems(FXCollections.observableArrayList(user.getMovies().stream().map(x -> x.toString()).collect(Collectors.toList())));
        setListeners(watchedMovies, unwatchMovieTitle, unwatchMovieButton);
    }


    // Methods for file handling

    private void handleLoad(String filename) {
        try {
            list = saveLoadHandler.load(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
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

        updateMoviebrowser();
    }

    @FXML
    void handleWatchMovie() {
        feedbackBoxBrowsing.setText("");
        String title = watchMovieTitle.getText();
        if (title.isEmpty()) {feedbackBoxBrowsing.setText("Please choose a movie from the list");}
        else {
            for (Movie m : list.getList()) {
                if (m.toString().equals(title)) {
                    feedbackBoxBrowsing.setText("Watched movie "+m.toString());
                    user.watchMovie(m);
                    updateWatchedMovies();
                }
            }
        }
        updateGUI();
    }

    // ! Handle methods for browsing


    // Handle methods for profile

    @FXML
    void handleUnwatchMovie() {
        feedbackBoxProfile.setText("");
        String title = unwatchMovieTitle.getText();
        if (title.isEmpty()) {feedbackBoxProfile.setText("Please choose a movie from the list");}
        else {
            if (! user.unwatchMovie(title)) {
                feedbackBoxProfile.setText("You have not watched this movie...");
            }
            else {
                for (Movie m : user.getMovies()) {
                    if (m.toString().equals(title)) {
                        user.unwatchMovie(m.getTitle());
                        updateWatchedMovies();
                    }
                }
            }
        }
        updateGUI();
    }

    // ! Handle methods for profile


    // Help methods for GUI

    private void updateGUI() {
        updateMoviebrowser();
        updateWatchedMovies();
    }

    private void redBorder(boolean set, TextField... tf) {
        if (set) {for (TextField t : tf) {t.setStyle("-fx-border-color:red");}}
        else {for (TextField t : tf) {t.setStyle("-fx-border-color:initial");}}
    }

    private void setListeners(ListView<String> lv, TextField tf, Button btn) {
        // Partially collected from https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2
        // We have customized it to our own project and added comments to show our understanding
        // Add a new listener to the listItems of WatchList
        lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                // Iterate through all movies to find out which movie was clicked
                for (Movie m : list.getList()) {
                    if (m.toString().equals(newValue)) {
                        Movie activeMovie = m;
                        
                        // Set the value of the textField to the chosen Movie
                        tf.setText(activeMovie.toString());
                        
                        // Enable the Watch-/Unwatch-button
                        btn.setDisable(false);

                        if (lv.equals(watchedMovies)) {activeProfileMovie = activeMovie;}
                        else {activeBrowserMovie = activeMovie;}

                        return;
                    }
                }

            }
        });
    }

    // ! Help methods for GUI

}

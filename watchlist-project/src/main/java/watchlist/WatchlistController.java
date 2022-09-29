package watchlist;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private ListView<String> watchedMovies;
    @FXML
    private TextField unwatchMovieTitle;
    @FXML
    private Text feedbackBoxProfile;

   
    public void initialize() {
        user = new User("Username", 21);
        list = new Watchlist();
        handleLoad("watchlist");
        updateMoviebrowser();
    }

    private void updateMoviebrowser() {
        moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
        setListeners(moviebrowser, list.getList());
    }

    private void updateWatchedMovies() {
        watchedMovies.setItems(FXCollections.observableArrayList(user.getMovies().stream().map(x -> x.toString()).collect(Collectors.toList())));
        setListeners(watchedMovies, user.getMovies());
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
        String title = watchMovieTitle.getText();
        if (title.isEmpty()) {feedbackBoxBrowsing.setText("Please choose a movie from the list");}
        else {
            for (Movie m : list.getList()) {
                if (m.toString().equals(title)) {
                    user.watchMovie(m);
                    updateWatchedMovies();
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
            else {
                for (Movie m : user.getMovies()) {
                    if (m.toString().equals(title)) {
                        user.unwatchMovie(m.getTitle());
                        updateWatchedMovies();
                    }
                }
            }
        }
    }

    // ! Handle methods for profile


    // Help methods for GUI

    private void redBorder(boolean set, TextField... tf) {
        if (set) {for (TextField t : tf) {t.setStyle("-fx-border-color:red");}}
        else {for (TextField t : tf) {t.setStyle("-fx-border-color:initial");}}
    }

    private void setListeners(ListView<String> lv, ArrayList<Movie> al) {
        // Partially collected from https://stackoverflow.com/questions/12459086/how-to-perform-an-action-by-selecting-an-item-from-listview-in-javafx-2
        // I have customized it to my own project and added comments to show my understanding
        // Add a new listener to the listItems of Utvalg
        lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                // Iterate through all Utvalg to find out which Utvalg was clicked
                for (Movie m : al) {
                    if (m.getTitle().equals(newValue)) {
                        newValue = m.getTitle();
                        
                        // Set the value of removeUtvalgName to the clicked Utvalg
                        watchMovieTitle.setText(m.getTitle());
                        
                        // Enable utvalgContent (add/remove medlem and medlemlist)
                        // utvalgContent.setDisable(false);

                        updateUtvalgGUI();
                        
                        // Add a new listener to the listItems of Medlemmer
                        medlemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                for (Student s : u.getMedlemmer()) {
                                    if (s.toString().equals(newValue)) {removeMedlemName.setText(s.toString());}
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // ! Help methods for GUI

}

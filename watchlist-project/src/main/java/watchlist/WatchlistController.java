package watchlist;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class WatchlistController {
    private User user;
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
    private Movie activeBrowserMovie; // not in use yet
    private Movie activeProfileMovie; // not in use yet
    
// BROWSER FIELDS
    @FXML
    private ListView<String> moviebrowser;
    @FXML
    private Text feedbackBoxBrowsing;

    @FXML
    private TextField watchMovieTitle;
    @FXML
    private Button watchMovieButton;

    @FXML
    private TextField addMovieTitle;
    @FXML
    private TextField addMovieYear;
    @FXML
    private Label BrowseUsername;

    // Information section of the browser
    @FXML
    private FlowPane infoBox;
    @FXML
    private ImageView infoImage;
    @FXML
    private Text infoTitle;
    @FXML
    private Text infoYear;
    @FXML
    private Label infoGenre;
    @FXML
    private Text infoDesc;
    @FXML
    private Text infoRating;
    @FXML
    private Text infoDirector;
    @FXML
    private Text infoActors;
// ! BROWSER FIELDS

// PROFILE FIELDS
    @FXML
    private ListView<String> watchedMovies;
    @FXML
    private Text feedbackBoxProfile;
    @FXML
    private Label ProfileUsername;
    
    @FXML
    private TextField unwatchMovieTitle;
    @FXML
    private Button unwatchMovieButton;
// ! PROFILE FIELDS

   
    public void initialize() {
        user = new User("Username", 21);
        list = new Watchlist();
        handleLoad("watchlist");
        // Temporary replacement for more complex movie-import from JSON
        list.addMovie(new Movie("Witness for the Prosecution", 1958, "A veteran British barrister must defend his client in a murder trial that has surprise after surprise.", 8.4, Arrays.asList("Tyrone Power", "Marlene Dietrich", "Charles Laughton"), Arrays.asList("Billy Wilder"), Arrays.asList("Crime", "Drama", "Mystery"), "https://m.media-amazon.com/images/M/MV5BNDQwODU5OWYtNDcyNi00MDQ1LThiOGMtZDkwNWJiM2Y3MDg0XkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BNDQwODU5OWYtNDcyNi00MDQ1LThiOGMtZDkwNWJiM2Y3MDg0XkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_.jpg"));
        list.addMovie(new Movie("UP", 2009, "78-year-old Carl Fredricksen travels to Paradise Falls in his house equipped with balloons, inadvertently taking a young stowaway.", 8.3, Arrays.asList("Edward Asner", "Jordan Nagai", "John Ratzenberger"), Arrays.asList("Pete Docter", "Bob Peterson"), Arrays.asList("Animation", "Adventure", "Comedy"), "https://m.media-amazon.com/images/M/MV5BMTk3NDE2NzI4NF5BMl5BanBnXkFtZTgwNzE1MzEyMTE@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BMTk3NDE2NzI4NF5BMl5BanBnXkFtZTgwNzE1MzEyMTE@._V1_UX182_CR0,0,182,268_AL__QL50.jpg"));
        
        updateGUI();
    }

    // Fetches username from Login.fxml and displays it in Watchlist.fxml

    public void setUsername(String name) {    
        BrowseUsername.setText(name);
        ProfileUsername.setText(name);
    }

    private void updateMoviebrowser() {
        if (list.getList().size() > 0) {
            moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
            setListeners(moviebrowser, watchMovieTitle, watchMovieButton);
        }
    }

    private void updateWatchedMovies() {
        if (user.getMovies().size() > 0) {
            watchedMovies.setItems(FXCollections.observableArrayList(user.getMovies().stream().map(x -> x.toString()).collect(Collectors.toList())));
            setListeners(watchedMovies, unwatchMovieTitle, unwatchMovieButton);
        }
    }


    // Methods for file handling

    private void handleLoad(String filename) {
        try {
            list.setList(saveLoadHandler.load(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        }
        updateWatchedMovies();
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

    private void showInfo(Movie movie) {
        if (movie == null) {infoBox.setVisible(false);}
        else {
            infoBox.setVisible(true);
            if (movie.getImage_url() != null) {infoImage.setImage(movie.getImage());}
            else {infoImage.setImage(null);}
            infoTitle.setText(movie.getName());
            infoYear.setText(String.valueOf(movie.getYear()));
            infoDesc.setText(movie.getDesc());
            // Since this branch is behind on certain objects and their methods, we have to comment out these calls
            infoRating.setText(movie.getRating()+"/10" /*("+  movie.getRatingCount()+ ")"*/);

            StringBuilder sb = new StringBuilder();
            if (movie.getDirectors().size()>0) {
                for (String d : movie.getDirectors()) {sb.append(d+", ");}
                sb.deleteCharAt(sb.length()-2);
            } else {sb.append("Unknown");}
            infoDirector.setText(sb.toString());

            sb = new StringBuilder();
            if (movie.getActors().size()>0) {
                for (String a : movie.getActors()) {sb.append(a+", ");}
                sb.deleteCharAt(sb.length()-2);
            } else {sb.append("None");}
            infoActors.setText(sb.toString());

            sb = new StringBuilder();
            if (movie.getGenre().size()>0) {
                for (String g : movie.getGenre()) {sb.append(g+" - ");}
                sb.delete(sb.length()-3,sb.length());
            }
            infoGenre.setText(sb.toString());
        }
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

                        showInfo(activeMovie);
                        
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

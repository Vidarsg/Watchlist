package watchlist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class WatchlistController {
    private User user;
    private Watchlist list;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
    private Movie activeBrowserMovie;
    private Movie activeProfileMovie;
    
// BROWSER FIELDS
    @FXML
    private ListView<String> moviebrowser;
    @FXML
    private Text feedbackBoxBrowsing;

    @FXML
    private TextField watchMovieTitle;
    @FXML
    private Button watchMovieButton;

    // For listeners on list-items
    private ChangeListener<String> browserChangeListener;

    @FXML
    private TextField addMovieTitle;
    @FXML
    private TextField addMovieYear;

    // Information section of the browser
    @FXML
    private AnchorPane infoBox;
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
    private TextField unwatchMovieTitle;
    @FXML
    private Button unwatchMovieButton;

    // For listeners on list-items
    private ChangeListener<String> profileChangeListener;

    // Information section of the profile
    @FXML
    private AnchorPane infoBoxProfile;
    @FXML
    private ImageView infoImageProfile;
    @FXML
    private Text infoTitleProfile;
    @FXML
    private Text infoYearProfile;
    @FXML
    private Label infoGenreProfile;
    @FXML
    private Text infoDescProfile;
    @FXML
    private Text infoRatingProfile;
    @FXML
    private Text infoDirectorProfile;
    @FXML
    private Text infoActorsProfile;
// ! PROFILE FIELDS

   
    public void initialize() {
        user = new User("Username", 21);
        list = new Watchlist();
        handleLoad("movies");

        browserChangeListener = generateListener(moviebrowser, watchMovieTitle, watchMovieButton);
        profileChangeListener = generateListener(watchedMovies, unwatchMovieTitle, unwatchMovieButton);
        
        updateMoviebrowser();

        updateGUI();
    }
    
    private ChangeListener<String> generateListener(ListView<String> lv, TextField tf, Button btn) {
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(oldValue+" -> "+newValue);
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

                        updateGUI();

                        break;
                    }
                }

            }
        };
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
                }
            }
        }
        updateWatchedMovies();
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
        updateBrowserGUI();
        updateProfileGUI();
    }

    private void updateBrowserGUI() {
        if (activeBrowserMovie != null) {
            showInfo(activeBrowserMovie, infoBox);
        }
    }

    private void updateMoviebrowser() {
        if (list.getList().size() > 0) {
            moviebrowser.setItems(FXCollections.observableArrayList(list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
            setListeners(moviebrowser);
        }
    }

    private void updateProfileGUI() {
        if (activeProfileMovie != null) {
            showInfo(activeProfileMovie, infoBoxProfile);
        }
    }

    private void updateWatchedMovies() {
        if (user.getMovies().size() > 0) {
            watchedMovies.setItems(FXCollections.observableArrayList(user.getMovies().stream().map(x -> x.toString()).collect(Collectors.toList())));
            setListeners(watchedMovies);
            feedbackBoxProfile.setText("");
        } else {
            watchedMovies.setItems(null);
            showInfo(null, infoBoxProfile);
            feedbackBoxProfile.setText("Nothing to show...");
        }
    }


    private void redBorder(boolean set, TextField... tf) {
        if (set) {for (TextField t : tf) {t.setStyle("-fx-border-color:red");}}
        else {for (TextField t : tf) {t.setStyle("-fx-border-color:initial");}}
    }

    private void showInfo(Movie movie, Pane pane) {
        if (movie == null) {pane.setVisible(false);}
        else {
            pane.setVisible(true);

            ObservableList<Node> children = pane.getChildren();
            ImageView img = (ImageView) children.get(1);
            if (movie.getImage_url() != null) {img.setImage(new Image(movie.getImage_url()));}
            else {img.setImage(null);}

            FlowPane box = (FlowPane) children.get(0);
            ObservableList<Node> f = box.getChildren();
            Text title = (Text) f.get(0);
            Text year = (Text) f.get(1);
            Label genre = (Label) f.get(2);
            Text desc = (Text) f.get(3);
            Text rating = (Text) f.get(4);
            // 5th child is a label
            Text director = (Text) f.get(6);
            // 7th child is a label
            Text actors = (Text) f.get(8);
            
            title.setText(movie.getName());
            year.setText(String.valueOf(movie.getYear()));
            desc.setText(movie.getDesc());
            // Since this branch is behind on certain objects and their methods, we have to comment out these parts
            rating.setText(movie.getRating()+"/10" /*("+  movie.getRatingCount()+ ")"*/);

            StringBuilder sb = new StringBuilder();
            if (movie.getDirectors().size()>0) {
                for (String d : movie.getDirectors()) {sb.append(d+", ");}
                sb.deleteCharAt(sb.length()-2);
            } else {sb.append("Unknown");}
            director.setText(sb.toString());

            sb = new StringBuilder();
            if (movie.getActors().size()>0) {
                for (String a : movie.getActors()) {sb.append(a+", ");}
                sb.deleteCharAt(sb.length()-2);
            } else {sb.append("None");}
            actors.setText(sb.toString());

            sb = new StringBuilder();
            if (movie.getGenre().size()>0) {
                for (String g : movie.getGenre()) {sb.append(g+" - ");}
                sb.delete(sb.length()-3,sb.length());
            }
            genre.setText(sb.toString());
        }
    }

    /**
     * Used to add listeners to a listView based on which ListView it is being called upon.
     * @param lv
     */
    private void setListeners(ListView<String> lv) {
        if (lv.equals(watchedMovies)) {
            lv.getSelectionModel().selectedItemProperty().removeListener(profileChangeListener);
            lv.getSelectionModel().selectedItemProperty().addListener(profileChangeListener);
        } else {
            lv.getSelectionModel().selectedItemProperty().removeListener(browserChangeListener);
            lv.getSelectionModel().selectedItemProperty().addListener(browserChangeListener);
        }
    }

    // ! Help methods for GUI

}

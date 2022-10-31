package watchlist.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import watchlist.core.Movie;
import watchlist.core.User;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;

/**
 * Controller for app window.
 *
 * @author IT1901 gruppe 63
 */
public class WatchlistController {
  private User user;
  private Watchlist list;
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
  private ObjectMapper objectMapper = new ObjectMapper();
  private Movie activeBrowserMovie;
  private Movie activeProfileMovie;

  @FXML
  private String movieResource;
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
  @FXML
  private Label browseUsername;

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
  private FlowPane infoGenre;
  @FXML
  private Text infoDesc;
  @FXML
  private Text infoRating;
  @FXML
  private Text infoDirector;
  @FXML
  private Text infoActors;

  @FXML
  private FlowPane ratingStars;
  @FXML
  private Slider ratingSlider;
  // ! BROWSER FIELDS

  // PROFILE FIELDS
  @FXML
  private ListView<String> watchedMovies;
  @FXML
  private Text feedbackBoxProfile;
  @FXML
  private Label profileUsername;

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
  private FlowPane infoGenreProfile;
  @FXML
  private Text infoDescProfile;
  @FXML
  private Text infoRatingProfile;
  @FXML
  private Text infoDirectorProfile;
  @FXML
  private Text infoActorsProfile;
  // ! PROFILE FIELDS

  /**
   * Runs watchlist.fxml and creates new user and watchlist objects.
   */
  public void initialize() {
    user = new User("TestUser");
    list = new Watchlist();
    // handleLoadResourceList(movieResource);
    handleLoadResourceList("movies");

    ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (activeBrowserMovie != null) {
          activeBrowserMovie.updateRating(oldValue.intValue() + 1, newValue.intValue() + 1);
          updateRating(newValue.intValue());
        } else {
          ratingSlider.setDisable(true);
        }
      }
    });

    browserChangeListener = generateListener(moviebrowser, watchMovieTitle, watchMovieButton);
    profileChangeListener = generateListener(watchedMovies, unwatchMovieTitle, unwatchMovieButton);

    updateMoviebrowser();

    updateGui();
  }

  /**
   * Made for testing of the controller.
   *
   * @return The Watchlist which is represented in this instance of the app
   */
  public Watchlist getWatchlist() {
    return this.list;
  }

  /**
   * <p>
   * Used to generate event listeners to items of a ListView.
   * Creates a ChangeListener to use
   * in the methods addListener() and removeListener():
   * </p>
   *
   * <pre>
   * ListView.getSelectionModel().selectedItemProperty().addListener(ChangeListener);
   * </pre>
   *
   * <pre>
   * ListView.getSelectionModel().selectedItemProperty().removeListener(ChangeListener);
   * </pre>
   *
   * @param listView  The ListView where the items are located
   * @param textField The corresponding TextField to keep track of selected item
   * @param button    The corresponding Button to watch/unwatch movies
   * @return A ChangeListener to use in the methods addListener() and
   *         removeListener()
   */
  private ChangeListener<String> generateListener(
      ListView<String> listView, TextField textField, Button button) {
    return new ChangeListener<String>() {
      @Override
      public void changed(
          ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Iterate through all movies to find out which movie was clicked
        for (Movie m : list.getList()) {
          if (m != null) {
            if (m.toString().equals(newValue)) {
              Movie activeMovie = m;

              // Set the value of the textField to the chosen Movie
              textField.setText(activeMovie.toString());

              // Enable the Watch-/Unwatch-button
              button.setDisable(false);

              if (listView.equals(watchedMovies)) {
                activeProfileMovie = activeMovie;
              } else {
                activeBrowserMovie = activeMovie;
              }

              updateGui();

              break;
            }
          }
        }

      }
    };
  }

  /**
   * Creates a user object.
   * Fetches username from Login.fxml and displays it in Watchlist.fxml.
   */
  public void setUsername(String name) {
    user = new User(name);
    browseUsername.setText(name);
    // profileUsername.setText(name);
    handleLoadUserList();
    updateWatchedMovies();
  }

  // Methods for file handling

  /**
   * Loads a files content as the content of the Watchlist's list of movies.
   *
   * @param filename The file to load the list from
   */
  public void handleLoadResourceList(String filename) {
    try (InputStream inputStream = WatchlistController.class.getResourceAsStream(filename + ".json")) {
      list.setList(objectMapper.readValue(inputStream, new TypeReference<>() {
      }));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the users local list into the application. If no list is saved locally
   * for the user, the
   * method will create a new one.
   */
  public void handleLoadUserList() {
    if (saveLoadHandler.getSaveFilePath() == null) {
      saveLoadHandler.setSaveFile(user.getName());
    }
    try {
      user.setMovies(saveLoadHandler.loadUserList());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Save the users list in the application into their local savefile.
   * If no list is saved locally for
   * the user, the method will create a new one.
   */
  public void handleSaveUserList() {
    if (saveLoadHandler.getSaveFilePath() == null) {
      saveLoadHandler.setSaveFile(user.getName());
    }
    try {
      saveLoadHandler.saveUserList(user.getMovies());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // ! Methods for file handling

  // Handle methods for browsing

  /*
   * <i>*FXML-method*</i>
   * <p>
   * Marks a movie from the Watchlist browser as watched
   * by the user and adds it to the users personal
   * list of watched movies.
   * </p>
   */
  @FXML
  void handleWatchMovie() {
    feedbackBoxBrowsing.setText("");
    String title = watchMovieTitle.getText();
    if (title.isEmpty()) {
      feedbackBoxBrowsing.setText("Please choose a movie from the list");
    } else {
      for (Movie m : list.getList()) {
        if (m != null) {
          if (m.toString().equals(title)) {
            feedbackBoxBrowsing.setText("Watched movie " + m.toString());
            user.watchMovie(m);
          }
        }
      }
    }
    updateWatchedMovies();
    handleSaveUserList();
  }

  // ! Handle methods for browsing

  // Handle methods for profile

  /*
   * <i>*FXML-method*</i>
   * <p>
   * Marks a movie from the Watchlist browser as <b>not</b> watched by the user
   * and removes it from
   * the users personal list of watched movies.
   * </p>
   */
  @FXML
  void handleUnwatchMovie() {
    feedbackBoxProfile.setText("");
    String title = unwatchMovieTitle.getText();
    if (title.isEmpty()) {
      feedbackBoxProfile.setText("Please choose a movie from the list");
    } else {
      if (!user.unwatchMovie(title)) {
        feedbackBoxProfile.setText("You have not watched this movie...");
      }
    }
    updateWatchedMovies();
    handleSaveUserList();
  }

  // ! Handle methods for profile

  // Help methods for GUI

  /**
   * Updates the whole Graphical User Interface (GUI) of the application.
   */
  private void updateGui() {
    updateBrowserGui();
    updateProfileGui();
  }

  /**
   * Updates the Graphical User Interface (GUI) of the browser-part of the
   * application.
   */
  private void updateBrowserGui() {
    if (activeBrowserMovie != null) {
      showInfo(activeBrowserMovie, infoBox);
    }
  }

  /**
   * Updates the ListView of the browser-part of the application.
   */
  private void updateMoviebrowser() {
    if (list.getList().size() > 0) {
      moviebrowser.setItems(FXCollections
          .observableArrayList(
              list.getList().stream().map(x -> x.toString()).collect(Collectors.toList())));
      setListeners(moviebrowser);
    }
  }

  private void updateProfileGui() {
    if (activeProfileMovie != null) {
      showInfo(activeProfileMovie, infoBoxProfile);
    }
  }

  /**
   * Updates the ListView of the profile-part of the application.
   */
  private void updateWatchedMovies() {
    if (user.getMovies().size() > 0) {
      watchedMovies.setItems(FXCollections
          .observableArrayList(
              user.getMovies().stream().map(x -> x.toString()).collect(Collectors.toList())));
      setListeners(watchedMovies);
      feedbackBoxProfile.setText("");
    } else {
      watchedMovies.setItems(null);
      showInfo(null, infoBoxProfile);
      feedbackBoxProfile.setText("Nothing to show...");
    }
  }

  /**
   * <p>
   * Module to show information about movies for both browser- and profile-view.
   * </p>
   *
   * <p>
   * The pane which the movie will be displayed has to have this configuration of
   * children:
   * </p>
   * <ol>
   * <li>
   *
   * <pre>
   * AnchorPane
   * </pre>
   * <ol>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Label
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Label
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Label
   * </pre>
   *
   * </li>
   * <li>
   *
   * <pre>
   * Text
   * </pre>
   *
   * </li>
   * </ol>
   * </li>
   * <li>
   *
   * <pre>
   * ImageView
   * </pre>
   *
   * </li>
   * </ol>
   *
   * @param movie The movie to display
   * @param pane  The Pane where the movie should be displayed.
   *              Has to match the criterias for a display-pane.
   */
  private void showInfo(Movie movie, Pane pane) {
    if (movie == null) {
      pane.setVisible(false);
      ratingSlider.setDisable(true);
    } else {
      pane.setVisible(true);
      ratingSlider.setDisable(false);

      ObservableList<Node> children = pane.getChildren();
      ImageView img = (ImageView) children.get(1);
      if (movie.getImageUrl() != null) {
        img.setImage(new Image(movie.getImageUrl()));
      } else {
        img.setImage(null);
      }

      FlowPane box = (FlowPane) children.get(0);
      ObservableList<Node> f = box.getChildren();
      Text title = (Text) f.get(0);
      Text year = (Text) f.get(1);
      Text desc = (Text) f.get(3);

      title.setText(movie.getName());
      year.setText(String.valueOf(movie.getYear()));
      desc.setText(movie.getDescription());
      Text rating = (Text) f.get(4);
      rating.setText(movie.getRating() + "/10 (" + movie.getRatingCount() + ")");

      StringBuilder sb = new StringBuilder();
      if (movie.getDirectors().size() > 0) {
        for (String d : movie.getDirectors()) {
          sb.append(d + ", ");
        }
        sb.deleteCharAt(sb.length() - 2);
      } else {
        sb.append("Unknown");
      }

      // 5th child is a label
      Text director = (Text) f.get(6);
      // 7th child is a label
      director.setText(sb.toString());
      Text actors = (Text) f.get(8);

      sb = new StringBuilder();
      if (movie.getActors().size() > 0) {
        for (String a : movie.getActors()) {
          sb.append(a + ", ");
        }
        sb.deleteCharAt(sb.length() - 2);
      } else {
        sb.append("None");
      }
      actors.setText(sb.toString());

      FlowPane genre = (FlowPane) f.get(2);
      genre.getChildren().clear();
      if (movie.getGenre().size() > 0) {
        for (String g : movie.getGenre()) {
          Label l = new Label(g);
          // TODO: Add onClick event to perform filtering of movies
          // l.setOnMouseClicked(new EventHandler<Event>() {
          // list.filterWatchlistByGenre(g);
          // });

          genre.getChildren().add(l);
          Text t = new Text(" - ");
          genre.getChildren().add(t);
        }
        genre.getChildren().remove(genre.getChildren().size() - 1);
      }
    }
  }

  /**
   * Updates the rating graphics.
   * 
   * @param value The value to indicate with graphics
   */
  private void updateRating(double value) {
    ObservableList<Node> child = ratingStars.getChildren();
    for (int i = 0; i < child.size(); i++) {
      if (child.get(i).getClass().equals(SVGPath.class)) {
        if (i <= value) {
          child.get(i).setStyle("-fx-fill: #ff0;");
        } else {
          child.get(i).setStyle("-fx-fill: #0000;");
        }
      }
    }
  }

  /**
   * Used to add listeners to a listView based on which ListView it is being
   * called upon.
   *
   * @param listView The ListView where the listeners are to be set to each list
   *                 item
   */
  private void setListeners(ListView<String> listView) {
    if (listView.equals(watchedMovies)) {
      listView.getSelectionModel().selectedItemProperty().removeListener(profileChangeListener);
      listView.getSelectionModel().selectedItemProperty().addListener(profileChangeListener);
    } else {
      listView.getSelectionModel().selectedItemProperty().removeListener(browserChangeListener);
      listView.getSelectionModel().selectedItemProperty().addListener(browserChangeListener);
    }
  }

  // ! Help methods for GUI

}
